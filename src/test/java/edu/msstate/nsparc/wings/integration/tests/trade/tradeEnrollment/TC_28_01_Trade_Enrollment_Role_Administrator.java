package edu.msstate.nsparc.wings.integration.tests.trade.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * In this test, we are checking trade enrollment functionality for an administrator.
 * Created by a.vnuchko on 23.08.2016.
 */

@TestCase(id = "WINGS-11121")
public class TC_28_01_Trade_Enrollment_Role_Administrator extends BaseWingsTest {
    TradeTraining trtr;
    ExpenditureEncumbrance expenditureEncumbrance;

    public void main() {
        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonTradeEnrollmentSteps(user);
    }

    /**
     * Check trade enrollment for different user roles
     *
     * @param user - current user
     */
    protected void commonTradeEnrollmentSteps(User user) {
        //(!) Create new trade enrollment
        if (user.getTradeEnrollment().getTeCreate()) {
            logStep("Create new trade enrollment");
            AccountUtils.init();
            trtr = new TradeTraining(user.getRole());
            expenditureEncumbrance = new ExpenditureEncumbrance();
            TrainingSteps.createTradeTraining(trtr, Roles.ADMIN, user.getRole());
        }

        BaseWingsSteps.logInAs(user.getRole());

        //(!) View trade enrollment
        if (user.getTradeEnrollment().getTeView()) {
            logStep("View trade enrollment");

            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);

            //If user has permissions to create trade enrollment, it's required confirm pop-up.
            if (user.getTradeEnrollment().getTeCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            TradeEnrollmentSearchForm searchForm = new TradeEnrollmentSearchForm();
            searchForm.performSearchAndOpen(trtr.getTradeEnrollment());

            TradeEnrollmentDetailsForm detailsPage = new TradeEnrollmentDetailsForm();
            detailsPage.validateInformation(trtr.getTradeEnrollment());
            detailsPage.validateEligibility(trtr.getTradeEnrollment());

            //(!) Edit trade enrollment
            if (user.getTradeEnrollment().getTeEdit()) {
                logStep("Edit trade enrollment (if possible)");
                detailsPage.clickButton(Buttons.Edit);
                trtr.getTradeEnrollment().setEligible(Constants.FALSE);
                TradeEnrollmentEditForm editPage = new TradeEnrollmentEditForm();
                editPage.changeEligibilityStatus(trtr.getTradeEnrollment());
            }

            detailsPage.checkTrdEnrlButtons(user);

            //(!) Check, that changes are saved
            detailsPage.validateInformation(trtr.getTradeEnrollment());
            detailsPage.validateEligibility(trtr.getTradeEnrollment());

            //(!) Check Forms/Print/Sign
            logStep("Check Forms/Print/Sign");
            detailsPage.checkFormsPrintSign(user, trtr.getTradeEnrollment());

            logStep("Check employment at separation");
            detailsPage.validateEmploymentSeparation(user, trtr.getTradeEnrollment());

            logStep("Edit employment at separation (if possible)");
            detailsPage.editEmploymentSeparation(user, trtr.getTradeEnrollment()); //TODO https://jira.nsparc.msstate.edu/browse/WINGS-9114

            logStep("Add expenditure/encumbrance (if possible)");
            detailsPage.addExpenditureEncumbrance(user, expenditureEncumbrance);

            logStep("Edit expenditure/encumbrance (if possible)");
            expenditureEncumbrance = new ExpenditureEncumbrance();
            detailsPage.editExpenditureEncumbrance(user, expenditureEncumbrance);


        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
