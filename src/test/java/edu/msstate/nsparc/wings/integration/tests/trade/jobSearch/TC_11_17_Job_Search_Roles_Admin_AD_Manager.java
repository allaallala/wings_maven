package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check job search functionality using different roles - administrator, area director, manager.
 * Created by a.vnuchko on 04.03.2016.
 */

@TestCase(id = "WINGS-10748")
public class TC_11_17_Job_Search_Roles_Admin_AD_Manager extends BaseWingsTest {
    private JobSearch jobSearch;
    String companyName;

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonJobSearchSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonJobSearchSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonJobSearchSteps(user);
    }

    /**
     * Represents common method to check user permissions for job search.Includes:
     * - create job search (if possible)
     * - view job search (if possible)
     * - edit job search (if possible)
     * - add job search expense (if possible)
     * - edit job search expense(if possible)
     * * @param user
     */
    protected void commonJobSearchSteps(User user) {
        //(!) Check, if user can create job search.
        if (user.getJobSearch().getJobSearchCreate()) {
            logStep("Create job search, if user has permissions to do this");
            AccountUtils.init();
            jobSearch = new JobSearch();
            TradeEnrollmentSteps.createJobSearch(jobSearch, user.getRole());
        }

        //(!) Check other functionality.
        logStep("Check other functionality: view, edit and expenses.");
        checkOtherFunctionality(user);
    }

    /**
     * Open job search detail form (if it possible)
     *
     * @param user - current user
     */
    protected void checkOtherFunctionality(User user) {
        divideMessage("Open [Job Search] [Search] form");
        BaseWingsSteps.logInAs(user.getRole());

        if (user.getJobSearch().getJobSearchView()) {
            divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);

            //(!) If user can create job search -> he has to confirm a pop-up window.
            if (user.getJobSearch().getJobSearchCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            JobSearchSearchForm searchForm = new JobSearchSearchForm();
            searchForm.performSearch(user, jobSearch);


            //(!) Check, if current user can view job search
            logStep("Check, if current user can view search results");
            searchForm.openFirstSearchResult();

            //(!) Check, if current user can edit job search
            logStep("Check, if current user can edit job search");
            JobSearchDetailsForm detailsPage = new JobSearchDetailsForm();

            //(!) Check, if [Edit] button present or not on the page.
            detailsPage.checkEditButton(user.getJobSearch().getJobSearchEdit());

            if (user.getJobSearch().getJobSearchEdit()) {
                //(!)Edit company name and save it.
                detailsPage.clickButton(Buttons.Edit);
                JobSearchEditForm editPage = new JobSearchEditForm();
                companyName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
                jobSearch.setCompanyName(companyName);
                editPage.editCompanyName(companyName);
                editPage.editDetails(jobSearch);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateInformation(jobSearch);
            }
            addJobSearchExpense();

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    //TODO https://jira.nsparc.msstate.edu/browse/WINGS-8775
    //(!)Check job search expense
    protected void editJobSearchExpense(JobSearch jbSearch, Roles role) {
        BaseNavigationSteps.logout();
    }

    //(!)Check job search expense
    protected void addJobSearchExpense() {
        info("todo");
    }
}
