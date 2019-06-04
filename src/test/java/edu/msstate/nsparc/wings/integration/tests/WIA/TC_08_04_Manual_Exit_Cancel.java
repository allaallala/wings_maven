package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


// Author: d.poznyak

@TestCase(id = "WINGS-10705")
public class TC_08_04_Manual_Exit_Cancel extends BaseWingsTest {

    private static final String REASON = "Deceased";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);

        logStep("Select panel 'Operations'->Manual Exit");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandOperations();
        participantDetailsForm.clickManualExit();

        logStep("Select the REASON for Exit");
        participantDetailsForm.selectExitReason(REASON);

        logStep("Cancel");
        participantDetailsForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("Validate, that changes aren't saved");
        participantDetailsForm.expandParticipationPeriods();
        CustomAssertion.softTrue("Manual Exit REASON is saved", !(participantDetailsForm.getParticipationPeriodsText().contains(REASON)));
        participantDetailsForm.expandOperations();
        CustomAssertion.softTrue("Manual Exit button isn't displayed", participantDetailsForm.checkManualExitButtonPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
