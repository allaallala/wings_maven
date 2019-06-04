package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralResultForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10698")
public class TC_07_94_Referral_Resulting_Job_Order extends BaseWingsTest {

    public void main() {
        info("For Referral Resulting we need Existing Referral");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User staff = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, staff);

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep(" Wagner-Peyser->Referral Resulting");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRAL_RESULTING);

        logStep("Select Job Order from preconditions");
        ReferralResultForm referralResultForm = new ReferralResultForm();
        referralResultForm.performSearch(jobOrder);

        logStep("Click on Referral");
        referralResultForm.openFirstSearchResult();
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();

        logStep("Check that referral doesnt have a result");
        assertFalse("Referral has a result", referralDetailsForm.checkResultDatePresent());
    }
}
