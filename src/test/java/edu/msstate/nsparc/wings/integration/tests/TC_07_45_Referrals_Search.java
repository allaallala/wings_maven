package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


// Author: d.poznyak

@TestCase(id = "WINGS-10690")
public class TC_07_45_Referrals_Search extends BaseWingsTest {

    public void main() {

        info("For Referral search we need:");
        info("Existing Referral");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User staff = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, staff);

        logStep("Login to the System");
        LoginForm loginForm = new LoginForm();
        loginForm.loginStaff();
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep(" Wagner-Peyser->Referrals");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);

        logStep("Search");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Select some filter for searching->Search");
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        logStep("Validate Search result");
        CustomAssertion.softAssertEquals(referralSearchForm.getFirstName(), participant.getFirstName(), "Participant First Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getLastName(), participant.getLastName(),"Participant Last Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getSSNFromTitle(), participant.getSsn().substring(5), "Participant SSN assert fail");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
