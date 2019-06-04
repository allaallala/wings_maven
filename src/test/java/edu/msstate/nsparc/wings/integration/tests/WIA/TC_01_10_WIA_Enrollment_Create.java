package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
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
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10510")
public class TC_01_10_WIA_Enrollment_Create extends BaseWingsTest {

    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";

    protected String applicationDate = CommonFunctions.getYesterdayDate();
    protected String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    protected String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    protected String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);

    protected String familyMember = "1" + CommonFunctions.getRandomIntegerNumber(2);
    protected String wages = CommonFunctions.getRandomIntegerNumber(2);
    protected String familyIncome = CommonFunctions.getRandomIntegerNumber(2);


    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        createWIAEnrollment(participant,true);
    }

    /**
     * Create WIA enrollment
     * @return participant
     */
    public Participant createWIAE () {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        createWIAEnrollment(participant, true);
        return participant;
    }

    /**
     * Create WIA enrollment with youth participant
     * @param participant - participant
     * @param youth - is participant youth
     */
    public void createWIAEnrollment(Participant participant, boolean youth) {

        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        logStep("Login as admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participant->WIOA Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Create);
        logStep("Fill out WIA enrollment basic information");
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipant(participant);
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, youth);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        logStep("Fill out WIA enrollment program information");
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(youth);

        logStep("Fill out WIA enrollment youth information");
        wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, youth);
        logStep("Check required data");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        CustomAssertion.softTrue("Incorrect application date", applicationDate.equals(wiaEnrollmentDetailsForm.getApplicationDate()));
        CustomAssertion.softAssertEquals(wiaEnrollmentDetailsForm.getContactPersonText(), contactPerson, "Incorrect contact person");
    }
}
