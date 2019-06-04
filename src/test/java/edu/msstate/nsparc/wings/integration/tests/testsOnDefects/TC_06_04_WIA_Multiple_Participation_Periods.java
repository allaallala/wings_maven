package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10650")
public class TC_06_04_WIA_Multiple_Participation_Periods extends BaseWingsTest {

    String date = "01/01/2008";
    private String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    private String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    private String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);

    private String trainingType = "Other Occupational Skills Training";
    private String dayTraining = "01/11/2008";
    private String dayCompletion = "01/14/2008";

    private static final String ERROR_MESSAGE = "This data may not be accepted because it results in one "
            + "or more WIA Enrollments being split across multiple participation periods. "
            + "Please contact an administrator, and provide details on the record you are attempting to create or modify, "
            + "including the Participant and WIA Enrollment selected.";


    //Bug WINGS-2352, sub-task WINGS-2536
    public void main() {

        // 1. Create participant
        // 2. Create WIA Enrollment with application and eligible date in 2008
        // 3. Create WIA Training with Begin Date in 2008 (new participation period was created)
        // 4. Create one more WIA Training in 2010 (new participation period was created)
        // 5. Try to result first created in 2008 WIA Training with result date in 2008 (so gap between services is more than 90 days)


        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        // steps 1-2
        WiaEnrollmentSteps.createWIAEnrollment(participant, false, date, contactPerson, contactPhone, relation);
        BaseNavigationSteps.logout();
        TrainingProvider trainingProvider = new TrainingProvider();
        // step 3
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);
        BaseNavigationSteps.loginAdminDashboard();
        TrainingSteps.createWIATraining(participant, trainingProvider, trainingType, dayTraining, dayCompletion);
        // step 4
        BaseNavigationSteps.logout();
        TrainingSteps.createOldWIATraining(participant, Constants.DAYS_OUTCOME_QUARTER);

        // step 5
        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);
        WIATrainingSearchForm searchForm = new WIATrainingSearchForm();

        logStep("Search for first Training and open it");
        searchForm.selectParticipant(participant);
        searchForm.inputFirstDayTraining(dayTraining);
        searchForm.clickButton(Buttons.Search);

        logStep("Open Training participantSSDetails");
        searchForm.clickFirstParticipant();

        logStep("Click on Edit button");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit WIA Training");
        WIATrainingEditForm editForm = new WIATrainingEditForm();
        editForm.selectResultTraining(Constants.COMPLETED);
        editForm.inputLastDayOfTraining(dayCompletion);

        logStep("Click on 'Save Changes' button");
        editForm.clickButton(Buttons.Save);

        logStep("Click on 'Save Changes' on Participation Periods page");
        editForm.passParticipationRecalculationPage();

        logStep("Check that error message is displayed");
        CustomAssertion.softAssertContains(ERROR_MESSAGE, editForm.getTrainingErrorText(), "Incorrect error message");
        CustomAssertion.softTrue("WIA Training was saved", editForm.isPresent(BaseWingsForm.BaseButton.SAVE_CHANGES));

        logEnd();
    }
}
