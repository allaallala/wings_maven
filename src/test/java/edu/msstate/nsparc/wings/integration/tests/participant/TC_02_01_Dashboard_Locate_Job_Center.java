package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10535")
public class TC_02_01_Dashboard_Locate_Job_Center extends BaseWingsTest {

    public void main() {


        info("We need to create Participant first");
        ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Home");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);

        logStep("Find a WIN Job Center");
        participantHomePage.findJobCenter();

        logStep("Contact information about Job center is shown");
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER);
        jobCenterSearchForm.checkJobCenterAddress();
    }
}
