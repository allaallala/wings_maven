package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import framework.CommonFunctions;
import framework.Logger;

import static org.testng.Assert.assertEquals;

public class TrainingSteps extends BaseNavigationSteps {

    public static void createTraining(TrainingProvider trainingProvider, Roles role) {
        AdvancedSqlFunctions.resetTrainingProviderCode(trainingProvider.getCode());
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);
        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.fillAllFields(trainingProvider);
        trainingProviderCreationForm.clickButton(Buttons.Create);
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        if (trainingProvider.isTradeProvider()) {
            detailsPage.approveForTrade();
        }
        trainingProviderCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static String createWIATraining(Participant participant, TrainingProvider trainingProvider) {
        String trainingType = "Other Occupational Skills Training";
        String dayTraining = CommonFunctions.getCurrentDate();
        String dayCompletion = CommonFunctions.getTomorrowDate();
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectParticipant(participant);
        wiaTrainingCreateForm.selectFirstEnrollment();
        String course = wiaTrainingCreateForm.selectProjectCode(trainingProvider.getName());
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        wiaTrainingCreateForm.clickButton(Buttons.Done);
        logout();
        return course;
    }

    public static void editWIATraining(Participant participant, String trainingResult, String lastDayTraining) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.performSearch(participant);
        wiaTrainingSearchForm.clickFirstParticipant();
        wiaTrainingSearchForm.clickButton(Buttons.Edit);
        WIATrainingEditForm wiaTrainingEditForm = new WIATrainingEditForm();
        wiaTrainingEditForm.editWIATrainingDetails(trainingResult, lastDayTraining);
        wiaTrainingEditForm.waitPleaseBeSure();
        wiaTrainingSearchForm.clickButton(Buttons.Save);
        logout();
    }

    /**
     *  method for creation of WIA Training With Populated Participant
     * @param trainingProvider for getting training provider info
     * @param trainingType training type
     * @param dayTraining day of training
     * @param dayCompletion day of completion
     */
    public static void createWIATrainingWithPopulatedParticipant(TrainingProvider trainingProvider, String trainingType, String dayTraining, String dayCompletion) {
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Create);
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectFirstEnrollment();
        wiaTrainingCreateForm.selectProjectCode(trainingProvider.getName());
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
    }

    public static void createWIATraining(Participant participant, TrainingProvider trainingProvider,
                                         String trainingType, String dayTraining, String dayCompletion) {
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Create);
        WIATrainingCreateForm createForm = new WIATrainingCreateForm();
        createForm.selectParticipant(participant);
        createForm.selectFirstEnrollment();
        createForm.selectProjectCode(trainingProvider.getName());
        createForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        createForm.clickButton(Buttons.Create);
        createForm.passParticipationRecalculationPage();
        createForm.clickButton(Buttons.Done);
    }

    public static void createWIATraining(Participant participant, String trainingType, String dayTraining, String dayCompletion) {
        String projectCode = "";
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectParticipant(participant);
        wiaTrainingCreateForm.selectFirstEnrollment();
        wiaTrainingCreateForm.selectProjectCode(projectCode);
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        logout();
    }

    /**
     * This method is used for create WIA Training with all needed prerequisite
     * @param participant Unregistered participant
     */
    public static void createWiaTrainingWithUnregisteredParticipant(Participant participant) {
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);
        TrainingProvider trainingProvider = new TrainingProvider();
        createTraining(trainingProvider, Roles.PCADMIN);
        createWIATraining(participant, trainingProvider);
    }

    /**
     * This method is used for create WIA Training needed to Program Outcomes
     * @param participant Registered Participant
     * @param days How many days ago WIA Enrollment was exited
     */
    public static void createOldWIATraining(Participant participant, int days) {
        String trainingType = "Other Occupational Skills Training";
        String dayTraining = CommonFunctions.getDaysAgoDate(days + 1);
        String dayCompletion = CommonFunctions.getDaysAgoDate(days);
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectParticipant(participant);
        wiaTrainingCreateForm.selectFirstEnrollment();
        wiaTrainingCreateForm.selectProjectCode(Constants.EMPTY);
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        wiaTrainingCreateForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createTradeTraining(TradeTraining tradeTraining, Roles roleTrain, Roles enroll) {
        createTraining(tradeTraining.getTrainingProvider(), Roles.TRADEDIRECTOR);
        TradeEnrollmentSteps.createTradeEnrollment(tradeTraining.getTradeEnrollment(), enroll);
        BaseWingsSteps.openCreationSearchForm(roleTrain, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);
        TradeTrainingCreateForm creationForm = new TradeTrainingCreateForm();
        creationForm.fillOutCreationForm(tradeTraining);
        creationForm.clickButton(Buttons.Create);
        TradeTrainingDetailsForm detailsForm = new TradeTrainingDetailsForm();
        detailsForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createTrainingWaiver(TrainingWaiver waiver) {
        createTradeTrainingWithoutLoggingOut(waiver);
        logout();
    }

    public static void createTrainingWaiverWithRenewal(TrainingWaiver waiver) {
        createTradeTrainingWithoutLoggingOut(waiver);
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();
        detailsForm.addRenewal(waiver);
        logout();
    }

    public static void createTrainingWaiverWithRenewalRevocation(TrainingWaiver waiver) {
        createTradeTrainingWithoutLoggingOut(waiver);
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();
        detailsForm.addRenewal(waiver);
        detailsForm.expandWaiverRevocationSection();
        detailsForm.addRevocation(waiver);
        logout();
    }

    public static void createTrainingWaiverWithRevocation(TrainingWaiver waiver) {
        createTradeTrainingWithoutLoggingOut(waiver);
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRevocationSection();
        detailsForm.addRevocation(waiver);
        logout();
    }

    private static void createTradeTrainingWithoutLoggingOut(TrainingWaiver trainingWaiver) {
        TradeEnrollmentSteps.createTradeEnrollment(trainingWaiver.getTradeEnrollment(), Roles.ADMIN);
        BaseNavigationSteps.loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS);
        BaseWingsSteps.popClick(Popup.Create);
        TrainingWaiverCreationForm creationForm = new TrainingWaiverCreationForm();
        creationForm.fillOutCreationForm(trainingWaiver);
        creationForm.completeCreation();
    }

    public static void validateLocationCodes(TrainingProvider provider) {
        TrainingProviderDetailsForm trainingProviderDetailsForm = new TrainingProviderDetailsForm();
        String[] locationCodes = trainingProviderDetailsForm.getLocationCodes();
        String[] dataBaseCodes = AdvancedSqlFunctions.getLocationCodesOrderedByASC(provider.getName());
        for (int i = 0; i < dataBaseCodes.length; i++) {
            if (dataBaseCodes[i] == null) {
                Logger.getInstance().info("Codes were not found in DB!");
            } else {
                Logger.getInstance().info("APP: " + locationCodes[i] + "  DB: " + dataBaseCodes[i]);
                assertEquals("Location codes are not the same in DB and application!", dataBaseCodes[i], locationCodes[i]);
            }
        }
    }

    /**
     * Create training and open search page.
     * @param role role of the user
     * @return new TradeTraining
     */
    public static TradeTraining createTrainingAndOpenSearchPage(Roles role) {
        Logger.getInstance().info("Precondition: Create some Trade Trainings");
        TradeTraining training = new TradeTraining();
        TrainingSteps.createTradeTraining(training, role, Roles.ADMIN);

        Logger.getInstance().info("Log in as admin and open trade training search form");
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        return training;
    }
}
