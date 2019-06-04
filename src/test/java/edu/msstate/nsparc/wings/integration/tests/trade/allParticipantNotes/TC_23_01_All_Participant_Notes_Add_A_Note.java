package edu.msstate.nsparc.wings.integration.tests.trade.allParticipantNotes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.PrintNotesForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Add a new note to participant and check, that is successfully added.
 * Created by a.korotkin on 10/16/2015.
 */
@TestCase(id = "WINGS-10441")
public class TC_23_01_All_Participant_Notes_Add_A_Note extends BaseWingsTest {

    private static final String TYPE_FIRST = "Automation test for adding note";
    private static final String TYPE_SECOND = "Automation test for adding note in Printable Version";

    public void main() {


        info("Creating Participant");
        Participant participant = new Participant() ;
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Opening Notes");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.clickNotes();

        logStep("Adding Note");
        NotesForm notes = new NotesForm();
        notes.addNoteWithoutClosing(TYPE_FIRST);
        notes.closeNotes();

        notes.checkNote(TYPE_FIRST);

        logStep("Opening a Printable Version");
        notes.clickPrintable();
        new PrintNotesForm().clickAndWait(BaseWingsForm.BaseButton.DONE);

        logStep("Adding Note");
        participantDetailsForm.clickNotes();
        notes.inputNote(TYPE_SECOND);
        notes.postNewNote();

        notes.checkAddedText(TYPE_SECOND);
    }
}
