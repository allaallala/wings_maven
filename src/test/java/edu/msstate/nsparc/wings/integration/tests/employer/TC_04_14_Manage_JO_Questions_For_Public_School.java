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
import framework.CommonFunctions;


/**
 * Create new job orded SS with checked radio button 'Is this job at a Public School in MS?'. Check, that not editable
 * questions are displayed on the questions page. Also check, that job order is successfully created and contain all
 * added questions.
 * Created by a.vnuchko on 27.02.2017.
 */

@TestCase(id = "WINGS-10603")
public class TC_04_14_Manage_JO_Questions_For_Public_School extends BaseWingsTest {
    private String question = CommonFunctions.getRandomLiteralCode(20);

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
        new BasicInformationSsForm().fillInTheFirstOrderPageSsAndContinue(jobOrder);

        logStep("Fill in all required fields->Go to the page with question");
        jobOrderCreationForm.fillInOrderPagesUpToCreate(Constants.TRUE, question);

        logResult("Check, that not editable questions are displayed on the question page");
        //TODO Questions default section is not displayed right now. So i marked it as t o d o with reference to https://jira.nsparc.msstate.edu/browse/WINGS-8455

        logStep("Create job  order");
        jobOrderCreationForm.finishOrderCreation(Constants.FALSE);

        logResult("Job Order with added questions should be successfully created");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();
        searchForm.findJobOrder(jobOrder.getJobTitle());
        searchForm.clickJobTitle();

        JobOrderDetailsForm detailsPage = new JobOrderDetailsForm(jobOrder.getJobTitle());
        detailsPage.checkAddedQuestion(question);
    }
}
