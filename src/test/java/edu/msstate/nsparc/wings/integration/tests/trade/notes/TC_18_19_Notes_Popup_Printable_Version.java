package edu.msstate.nsparc.wings.integration.tests.trade.notes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.NotesSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant participantSSDetails information, click the [Notes] button and click [Printable version] link.
 * Created by a.vnuchko on 09.09.2015.
 */

@TestCase(id = "WINGS-10941")
public class TC_18_19_Notes_Popup_Printable_Version extends BaseWingsTest {

    public void main() {


        openPrintableVersion();

        logResult("The Print Notes Screen is shown");
        //new PrintNotesForm().assertIsOpen();
    }

    /**
     * Open printable version for defined note record
     */
    public void openPrintableVersion() {
        info("Precondition: Create participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Constants.TRUE, Constants.FALSE);

        NotesSteps.openParticipantDetailsInformation(partp);

        logStep("Click the [Notes] button on any record associated with a participant");
        NotesForm notesPage = new NotesForm();
        notesPage.clickNotesLink();

        logStep("Click a 'Printable Version' link");
        notesPage.clickPrintable();
    }
}
