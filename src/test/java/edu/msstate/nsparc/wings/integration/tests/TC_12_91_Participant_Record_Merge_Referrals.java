package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
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


@TestCase(id = "WINGS-10805")
public class TC_12_91_Participant_Record_Merge_Referrals extends TC_12_88_Participant_Record_Merge {

    public void main() {
        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        Participant discardParticipant = new Participant(participantAccount);
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User user = new User(Roles.STAFF);
        ReferralSteps.createReferral(discardParticipant, jobOrder, user);

        createSecondParticipant(participantAccount, discardParticipant);

        ParticipantMergeForm mergeForm = mergeDiscardKeepParticipant(discardParticipant, keepParticipant);

        logStep("Select some parameters for merging (Referral)");
        mergeForm.selectParametersMergeReferral(jobOrder);

        logStep("Merge");
        mergeForm.completeMerging();

        logStep("Open merged record");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(keepParticipant);

        logStep("Check, that information is merged");
        CustomAssertion.softAssertEquals(referralSearchForm.getFirstName(), keepParticipant.getFirstName(),"Participant First Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getLastName(), keepParticipant.getLastName(),"Participant Last Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getSSNFromTitle(), keepParticipant.getSsn().substring(Constants.EMAIL_LENGTH), "Participant SSN assert fail");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
