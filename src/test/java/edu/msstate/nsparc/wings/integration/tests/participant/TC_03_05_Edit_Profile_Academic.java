package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10565")
public class TC_03_05_Edit_Profile_Academic extends BaseWingsTest {



    public void main() {
        String degree = "Bachelor's Degree";
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();

        logStep("Add Highest Grade completed information");
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);
        detailsForm.changeHighestGradeSelfService(participant.getGrade());

        logStep("Add Academic record");
        detailsForm.addAcademicRecordSelfService();

        logStep("Check, that changes are saved");
        ParticipantDetailSteps.checkStatusCertificateParticipant(participant, degree);
    }
}
