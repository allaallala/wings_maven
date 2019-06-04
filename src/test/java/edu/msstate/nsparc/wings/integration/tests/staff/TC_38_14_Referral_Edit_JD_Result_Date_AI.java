package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
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
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Edit job development, result datea and additional information of the chosen referral. Then check, that information
 * was saved.
 * Created by a.vnuchko on 13.03.2017.
 */

@TestCase(id = "WINGS-11254")
public class TC_38_14_Referral_Edit_JD_Result_Date_AI extends BaseWingsTest {
    private String result = "Canceled";
    private String resultDate = CommonFunctions.getCurrentDate();
    private String additionalInfo = "My baby shot me down";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User admin = new User(Roles.ADMIN);
        ReferralSteps.createReferral(participant, jobOrder, admin);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);

        logStep("Select some filter for searching->Search");
        ReferralSearchForm searchPage = new ReferralSearchForm();
        searchPage.performSearchAndOpen(participant);

        logStep("Open a referral->Edit");
        ReferralDetailsForm detailsPage = new ReferralDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Change some parameters:  radio button Job Development, add additional information, result and result"
                + "date. Save changes");
        ReferralEditForm editPage = new ReferralEditForm();
        editPage.resultReferral(result, resultDate);
        editPage.makeJobDevelopmentYes();
        editPage.addAdditionalInfo(additionalInfo);
        editPage.clickButton(Buttons.Save);

        logResult("Check, that all information was saved.");
        detailsPage.validateJoResultAddInfo(Constants.TRUE, result, resultDate, additionalInfo);
    }
}
