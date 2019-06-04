package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create a participant, try to merge the same participant and check error message appeared.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-11268")
public class TC_39_02_Participant_Merge_Same_Participant extends BaseWingsTest {

    public void main() {
        info("Precondition: create participant to discard and to keep (the same)");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Advanced -> Data Utilities -> Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(participant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(participant);

        logResult("Verify that merge cannot be performed.");
        mergeForm.validateErrorMerge(ParticipantMergeForm.ErrorMergeType.SAME_PARTICIPANT);
    }
}
