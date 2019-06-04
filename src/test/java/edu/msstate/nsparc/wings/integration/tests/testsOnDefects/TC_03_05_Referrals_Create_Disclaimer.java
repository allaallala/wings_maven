package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_07_53_Referrals_Create;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10566")
public class TC_03_05_Referrals_Create_Disclaimer extends TC_07_53_Referrals_Create {


    //sub-task WINGS-2596
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
        String disclaimer = JobOrderSteps.createJobOrderWithDisclaimer(jobOrder);

        ReferralCreationForm referralCreationPage = repeatedCommonSteps(participant, jobOrder);

        logStep("Continue");
        referralCreationPage.clickButton(Buttons.Continue);

        logStep("Agree with Disclaimer");
        assertEquals("Disclaimer text isn't the same", disclaimer, referralCreationPage.getDisclaimerText());
        referralCreationPage.inputInitials(participant.getInitials());

        logStep("Create");
        referralCreationPage.createReferral();
        referralCreationPage.clickButton(Buttons.Done);

        logStep("Find created referral");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        BaseNavigationSteps.logout();
        logEnd();
    }
}
