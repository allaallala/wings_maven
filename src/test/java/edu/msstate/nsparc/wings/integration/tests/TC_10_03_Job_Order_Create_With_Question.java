package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;


@TestCase(id = "WINGS-10719")
public class TC_10_03_Job_Order_Create_With_Question extends BaseWingsTest {

    public void main() {
        logStep("Creating Employer for Job Order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        logStep("Creating Job Order with question");
        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        String question = jobOrderCreationForm.createJobOrder(jobOrder, Constants.FALSE, Constants.FALSE, Constants.TRUE);

        logStep("Find created job order");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);
        BaseWingsSteps.popClick(Popup.Search);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearch(jobOrder);
        jobOrderSearchForm.clickJobTitle();

        logStep("Check, that question is present");
        JobOrderDetailsForm detailsForm = new JobOrderDetailsForm();
        detailsForm.expandCustomQuestions();
        Assert.assertTrue(detailsForm.getCustomQuestion().contains(question));
    }
}
