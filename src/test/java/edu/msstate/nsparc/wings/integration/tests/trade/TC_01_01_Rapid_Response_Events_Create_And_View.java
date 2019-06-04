package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventCreationForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventDetailsForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10444")
public class TC_01_01_Rapid_Response_Events_Create_And_View extends BaseWingsTest {


    public void main() {


        info("Creating Employer for RRE");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        RapidResponseEvent event = new RapidResponseEvent(employer);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.RRADMIN, WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS, Popup.Create);

        logStep("Fill out the creation form");
        RapidResponseEventCreationForm creationForm = new RapidResponseEventCreationForm();
        creationForm.fillOutCreationForm(event);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        RapidResponseEventDetailsForm detailsForm = new RapidResponseEventDetailsForm();
        detailsForm.validateInformation(event);
    }
}
