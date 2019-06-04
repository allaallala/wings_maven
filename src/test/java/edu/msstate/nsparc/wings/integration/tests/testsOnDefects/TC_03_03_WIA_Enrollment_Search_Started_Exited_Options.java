package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_04_22_WIA_Training_Edit;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_07_09_Training_Provider_Create;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10563")
public class TC_03_03_WIA_Enrollment_Search_Started_Exited_Options extends BaseWingsTest {

    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";
    private String applicationDate = CommonFunctions.getDaysAgoDate(365);
    private String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    private String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    private String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);

    private String trainingType = "Other Occupational Skills Training";
    private String dayTraining = CommonFunctions.getCurrentDate();
    private String dayCompletion = CommonFunctions.getCurrentDate();
    private String wiaYouth = "Youth";


    //sub-task WINGS-2553
    public void main() {

        /*
         * 0. Create participant
         * 1. Create WIA E that is not started yet
         * 2. Create WIA E and start it
         * 3. Create WIA E , start and finish it
         * 4. Check filters: start - yes , exit - yes ; start - yes , exit - no ,
         *                      start - no ,  exit - yes ; start - no  , exit - yes
         */


        logStep("Create participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(),true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Create training provider");
        TC_07_09_Training_Provider_Create trainingProviderCreate = new TC_07_09_Training_Provider_Create();
        TrainingProvider trainingProvider = trainingProviderCreate.createTrainingProvider(true);
        BaseNavigationSteps.logout();

        logStep("Create WIA Enrollments with old dates");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Create);
        WIAEnrollmentCreationForm wIAEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wIAEnrollmentCreationForm.selectParticipant(participant);
        wIAEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, true);
        wIAEnrollmentCreationForm.clickButton(Buttons.Continue);

        wIAEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(true);
        wIAEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, true);

        WIAEnrollmentDetailsForm detailsForm = new WIAEnrollmentDetailsForm();
        detailsForm.validateBasicInformation(applicationDate, contactPerson, relation);

        logStep("Create WIA Training with old dates");
        TrainingSteps.createWIATrainingWithPopulatedParticipant(trainingProvider, trainingType, dayTraining, dayCompletion);

        logStep("Result WIA Training as completed");
        TC_04_22_WIA_Training_Edit wiaTrainingEdit = new TC_04_22_WIA_Training_Edit();
        wiaTrainingEdit.editWIATraining(Constants.COMPLETED, dayCompletion);
        BaseNavigationSteps.logout();

        logStep("Create new WIA Enrollment (youth)");
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Create new WIA Enrollment (dislocated)");
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, true);

        logStep("Start one of WIA Enrollments: create training enrollment");
        TrainingSteps.createWIATraining(participant, trainingProvider);


        logStep("Open WIA Enrollment search form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Search);

        logStep("Select participant");
        WIAEnrollmentSearchForm searchForm = new WIAEnrollmentSearchForm();
        searchForm.selectParticipant(participant);

        logStep("Select filters: Started - Yes, Exited - No");
        searchForm.clickWiaStarted(true);
        searchForm.clickWiaExited(false);
        searchForm.clickButton(Buttons.Search);

        logStep("Check results for filters: Started - Yes, Exited - No ");
        CustomAssertion.softAssertContains(searchForm.getParticipantTextPage(), participant.getFirstName(), "Participant text on the page does not contain first name of the participant");

        logStep("Select filters: Started - Yes, Exited - Yes");
        searchForm.clickWiaStarted(true);
        searchForm.clickWiaExited(true);
        searchForm.clickButton(Buttons.Search);

        logStep("Check results for filters: Started - Yes, Exited - Yes ");
        CustomAssertion.softAssertContains(searchForm.getParticipantTypeExited(), wiaYouth, "Assert exited WIA Enrollment search failed!");

        logStep("Select filters: Started - No, Exited - Yes");
        searchForm.clickWiaStarted(false);
        searchForm.clickWiaExited(true);
        searchForm.clickButton(Buttons.Search);

        logStep("Check results for filters: Started - No, Exited - Yes ");
        assertTrue("Assert exited WIA Enrollment search failed!", new SearchResultsForm().isNothingResult());

        logStep("Select filters: Started - No, Exited - No");
        searchForm.clickWiaStarted(false);
        searchForm.clickWiaExited(false);
        searchForm.clickButton(Buttons.Search);

        logStep("Check results for filters: Started - No, Exited - No ");
        CustomAssertion.softAssertContains(searchForm.getParticipantTextPage(), participant.getFirstName(),
                "Participant text on the page does not contain first name of the participant");
    }
}
