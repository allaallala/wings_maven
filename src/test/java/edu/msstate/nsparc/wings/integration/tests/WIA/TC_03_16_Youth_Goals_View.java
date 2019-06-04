package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.YouthGoalsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10572")
public class TC_03_16_Youth_Goals_View extends BaseWingsTest {

    private static final String GOAL_TYPE = "Basic Skills";
    private String dateSet = CommonFunctions.getCurrentDate();


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        viewYouthGoal(participant, GOAL_TYPE, dateSet);

        YouthGoalsDetailsForm youthGoalsDetailsForm = new YouthGoalsDetailsForm();
        youthGoalsDetailsForm.clickButton(Buttons.Done);
        logEnd();
        BaseNavigationSteps.logout();
    }

    /**
     * View youth goal
     *
     * @param participant - participant
     * @param typeGoal    - goal type
     * @param setDate     - data set
     */
    public void viewYouthGoal(Participant participant, String typeGoal, String setDate) {
        info("First we need to create Youth Goals");
        User user = new User(Roles.ADMIN);
        YouthGoalsSteps.createYouthGoals(participant, user);

        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participants->WIA->Youth Goal->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Enter parameters for searching");
        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.selectParticipant(participant);
        youthGoalsSearchForm.clickButton(Buttons.Search);

        logStep("Select some Youth Goal and open it");
        youthGoalsSearchForm.openFirstSearchResult();

        logStep("Validate Youth Goal data");
        YouthGoalsDetailsForm detailsForm = new YouthGoalsDetailsForm();
        detailsForm.validateYouthGoalData(participant, setDate, typeGoal);
    }
}
