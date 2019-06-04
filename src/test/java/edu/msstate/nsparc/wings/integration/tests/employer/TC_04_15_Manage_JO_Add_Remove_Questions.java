package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
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
 * Fill in the Job Order SS creation form until form with questions is displayed. Return back to the first page and
 * choose 'No' in the 'Is this job at a Public School in MS?' rb. Create job order and check, that questions for it are
 * not displayed.
 * Created by a.vnuchko on 27.02.2017.
 */

@TestCase(id = "WINGS-10604")
public class TC_04_15_Manage_JO_Add_Remove_Questions extends BaseWingsTest {

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

        logStep("Fill in all required fields -> Go to the page with question");
        jobOrderCreationForm.fillInOrderPagesUpToCreate(Constants.FALSE, Constants.EMPTY);

        logStep("Return back to the first page and change 'Yes' value in the 'Is this job at a Public School in MS?' radio button group to 'No'");
        jobOrderCreationForm.returnToTheFirstPage();
        jobOrderCreationForm.clickJobPublicSchool(Constants.FALSE);

        logStep("Go to the page with question");
        new BasicInformationSsForm().clickButton(Buttons.Continue);

        logStep("Create");
        jobOrderCreationForm.clickButton(Buttons.Continue);
        jobOrderCreationForm.finishOrderCreation(Constants.FALSE);

        logResult("Job Order without screening questions should be created");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();
        searchForm.findJobOrder(jobOrder.getJobTitle());
        searchForm.clickJobTitle();

        JobOrderDetailsForm detailsPage = new JobOrderDetailsForm(jobOrder.getJobTitle());
        detailsPage.checkCustomQuestionTitle(Constants.FALSE);
    }
}
