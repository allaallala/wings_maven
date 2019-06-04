package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
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

@TestCase(id = "WINGS-10701")
public class TC_08_02_Gap_in_Service_Cancel extends BaseWingsTest {

    private static final String REASON = "Provider Care (Family)";

    public void main() {


        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);

        logStep("Select panel 'Operations'->Gap in Service");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandOperations();
        participantDetailsForm.clickGapInService();

        logStep("Select the REASON for Gap in Service");
        participantDetailsForm.selectGapReason(REASON);

        logStep("Cancel");
        participantDetailsForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("Validate, that changes aren't saved");
        participantDetailsForm.expandParticipationPeriods();
        CustomAssertion.softTrue("Gap in Service is saved", !(participantDetailsForm.getParticipationPeriodsText().contains(REASON)));
        participantDetailsForm.expandOperations();
        CustomAssertion.softTrue("Gap in Service button isn't displayed", participantDetailsForm.checkGapServiceButton());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
