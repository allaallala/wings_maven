package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsCreationForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10571")
public class TC_03_14_Youth_Goal_Create_Cancel extends BaseWingsTest {


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);

        TC_03_08_Youth_Goals_Create youthGoalsCreate = new TC_03_08_Youth_Goals_Create();
        youthGoalsCreate.fillYouthGoalDate(participant);

        logStep("Cancel youth goal creation");
        YouthGoalsCreationForm youthGoalsCreationForm = new YouthGoalsCreationForm();
        youthGoalsCreationForm.clickButton(Buttons.Cancel);
        youthGoalsCreationForm.areYouSure(Popup.Yes);

        logStep("Participants->WIA->Youth Goal->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Select participant for searching and perform Search");
        YouthGoalsSearchForm youthGoalsSearchForm = new YouthGoalsSearchForm();
        youthGoalsSearchForm.performSearch(participant);

        logStep("Check that Youth Goals weren't created");
        assertFalse("Date Goal Set and Goal Type assert fail", youthGoalsSearchForm.dateSetGoalTypePresent());
    }
}
