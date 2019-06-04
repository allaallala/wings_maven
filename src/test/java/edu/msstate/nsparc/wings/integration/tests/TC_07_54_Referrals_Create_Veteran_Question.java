package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10691")
public class TC_07_54_Referrals_Create_Veteran_Question extends TC_07_53_Referrals_Create {

    public void main() {

        info("Init test data");
        initData(true, true);
        JobOrderSteps.createJobOrder(jobOrder, true, true, true);
        mainSteps();
    }

    protected void mainSteps(){
        ReferralCreationForm referralCreationForm = repeatedCommonSteps(participant, jobOrder);

        logStep("Continue");
        referralCreationForm.clickButton(Buttons.Continue);

        logStep("Answer the question");
        referralCreationForm.clickAnswer();
        referralCreationForm.clickButton(Buttons.Continue);

        logStep("Create");
        referralCreationForm.clickButton(Buttons.Done);

        logStep("Find created referral");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);

        BaseNavigationSteps.logout();
        logEnd();
    }
}
