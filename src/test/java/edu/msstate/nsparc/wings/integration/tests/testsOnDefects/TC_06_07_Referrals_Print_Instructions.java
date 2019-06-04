package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_07_53_Referrals_Create;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10655")
public class TC_06_07_Referrals_Print_Instructions extends TC_07_53_Referrals_Create {
    private String jobTitle = "Cook";

    public void main() {

        info("Creating Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        info("E-Verify Participant");
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));
        info("Creating Employer");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        info("Creating Job Order");
        jobOrder.setJobTitle(jobTitle);
        String instructions = JobOrderSteps.createJobOrderWithInstructionsForParticipant(jobOrder);

        ReferralCreationForm referralCreationForm =  repeatedCommonSteps(participant, jobOrder);

        logStep("Create");
        referralCreationForm.createReferral();

        logStep("Click on Print button");
        ReferralDetailsForm detailsForm = new ReferralDetailsForm();
        detailsForm.print();

        logStep("Check, that Instructions for Participant are displayed");
        assertTrue("Instructions for Participant aren't displayed", detailsForm.getParticipantInstructions().contains(instructions));

        logEnd();
    }
}
