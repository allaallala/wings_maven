package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that it is possible to create Assessment with Wagner-Peyser program.
 * Created by a.vnuchko on 16.09.2015.
 */

@TestCase(id = "WINGS-10947")
public class TC_19_02_03_Assessment_Create_With_WP_Program extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Precondition: create new Participant");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        Assessment assessment = new Assessment(participant, WAGNER_PEYSER);

        AssessmentCreationForm creationPage = AssessmentSteps.openFillCreationForm(assessment);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The Assessment Detail Screen is shown. A new Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
