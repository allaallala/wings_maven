package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation.BasicInformationSsForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create job openings with blockers (driver's licence, tools used, education level and appropriate 'Apply online' checkboxes)
 * Created by a.vnuchko on 27.02.2017.
 */

@TestCase(id = "WINGS-10605")
public class TC_04_16_Manage_JO_Create_With_Blockers extends BaseWingsTest {

    public void main() {
        info("We need to create Employer first");
        Employer employer = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login to the system");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Manage Job Openings");
        EmployerHomePage homePage = new EmployerHomePage(employer.getCompanyName());
        JobOrderCreationForm jobOrderCreationForm = homePage.openJobOrderCreate();

        logStep("Create new opening: Select employer, job title. Add occupation code. Select 'Yes' value in the "
                + "'Is this job at a Public School in MS?' radio button group");
        JobOrder jobOrder = new JobOrder();
        new BasicInformationSsForm().fillInTheFirstOrderPageSs(jobOrder);

        logStep("Select 'Yes' for Driver License requirement\n"
                + "Select 'Skill with using the following Tools and Technologies Required'\n"
                + "Select 'Education level required' \n"
                + "Select appropriate check box 'Required to apply online'");
        jobOrderCreationForm.fillSecondPageWithBlockers();

        logStep("Create Job Order");
        jobOrderCreationForm.finishOrderCreation(Constants.FALSE);

        logResult("Job Order was successfully created. Its Details page is opened. All entered data correspond to the entered one");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();
        searchForm.findJobOrder(jobOrder.getJobTitle());
        searchForm.clickJobTitle();

        new JobOrderDetailsForm(jobOrder.getJobTitle()).validateSpecDataBlockers();
    }
}
