package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for a staff to check participant functionality.
 * Created by a.vnuchko on 17.08.2016.
 */

@TestCase(id = "WINGS-11106")
public class TC_27_04_Participant_Role_Staff extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        User user = new User(Roles.STAFF);
        commonSteps(participant, user);
    }
}
