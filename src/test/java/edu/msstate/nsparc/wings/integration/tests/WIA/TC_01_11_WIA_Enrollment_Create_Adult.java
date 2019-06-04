package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10515")
public class TC_01_11_WIA_Enrollment_Create_Adult extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {

    private static final String WIB_EMERGENCY = "National Emergency Grant (NEG)";
    private static final String NEG_TORNADO = "Tornado 2011";
    private static final String WIB_LOCAL = "Local";


    public void main () {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createRegularParticipant(participant, Boolean.TRUE);

        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participants->WIA->WIA Enrollment->Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Select adult participant");
        WIAEnrollmentCreationForm creationForm = new WIAEnrollmentCreationForm();
        creationForm.selectParticipant(participant);

        logStep("Select Participant type = Adult");
        creationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, false);

        info("Checking validation for WINGS-2736");
        creationForm.checkDataValidation();

        logStep("Check that Tornado 2011 shows up in the National Emergency Grant drop down");
        creationForm.selectWIB(WIB_EMERGENCY);
        WIAEnrollmentCreationForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        creationForm.waitCheckNEG(NEG_TORNADO);
        creationForm.selectWIB(WIB_LOCAL);

        creationForm.clickButton(Buttons.Continue);

        logStep("Fill in all required fields->Create");
        creationForm.fillWIAEnrollmentProgramInformation(false);

        logStep("Find new created participant WIA Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm searchForm = new WIAEnrollmentSearchForm();
        searchForm.selectParticipant(participant);
        searchForm.clickButton(Buttons.Search);

        logStep("Validate Enrollment data");
        searchForm.openFirstSearchResult();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();

        CustomAssertion.softTrue("Assert Basic Information Failed",
                wiaEnrollmentDetailsForm.validateBasicInformation(applicationDate, contactPerson, relation));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
