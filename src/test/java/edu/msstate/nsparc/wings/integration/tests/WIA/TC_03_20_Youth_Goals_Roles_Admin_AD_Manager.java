package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
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
import framework.CommonFunctions;


/**
 * Check functionality of the youth goals for administrator, area director and manager roles.
 * Created by a.vnuchko on 11.02.2016.
 */

@TestCase(id = "WINGS-10575")
public class TC_03_20_Youth_Goals_Roles_Admin_AD_Manager extends BaseWingsTest {
    private static String GOAL_TYPE = "Basic Skills";
    private static String NEW_GOAL_TYPE = "Occupational Skills";
    private String dateSet = CommonFunctions.getCurrentDate();
    private Participant participant;

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonYouthGoalsSteps(user);

        //Role - area director
        user = new User(Roles.AREADIRECTOR);
        commonYouthGoalsSteps(user);

        //Role - manager
        user = new User(Roles.MANAGER);
        commonYouthGoalsSteps(user);
    }

    /**
     * /**
     * Common steps for checking user permissions
     *
     * @param user - current user
     */
    protected void commonYouthGoalsSteps(User user) {

        //(!) Create youth goal if possible
        if (user.getYouthGoal().getGoalCreate()) {
            logStep("Create youth goal");
            AccountUtils.init();
            participant = new Participant(AccountUtils.getParticipantAccount(), true);
            YouthGoalsSteps.createYouthGoals(participant, user);
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality: edit, view, audit.
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");

        BaseWingsSteps.logInAs(user.getRole());

        if (user.getYouthGoal().getGoalView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);


            //(!) If user can create youth goals - > he should confirm pop-up window.
            if (user.getYouthGoal().getGoalCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            //(!) If user can view search results.
            YouthGoalsSearchForm searchPage = new YouthGoalsSearchForm();
            searchPage.selectParticipantByUser(user, participant);
            searchPage.clickButton(Buttons.Search);

            searchPage.openFirstSearchResult();
            YouthGoalsDetailsForm detailsPage = new YouthGoalsDetailsForm();
            detailsPage.validateYouthGoalData(participant, dateSet, GOAL_TYPE);

            //(!) Check buttons on the detail Youth Goal page.
            logStep("Check [Audit], [Edit] buttons on the page");
            detailsPage.checkButtonsPresent(user.getYouthGoal().getGoalViewEditButton(), user.getYouthGoal().getGoalViewAuditButton());

            //(!) Check edit functionality
            if (user.getYouthGoal().getGoalEdit()) {
                logStep("Check youth goal edit functionality");
                detailsPage.clickButton(Buttons.Edit);
                YouthGoalsEditForm youthGoalsEditForm = new YouthGoalsEditForm();
                youthGoalsEditForm.selectGoalType(NEW_GOAL_TYPE); //Change goal type and check, that it's correctly changed.
                youthGoalsEditForm.clickButton(Buttons.Save);
                detailsPage.validateYouthGoalData(participant, dateSet, NEW_GOAL_TYPE);
                detailsPage.clickButton(Buttons.Edit);//Return to default value.
                youthGoalsEditForm.selectGoalType(GOAL_TYPE);
                youthGoalsEditForm.clickButton(Buttons.Save);

            }

        } else {
            WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS.getMenuItem().assertIsNotPresent();
        }
        BaseNavigationSteps.logout();
    }
}
