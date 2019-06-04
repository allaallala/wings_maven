package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventEditForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10499")
public class TC_01_02_Rapid_Response_Events_Search_And_Edit extends BaseWingsTest {


    public void main() {

        RapidResponseEvent event = new RapidResponseEvent();
        EmployerSteps.createRapidResponseEvent(event, Roles.RRADMIN);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.RRADMIN, WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS, Popup.Search);

        logStep("Search for the Event record and open it");
        RapidResponseEventSearchForm searchForm = new RapidResponseEventSearchForm();
        searchForm.performSearchAndOpen(event);

        logStep("Press edit button");
        RapidResponseEventDetailsForm detailsForm = new RapidResponseEventDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        event.generateRandomData();
        RapidResponseEventEditForm editForm = new RapidResponseEventEditForm();
        editForm.fillOutEditForm(event);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(event);
    }
}
