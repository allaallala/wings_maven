package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_01_12_WIA_Enrollment_Create_Dislocated_Worker;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10548")
public class TC_02_10_WIA_Enrollment_Create_Several_For_One_Participant_Type_And_WIB extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {

    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";
    private static final String YOUTH = "Youth";
    private static final String LOCAL = "Local";


    //sub-task WINGS-2532
    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        createWIAEnrollment(participant, YOUTH, LOCAL, true);
        createWIAEnrollment(participant, YOUTH, "Statewide Activities", true);
        createWIAEnrollment(participant, YOUTH, LOCAL, true);
        createWIAEnrollment(participant, "Adult", LOCAL, false);
    }

    /**
     * Create WIA enrollment
     *
     * @param participant     - participant
     * @param participantType - participant type
     * @param wIB             - WIB
     * @param youth           - is participant youth
     */
    public void createWIAEnrollment(Participant participant, String participantType, String wIB, boolean youth) {

        logStep("Login as admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participant->WIA enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Create);
        logStep("Fill out enrollment basic information");
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipant(participant);
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, participantType, wIB);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        logStep("Fill out wia enrollment program information");
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(youth);

        logStep("Fill out WIA enrollment youth information");
        wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, youth);
        logStep("Check required data");
        WIAEnrollmentDetailsForm wIAEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        CustomAssertion.softTrue("Incorrect application date", applicationDate.equals(wIAEnrollmentDetailsForm.getApplicationDate()));
        CustomAssertion.softAssertEquals(wIAEnrollmentDetailsForm.getContactPersonText(), contactPerson, "Incorrect contact person text");
        CustomAssertion.softTrue("Incorrect relationship participant", relation.equals(wIAEnrollmentDetailsForm.getRelationshipParticipantText()));
        BaseNavigationSteps.logout();
    }

}
