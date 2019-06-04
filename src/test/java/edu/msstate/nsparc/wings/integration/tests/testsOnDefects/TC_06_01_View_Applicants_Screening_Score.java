package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.ViewApplicantsForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10641")
public class TC_06_01_View_Applicants_Screening_Score extends BaseWingsTest {

    //Bug WINGS-2772, sub-task WINGS-2773
    public void main() {
        String screeningScore = "100";

        info("We need to create Job Order with question");
        JobOrder jobOrder = JobOrderSteps.createJobOrderForApplyOnline();
        info("Create Participant and apply for Job Order");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        JobOrderSteps.applyForJobOrder(participant, jobOrder);

        logStep("Log in WINGS as Employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("View Applicants");
        EmployerHomePage employerHomePage = new EmployerHomePage(jobOrder.getEmployer().getCompanyName());
        employerHomePage.viewApplicants();

        logStep("Search for Job Order");
        ViewApplicantsForm applicantsForm = new ViewApplicantsForm();
        applicantsForm.inputJobTitle(jobOrder.getJobTitle());
        applicantsForm.clickButton(Buttons.Search);
        applicantsForm.checkInternalError();

        logStep("Check Screening score");
        String[] data = applicantsForm.getDataText(); //TODO Test should be further developed.
    }
}
