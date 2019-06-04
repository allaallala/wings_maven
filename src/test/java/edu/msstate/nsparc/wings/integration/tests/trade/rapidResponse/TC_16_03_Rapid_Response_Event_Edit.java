package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

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


@TestCase(id = "WINGS-10877")
public class TC_16_03_Rapid_Response_Event_Edit extends BaseWingsTest {

    public void main() {
        logStep("Preconditions: making new Rapid Response Event");
        RapidResponseEvent event = new RapidResponseEvent();
        EmployerSteps.createRapidResponseEvent(event, Roles.RRADMIN);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.RRADMIN, WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS, Popup.Search);

        logStep("Search for Rapid Response Event from the precondition");
        RapidResponseEventSearchForm rresf = new RapidResponseEventSearchForm();
        rresf.performSearch(event);

        logStep("Click the 'Event Number' of the Rapid Response Event shown in the Search Results");
        rresf.openFirstSearchResult();

        logStep("Click the [Edit] button");
        RapidResponseEventDetailsForm detForm = new RapidResponseEventDetailsForm();
        detForm.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        RapidResponseEventEditForm editForm = new RapidResponseEventEditForm();
        editForm.fillOutEditForm(new RapidResponseEvent());


        logStep(" Click the [Cancel] button");
        editForm.clickButton(Buttons.Cancel);
        editForm.areYouSure(Popup.Yes);

        logResult("The Rapid Response Event Detail Screen is shown, the changes are not saved");
        new RapidResponseEventDetailsForm();
        detForm.validateInformation(event);
    }

}
