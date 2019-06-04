package edu.msstate.nsparc.wings.integration.tests.participant.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Test for checking Job Search in Participant S-S side
 * Created by a.korotkin on 08.11.2016.
 */
@TestCase(id = "WINGS-11176")
public class TC_32_01_WP_Job_Order_Search_ParticipantS_S extends BaseWingsTest {

    public void main() {

        logStep("Creating a Job");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, true);
        LoginForm loginForm = new LoginForm();
        loginForm.openCandidateSearchPage();

        logStep("Searching by Title");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);
        jobFindForm.openJobOrderDetailsNew(jobOrder.getJobTitle());
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        jobOrderDetailsForm.checkSearchResultOnDetailsS_S(jobID);
    }
}
