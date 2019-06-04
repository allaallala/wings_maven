package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check WIA Training functionality using different roles - administrator, area director, manager
 * Created by a.vnuchko on 02.03.2016.
 */

@TestCase(id = "WINGS-11072")
public class TC_24_13_WIA_Training_Roles_Admin_AD_Manager extends TC_24_11_WIA_Enrollment_Roles_Admin_AD_Manager {
    private String otherSkills = "Other Occupational Skills Training";

    String trainingType = otherSkills;
    String firstDayTraining = CommonFunctions.getCurrentDate();
    String dayCompletion = CommonFunctions.getTomorrowDate();
    String date = CommonFunctions.getYesterdayDate();
    String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    int daysCounter = 1;
    private Participant partip;

    public void main() {
        //Role administrator
        User user = new User(Roles.ADMIN);
        commonWiaTrainingSteps(user);

        //Role area director
        user = new User(Roles.AREADIRECTOR);
        commonWiaTrainingSteps(user);

        //Role manager
        user = new User(Roles.MANAGER);
        commonWiaTrainingSteps(user);
    }

    /**
     * Common steps for checking user permission in WIA Training module
     *
     * @param user - current user.
     */
    protected void commonWiaTrainingSteps(User user) {
        //(!) Check WIA Training creation
        if (user.getWiaTrain().getWiaTrainingCreate()) {
            logStep("Create new WIA Training enrollment");
            AccountUtils.init();
            partip = new Participant();
            ParticipantCreationSteps.createParticipantDriver(partip, Boolean.TRUE, Boolean.FALSE);
            User admin = new User(Roles.ADMIN);
            WiaEnrollmentSteps.createWIAEnrollmentForCreatedParticipant(partip, false, date, contactPerson, contactPhone, relation, admin);
            BaseNavigationSteps.logout();
            createWIATraining(user, partip);

            //(!) Check [Create Another] button.
            if (user.getWiaTrain().getWiaTrainingViewCreateAnother()) {
                logStep("Create another WIA Training");
                setTrainingDetails(); //sets new data to create new WIA training
                WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
                wiaTrainingDetailsForm.createAnotherWiaTraining(partip, trainingType, firstDayTraining, dayCompletion);
                setDetailsToDefault(); //sets to default value to use it for editing.
            }
            BaseNavigationSteps.logout();
        }

        checkOtherFunctionalities(user);

    }

    /**
     * Check view, audit, delete and edit functionality
     *
     * @param user - current user.
     */
    private void checkOtherFunctionalities(User user) {
        divideMessage("Check other functionality");
        BaseWingsSteps.logInAs(user.getRole());


        //(!) If user can view search results.
        if (user.getWiaTrain().getWiaTrainingView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);

            //(!) If user can create WIA training -> he should confirm pop-up window.
            if (user.getWiaTrain().getWiaTrainingCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            WIATrainingSearchForm searchPage = new WIATrainingSearchForm();
            searchPage.performSearch(partip);
            searchPage.openFirstSearchResult();

            //(!) Check buttons on the detailed WIA training page.
            logStep("Check buttons present or not on the WIA training participantSSDetails form");
            WIATrainingDetailsForm detailsPage = new WIATrainingDetailsForm();
            detailsPage.checkButtonsAuditDelete(user);

            //(!) If possible to edit WIA training
            if (user.getWiaTrain().getWiaTrainingEdit()) {
                logStep("Edit WIA training");
                setTrainingDetails();
                detailsPage.editWiaTraining(trainingType, firstDayTraining, dayCompletion);
                setDetailsToDefault();
            }
        }
        //(!) If user cannot view detailed information about WIA training
        else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Create WIA training
     *
     * @param user        - user role
     * @param participant - participant
     */
    protected void createWIATraining(User user, Participant participant) {
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);

        logStep("Fill out WIA training creation form");
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectProjectCode("");
        wiaTrainingCreateForm.selectParticipantByUser(user, participant);
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, firstDayTraining, dayCompletion);

        logStep("Click [Create] button and confirm it");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();

        logStep("Check WIA training participantSSDetails");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.validateWIATrainingDetails(partip, trainingType, firstDayTraining, dayCompletion);

    }

    /**
     * Set new data for training participantSSDetails.
     */
    private void setTrainingDetails() {
        this.trainingType = otherSkills;
        this.firstDayTraining = CommonFunctions.getDaysAgoDate(daysCounter);
        this.dayCompletion = CommonFunctions.getFutureDate(daysCounter + 1);
    }

    /**
     * Sets training participantSSDetails values to default to minimize input test data.
     */
    private void setDetailsToDefault() {
        this.trainingType = otherSkills;
        this.firstDayTraining = CommonFunctions.getCurrentDate();
        this.dayCompletion = CommonFunctions.getTomorrowDate();
    }
}
