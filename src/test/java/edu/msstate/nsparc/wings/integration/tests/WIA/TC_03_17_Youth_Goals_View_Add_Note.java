package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10573")
public class TC_03_17_Youth_Goals_View_Add_Note extends BaseWingsTest {

    private static final String GOAL_TYPE = "Basic Skills";
    private String dateSet = CommonFunctions.getCurrentDate();
    private String note = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);

        TC_03_16_Youth_Goals_View youthGoalsView = new TC_03_16_Youth_Goals_View();
        youthGoalsView.viewYouthGoal(participant, GOAL_TYPE, dateSet);

        logStep("Click [Add Note]");
        YouthGoalsDetailsForm youthGoalsDetailsForm = new YouthGoalsDetailsForm();
        youthGoalsDetailsForm.clickAddNote();

        logStep("Add note information");
        NotesForm notesForm = new NotesForm();
        notesForm.addNoteWithoutClosing(note);
        notesForm.closeNotes();

        logStep("Add note");
        youthGoalsDetailsForm = new YouthGoalsDetailsForm();
        youthGoalsDetailsForm.clickAddNote();

        assertTrue("Note was not added to notes list", notesForm.getNotesText().contains(note));
    }
}
