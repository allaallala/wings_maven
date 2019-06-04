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
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10689")
public class TC_07_30_Job_Matching_Search_Drivers_License extends BaseWingsTest {

    public void main() {
        String jobTitle = "cook";

        info("Creating Participant for Job Matching");


        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        ParticipantDetailSteps.changeDriversLicense(participant);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);

        info("Creating Employer for Job Matching");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        info("Creating Job Order for Job Matching");
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobTitle);

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Wagner-Peyser->Job Matching");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_JOB_MATCHING);

        logStep("Select Participant from precondition");
        JobMatchingForm jobMatchingForm = new JobMatchingForm();
        jobMatchingForm.selectParticipant(participant);

        logStep("Select Employer");
        jobMatchingForm.selectEmployer(jobOrder.getEmployer());

        logStep("Check checkbox Jobs in which the Participant meet Drivers License Requirements->Search");
        jobMatchingForm.clickButton(Buttons.Search);

        info("Check, that there no data with different drivers license data in job orders and participant record");
        assertTrue("Assert Drivers License Requirement checkbox failed", new SearchResultsForm().isNothingResult());

        logStep("There no data with different drivers license data in job orders and participant record");
        jobMatchingForm.chooseDriverLicense();
        jobMatchingForm.clickButton(Buttons.Search);

        info("Drivers license doesn't influence to search results");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_JOB_TITLE.getText(), jobTitle, "Incorrect job title");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_EMPLOYER.getText(), jobOrder.getEmployer().getCompanyName(), "Incorrect employer name");
    }
}
