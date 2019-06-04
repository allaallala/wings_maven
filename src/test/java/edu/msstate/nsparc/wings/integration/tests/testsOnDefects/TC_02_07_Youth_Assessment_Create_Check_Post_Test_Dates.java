package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10545")
public class TC_02_07_Youth_Assessment_Create_Check_Post_Test_Dates extends BaseWingsTest {



    private String date = CommonFunctions.getDaysAgoDate(90);
    private String contactPerson = CommonFunctions.getRandomLiteralCode(10);
    private String contactPhone = CommonFunctions.getRandomIntegerNumber(10);
    private String relation = CommonFunctions.getRandomLiteralCode(10);

    //Assessment
    private String dateAdministred = CommonFunctions.getDaysAgoDate(120);
    private String type = "ABLE";
    private String score = CommonFunctions.getRandomIntegerNumber(3);
    private String functionalArea = "Reading/Reading for Information";
    private String creationDate = "";
    private String program = "WIA";

    private static final String ERROR_PRE_POST_TEST_DATES = "Post-Test/Re-Test cannot be before WIA Eligibility Date.";
    private static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(10);


    //Bug WINGS-2421 , sub-task WINGS-2488
    public void main() {

        /*
         * 1. Test date can be more than 45 days ago
         * 2. Test date can not be more that 6 month ago
         * 3. Test date can not be older that WIA Enrollment eligibility date
         * 4. Test date can not be older that pre-test date for the same test type, category, functional area
         */
        /*
         * 1. Create youth participant
         * 2. create WIA E with needed eligible date
         * 3. create pre-test YA with needed date
         * 3. try to create youth assessment
         * 4. create post-test assessment
         */   


        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        WiaEnrollmentSteps.createWIAEnrollment(participant, true, date, contactPerson, contactPhone, relation);
        BaseNavigationSteps.logout();
        Assessment assessment = new Assessment(participant, program, dateAdministred, type, score, functionalArea);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, true, true);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, SERVICE_NAME, Constants.COMPLETED, creationDate);
        Assessment postTestAssessment = new Assessment(participant, program, dateAdministred, type, score, functionalArea);
        postTestAssessment.setPreTest(false);
        postTestAssessment.setDateAdministered(CommonFunctions.getDaysAgoDate(200));
        postTestAssessment.setScore(CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH));



        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Fill out assessment information");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), postTestAssessment);

        logStep("Click [Create] button");
        creationForm.clickButton(Buttons.Create);

        logStep("Check error message that test date can't be more than 6 month ago");
        CustomAssertion.softTrue("Error text, that date cannot be more than 6 month ago is incorrect",
                creationForm.getErrorDataText().contains(ERROR_PRE_POST_TEST_DATES));

        logStep("date can not be older than pre-test date for the same test type, category, functional area");
        creationForm.inputDateAdministered(CommonFunctions.getDaysAgoDate(150));
        creationForm.clickButton(Buttons.Create);

        logStep("Check error messages");
        CustomAssertion.softTrue("Error text, that date can't be older than pre-test",
                creationForm.getErrorDataText().contains(ERROR_PRE_POST_TEST_DATES));

        logStep("date can not be older that WIA Enrollment eligibility date");
        creationForm.inputDateAdministered(CommonFunctions.getDaysAgoDate(100));
        creationForm.clickButton(Buttons.Create);
        CustomAssertion.softTrue("Error text, that date can't be older than WIA Enrollment eligibility date is incorrect",
                creationForm.getErrorDataText().contains(ERROR_PRE_POST_TEST_DATES));

        logStep("Date can be more than 45 days ago");
        creationForm.inputDateAdministered(CommonFunctions.getDaysAgoDate(50));
        creationForm.clickButton(Buttons.Create);

        logStep("Save Changes to participation period");
        creationForm.passParticipationRecalculationPage();

        logStep("Assert that Youth Assessment was created successfully");
        AssessmentDetailsForm youthAssessmentDetailsForm = new AssessmentDetailsForm();
        CustomAssertion.softTrue("Assert date administrated failed!",
                youthAssessmentDetailsForm.getDateAdministered().equals(CommonFunctions.getDaysAgoDate(50)));
    }
}
