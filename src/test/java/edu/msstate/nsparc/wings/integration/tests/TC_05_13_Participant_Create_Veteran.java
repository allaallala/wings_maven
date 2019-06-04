package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


// Author: d.poznyak

@TestCase(id = "WINGS-10628")
public class TC_05_13_Participant_Create_Veteran extends TC_05_12_Participant_Create_Regular {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        commonSteps(participant, Constants.TRUE);
    }
}
