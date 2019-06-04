package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
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
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10614")
public class TC_05_01_Referrals_Create_And_Edit extends TC_07_53_Referrals_Create {

    private static final String REFERRAL_RESULT = "No Show";


    //Bug WINGS-2579, sub-task WINGS-2721
    public void main() {

        info("Creating participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        info("E-Verify Participant");
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));
        info("Employer");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        info("Job Order");
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        ReferralCreationForm creationPage = repeatedCommonSteps(participant, jobOrder);

        logStep("Press 'Create' button");
        creationPage.createReferral();

        logStep("Press 'Edit' button on referral participantSSDetails page");
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();
        referralDetailsForm.clickButton(Buttons.Edit);

        logStep("Validate, that no errors are displayed");
        ReferralEditForm referralEditForm = new ReferralEditForm();
        CustomAssertion.softTrue("Referral edit page wasn't displayed", referralEditForm.checkResultCbPresent());

        logStep("Edit referral");
        referralEditForm.resultReferral(REFERRAL_RESULT, CommonFunctions.getCurrentDate());

        logStep("Press 'Save changes' button");
        referralEditForm.clickButton(Buttons.Save);

        logStep("Validate, that referral participantSSDetails page is displayed");
        CustomAssertion.softTrue("Referral participantSSDetails page wasn't displayed", referralEditForm.isPresent(BaseWingsForm.BaseButton.EDIT));

        logStep("Validate, that changes are saved");
        CustomAssertion.softAssertEquals(referralDetailsForm.getResult(), REFERRAL_RESULT,"Referral Status assert fail");

        logEnd();
    }
}
