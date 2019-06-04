package edu.msstate.nsparc.wings.integration.tests.trade.notes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.NotesSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant participantSSDetails information, add note and check, that this note was added successfully.
 * Created by a.vnuchko on 03.09.2015.
 */
@TestCase(id = "WINGS-10940")
public class TC_18_18_Notes_Popup_Add extends BaseWingsTest {

    public void main() {


        info("Generate some test data");
        String note = "newNote";

        info("Precondition: Create participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);

        NotesSteps.openParticipantDetailsInformation(partp);

        logStep("Add new note with valid data");
        NotesForm notesPage = new NotesForm();
        notesPage.addNote(note);

        logResult("A new Note was added to the list");
        notesPage.checkNote(note);
    }
}
