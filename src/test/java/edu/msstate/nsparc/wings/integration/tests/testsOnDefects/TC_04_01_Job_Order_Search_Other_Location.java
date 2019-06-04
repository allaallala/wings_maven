package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;

import static org.testng.AssertJUnit.assertFalse;


@TestCase(id = "WINGS-10578")
public class TC_04_01_Job_Order_Search_Other_Location extends BaseWingsTest {


    //Bug WINGS-2587, sub-task WINGS-2623
    public void main() {
        String employerCity = "Batesville";
        String employerZip = "38606";
        String jobOrderCity = "Tunica";
        String jobOrderZip = "38767";

        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        info("Set Employer address to Batesville, Zip Code 38606");
        jobOrder.getEmployer().getAddress().setCity(employerCity);
        jobOrder.getEmployer().getAddress().setZipCode(employerZip);
        info("Set Job Order address to Tunica, Zip Code 38767");
        jobOrder.setCity(jobOrderCity);
        jobOrder.setZipCode(jobOrderZip);
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrderWithLocation(jobOrder);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);
        JobOrderSearchForm searchForm = new JobOrderSearchForm();

        logStep("Select Employer");
        searchForm.selectEmployer(jobOrder.getEmployer());

        logStep("Select Employer location");
        searchForm.inputCityZip(jobOrder.getEmployer().getAddress().getCity(), jobOrder.getEmployer().getAddress().getZipCode());

        logStep("Perform search");
        searchForm.clickButton(Buttons.Search);

        logStep("Validate that nothing was found");
        assertFalse("Job Order was found", searchForm.jobTitlePresent());

        logStep("Select Job Order location");
        searchForm.inputCityZip(jobOrder.getCity(), jobOrder.getZipCode());

        logStep("Perform search");
        searchForm.clickButton(Buttons.Search);

        logStep("Validate that Job Order was found");
        CustomAssertion.softAssertEquals(searchForm.getJobTitleText(), jobOrder.getJobTitle(), "Incorrect job title text");
    }
}
