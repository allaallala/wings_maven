package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.AuditForm;
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


@TestCase(id = "WINGS-10699")
public class TC_08_01_Gap_in_Service_Save_Changes extends BaseWingsTest {

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

        logStep("Select the reason for Gap in Service");
        participantDetailsForm.selectGapReason(REASON);

        logStep("Save changes");
        participantDetailsForm.clickButton(Buttons.Save);

        logStep("Validate, that changes are saved");
        participantDetailsForm.expandParticipationPeriods();
        CustomAssertion.softTrue("Gap in Service isn't saved", participantDetailsForm.getParticipationPeriodsText().contains(REASON));
        participantDetailsForm.expandOperations();
        CustomAssertion.softTrue("Gap in Service button is displayed", !(participantDetailsForm.checkGapServiceButton()));
        BaseNavigationSteps.logout();

        //sub-task WINGS-2642
        logStep("Search participant record and open its participantSSDetails page");
        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.ADMIN, participant);

        logStep("Check Audit page for 'Gap In Service' record");
        participantDetailsForm.openAuditForm();
        CustomAssertion.softTrue("Audit record wasn't added", AuditForm.TBC_AUDIT_TABLE.getText().contains("Gap In Service"));

        logEnd();
    }
}
