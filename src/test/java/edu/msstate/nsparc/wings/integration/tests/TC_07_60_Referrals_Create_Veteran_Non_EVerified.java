package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

// Author: d.poznyak

@TestCase(id = "WINGS-10693")
public class TC_07_60_Referrals_Create_Veteran_Non_EVerified extends TC_07_53_Referrals_Create {

    private static final String ERROR_MESSAGE = "The participant must be E-Verified before they can be referred to this job.";

    public void main() {

        info("Init test data");
        initData(false, true);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        ReferralCreationForm referralCreationForm = repeatedCommonSteps(participant, jobOrder);

        logStep("Create");
        referralCreationForm.createReferral();

        logStep("Check, that error message is displayed");
        assertEquals("Error message assert fail", ERROR_MESSAGE, referralCreationForm.getParticipantErrorText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
