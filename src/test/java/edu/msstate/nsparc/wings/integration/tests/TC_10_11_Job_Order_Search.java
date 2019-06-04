package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10723")
public class TC_10_11_Job_Order_Search extends BaseWingsTest {

    public void main() {
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        logStep("Open job order search form");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.clickAndWait(BaseWingsForm.BaseButton.EMPLOYER_LOOK_UP);

        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearchAndSelect(jobOrder.getEmployer());
        jobOrderSearchForm.inputJobTitle(jobOrder.getJobTitle());

        logStep("Click [Search] button");
        jobOrderSearchForm.clickButton(Buttons.Search);
        jobOrderSearchForm.checkInternalError();

        logStep("Validate search results");
        jobOrderSearchForm.validateSearchResults(jobOrder.getEmployer().getCompanyName(), jobOrder.getJobTitle());
        logEnd();
    }
}
