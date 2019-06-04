package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for WIOA administrator PLUS to check participant functionality.
 * Created by a.korotkin on 02.11.2016.
 */

@TestCase(id = "WINGS-11120")
public class TC_27_18_Participant_Role_WIOAPLUS extends TC_27_01_Participant_Role_Admin {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        //create participant, because WIOA administrator PLUS can't create participants
        ParticipantCreationSteps.createParticipantRoleWithEmploymentHistory(new User(Roles.ADMIN), participant, false);
        commonSteps(participant, new User(Roles.WIOAPLUS));
    }
}