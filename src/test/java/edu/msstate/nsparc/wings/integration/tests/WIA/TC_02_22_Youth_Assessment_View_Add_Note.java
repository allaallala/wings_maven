package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10552")
public class TC_02_22_Youth_Assessment_View_Add_Note extends TC_02_21_Youth_Assessment_View {

    private static final String NOTE = CommonFunctions.getRandomLiteralCode(20);


    public void main() {
        Assessment assessment = baseSteps();
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);
        AssessmentSearchForm searchForm = new AssessmentSearchForm();
        searchForm.performSearchAndOpen(assessment);

        logStep("Click [Add note] button");
        AssessmentDetailsForm youthAssessmentDetailsForm = new AssessmentDetailsForm();
        youthAssessmentDetailsForm.addNote();

        logStep("Fill out note information");
        NotesForm notesForm = new NotesForm();
        notesForm.addNoteWithoutClosing(NOTE);
        notesForm.closeNotes();

        logStep("Add note");
        youthAssessmentDetailsForm.addNote();

        assertTrue("Note was not added to notes list", notesForm.getNotesText().contains(NOTE));
    }
}
