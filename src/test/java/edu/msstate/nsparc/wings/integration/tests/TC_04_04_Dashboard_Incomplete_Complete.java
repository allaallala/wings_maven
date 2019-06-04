package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.PersonalInformationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;


@TestCase(id = "WINGS-10587")
public class TC_04_04_Dashboard_Incomplete_Complete extends BaseWingsTest {


    public void main() {

        info("We need incomplete record");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        //ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ParticipantCreationSteps.createIncompleteParticipant(participant, true, false);

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Select record and open it");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        staffHomeForm.openIncompleteParticipant(participant);

        logStep("Be sure that all data you have entered are saved");
        new PersonalInformationForm().checkPartipData(participant);

        logStep("Complete it");
        ParticipantCreationSteps.completeIncompleteParticipant(participant, Constants.TRUE, Constants.FALSE);
        logStep("New participant record is created, it disappears from Incomplete participants records");
        assertFalse("Record wasn't removed from list", staffHomeForm.checkIncompleteRecord(participant));

        BaseNavigationSteps.logout();
    }
}
