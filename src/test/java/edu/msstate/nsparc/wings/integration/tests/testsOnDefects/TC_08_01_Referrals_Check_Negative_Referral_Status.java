package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
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
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10700")
public class TC_08_01_Referrals_Check_Negative_Referral_Status extends BaseWingsTest {

    String resultValue = "Negative Referral";

    public void main() {

        info("Creating Referral");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User user = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, user);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);

        logStep("Check that Negative Referral is displayed as Search filter");
        ReferralSearchForm searchForm = new ReferralSearchForm();
        CustomAssertion.softTrue("Negative Referral isn't displayed", !searchForm.checkResultValue(resultValue));

        logStep("Open any Referral record");
        searchForm.performSearchAndOpen(participant);
        logStep("Change to Edit form");
        ReferralDetailsForm detailsForm = new ReferralDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Check that Negative Referral is not displayed in result drop-down");
        ReferralEditForm editForm = new ReferralEditForm();
        CustomAssertion.softTrue("Negative Referral should not be displayed", !(editForm.checkResultValue(resultValue)));
    }
}
