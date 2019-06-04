package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.YouthGoalsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10557")
public class TC_03_01_Youth_Goals_Search extends BaseWingsTest {
    private static final String GOAL_TYPE = "Basic Skills";
    private String dateSet = CommonFunctions.getCurrentDate();


    public void main() {

        info("First we need to create Youth Goals");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        User user = new User(Roles.ADMIN);
        YouthGoalsSteps.createYouthGoals(participant, user);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS, Popup.Search);

        logStep("Select some data for searching(1 parameter)->Search");
        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);

        logStep("Validate search result");
        CustomAssertion.softAssertEquals(youthGoalsSearchForm.getDateSetText(), dateSet, "Date Goal Set assert fail");
        CustomAssertion.softAssertEquals(youthGoalsSearchForm.getGoalTypeText(), GOAL_TYPE, "Goal Type assert fail");

        logEnd();
        BaseNavigationSteps.logout();
    }
}
