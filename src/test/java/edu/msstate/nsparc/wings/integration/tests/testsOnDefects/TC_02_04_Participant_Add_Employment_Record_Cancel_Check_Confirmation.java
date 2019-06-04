package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10543")
public class TC_02_04_Participant_Add_Employment_Record_Cancel_Check_Confirmation extends BaseWingsTest {


    //sub-task WINGS-2459
    public void main() {
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();

        logStep("Edit your profile");
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);

        logStep("Press 'Add' button");
        detailsForm.clickAddPreviousJob();

        logStep("Press 'Cancel' button");
        ParticipantAddEmploymentForm participantAddEmploymentForm = new ParticipantAddEmploymentForm(Constants.PARTICIPANT_SS);
        participantAddEmploymentForm.clickButton(Buttons.Cancel);

        logStep("Click Yes");
        participantAddEmploymentForm.areYouSure(Popup.Yes);

        logStep("Make sure Participant participantSSDetails page is displayed");
        CustomAssertion.softTrue("Participant title does not contain first name", detailsForm.getLinkTitle().contains(participant.getFirstName()));
        CustomAssertion.softTrue("Participant title does not contain last name", detailsForm.getLinkTitle().contains(participant.getLastName()));
    }
}
