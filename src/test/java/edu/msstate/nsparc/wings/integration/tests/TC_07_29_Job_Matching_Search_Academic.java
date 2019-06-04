package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10688")
public class TC_07_29_Job_Matching_Search_Academic extends BaseWingsTest {

    private static final String JOB_TITLE = "cook";

    public void main() {

        info("Creating Employer for Job Matching");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        info("Creating Job Order for Job Matching");
        JobOrderSteps.createJobOrder(employer, JOB_TITLE);

        info("Creating Participant for Job Matching");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
        ParticipantSqlFunctions.changeHighestGradeCompletedToNone(participant);

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Wagner-Peyser->Job Matching");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_JOB_MATCHING);

        logStep("Select Participant from precondition");
        JobMatchingForm jobMatchingForm = new JobMatchingForm();
        jobMatchingForm.selectParticipant(participant);

        logStep("Select Employer");
        jobMatchingForm.selectEmployer(employer);

        logStep("Check checkbox Jobs in which the Participant meet Academic Requirements->Search");
        jobMatchingForm.clickButton(Buttons.Search);

        info("Check, that There no data with different education in job orders and participant record");
        assertTrue("Assert Academic Requirement checkbox failed", new SearchResultsForm().isNothingResult());

        logStep("Uncheck checkbox Jobs in which the Participant meet Academic Requirements->Search");
        jobMatchingForm.chooseAcademicCheckBox();
        jobMatchingForm.clickButton(Buttons.Search);

        info("Education doesn't influence to search results");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_JOB_TITLE.getText(), JOB_TITLE, "Incorrect job title");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_EMPLOYER.getText(), employer.getCompanyName(), "Incorrect employer name");
    }
}
