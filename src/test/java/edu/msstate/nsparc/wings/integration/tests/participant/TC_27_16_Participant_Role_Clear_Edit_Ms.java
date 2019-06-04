package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
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
import framework.AccountUtils;


/**
 * This test is specially created for checking the following functionality (clear username and edit it)
 * Created by a.vnuchko on 22.08.2016.
 */

@TestCase(id = "WINGS-11118")
public class TC_27_16_Participant_Role_Clear_Edit_Ms extends TC_27_01_Participant_Role_Admin {

    public void main() {
        //Role - administrator
        User user = new User(Roles.ADMIN);
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantRoleWithEmploymentHistory(user, participant, true);
        editClear(user, participant);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        editClear(user, participant);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        editClear(user, participant);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        editClear(user, participant);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        editClear(user, participant);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        editClear(user, participant);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        editClear(user, participant);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        editClear(user, participant);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        editClear(user, participant);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);  //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9184
        editClear(user, participant);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        editClear(user, participant);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        editClear(user, participant);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        editClear(user, participant);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        editClear(user, participant);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        editClear(user, participant);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        editClear(user, participant);
    }

    /**
     * Edit/Clear MS access username
     *
     * @param user - current user.
     */
    private void editClear(User user, Participant participant) {
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Search for participant and view its participantSSDetails information
        if (user.getParticipant().getParticipantPermissions().getPpView()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

            //(!) If user can create participant, he should confirm pop-up
            if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantSearchForm searchPage = new ParticipantSearchForm();
            searchPage.performSearchAndOpenByUser(user, participant);

            BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();

            //(!) Clear Access MS Username
            logStep("Clear Access MS Username, if possible");
            detailsPage.clearMs(user);
            //TODO specials programs is not ready?

            //(!) Edit Access MS Info
            logStep("Edit access ms info if possible");
            detailsPage.editMs(user, participant);

            BaseNavigationSteps.logout();
            divideMessage("Clear access username again to check, that other user roles can edit it");
            clearUsername(user, participant);
        }
    }

    /**
     * Login to the system, reset username
     */
    private void clearUsername(User user, Participant participant) {
        //If user can edit username, it's necessary to clear it to check other user roles.
        if (user.getParticipant().getParticipantPermissions().getPpEditEditAccessMSInfoButton()) {
            BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
            ParticipantSearchForm searchForm = new ParticipantSearchForm();
            searchForm.performSearchAndOpenByUser(user, participant);
            BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
            detailsPage.clear(); //clear username
            BaseNavigationSteps.logout();
        }
    }

}
