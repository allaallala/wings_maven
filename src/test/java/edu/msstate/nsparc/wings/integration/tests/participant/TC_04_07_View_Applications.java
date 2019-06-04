package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ViewYourApplicationsForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10593")
public class TC_04_07_View_Applications extends BaseWingsTest {


    public void main() {
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        info("We need to create Job Order too");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        jobOrder.setJobID(JobOrderSteps.createJobOrder(jobOrder, true, true));
        info("And apply for a job");
        JobOrderSteps.applyForJobOrder(participant, jobOrder);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("View Your Applications");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.viewApplications();

        logStep("Enter some parameters for searching->Search");
        ViewYourApplicationsForm applicationsForm = new ViewYourApplicationsForm();
        applicationsForm.inputJobOrderId(jobOrder.getJobID());
        applicationsForm.clickButton(Buttons.Search);
        CustomAssertion.softTrue("Incorrect Job Title",applicationsForm.getJobTitle().contains(jobOrder.getJobTitle()));

        logStep("Open one of the applications");
        applicationsForm.openJobOrderDetailsForm();

        logStep("Validate Job Order participantSSDetails");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        CustomAssertion.softTrue("Incorrect job title", jobOrderDetailsForm.getJobTitleText().contains(jobOrder.getJobTitle()));
        CustomAssertion.softTrue("Incorrect job ID", jobOrderDetailsForm.getJobTitleText().contains(jobOrder.getJobID()));
    }
}
