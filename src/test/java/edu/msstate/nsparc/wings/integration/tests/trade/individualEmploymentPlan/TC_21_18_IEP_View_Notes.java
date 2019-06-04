package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open individual employment search form, find existing IEP. Click the participant's name and check notes.
 * Created by a.vnuchko on 27.08.2015.
 */

@TestCase(id = "WINGS-11010")
public class TC_21_18_IEP_View_Notes extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);
        String note = "new note";

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Notes] button");
        NotesForm notesPage = new NotesForm();
        notesPage.addNote(note);

        logResult("A pop-up appears and contains the notes for a selected  participant");
        notesPage.checkNote(note);
    }
}
