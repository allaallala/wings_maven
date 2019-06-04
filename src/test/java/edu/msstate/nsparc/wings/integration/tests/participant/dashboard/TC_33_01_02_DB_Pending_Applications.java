package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking that counter of pending applications works correctly
 * Created by a.korotkin on 10.11.2016.
 */

@TestCase(id = "WINGS-11193")
public class TC_33_01_02_DB_Pending_Applications extends BaseWingsTest {

    public void main() {
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        int n = 0;
        while (n < 5) {
            n++;
            AccountUtils.initEmployer();
            JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            ReferralSteps.createReferralSameParticipant(participant, jobOrder, Roles.STAFF);
        }

        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        softAssertEquals(participantHomePage.getTextOfPending(), String.valueOf(n), "Incorrect Number of pending Applications");
    }
}
