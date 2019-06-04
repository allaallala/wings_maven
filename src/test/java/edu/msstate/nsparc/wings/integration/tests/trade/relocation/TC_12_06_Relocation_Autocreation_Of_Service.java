package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10768")
public class TC_12_06_Relocation_Autocreation_Of_Service extends BaseWingsTest {

    private static final String SERVICE = "Process Job Relocation Allowance Application";

    public void main() {

        Relocation relocation = new Relocation();
        TradeEnrollmentSteps.createTradeEnrollment(relocation.getTradeEnrollment(), Roles.ADMIN);

        logStep("Log in as Trade Director and open Relocation creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Create);

        logStep("Fill out the creation form");
        RelocationCreateForm creationForm = new RelocationCreateForm();
        creationForm.fillOutCreationForm(relocation);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Navigate to: Participants -> Participant Service Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for the participant with created Relocation");
        ParticipantEnrollmentSearchForm participantEnrollmentSearchForm = new ParticipantEnrollmentSearchForm();
        participantEnrollmentSearchForm.performSearch(relocation.getTradeEnrollment().getParticipant(), SERVICE);

        logStep("Open found item");
        participantEnrollmentSearchForm.openFirstSearchResult();

        logStep("Check that 'Process Job Relocation Allowance Application' service enrollment is created");
        ParticipantEnrollmentDetailsForm enrollmentDetailsForm = new ParticipantEnrollmentDetailsForm();
        assertTrue("Was created invalid service enrollment!", enrollmentDetailsForm.getServiceName().contains(SERVICE));
    }
}
