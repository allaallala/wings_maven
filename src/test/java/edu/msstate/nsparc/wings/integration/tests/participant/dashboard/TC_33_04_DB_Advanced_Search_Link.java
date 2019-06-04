package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Test for checking advanced job search navigation for authorized users
 * Created by a.korotkin on 14.11.2016.
 */

@TestCase(id = "WINGS-11196")
public class TC_33_04_DB_Advanced_Search_Link extends BaseWingsTest {

    public void main() {
        String titleOfPage = "Job Search";
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        String jobTitle = participantHomePage.getJobTitle();
        participantHomePage.clickAdvancedSearch();
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearchByTitle(jobTitle);
        jobFindForm.waitMatchingTitlePresent(jobTitle);
        CustomAssertion.softAssertContains(jobFindForm.getSearchPageTitle(), jobTitle, "Incorrect job title.");
    }
}
