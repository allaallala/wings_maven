package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.forms.ResumeForm;
import edu.msstate.nsparc.wings.integration.forms.ViewApplicantsForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10598")
public class TC_04_11_View_Applicants_Resume extends TC_04_10_View_Applicants_Job_Order_Details {

    public void main () {
        info("We need to create Job Order first");
        JobOrder jobOrder = JobOrderSteps.createJobOrderForApplyOnline();

        logStep("Enter some parameters for searching->Search");
        ViewApplicantsForm viewApplicantsForm = searchForJobOrder(jobOrder);

        logStep("Resume");
        viewApplicantsForm.checkInternalError();
        viewApplicantsForm.openResumeForm();

        logStep("Validate Resume is shown");
        CustomAssertion.softAssertContains(ResumeForm.DIV_PARTICIPANT_NAME.getText(), participant.getFirstName(), "Incorrect participant first name");
        CustomAssertion.softAssertContains(ResumeForm.DIV_PARTICIPANT_NAME.getText(), participant.getLastName(), "Incorrect participant last name");
    }
}
