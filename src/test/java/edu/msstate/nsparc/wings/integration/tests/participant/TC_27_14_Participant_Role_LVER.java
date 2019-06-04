package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for LVER to check participant functionality.
 * Created by a.vnuchko on 19.08.2016.
 */

@TestCase(id = "WINGS-11116")
public class TC_27_14_Participant_Role_LVER extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        //create participant, because LVER can't create participants
        commonSteps(participant, new User(Roles.LVER));
    }
}
