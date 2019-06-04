package edu.msstate.nsparc.wings.integration.tests.trade.allParticipantNotes;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_07_53_Referrals_Create;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

/**
 * View all notes for chosen participant
 * Created by a.korotkin on 10/20/2015.
 */

@TestCase(id = "WINGS-11039")
public class TC_23_04_All_Participant_Notes_View_All_Notes extends TC_07_53_Referrals_Create {

    private static final String TYPE_FIRST = "Automation test for adding notes for Participant";
    private static final String TYPE_SECOND = "Automation test for adding notes for Referral";

    public void main() {
        String tp3;
        info("Creating Participant");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        info("E-Verify Participant");
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Open notes");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.clickNotes();

        logStep("Adding Notes for Participant");
        NotesForm notes = new NotesForm();
        notes.addNoteWithoutClosing(TYPE_FIRST);

        notes.checkAddedText(TYPE_FIRST);

        notes.addNoteWithoutClosing(TYPE_FIRST);

        notes.checkAddedText(TYPE_FIRST);

        notes.addNoteWithoutClosing(TYPE_FIRST);

        notes.checkAddedText(TYPE_FIRST);
        notes.closeNotes();

        BaseNavigationSteps.logout();

        info("Creating Employer");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        info("Creating Job Order");
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        ReferralCreationForm creationPage = repeatedCommonSteps(participant, jobOrder);

        logStep("Create");
        creationPage.createReferral();

        logStep("Open notes again");
        participantDetailsForm.clickNotes();

        logStep("Adding Notes for Referral");
        notes.addNoteWithoutClosing(TYPE_SECOND);
        notes.checkAddedText(TYPE_SECOND);

        notes.addNoteWithoutClosing(TYPE_SECOND);
        notes.checkAddedText(TYPE_SECOND);

        notes.addNoteWithoutClosing(TYPE_SECOND);
        notes.checkAddedText(TYPE_SECOND);
        notes.closeNotes();

        logStep("Searching for Participant");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Try to open notes");
        participantDetailsForm.clickNotes();

        logStep("Displaying All Notes");
        String notesOnPage = notes.getNotesCount();
        notes.closeNotes();

        tp3 = String.valueOf(ParticipantSqlFunctions.getNumberOfNotes(participant));

        logStep("Checking the Number of Existing Notes");
        assertEquals("The number of notes doesn't match", tp3, notesOnPage);

    }
}