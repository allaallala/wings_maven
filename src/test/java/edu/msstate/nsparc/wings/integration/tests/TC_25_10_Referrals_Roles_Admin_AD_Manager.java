package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
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


/**
 * Check Referrals for different user roles. Roles: administrator, area director, manager.
 * Created by a.korotkin on 03.06.2016.
 */

@TestCase(id = "WINGS-11087")
public class TC_25_10_Referrals_Roles_Admin_AD_Manager extends BaseWingsTest {
    private Participant participant;
    JobOrder jobOrder;
    String date;
    private static final String DATE_EDIT = CommonFunctions.getYesterdayDate();
    private String initialResult;
    private static final String REFERRAL_RESULT = "No Show";

    public void main() {
        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonReferralSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonReferralSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonReferralSteps(user);
    }

    /**
     * Common steps for checking user permissions.
     *
     * @param user - user type
     */
    void commonReferralSteps(User user) {
        //(!) Check, if user can create a referral.
        if (user.getReferral().getReferralsCreate()) {
            logStep("Create new referral");
            AccountUtils.init();
            initialResult = "Unresulted";
            date = CommonFunctions.getCurrentDate();
            participant = new Participant();
            jobOrder = new JobOrder(AccountUtils.getEmployerAccount());

            ReferralSteps.createReferral(participant, jobOrder, user);
        }

        checkOtherFunctionality(user);
    }

    /**
     * Open service participantSSDetails page
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality of referrals");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view referral
        if (user.getReferral().getReferralsView()) {
            logStep("Check view functionality");

            divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);

            //(!) If user can create, hence he can search. Pop-up confirmation is required.
            if (user.getReferral().getReferralsCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ReferralSearchForm referralSearchForm = new ReferralSearchForm();
            referralSearchForm.performSearchAndOpen(participant);
            ReferralDetailsForm detailsPage = new ReferralDetailsForm();
            detailsPage.validateInformation(participant, jobOrder, date, initialResult);

            logStep("Check buttons [Edit], [Audit], [Print], [Delete] present or not on the page.");
            detailsPage.checkReferralButtons(user);

            if (user.getReferral().getReferralsEdit()) {
                logStep("Check edit functionality and result (if possible)");

                initialResult = REFERRAL_RESULT;
                detailsPage.clickButton(Buttons.Edit);
                ReferralEditForm editPage = new ReferralEditForm();
                editPage.inputCreationDate(DATE_EDIT);

                if (user.getReferral().getReferralsResultEdit()) {
                    logStep("Check result functionality");
                    editPage.selectRefResult(REFERRAL_RESULT);
                    initialResult = REFERRAL_RESULT;
                    editPage.inputDateResult(CommonFunctions.getCurrentDate());
                }
                date = DATE_EDIT;
                editPage.clickButton(Buttons.Save);
                detailsPage.validateInformation(participant, jobOrder, date, initialResult);

            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRAL_RESULTING, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }

}
