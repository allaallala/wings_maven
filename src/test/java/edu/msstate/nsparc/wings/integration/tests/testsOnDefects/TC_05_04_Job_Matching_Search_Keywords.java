package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10618")
public class TC_05_04_Job_Matching_Search_Keywords extends BaseWingsTest {


    //sub-task WINGS-2740
    public void main() {

        info("Creating Job Order for Job Matching search");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        logStep("Login as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Open Job Matching form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_JOB_MATCHING);
        logStep("Type Job Title in Keywords field");
        JobMatchingForm jobMatchingForm = new JobMatchingForm();
        JobMatchingForm.TXB_KEYWORDS.type(jobOrder.getJobTitle());

        logStep("Click on Search button");
        jobMatchingForm.clickButton(Buttons.Search);

        logStep("Check that Job Order was found");
        CustomAssertion.softAssertContains(jobOrder.getJobTitle(), JobMatchingForm.TBC_JOB_TITLE.getText(), "Assert Job Title Failed");
        CustomAssertion.softAssertContains(jobOrder.getEmployer().getCompanyName(), JobMatchingForm.TBC_EMPLOYER.getText(), "Assert Employer failed");

        logStep("Click 'Clear Form' button");
        jobMatchingForm.clickButton(Buttons.Clear);

        logStep("Type Job Title in 'Job Title' field");
        JobMatchingForm.TXB_JOB_TITLE.type(jobOrder.getJobTitle());

        logStep("Click on Search button");
        jobMatchingForm.clickButton(Buttons.Search);

        logStep("Check that Job Order was found");
        jobMatchingForm.checkField(JobMatchingForm.TBC_JOB_TITLE, jobOrder.getJobTitle(), true);
        jobMatchingForm.checkField(JobMatchingForm.TBC_EMPLOYER, jobOrder.getEmployer().getCompanyName(), true);
    }
}
