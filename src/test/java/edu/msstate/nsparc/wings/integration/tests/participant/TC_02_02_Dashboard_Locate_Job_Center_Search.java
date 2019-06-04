package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10538")
public class TC_02_02_Dashboard_Locate_Job_Center_Search extends BaseWingsTest {

    public void main() {
        info("We need to create Participant first");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), false);
        ParticipantCreationSteps.createParticipantDriver(participant, true, false);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Home");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);

        logStep("Find a WIN Job Center");
        participantHomePage.findJobCenter();

        logStep("Search");
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER);
        jobCenterSearchForm.clickButton(Buttons.Search);

        logStep("Validate number of job centers");
        List<String> jobCenters = jobCenterSearchForm.getJobCentersNames();
        List<String> jobCentersFromDB = AdvancedSqlFunctions.getJobCentresNamesFromDB();
        assertEquals(jobCentersFromDB.size(), jobCenters.size(), "Numbers of job centers from DB and application are not the same!");

        logStep("Validate job centers names");
        assertTrue("Job Center name is not correct", jobCentersFromDB.containsAll(jobCenters));

        BaseNavigationSteps.logout();

        logEnd();
    }
}
