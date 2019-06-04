package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.AuditForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10703")
public class TC_08_03_Manual_Exit_Save_Changes extends BaseWingsTest {

    private static final String REASON = "Deceased";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Find one of the records and open it");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);

        logStep("Select panel 'Operations'->Manual Exit");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandOperations();
        participantDetailsForm.clickManualExit();

        logStep("Select the REASON for Exit");
        participantDetailsForm.selectExitReason(REASON);

        logStep("Save changes");
        participantDetailsForm.clickButton(Buttons.Save);

        logStep("Validate, that changes are saved");
        participantDetailsForm.expandParticipationPeriods();
        CustomAssertion.softTrue("Manual Exit REASON isn't saved", participantDetailsForm.getParticipationPeriodsText().contains(REASON));
        participantDetailsForm.expandOperations();
        CustomAssertion.softTrue("Manual Exit button is displayed", !(participantDetailsForm.checkManualExitButtonPresent()));
        BaseNavigationSteps.logout();

        //sub-task WINGS-2642
        logStep("Search participant record and open its participantSSDetails page");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);

        logStep("Check Audit page for 'Manual Exit' record");
        participantDetailsForm.openAuditForm();
        CustomAssertion.softTrue("Audit record wasn't added", AuditForm.TBC_AUDIT_TABLE.getText().contains("Manual Exit"));

        logEnd();
    }
}
