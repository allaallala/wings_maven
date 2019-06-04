package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.ViewApplicantsForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10596")
public class TC_04_10_View_Applicants_Job_Order_Details extends BaseWingsTest {
    Participant participant;


    public void main() {
        info("We need to create Job Order first");
        JobOrder jobOrder = JobOrderSteps.createJobOrderForApplyOnline();
        ViewApplicantsForm viewApplicantsPage = searchForJobOrder(jobOrder);

        logStep("Job order participantSSDetails");
        viewApplicantsPage.openJobOrderDetails();

        logStep("Validate Job Order information");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        Assert.assertTrue(jobOrderDetailsForm.getJobTitleText().contains(jobOrder.getJobTitle()));
    }

    /**
     * Search for job order
     *
     * @param order - job order
     * @return - the View Applicants Form.
     */
    protected ViewApplicantsForm searchForJobOrder(JobOrder order) {
        info("Create Participant and apply for Job Order");
        participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        JobOrderSteps.applyForJobOrder(participant, order);

        logStep("Login to the System");
        BaseNavigationSteps.loginEmployer();

        logStep("View Applicants");
        EmployerHomePage homePage = new EmployerHomePage(order.getEmployer().getCompanyName());
        homePage.viewApplicants();

        logStep("Enter some parameters for searching->Search");
        ViewApplicantsForm viewApplicantsForm = new ViewApplicantsForm();
        viewApplicantsForm.performSearch(order);
        return viewApplicantsForm;
    }
}
