package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;

import static org.testng.AssertJUnit.assertFalse;


@TestCase(id = "WINGS-10594")
public class TC_04_07_WIA_Training_Edit_Participant extends BaseWingsTest {


    //Bug WINGS-2584, sub-task WINGS-2621
    public void main() {

        info("Creating WIA Training for first Participant");
        String participantAccount = AccountUtils.getParticipantAccount();
        Participant firstParticipant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(firstParticipant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), firstParticipant, true, false);
        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);
        TrainingSteps.createWIATraining(firstParticipant, trainingProvider);
        info("Creating WIA Enrollment for second Participant");
        AdvancedSqlFunctions.resetAccount(participantAccount);
        Participant secondParticipant = new Participant(participantAccount, true);
        ParticipantCreationSteps.createParticipantDriver(secondParticipant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), secondParticipant, true, false);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);

        logStep("Find WIA Training for first Participant");
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.performSearch(firstParticipant);

        logStep("Open WIA Training edit form");
        wiaTrainingSearchForm.clickFirstParticipant();
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.clickButton(Buttons.Edit);
        WIATrainingEditForm wiaTrainingEditForm = new WIATrainingEditForm();

        logStep("Change Participant in WIA Training");
        wiaTrainingEditForm.removeParticipant();
        WIATrainingEditForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        wiaTrainingEditForm.selectParticipant(secondParticipant);
        wiaTrainingEditForm.clickButton(Buttons.Save);
        wiaTrainingEditForm.passParticipationRecalculationPage();

        logStep("Open first participant participantSSDetails");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(firstParticipant);
        participantSearchForm.openFirstSearchResult();

        logStep("View 'Participation Periods' section and click 'View unresulted services' link");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandParticipationPeriods();
        participantDetailsForm.showUnresultedServices();

        logStep("Search for services");
        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.clickButton(Buttons.Search);

        logStep("Validate, that no services are displayed");
        assertFalse("Service was found", searchForm.checkParticipantName());

        logStep("Change Participant on search form");
        searchForm.clickButton(Buttons.Clear);
        ParticipantEnrollmentSearchForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        searchForm.selectResult("Unresulted");
        searchForm.selectParticipant(secondParticipant);

        logStep("Perform search");
        searchForm.clickButton(Buttons.Search);

        logStep("Validate, that service was found");
        Assert.assertEquals(searchForm.getParticipantText(), secondParticipant.getNameForSearchPages());
    }
}
