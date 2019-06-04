package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralResultForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10697")
public class TC_07_90_Referral_Resulting extends BaseWingsTest {

    private static final String REFERRAL_RESULT = "No Show";

    public void main() {
        info("For Referral Resulting we need:");
        info("Existing Referral");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User staff = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, staff);

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);
        resultReferralAndCheck(participant, REFERRAL_RESULT, CommonFunctions.getCurrentDate());
        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Check referral status
     * @param participant - participant
     * @param referralResult - referral result
     * @param date - result date
     */
    public void resultReferralAndCheck(Participant participant, String referralResult, String date) {

        logStep("Wagner-Peyser->Referral Resulting");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRAL_RESULTING);
        logStep("Select participant->Search");
        ReferralResultForm referralResultForm = new ReferralResultForm();
        referralResultForm.performSearch(participant);

        logStep("Select some result and Result Date");
        referralResultForm.selectResultRef(referralResult);
        referralResultForm.inputResultDate(date);

        logStep("Process referrals");
        referralResultForm.processReferrals();

        logStep("Find Referral");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        logStep("Validate, that Referral has a new Result");
        assertEquals("Referral Status assert fail", referralResult, referralSearchForm.getResultText());
    }
}
