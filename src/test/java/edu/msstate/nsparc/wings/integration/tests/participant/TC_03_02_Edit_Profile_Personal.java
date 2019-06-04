package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10559")
public class TC_03_02_Edit_Profile_Personal extends BaseWingsTest {

    public void main() {

        String editTItle = "Identification";
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Open Home page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);

        logStep("Edit your profile");
        participantHomePage.openMyProfile();
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);
        detailsForm.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_IDENTIFICATION);

        logStep("Change some parameters from personal information");
        Participant editedParticipant = new Participant();
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(editTItle);
        editPage.inputFirstLastName(editedParticipant.getFirstName(), editedParticipant.getLastName());
        editPage.clickButton(Buttons.Save);

        logStep("Check, that changes are saved");
        CustomAssertion.softTrue("Link does not contain first name", detailsForm.getLinkTitle().contains(editedParticipant.getFirstName()));
        CustomAssertion.softTrue("Link does not contain last name", detailsForm.getLinkTitle().contains(editedParticipant.getLastName()));
    }
}
