package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10692")
public class TC_07_55_Referrals_Create_Veteran_Non_Certify extends TC_07_53_Referrals_Create {

    private static final String ERROR_MESSAGE = "Agreement to Job Requirements is required.";

    public void main() {
        info("Init test data");
        initData(Constants.TRUE, Constants.TRUE);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        mainSteps();
    }

    protected void mainSteps(){
        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();

        logStep("Select some participant (veteran)");
        referralCreationForm.selectParticipant(participant);

        logStep("Select Job order is corresponding to participant record");
        referralCreationForm.selectJobOrder(jobOrder);

        logStep("Create");
        referralCreationForm.createReferral();

        logStep("Check, that error message is displayed");
        assertEquals("Error message assert fail", ERROR_MESSAGE, referralCreationForm.getAgreementErrorText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
