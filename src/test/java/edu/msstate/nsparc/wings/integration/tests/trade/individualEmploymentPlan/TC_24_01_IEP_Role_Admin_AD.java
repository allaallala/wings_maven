package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check functionality of the individual employment plan for administrator ,area director roles.
 * Created by a.vnuchko on 03.02.2016.
 */

@TestCase(id = "WINGS-11058")
public class TC_24_01_IEP_Role_Admin_AD extends BaseWingsTest {
    private IndividualEmploymentPlan indEmplPlan;
    private String dateToEdit = CommonFunctions.getYesterdayDate();

    public void main() {

        //Role Administrator
        User user = new User(Roles.ADMIN);
        commonSteps(user);

        //Role Area Director
        user = new User(Roles.AREADIRECTOR);
        commonSteps(user);
    }

    /**
     * Desctibes the entire cycle of creating, view, editing individual employment plan and checks user permissions.
     *
     * @param user - current user
     */
    protected void commonSteps(User user) {
        logStep("Create an individual employment plan, if it's possible");
        if (user.getIEP().getIepCreate()) {
            info("Generate test data");
            AccountUtils.init();
            indEmplPlan = new IndividualEmploymentPlan(user.getRole());
            indEmplPlan.setIsLogout(false);  //We don't want to make log out after creation new IEP.
            TradeEnrollmentSteps.createIndividualEmploymentPlan(user, indEmplPlan);

            //(!) If possible to click create another button and create new WIA Enrollment
            logStep("Click [Create Another] if possible");
            IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
            detailsPage.createAnotherIep();
            BaseNavigationSteps.logout();
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality: View, audit, edit, print.
     *
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");

        BaseWingsSteps.logInAs(user.getRole());

        if (user.getIEP().getIepView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS);

            //(!) If user can create Individual employment plan - > he should confirm pop-up window.
            if (user.getIEP().getIepCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            //(!) If user can view search results.
            IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();


            searchPage.performSearch(user, indEmplPlan);
            searchPage.openFirstSearchResult();

            //(!) Check view functionality
            IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
            detailsPage.validateInformation(indEmplPlan);

            //(!) Check buttons on the detailed Individual Employment Plans page
            logStep("Check [Audit], [Forms sign], [Print], [Edit] buttons on the page");
            detailsPage.checkElementsPresent(user, CommonFunctions.getCurrentDate(), indEmplPlan);

            //(!) Check edit functionality
            logStep("Check edit functionality");
            if (user.getIEP().getIepEdit()) {
                detailsPage.editIEP(indEmplPlan, dateToEdit); //edit...
                detailsPage.editIEP(indEmplPlan, CommonFunctions.getCurrentDate()); //... and return values to default
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}