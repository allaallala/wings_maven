package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for a rapid response administrator to check participant functionality.
 * Created by a.vnuchko on 18.08.2016.
 */

@TestCase(id = "WINGS-11111")
public class TC_27_09_Participant_Role_Rapid_Response_Admin extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
         //create participant, because rapid response administrator can't create participants
        ParticipantCreationSteps.createParticipantRoleWithEmploymentHistory(new User(Roles.ADMIN), participant, false);
        commonSteps(participant, new User(Roles.RRADMIN));
    }
}
