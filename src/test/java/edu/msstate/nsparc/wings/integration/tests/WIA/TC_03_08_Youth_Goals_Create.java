package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsCreationForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10440")
public class TC_03_08_Youth_Goals_Create extends BaseWingsTest {
    private static final String GOAL_TYPE = "Basic Skills";
    private String dateSet = CommonFunctions.getCurrentDate();
    private static final String CENTER_NAME = "Golden Triangle Region";


    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        fillYouthGoalDate(participant);

        createYouthGoalSteps6to8(participant);
        logEnd();
        BaseNavigationSteps.logout();
    }

    /**
     * Fill out youth goal date
     *
     * @param participant participant
     */
    public void fillYouthGoalDate(Participant participant) {
        info("First we need participant and WIA Enrollment");
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        createYouthGoalSteps1to5(participant);
    }

    /**
     * Create some steps for youth goal
     *
     * @param participant - participant youth
     */
    public void createYouthGoalSteps1to5(Participant participant) {
        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participants->WIA->Youth Goal->Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Select Youth Participant");
        YouthGoalsCreationForm youthGoalsCreationForm = new YouthGoalsCreationForm();
        try {
            youthGoalsCreationForm.selectParticipant(participant);
        } catch (AssertionError e) {
            throw new AssertionError(e);
        }
        logStep("Select WIN Job Center as Youth Provider");
        youthGoalsCreationForm.selectYouthProvider(CENTER_NAME);

        logStep("Fill all required fields");
        youthGoalsCreationForm.inputDateSet(dateSet);
        youthGoalsCreationForm.selectGoalType(GOAL_TYPE);
        logStep("Select WIA Enrollment");
        youthGoalsCreationForm.selectFirstEnrollment();
    }

    /**
     * This method is ised for creating youth goal (steps 6 to 8)
     *
     * @param participant - participant
     */
    public void createYouthGoalSteps6to8(Participant participant) {
        logStep("Create");
        YouthGoalsCreationForm youthGoalsCreationForm = new YouthGoalsCreationForm();
        youthGoalsCreationForm.clickButton(Buttons.Create);
        youthGoalsCreationForm.clickButton(Buttons.Done);

        logStep("Find new created Youth Goal");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);

        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);

        logStep("Validate search result");
        CustomAssertion.softAssertEquals(youthGoalsSearchForm.getDateSetText(), dateSet, "Date Goal Set assert fail");
        CustomAssertion.softAssertEquals(youthGoalsSearchForm.getGoalTypeText(), GOAL_TYPE, "Goal Type assert fail");
    }
}
