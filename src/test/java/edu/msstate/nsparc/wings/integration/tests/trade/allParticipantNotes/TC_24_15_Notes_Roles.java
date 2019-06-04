package edu.msstate.nsparc.wings.integration.tests.trade.allParticipantNotes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check functionality of the 'Notes' module using different user roles.
 * Created by a.vnuchko on 03.03.2016.
 */

@TestCase(id = "WINGS-11075")
public class TC_24_15_Notes_Roles extends BaseWingsTest {
    private String note = "Notta-notta";
    Participant participant;

    public void main() {
        info("Creating Participant");
        participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        //Administrator
        User user = new User(Roles.ADMIN);
        commonNotesSteps(user);

        //Area Director
        user.setNewUser(Roles.AREADIRECTOR);
        commonNotesSteps(user);

        //Manager
        user.setNewUser(Roles.MANAGER);
        commonNotesSteps(user);

        //Staff
        user.setNewUser(Roles.STAFF);
        commonNotesSteps(user);

        //Intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonNotesSteps(user);

        //Trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonNotesSteps(user);

        //Rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonNotesSteps(user);

        //Everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonNotesSteps(user);

        //WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonNotesSteps(user);

        //WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonNotesSteps(user);

        //Project code administrator
        user.setNewUser(Roles.PCADMIN); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9123
        commonNotesSteps(user);

        //DVOP Veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonNotesSteps(user);

        //LVER
        user.setNewUser(Roles.LVER);
        commonNotesSteps(user);

        //Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonNotesSteps(user);

        //LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonNotesSteps(user);

        //WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonNotesSteps(user);
    }

    /**
     * Common actions to check user permissions in the 'Notes' module
     *
     * @param user - current user
     */
    private void commonNotesSteps(User user) {
        logStep("Search for participant and open its participantSSDetails page");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view notes.
        if (user.getNote().getNotesView()) {
            divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

            //(!) If user can create a new participant, he have to click button on the pop-up.
            if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
            participantSearchForm.performSearchAndOpen(participant);
            BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();

            logStep("Open notes page and check functionality");
            detailsPage.clickNotes();
            createEditViewNote(user);
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Create, edit and view note or only view it.
     *
     * @param user - current user
     */
    private void createEditViewNote(User user) {
        NotesForm notes = new NotesForm();

        //(!) Check, that is possible to create a note
        logStep("Create note");
        if (user.getNote().getNotesCreate()) {
            setNewNoteValue();
            notes.addNoteWithoutClosing(note);
        }

        //(!) Check, that is possible to view a note
        logStep("View note");
        notes.checkAddedText(note);

        //(!) Check, that is possible to edit a note
        logStep("Edit");
        notes.checkPostButtonPresent(user.getNote().getNotesEditPostNewButton());

        if (user.getNote().getNotesEditRadioButton()) {
            logStep("Check edit radiobutton");
            notes.clickPrintable();
            notes.clickFirstNoteRb();
            notes.clickButton(Buttons.Edit);
            setNewNoteValue();
            notes.inputEditedNote(note);
            notes.clickButton(Buttons.Save);
            notes.validateNoteData(note, CommonFunctions.getCurrentDate());
            notes.clickButton(Buttons.Done);
        }
        notes.closeNotes();
    }

    /**
     * Set new value for the note.
     */
    private void setNewNoteValue() {
        this.note = CommonFunctions.getRandomAlphanumericalCode(10);
    }

}
