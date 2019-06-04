package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10736")
public class TC_11_06_Job_Search_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {

        logStep("Log in as Staff and open Job Search searching page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        logStep("Click Search button with no search criteria entered");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.clickButton(Buttons.Search);

        logStep("At least one Job Search was found");
        jobSearchSearchForm.yesSearchResult();
    }
}
