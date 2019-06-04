package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsEditForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.YouthGoalsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;


@TestCase(id = "WINGS-10574")
// Author: d.poznyak
public class TC_03_19_Youth_Goals_Edit extends BaseWingsTest {

    private static final String GOAL_TYPE = "Occupational Skills";

    public void main() {
        info("First we need to create Youth Goals");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        User user = new User(Roles.ADMIN);
        YouthGoalsSteps.createYouthGoals(participant, user);

        logStep("Log in to WINGS");
        LoginForm loginForm = new LoginForm();
        loginForm.loginAdmin();
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep("Participants->WIA->Youth Goal->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Enter parameters for searching");
        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);

        logStep("Select some Youth Goal and open it");
        youthGoalsSearchForm.openFirstSearchResult();

        logStep("Click Edit");
        YouthGoalsDetailsForm youthGoalsDetailsForm = new YouthGoalsDetailsForm();
        youthGoalsDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit one parameters->Save changes");
        YouthGoalsEditForm youthGoalsEditForm = new YouthGoalsEditForm();
        youthGoalsEditForm.selectGoalType(GOAL_TYPE);
        youthGoalsEditForm.clickButton(Buttons.Save);

        logStep("Search changed Youth Goal and be sure that all changes are saved");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);
        youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);
        assertEquals("Goal Type assert fail", GOAL_TYPE, youthGoalsSearchForm.getGoalTypeText());

        logEnd();
        BaseNavigationSteps.logout();
    }
}
