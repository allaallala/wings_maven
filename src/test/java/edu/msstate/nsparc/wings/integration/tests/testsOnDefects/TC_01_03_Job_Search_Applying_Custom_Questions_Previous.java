package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10501")
public class TC_01_03_Job_Search_Applying_Custom_Questions_Previous extends BaseWingsTest {


    //Bug WINGS-2342, sub-task 2394
    public void main() {
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        info("We need to create Job Order too");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, true);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Job Search");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.searchForJobs();

        logStep("Enter Job title and click Search");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);

        logStep("Validate search results");
        jobFindForm.validateNewJobOrderFormTitle(jobOrder, participant);

        logStep("Open one of the job orders");
        jobFindForm.openJobOrderDetailsNew(jobOrder.getJobTitle());

        logStep("Apply");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        jobOrderDetailsForm.apply();

        logStep("Answer the screening question");
        CustomAssertion.softTrue("Incorrect screening question", jobOrderDetailsForm.getScreeningQuestion().contains(jobOrder.getQuestion().getText()));
        jobOrderDetailsForm.answerScreeningQuestion(jobOrder);

        logStep("Proceed application");
        jobOrderDetailsForm.inputInitialsConfirm(participant.getInitials());

        logStep("Make sure the Apply button is no longer displayed");
        homePage.searchForJobs();
        jobFindForm.performSearch(jobID);
        jobFindForm.openJobOrderDetails();
        jobOrderDetailsForm.checkApplyNotPresent();
    }
}
