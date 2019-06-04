package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
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
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10568")
public class TC_03_08_WIA_Enrollment_Create_Youth_21_Years extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {

    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";
    private static final String LOW_INCOME_MESSAGE = "This participant is low income";
    private static final String BARRIER_MESSAGE = "The participant must meet at least one youth barrier to be eligible";


    //sub-task WINGS-2614
    //sub-task WINGS-2907
    //bug WINGS-10129
    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        participant.getParticipantBioInfo().setDateOfBirth(CommonFunctions.getDaysAndYearsAgoDate(Constants.DAYS_HALF_YEAR, Constants.YEARS_ADULT));
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Create);

        logStep("Select youth participant");
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipant(participant);

        logStep("Fill in all required fields on the 'Basic Information' and 'Program Information' pages");
        info("Check, that the 'Offender' radio button have became required");
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, true);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(true);

        logStep("Answer 'No' to all questions on the Youth Information page");
        wiaEnrollmentCreationForm.clickNoAllQuestionsYouthPage();
        wiaEnrollmentCreationForm.selectEducationStatus(EDUCATIONAL_STATUS);
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        wiaEnrollmentCreationForm.selectBasicSkillsDeficientNo(TOOL_USED);

        logStep("Click on 'Continue' and make sure an error is thrown");
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        CustomAssertion.softTrue("Error wasn't thrown", wiaEnrollmentCreationForm.getErrorText().contains(BARRIER_MESSAGE));

        logStep("Fill 'Youth Information' page with correct data ->Create");
        wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, true);

        logStep("Make sure the enrollment is indicated as low income");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        CustomAssertion.softTrue("Enrollment isn't indicated as low income",
                wiaEnrollmentDetailsForm.getMessagesText().contains(LOW_INCOME_MESSAGE));

        logStep("Validate Enrollment data");
        CustomAssertion.softTrue("Assert Basic Information Failed", wiaEnrollmentDetailsForm.validateBasicInformation(applicationDate, contactPerson, relation));
        CustomAssertion.softAssertEquals(wiaEnrollmentDetailsForm.getParticipantTypeText(), "Youth","Assert Participant Type Failed");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
