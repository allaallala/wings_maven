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
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * This test is specially created for checking the following functionality (employment history for a participant, associated with trade and some operation buttons)
 * Created by a.vnuchko on 22.08.2016.
 */

@TestCase(id = "WINGS-11119")
public class TC_27_17_Participant_Role_Associated_Trade extends TC_27_01_Participant_Role_Admin {

    public void main() {
        TradeEnrollment trdEnrl = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(trdEnrl, Roles.ADMIN);
        User user = new User(Roles.ADMIN);

        //Role - administrator
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - staff
        user.setNewUser(Roles.STAFF);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - LVER
        user.setNewUser(Roles.LVER);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        checkOperationsFullTrade(user, trdEnrl.getParticipant());
    }

    /**
     * This method is used for checking some operation buttons, if participant is enrolled.
     *
     * @param user        - current user
     * @param participant - participant
     */
    private void checkOperationsFullTrade(User user, Participant participant) {
        logStep("Check operations buttons for " + user.getRole().toString());
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Search for participant and view its participantSSDetails information
        if (user.getParticipant().getParticipantPermissions().getPpView()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

            //(!) If user can create participant, he should confirm pop-up
            if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantSearchForm searchPage = new ParticipantSearchForm();
            searchPage.performSearchByUser(user, participant);
            searchPage.clickOnSearchResult(participant);

            BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
            detailsPage.checkOperationsNotEmpty(user);
        }
        BaseNavigationSteps.logout();
    }
}
