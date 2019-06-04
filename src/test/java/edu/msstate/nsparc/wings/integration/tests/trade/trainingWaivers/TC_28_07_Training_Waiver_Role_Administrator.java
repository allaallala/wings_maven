package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check different functionality of the training waiver (Role - administrator)
 * Created by a.vnuchko on 25.08.2016.
 */

@TestCase(id = "WINGS-11127")
public class TC_28_07_Training_Waiver_Role_Administrator extends BaseWingsTest {
    TrainingWaiver waiver;
    private String newReasonTW = "Health - Training not Possible/Able to Work";
    private String newReasonRenewal = "Training Will Start in 60 Days";
    private String newReasonRevoc = "Is Employed";
    private String renewalDate = CommonFunctions.getDaysAgoDate(20);
    private String expirationDate;

    public void main() {
        User user = new User(Roles.ADMIN);
        commonTrainingWaiverSteps(user);
    }

    /**
     * Common steps for checking training waiver (create, edit, view, etc.)
     *
     * @param user - current user
     */
    protected void commonTrainingWaiverSteps(User user) {
        if (user.getTrainingWaiver().getTwCreate()) {
            logStep("Create new training waiver");
            AccountUtils.init();
            waiver = new TrainingWaiver();
            waiver.initializeExpired();
            expirationDate = CommonFunctions.getDaysNextDate(waiver.getIssueDate(), 90);
            TrainingSteps.createTrainingWaiver(waiver);
        }

        checkViewEditFunctionality(user);
    }

    /**
     * Check view, edit functionality of the training waiver
     *
     * @param user - current user
     */
    private void checkViewEditFunctionality(User user) {
        BaseWingsSteps.logInAs(user.getRole());

        if (user.getTrainingWaiver().getTwView()) {
            logStep("Check view functionality");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS);

            //(!) If user can create training waiver, he should confirm pop-up.
            if (user.getTrainingWaiver().getTwCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            TrainingWaiverSearchForm searchPage = new TrainingWaiverSearchForm();
            searchPage.selectParticipantByUser(user, waiver.getTradeEnrollment().getParticipant());
            searchPage.selectInactivePetition(waiver.getTradeEnrollment().getPetition());
            searchPage.clickButton(Buttons.Search);
            searchPage.openFirstSearchResult();


            TrainingWaiverDetailsForm detailsPage = new TrainingWaiverDetailsForm();
            detailsPage.validateFullInformation(waiver, expirationDate);

            //Check buttons
            detailsPage.checkWaiverButtons(user);

            //(!) Check edit training waiver
            if (user.getTrainingWaiver().getTwEdit()) {
                logStep("Edit training waiver");
                waiver.setWaiverReason(newReasonTW);
                detailsPage.editWaiver();
                TrainingWaiverEditForm editPage = new TrainingWaiverEditForm();
                editPage.editWaiverReason(newReasonTW);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateFullInformation(waiver, expirationDate);
            }

            //(!) Check adding renewal to the training waiver
            if (user.getTrainingWaiver().getTwAddRenewal()) {
                logStep("Add renewal");
                waiver.getRenewal().setRenewalDate(renewalDate);
                detailsPage.expandWaiverRenewalsSection();
                detailsPage.addRenewal(waiver);
                detailsPage.validateRenewalsBlock(waiver);
                //before checking issue date, it's necessary to change expired date of the training waiver.
                expirationDate = CommonFunctions.getDaysNextDate(waiver.getRenewal().getRenewalDate(), 30);
                detailsPage.validateFullInformation(waiver, expirationDate);
            }

            //(!) Check edit renewal of the TW
            if (user.getTrainingWaiver().getTwEditRenewal()) {
                logStep("Edit renewal");
                waiver.getRenewal().setRenewalReason(newReasonRenewal);
                detailsPage.expandWaiverRenewalsSection();
                detailsPage.editRenewal(waiver);
                detailsPage.validateRenewalsBlock(waiver);
                detailsPage.validateFullInformation(waiver, expirationDate);
            }

            //(!) Check adding revocation to the TW
            if (user.getTrainingWaiver().getTwAddRevocation()) {
                logStep("Add revocation");
                detailsPage.expandWaiverRevocationSection();
                detailsPage.addRevocation(waiver);
                detailsPage.validateRevocationsBlock(waiver);
            }

            //(!) Check edit revocation of the TW
            if (user.getTrainingWaiver().getTwEditRevocation()) {
                logStep("Edit revocation");
                waiver.getRevocation().setRevocationReason(newReasonRevoc);
                detailsPage.expandWaiverRevocationSection();
                detailsPage.editRevocation(waiver);
                detailsPage.validateRevocationsBlock(waiver);
                detailsPage.validateFullInformation(waiver, expirationDate);
            }

            //(!) Check forms, print, sign.
            if (user.getTrainingWaiver().getTwPrintSign()) {
                logStep("Print, data sign"); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-8579
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Constants.ZERO);
            WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS.getMenuItem().assertIsNotPresent();
        }
        BaseNavigationSteps.logout();
    }
}
