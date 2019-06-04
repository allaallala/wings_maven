package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check permissions for an administrator to check participant functionality.
 * Created by a.vnuchko on 03.08.2016.
 */

@TestCase(id = "WINGS-11103")
public class TC_27_01_Participant_Role_Admin extends BaseWingsTest {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        User user = new User(Roles.ADMIN);
        commonSteps(participant, user);
    }

    /**
     * Describes common steps for checking participant functionality using different user roles
     *
     * @param user - current user.
     */
    protected void commonSteps(Participant participant, User user) {
        participant.setCertification("Oracle");
        participant.setOsoc("Cooks, Restaurant");

        //(!) Create new participant, if user has permissions to do it.
        ParticipantCreationSteps.createParticipantWithEmploymentByUser(participant, user);

        logStep("Search for participant and view its participantSSDetails information");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Search for participant and view its participantSSDetails information
        if (user.getParticipant().getParticipantPermissions().getPpView()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

            //(!) If user can create participant, he should confirm pop-up
            if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantSearchForm searchPage = new ParticipantSearchForm();
            searchPage.performSearchAndOpen(participant);

            ParticipantDetailSteps.validateParticipant(user, participant);

            logStep("Check different edit functionality, if user has permissions to do it.");
            ParticipantDetailSteps.checkEditFunctionality(user, participant);

        } else {
            WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS.getMenuItem().assertIsNotPresent();
        }
        BaseNavigationSteps.logout();
    }
}
