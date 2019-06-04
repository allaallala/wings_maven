package edu.msstate.nsparc.wings.integration.tests.trade.allParticipantNotes;

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


/**
 * Edit participant note and check, that changes are saved.
 * Created by a.korotkin on 10/19/2015.
 */

@TestCase(id = "WINGS-11037")
public class TC_23_02_All_Participant_Notes_Edit_Note extends BaseWingsTest {

    private static final String TYPE_FIRST = "Automation test for adding a note";
    private static final String TYPE_SECOND = "Automation test for editing a note";
    private static final String TYPE_THIRD = "Automation test for editing a note in Printable Version";

    public void main() {


        info("Creating Participant");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Opening Notes");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.clickNotes();

        logStep("Adding a Note");
        NotesForm notes = new NotesForm();
        notes.addNoteWithoutClosing(TYPE_FIRST);

        logStep("Editing a Note");
        notes.editNote(TYPE_SECOND);
        notes.checkAddedText(TYPE_SECOND);

        logStep("Opening a Printable Version");
        notes.clickPrintable();

        logStep("Editing a Note in Printable Version");
        notes.clickFirstNoteRb();
        notes.clickButton(Buttons.Edit);
        notes.inputEditedNote(TYPE_THIRD);
        notes.clickButton(Buttons.Save);

        notes.checkAddedText(TYPE_THIRD);
    }
}