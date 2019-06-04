package edu.msstate.nsparc.wings.integration.tests.participant.winJobCenter;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static edu.msstate.nsparc.wings.integration.enums.Buttons.Search;
import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Checking serching for WIN Job Centers by City and ZipCode
 * Created by a.korotkin on 11/17/2016.
 */

@TestCase(id = "WINGS-11181")
public class TC_32_06_WJC_Searching_By_Only_City_And_Only_ZipCode extends BaseWingsTest {
    private String city = "Amory";
    private String zipCode = "38821";

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Going to WIN Job Center");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goWinJobCenterS_S();
        JobCenterSearchForm jobCenterSearchForm = new JobCenterSearchForm(Constants.EMPLOYER_SS);

        logStep("Searching by City only");
        jobCenterSearchForm.typeCity(city);
        jobCenterSearchForm.clickButton(Search);
        softTrue("Searching by City doesn't work", jobCenterSearchForm.getFirstAddress().contains(city));

        logStep("Searching by ZipCode only");
        jobCenterSearchForm.clearCity();
        jobCenterSearchForm.typeZipCode(zipCode);
        jobCenterSearchForm.clickButton(Search);
        softTrue("Searching by ZipCode doesn't work", jobCenterSearchForm.getFirstAddress().contains(zipCode));
    }
}
