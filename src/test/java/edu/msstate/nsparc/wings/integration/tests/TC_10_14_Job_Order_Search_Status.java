package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10725")
public class TC_10_14_Job_Order_Search_Status extends BaseWingsTest {

    private static final String STATUS = "Open";
    private static final String WRONG_STATUS = "Closed";

    public void main() {

        info("We will create JobOrder in Open status and check if it's displayed in Search results");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        logStep("Select wrong status you need->Search");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.selectStatus(WRONG_STATUS);
        jobOrderSearchForm.inputJobTitle(jobOrder.getJobTitle());
        jobOrderSearchForm.clickButton(Buttons.Search);

        info("Nothing should be found");
        CustomAssertion.softAssertContains(jobOrderSearchForm.getJobResultTableText(), "No items found", "Search by Status assert fail");

        logStep("Select status->Search");
        jobOrderSearchForm.selectStatus(STATUS);
        jobOrderSearchForm.clickButton(Buttons.Search);

        info("Created Job Order should be found");
        assertTrue("Search by Status assert fail", jobOrderSearchForm.getJobTitleText().contains(jobOrder.getJobTitle()));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
