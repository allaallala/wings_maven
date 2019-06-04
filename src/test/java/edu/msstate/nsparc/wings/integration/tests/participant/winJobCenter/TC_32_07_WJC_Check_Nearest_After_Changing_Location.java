package edu.msstate.nsparc.wings.integration.tests.participant.winJobCenter;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softNotSame;
import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Checking that appropriate nearest WIN Job Center is selected after changing location
 * Created by a.korotkin on 11/17/2016.
 */

@TestCase(id = "WINGS-11182")
public class TC_32_07_WJC_Check_Nearest_After_Changing_Location extends BaseWingsTest {

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Going to WIN Job Center");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goWinJobCenterS_S();
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER_SS);
        String oldNearest = jobCenterSearchForm.getNearestAddress();
        BaseNavigationSteps.home();
        participantHomePage.clickEditLocation();
        ParticipantEditForm participantEditForm = new ParticipantEditForm(Constants.CONTACT);
        participantEditForm.fillLocations(participant);
        participantHomePage.goWinJobCenterS_S();
        softNotSame(jobCenterSearchForm.getNearestAddress(), oldNearest, "Nearest Job Center is not changed");
        softTrue("Nearest WIN Job Center is incorrect", jobCenterSearchForm.getNearestAddress().contains(jobCenterSearchForm.getFirstAddress()));
    }
}
