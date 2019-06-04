package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

// Author: d.poznyak

@TestCase(id = "WINGS-10632")
public class TC_05_19_Participant_Create_Cancel extends BaseWingsTest {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());

        logStep("Fill in some fields");

        ParticipantCreationSteps.createIncompleteParticipant(participant, false, false);
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Check, that record wasn't created");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchWithoutCheck(participant);
        assertFalse("Participant was created", participantSearchForm.checkParticipantFound());

        BaseNavigationSteps.logout();
    }
}
