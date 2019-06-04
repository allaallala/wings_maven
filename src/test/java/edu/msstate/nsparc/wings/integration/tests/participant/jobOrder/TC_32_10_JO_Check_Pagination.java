package edu.msstate.nsparc.wings.integration.tests.participant.jobOrder;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.korotkin on 11/18/2016.
 */

@TestCase(id = "WINGS-11185")
public class TC_32_10_JO_Check_Pagination extends BaseWingsTest {

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        info("Check Pagination");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.searchForJobs();
        JobFindForm jobFindForm = new JobFindForm();
        jobFindForm.checkPaginationIsDisplayed();
    }
}
