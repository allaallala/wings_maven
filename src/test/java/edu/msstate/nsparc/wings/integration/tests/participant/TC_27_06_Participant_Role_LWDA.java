package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for a LWDA staff to check participant functionality.
 * Created by a.vnuchko on 18.08.2016.
 */

@TestCase(id = "WINGS-11108")
public class TC_27_06_Participant_Role_LWDA extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        User user = new User(Roles.LWDASTAFF);
        commonSteps(participant, user);
    }
}
