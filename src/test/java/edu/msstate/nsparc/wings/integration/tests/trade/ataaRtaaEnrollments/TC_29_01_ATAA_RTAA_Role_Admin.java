package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check different functionality of the ATAA/RTAA enrollment, role - Administrator.
 * Created by a.vnuchko on 29.08.2016.
 */

@TestCase(id = "WINGS-11133")
public class TC_29_01_ATAA_RTAA_Role_Admin extends BaseWingsTest {
    AtaaRtaaEnrollment atrt;
    String workedState = "Family Care";

    public void main() {
        User user = new User(Roles.ADMIN);
        commonAtaaRtaaSteps(user);
    }

    /**
     * Check different functionality of the ATAA/RTAA enrollment depends on current user (Create, View, Edit, Manage, etc.)
     *
     * @param user - current user
     */
    protected void commonAtaaRtaaSteps(User user) {
        //(!) Create new ataa/rtaa enrollment
        if (user.getAtrt().getAtaaCreate()) {
            logStep("Create new ataa/rtaa enrollment");
            AccountUtils.init();
            atrt = new AtaaRtaaEnrollment(user.getRole());
            TradeEnrollmentSteps.createAtaaRtaaWithNoStatus(atrt, user.getRole());

            //Change status to eligible. It can do only Trade administrator or administrator
            AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(atrt, Roles.ADMIN);
            AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
            detailsPage.clickButton(Buttons.Edit);
            AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
            editPage.changeEligibility(atrt);
            editPage.clickButton(Buttons.Save);
            BaseNavigationSteps.logout();
        }

        //(!) Check other functionality (view, edit, manage wage subsidies, etc.)
        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality (view, edit, manage wage subsidies)
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Check view functionality
        if (user.getAtrt().getAtaaView()) {
            logStep("Check view functionality");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS);

            //It's necessary to confirm pop-up, if user can create ataa/rtaa enrollment
            if (user.getAtrt().getAtaaCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            AtaaRtaaEnrollmentSearchForm searchPage = new AtaaRtaaEnrollmentSearchForm();
            searchPage.performSearchAndOpen(atrt);

            AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
            detailsPage.validateFullInformation(atrt);

            //(!) Manage wage subsidy (add, edit it and payments), it's necessary to do it before changing status of ATAA/RTAA to Ineligible (to check denials section).
            logStep("Manage Wage Subsidy(add, edit it and manage payments)");
            detailsPage.subsidyManipulation(user, atrt, workedState);

            divideMessage("Change status to ineligible");
            if (user.getAtrt().getAtaaEdit()) {
                atrt.setEligible(Constants.FALSE);
                detailsPage.clickButton(Buttons.Edit);

                AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
                editPage.changeEligibility(atrt);
                editPage.clickButton(Buttons.Save);
            }

            //(!) Check buttons presented on the page
            detailsPage.checkAtaaButtons(user);

            if (user.getAtrt().getAtaaEdit()) {
                logStep("Check edit functionality");
                atrt.setEligibilityDeterminationDate(CommonFunctions.getCurrentDate());
                detailsPage.clickButton(Buttons.Edit);
                AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
                editPage.inputDeterminationDate(atrt);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateFullInformation(atrt);
            }

            if (user.getAtrt().getAtaaFormsPrintSign()) {
                logStep("Check forms, print, sign");
                detailsPage.checkFormsPrint();
            }

            logStep("Check Qualifying Re-employment");
            detailsPage.validateQualifyingReemployment(user, atrt);
            detailsPage.editQualifyingReemployment(user, atrt); //TODO https://jira.nsparc.msstate.edu/browse/WINGS-9163
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

}
