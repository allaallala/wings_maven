package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

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


/**
 * Checking job search by keyword from the Dashboard
 * Created by a.korotkin on 14.11.2016.
 */

@TestCase(id = "WINGS-11197")
public class TC_33_05_DB_Search_By_Keyword extends BaseWingsTest {
    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Creating a Job");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, true);

        logStep("Searching for created Job Order");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.searchForJobs();

        logStep("Checking found Job");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);
        jobFindForm.openJobOrderDetailsNew(jobOrder.getJobTitle());
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        jobOrderDetailsForm.checkSearchResultOnDetailsS_S(jobID);
    }
}
