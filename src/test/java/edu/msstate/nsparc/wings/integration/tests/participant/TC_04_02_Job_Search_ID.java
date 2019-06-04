package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10581")
public class TC_04_02_Job_Search_ID extends BaseWingsTest {


    public void main() {
        info("We need to create Participant first");
        ParticipantCreationSteps.createParticipantSelfRegistration();
        info("We need to create Job Order too");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, false);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Job Search");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.searchForJobs();

        logStep("Search Job by ID");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearchByID(jobID);

        logStep("Validate search results");
        CustomAssertion.softTrue("Incorrect job title", jobFindForm.getJobTitleTextForm().contains(jobOrder.getJobTitle()));
        CustomAssertion.softTrue("Incorrect job ID", jobFindForm.getJobTitleTextForm().contains(jobID));
    }
}
