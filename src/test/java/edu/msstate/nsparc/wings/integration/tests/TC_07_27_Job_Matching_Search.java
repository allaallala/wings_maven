package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
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


@TestCase(id = "WINGS-10687")
public class TC_07_27_Job_Matching_Search extends BaseWingsTest {

    private static final String JOB_TITLE = "cook";

    public void main() {
        info("Creating Employer for Job Matching");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        info("Creating Participant for Job Matching");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);

        info("Creating Job Order for Job Matching");
        JobOrderSteps.createJobOrder(employer, JOB_TITLE);

        logStep("Login as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Open Job Matching form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_JOB_MATCHING);

        logStep("Select Job Order");
        JobMatchingForm jobMatchingForm = new JobMatchingForm();
        jobMatchingForm.selectEmployer(employer);

        logStep("Select Participant");
        jobMatchingForm.selectParticipant(participant);

        logStep("Perform search");
        JobMatchingForm.TXB_JOB_TITLE.type(JOB_TITLE);
        jobMatchingForm.clickButton(Buttons.Search);

        logStep("Validate, that Job Order is found");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_JOB_TITLE.getText(), JOB_TITLE, "Incorrect job title");
        CustomAssertion.softAssertEquals(JobMatchingForm.TBC_EMPLOYER.getText(), employer.getCompanyName(), "Incorrect employer name");
    }
}
