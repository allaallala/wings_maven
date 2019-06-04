package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10634")
public class TC_05_40_Participant_View_Notes extends BaseWingsTest {
    String notesNumber = "1";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select some records and open it");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();

        logStep("Notes->add some data");
        String text = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
        NotesForm notesForm = new NotesForm();
        notesForm.addNote(text);
        participantDetailsForm.clickButton(Buttons.Done);

        logStep("Check, that notes are saved");
        participantSearchForm.clickOnSearchResult(participant);
        CustomAssertion.softTrue("Note wasn't saved", notesForm.checkNote(text));
        CustomAssertion.softAssertEquals(participantDetailsForm.getNotesCount(), notesNumber, "Notes Count wasn't changed");
        logEnd();
    }
}
