package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this test, we check if sections in the resume view are displayed correctly and can be edited.
 * Created by a.vnuchko on 30.10.2016.
 */

@TestCase(id = "WINGS-11160")
public class TC_31_02_EP_General_Resume_View extends TC_31_01_EP_General_Standard_View {

    public void main() {
        Participant participant = precondition();

        logStep("Log in to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.openMyProfile();

        logStep("Click [Resume View] button");
        BaseParticipantSsDetailsForm detailPage = new BaseParticipantSsDetailsForm(participant);
        detailPage.clickResumeView();

        logResult("Resume view is opened. Every section can be edited from here.");
        detailPage.checkButtonsResumeView();
    }
}
