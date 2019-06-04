package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyCreationForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import framework.Logger;

import static edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps.logout;

public class EverifySteps extends BaseWingsSteps {

    /**
     * Open e-verify search form and fill it out
     * 1. Login as a role
     * 2. Wagner-Peyser->E-Verify
     * 3. Click [Seach] in the pop-up.
     * @param participant - participant
     * @param role - user role.
     * @param caseNumber - case number
     * @return new e-verify search page.
     */
    public static EverifySearchForm openEverifySearchPage(Participant participant, Roles role, String caseNumber) {
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        eVerifyParticipant(participant, new User(Roles.STAFF));

        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Search);

        Logger.getInstance().info("Fill search fields and press Search");
        EverifySearchForm everifySearchForm = new EverifySearchForm();
        everifySearchForm.performSearchAndCheckResults(participant, caseNumber);
        return new EverifySearchForm();
    }

    public static Everify eVerifyParticipant(Participant participant, User user) {
        Everify everify = new Everify();
        createEverify(everify, participant, user);
        return everify;
    }

    public static void createEverify(Everify everify, Participant participant, User user) {
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Create);
        EverifyCreationForm creationForm = new EverifyCreationForm();
        creationForm.selectParticipantByUser(user, participant);
        creationForm.fillRequiredFields(everify);
        creationForm.isPresent(BaseWingsForm.BaseButton.CREATE);
        creationForm.clickButton(Buttons.Create);
        creationForm.passParticipationRecalculationPage();
        creationForm.clickButton(Buttons.Done);
        logout();
    }
}
