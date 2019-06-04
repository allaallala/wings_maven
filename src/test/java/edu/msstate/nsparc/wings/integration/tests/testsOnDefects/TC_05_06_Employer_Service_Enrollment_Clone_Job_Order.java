package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
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
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10620")
public class TC_05_06_Employer_Service_Enrollment_Clone_Job_Order extends BaseWingsTest {

    private static final String SERVICE_TITLE = "Taking Job Order Information";
    private final String expectedCount = "2";

    //Bug WINGS-2709, sub-task WINGS-2710
    public void main() {

        info("Creating Job Order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        logStep("Search for created Job Order");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearch(jobOrder);
        jobOrderSearchForm.checkInternalError();

        logStep("Open Job Order");
        jobOrderSearchForm.clickJobTitle();
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();

        logStep("Click on 'Clone This Job Order' button");
        jobOrderDetailsForm.clickClone();

        logStep("Fill in all required fields on Cloning form");
        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm(Constants.CLONE, jobOrder.getJobTitle());
        jobOrderCreationForm.cloneJobOrder();

        logStep("Open Employer Service Enrollment search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search by Employer and Service name");
        EmployerEnrollmentSearchForm employerEnrollmentSearchForm = new EmployerEnrollmentSearchForm();
        employerEnrollmentSearchForm.performSearch(jobOrder.getEmployer(), SERVICE_TITLE);

        logStep("Check that 2 records were found");
        CustomAssertion.softAssertEquals(new SearchResultsForm().getResultRowsCount().toString(), expectedCount,
                "Service Enrollment wasn't created");
    }
}
