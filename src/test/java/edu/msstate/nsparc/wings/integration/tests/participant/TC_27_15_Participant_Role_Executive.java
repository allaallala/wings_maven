package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for Executive to check participant functionality.
 * Created by a.vnuchko on 19.08.2016.
 */

@TestCase(id = "WINGS-11117")
public class TC_27_15_Participant_Role_Executive extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        //create participant, because executive can't create participants
        ParticipantCreationSteps.createParticipantRoleWithEmploymentHistory(new User(Roles.ADMIN), participant, false);
        commonSteps(participant, new User(Roles.EXECUTIVE));
    }
}
