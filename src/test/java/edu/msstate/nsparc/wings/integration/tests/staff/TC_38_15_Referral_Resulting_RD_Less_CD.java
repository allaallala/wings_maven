package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_07_90_Referral_Resulting;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * In this case the following is checked:
 * Create a referral, change his result date and make it less than the current date in the Referral Resulting.
 * Search this referral and make sure, that result date has been changed.
 * Created by a.vnuchko on 13.03.2017.
 */
@TestCase(id = "WINGS-10431")
public class TC_38_15_Referral_Resulting_RD_Less_CD extends TC_07_90_Referral_Resulting {
    private String expectedDate = CommonFunctions.getYesterdayDate();
    private String referralResult = "Canceled";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User user = new User(Roles.ADMIN);
        ReferralSteps.createReferralWithYesterdayJobOpenDate(participant, jobOrder, user);

        logStep("Change the referral creation date in order to have a possibility to change result date later");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);
        ReferralSearchForm searchPage = new ReferralSearchForm();
        searchPage.performSearchAndOpen(participant);
        new ReferralDetailsForm().clickButton(Buttons.Edit);
        ReferralEditForm editPage = new ReferralEditForm();
        editPage.inputCreationDate(expectedDate);
        editPage.clickButton(Buttons.Save);

        resultReferralAndCheck(participant, referralResult, expectedDate);
    }
}
