package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check that is possible to create assessment (trade) with pre test
 */

@TestCase(id = "WINGS-10948")
public class TC_19_03_Assessment_Trade_Create_Pre_Test extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Precondition: create new Participant");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, WAGNER_PEYSER);

        AssessmentSteps.openFillCreationForm(assessment);

        logResult("The Assessment Detail Screen is shown. A new Assessment was created and contains the same data you have entered");
    }
}
