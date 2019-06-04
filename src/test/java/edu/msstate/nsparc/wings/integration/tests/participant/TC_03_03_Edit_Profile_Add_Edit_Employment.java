package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10562")
public class TC_03_03_Edit_Profile_Add_Edit_Employment extends BaseWingsTest {


    public void main() {
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();

        logStep("Add Employment record");
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);
        detailsForm.addPreviousJobSelfServices();

        logStep("Check, that changes are saved");
        CustomAssertion.softTrue("Incorrect previous job text", detailsForm.getPreviousJobPageText().contains("Cook at Automation"));

        logStep("Click Edit for added Employment");
        detailsForm.editEmploymentSelfService();

        logStep("Edit information about previous job");
        ParticipantAddEmploymentForm employmentForm = new ParticipantAddEmploymentForm(Constants.PARTICIPANT_SS);
        employmentForm.inputJobTitle("Edited");
        employmentForm.clickButton(Buttons.Save);

        logStep("Check, that changes are saved");
        CustomAssertion.softTrue("Changes are not saved", detailsForm.getPreviousJobPageText().contains("Edited at Automation"));
    }
}
