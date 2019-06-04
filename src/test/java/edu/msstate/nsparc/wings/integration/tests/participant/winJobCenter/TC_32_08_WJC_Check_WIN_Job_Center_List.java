package edu.msstate.nsparc.wings.integration.tests.participant.winJobCenter;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;
import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Checking that WIN Job Center list is displayed correctly and all WIN Job Centers are displayed
 * Created by a.korotkin on 11/17/2016.
 */

@TestCase(id = "WINGS-11183")
public class TC_32_08_WJC_Check_WIN_Job_Center_List extends BaseWingsTest {

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        info("Checking WIN Job Center List");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goWinJobCenterS_S();
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER_SS);
        softTrue("The table of results is incorrectly displayed", jobCenterSearchForm.checkHeadersArePresent());
        softAssertEquals(jobCenterSearchForm.getNumberOfDisplayedRecords().toString(), String.valueOf(AdvancedSqlFunctions.getNumberOfJobCenters()),
                "Not All Job Centers are displayed");
    }
}
