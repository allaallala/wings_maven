package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsCreationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import framework.CommonFunctions;

public class YouthGoalsSteps extends BaseWingsSteps {

    public static void createYouthGoals(Participant participant, User user) {
        String goalType = "Basic Skills";
        String dateSet = CommonFunctions.getCurrentDate();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Constants.TRUE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS, Popup.Create);

        YouthGoalsCreationForm youthGoalsCreationForm = new YouthGoalsCreationForm();
        youthGoalsCreationForm.selectParticipantByUser(user, participant);

        youthGoalsCreationForm.selectFirstEnrollment();
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();

        youthGoalsCreationForm.inputDateSet(dateSet);
        youthGoalsCreationForm.selectGoalType(goalType);

        youthGoalsCreationForm.clickYouthProvider();
        CenterSearchForm amSearchForm = new CenterSearchForm();
        amSearchForm.selectAndReturnCenter();

        amSearchForm.clickButton(Buttons.Create);
        amSearchForm.clickButton(Buttons.Done);

        BaseNavigationSteps.logout();
    }
}
