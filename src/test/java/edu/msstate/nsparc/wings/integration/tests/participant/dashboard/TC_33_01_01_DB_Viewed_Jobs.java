package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import java.util.LinkedList;
import java.util.List;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking that counter of viewed jobs works correctly
 * Created by a.korotkin on 10.11.2016.
 */

@TestCase(id = "WINGS-11192")
public class TC_33_01_01_DB_Viewed_Jobs extends BaseWingsTest {

    public void main() {
        logStep("Generate job orders to check");
        //String[] jobTitles = getJobOrderTitles();  //TODO Now job order cannot be created through database.

        logStep("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Viewing Jobs for increasing the Counter");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.searchForJobs();

        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        String num = jobFindForm.viewingSeveralJobs();

        logStep("Check the Counter");
        BaseNavigationSteps.home();
        softAssertEquals(participantHomePage.getTextOfViewed(), num, "Incorrect Number of Viewed Jobs");
    }

    /**
     * Create job orders and get their titles
     * @return job titles
     */
    private String[] getJobOrderTitles() {
        JobOrder jobOrder;
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            AccountUtils.init();
            jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            EmployerSqlFunctions.createEmployerUsingSQL(jobOrder.getEmployer());
            EmployerSqlFunctions.createJobOrderUsingSQL(jobOrder);
            list.add(jobOrder.getJobTitle());
        }
        return list.toArray(new String[list.size()]);
    }
}
