package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10626")
public class TC_05_10_Call_In_Job_Order_Power_Search extends BaseWingsTest {


    //sub-task WINGS-2762
    public void main() {
        info("Creating Job Order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        logStep("Search for Job Order with 'Open' status");
        JobOrderSearchForm searchForm = new JobOrderSearchForm();
        searchForm.performSearch(jobOrder);

        logStep("Open Job Order and click on 'Done' button");
        searchForm.clickJobTitle();
        JobOrderDetailsForm detailsForm = new JobOrderDetailsForm();
        detailsForm.clickButton(Buttons.Done);

        logStep("Navigate to Call-In creation screen");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Make sure the Job Order is populated");
        CallInCreationForm creationPage = new CallInCreationForm();
        assertTrue("Job Order isn't populated", creationPage.getJobOrderTitleText().contains(jobOrder.getJobTitle()));

        logEnd();
    }
}
