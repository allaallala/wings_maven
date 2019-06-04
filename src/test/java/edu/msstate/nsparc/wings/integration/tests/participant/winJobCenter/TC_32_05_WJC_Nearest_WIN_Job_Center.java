package edu.msstate.nsparc.wings.integration.tests.participant.winJobCenter;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;
import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Check that correct Nearest WIN Job Center is selected
 * Created by a.korotkin on 11/16/2016.
 */
@TestCase(id = "WINGS-11180")
public class TC_32_05_WJC_Nearest_WIN_Job_Center extends BaseWingsTest {
    private String titleOfPage = "Your Nearest WIN Job Center";

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        info("Going to WIN Job Center");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goWinJobCenterS_S();

        info("Checking Nearest Job Center");
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER_SS);
        softAssertEquals(jobCenterSearchForm.getPageTitle(), titleOfPage, "Incorrect Page is displayed");
        softTrue("Nearest WIN Job Center is incorrect", jobCenterSearchForm.getNearestAddress().contains(jobCenterSearchForm.getFirstAddress()));
    }
}
