package edu.msstate.nsparc.wings.integration.tests.trade.allParticipantNotes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Checking different roles and possibilities for working with note
 * Created by a.korotkin on 10/20/2015.
 */

@TestCase(id = "WINGS-11038")
public class TC_23_03_All_Participant_Notes_Edit_Security extends BaseWingsTest {

    private static final String TYPE_FIRST = "Automation test for adding a note";
    private static final String TYPE_SECOND = "Automation test for editing a note by Admin";
    private static final String TYPE_THIRD = "Automation test for editing a note in Printable Version by Admin";
    private static final String TYPE_FOURTH = "Automation test for editing a note by pcAdmin";
    private static final String TYPE_FIFTH = "Automation test for editing a note in Printable Version by pcAdmin";

    public void main() {


        info("Creating Participant");
        Participant participant = new Participant() ;
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        searchForParticipant(participant);

        logStep("Try to open note");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.clickNotes();

        logStep("Adding Note");
        NotesForm notes = new NotesForm();
        notes.addNoteWithoutClosing(TYPE_FIRST);
        notes.closeNotes();

        BaseNavigationSteps.logout();

        logStep("Log in to WINGS as another user");
        LoginForm loginForm = new LoginForm();
        loginForm.loginStaff02();
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.clickButton(Buttons.Continue);

        searchForParticipant(participant);

        logStep("Trying to edit a Note");
        participantDetailsForm.clickNotes();
        notes.editImpossible();

        logStep("Opening version for print");
        notes.clickPrintable();

        logStep("Editing a Note in Printable Version");
        notes.checkFirstNoteNotPresent();

        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginAdminDashboard();

        searchForParticipant(participant);

        logStep("Opening notes");
        participantDetailsForm.clickNotes();

        logStep("Editing a Note");
        notes.editNote(TYPE_SECOND);
        notes.closeNotes();

        notes.checkNote(TYPE_SECOND);

        logStep("Open version for print");
        notes.clickPrintable();

        logStep("Editing a Note in Printable Version");
        notes.clickFirstNoteRb();
        notes.clickButton(Buttons.Edit);
        notes.inputEditedNote(TYPE_THIRD);
        notes.clickButton(Buttons.Save);

        notes.checkAddedText(TYPE_THIRD);

        BaseNavigationSteps.logout();

        BaseWingsSteps.logInAs(Roles.ADMIN);

        searchForParticipant(participant);

        logStep("Try to open notes again");
        participantDetailsForm.clickNotes();

        logStep("Editing a Note");
        notes.editNote(TYPE_FOURTH);

        notes.checkAddedText(TYPE_FOURTH);

        logStep("Open again version for print");
        notes.clickPrintable();

        logStep("Editing a Note in Printable Version");
        notes.clickFirstNoteRb();
        notes.clickButton(Buttons.Edit);
        notes.inputEditedNote(TYPE_FIFTH);
        notes.clickButton(Buttons.Save);
        notes.checkAddedText(TYPE_FIFTH);

    }

    /**
     * Searching for participant
     * @param participant - participant to be found.
     */
    private void searchForParticipant(Participant participant){
        logStep("Searching for Participant");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndOpen(participant);
    }
}
