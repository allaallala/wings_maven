package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10508")
public class TC_01_09_Participant_Add_Employee_OSOC_Multiple_Choice extends BaseWingsTest {

    private static final String FIRST_OSOC = "Taxi drivers and chauffeurs";
    private static final String SECOND_OSOC = "Cooks, Restaurant";


    //Bug WINGS-2360 , sub-task WINGS-2409
    public void main() {


        Participant part = ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();

        logStep("Start adding new Previous Job record");
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(part);
        detailsForm.clickAddPreviousJob();

        logStep("Select OSOC");
        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm(Constants.PARTICIPANT_SS);
        addEmploymentForm.selectOsocSelfServices(FIRST_OSOC);
        addEmploymentForm.selectOsocSelfServices(SECOND_OSOC);

        logStep("Check data");
        CustomAssertion.softTrue("Incorrect first OSOC", addEmploymentForm.getOSOCValues().contains(FIRST_OSOC));
        CustomAssertion.softTrue("Incorrect second OSOC", addEmploymentForm.getOSOCValues().contains(SECOND_OSOC));
    }
}
