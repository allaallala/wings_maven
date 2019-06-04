package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check, that it is possible to create Assessment with WIA program.
 * Created by a.vnuchko on 16.09.2015.
 */

@TestCase(id = "WINGS-10946")
public class TC_19_02_02_Assessment_Create_With_Wia_Program extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Precondition: create new WIA Enrollment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), Constants.FALSE);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Constants.FALSE, Constants.FALSE);
        Assessment assessment = new Assessment(participant, Constants.WIOA.toUpperCase());

        AssessmentSteps.openFillCreationForm(assessment);

        logStep("Click the [Create] button");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        creationPage.clickButton(Buttons.Create);

        logResult("The Assessment Detail Screen is shown. A new Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
