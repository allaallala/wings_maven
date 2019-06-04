package edu.msstate.nsparc.wings.integration.tests.trade.notes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.NotesSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Open participant participantSSDetails information, click the [Notes] button, fill out some notes and click 'Close' icon. Check, that new note isn't created.
 * Created by a.vnuchko on 09.09.2015.
 */

@TestCase(id = "WINGS-10943")
public class TC_18_21_Notes_Popup_Close extends BaseWingsTest {
    String expectedCount = "0";

    public void main() {


        info("Generate some test data");
        String note = "newNote";

        info("Precondition: Create participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Constants.TRUE, Constants.FALSE);

        NotesSteps.openParticipantDetailsInformation(partp);

        logStep("Click the [Notes] button on any record associated with a participant");
        NotesForm notesPage = new NotesForm();
        notesPage.clickNotesLink();

        logStep("Fill out an 'Add Note' field with valid data");
        notesPage.inputNote(note);

        logStep("Click the 'Close' icon");
        notesPage.closeNotes();

        logResult("A new Note wasn't created");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        CustomAssertion.softTrue("Incorrect note count", expectedCount.equals(detailsPage.getNotesCount()));
    }
}
