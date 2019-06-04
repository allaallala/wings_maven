package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertFalse;


@TestCase(id = "WINGS-10585")
public class TC_04_03_Dashboard_Incomplete_Abandon extends BaseWingsTest {

    public void main() {

        info("We need incomplete record");
        Participant participant = new Participant();
        ParticipantCreationSteps.createIncompleteParticipant(participant, true, false);

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Select record->Abandon");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        staffHomeForm.abandonIncompleteParticipant(participant);

        logStep("Confirm");
        staffHomeForm.areYouSure(Popup.Yes);

        logStep("Validate, that record is deleted");
        assertFalse("Record wasn't deleted", staffHomeForm.checkIncompleteRecord(participant));

        BaseNavigationSteps.logout();
        logEnd();
    }
}

