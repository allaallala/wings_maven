package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
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

@TestCase(id = "WINGS-10434")
public class TC_07_79_Referrals_Edit_Result extends BaseWingsTest {

    private static final String REFERRAL_RESULT = "No Show";

    public void main() {
        info("For editing Referral we need:");
        info("Existing Referral");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User staff = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, staff);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);

        logStep("Select some filter for searching->Search");
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        logStep("Open a referral->Edit");
        referralSearchForm.openFirstSearchResult();
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();
        referralDetailsForm.clickButton(Buttons.Edit);

        logStep("Select new data from the field Result->Save changes");
        ReferralEditForm referralEditForm = new ReferralEditForm();
        referralEditForm.resultReferral(REFERRAL_RESULT, CommonFunctions.getCurrentDate());
        referralEditForm.clickButton(Buttons.Save);

        logStep("Search this record via Participant->Wagner-Peyser->Referrals");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        logStep("Validate, that Referral has a new Result");
        assertEquals("Referral Status assert fail", REFERRAL_RESULT, referralSearchForm.getResultText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
