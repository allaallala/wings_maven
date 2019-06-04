package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10739")
public class TC_11_09_Job_Search_Clear_Search_Form extends BaseWingsTest {

    private static final String COMPANY_NAME = CommonFunctions.getRandomLiteralCode(10);

    public void main() {

        JobSearch jobSearch = new JobSearch();
        jobSearch.setCompanyName(COMPANY_NAME);

        logStep("Log in as Staff and open Job Search searching page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        logStep("Fill any search criteria fields with any data");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.inputCompanyName(COMPANY_NAME);

        logStep("Click the [Clear Form] button");
        jobSearchSearchForm.clickButton(Buttons.Clear);

        logStep("Check that parameters are set to default");
        assertTrue("Company Name was not cleared!", jobSearchSearchForm.getCompanyText().contains(Constants.EMPTY));
    }
}
