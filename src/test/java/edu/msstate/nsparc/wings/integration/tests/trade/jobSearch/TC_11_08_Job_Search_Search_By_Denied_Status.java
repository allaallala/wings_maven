package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
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

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10738")
public class TC_11_08_Job_Search_Search_By_Denied_Status extends BaseWingsTest {

    private static final String DENIED_JOB_SEARCH_STATUS = "Denied";

    public void main() {

        JobSearch jobSearch = new JobSearch();
        jobSearch.setApproved(Constants.FALSE);
        TradeEnrollmentSteps.createJobSearchWithStatus(jobSearch);

        logStep("Log in as Staff and open Job Search searching page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        logStep("Select Denied Status and perform search");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.selectStatus(DENIED_JOB_SEARCH_STATUS);
        jobSearchSearchForm.clickButton(Buttons.Search);

        logStep("Check that Job Search with denied status was found");
        jobSearchSearchForm.openFirstSearchResult();
        JobSearchDetailsForm jobSearchDetailsForm = new JobSearchDetailsForm();
        assertTrue("Selected Job Search is not with Denied status!", jobSearchDetailsForm.getStatusTextPage().contains(DENIED_JOB_SEARCH_STATUS));
    }
}
