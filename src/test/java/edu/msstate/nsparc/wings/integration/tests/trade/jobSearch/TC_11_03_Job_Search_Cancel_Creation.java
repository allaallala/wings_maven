package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchCreateForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10733")
public class TC_11_03_Job_Search_Cancel_Creation extends BaseWingsTest {

    private static final String COMPANY_NAME = CommonFunctions.getRandomLiteralCode(5);

    public void main() {


        logStep("Log in as Staff and open Job Search creation page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Create);

        logStep("Fill out some fields");
        JobSearchCreateForm jobSearchCreateForm = new JobSearchCreateForm();
        jobSearchCreateForm.inputCompanyName(COMPANY_NAME);

        logStep("Click the [Cancel] button");
        jobSearchCreateForm.clickButton(Buttons.Cancel);
        jobSearchCreateForm.areYouSure(Popup.Yes);

        logStep("Validate that Job Search wasn't created: Navigate to Participants -> Trade -> Job Search -> Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Validate that Job Search wasn't created: perform search");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.inputCompanyName(COMPANY_NAME);
        jobSearchSearchForm.clickButton(Buttons.Search);

        logStep("Validate that Job Search wasn't created: NO records should be found");
        jobSearchSearchForm.noSearchResults();
    }
}
