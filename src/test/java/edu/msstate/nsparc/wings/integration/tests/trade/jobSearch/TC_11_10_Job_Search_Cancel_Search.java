package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10740")
public class TC_11_10_Job_Search_Cancel_Search extends BaseWingsTest {

    private static final String COMPANY_NAME = CommonFunctions.getRandomLiteralCode(10);

    public void main() {

        JobSearch jobSearch = new JobSearch();
        jobSearch.setCompanyName(COMPANY_NAME);

        logStep("Log in as Staff and open Job Search searching page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        logStep("Fill any search criteria fields with any data");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.inputCompanyName(COMPANY_NAME);

        logStep("Click the [Cancel] button");
        jobSearchSearchForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("The Staff Home Screen is shown");
        new StaffHomeForm();
    }
}
