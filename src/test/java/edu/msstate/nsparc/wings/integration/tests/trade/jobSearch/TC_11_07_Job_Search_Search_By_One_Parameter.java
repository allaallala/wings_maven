package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10737")
public class TC_11_07_Job_Search_Search_By_One_Parameter extends BaseWingsTest {

    private static final String COMPANY_NAME = CommonFunctions.getRandomLiteralCode(10);

    public void main() {

        JobSearch jobSearch = new JobSearch();
        jobSearch.setCompanyName(COMPANY_NAME);
        TradeEnrollmentSteps.createJobSearch(jobSearch, Roles.ADMIN);

        logStep("Log in as Staff and open Job Search searching page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        logStep("Fill one search criteria field");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.inputCompanyName(COMPANY_NAME);

        logStep("Click Search button and open found Job Search");
        jobSearchSearchForm.clickButton(Buttons.Search);
        jobSearchSearchForm.openFirstSearchResult();

        logStep("Validate found Job search");
        JobSearchDetailsForm jobSearchDetailsForm = new JobSearchDetailsForm();
        jobSearchDetailsForm.validateInformation(jobSearch);
    }
}
