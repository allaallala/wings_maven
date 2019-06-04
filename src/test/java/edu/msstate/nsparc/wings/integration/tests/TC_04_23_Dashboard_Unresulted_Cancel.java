package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.CancelMenuForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10610")
public class TC_04_23_Dashboard_Unresulted_Cancel extends BaseWingsTest {
    String expectedResult = "Hired";

    public void main() {

        checkUnresultedReferral();
        ReferralDetailsForm refDetails = new ReferralDetailsForm();
        refDetails.clickButton(Buttons.Edit);

        logStep("Complete with some data all fields->Cancel");
        ReferralEditForm editForm = new ReferralEditForm();
        editForm.resultReferral(expectedResult, CommonFunctions.getCurrentDate());
        editForm.clickButton(Buttons.Cancel);
        new CancelMenuForm().areYouSure(Popup.Yes);

        logStep("Unresulted referral is opened in read mode");
        //new ReferralDetailsForm().assertIsOpen();
        assertTrue("Referral View form isn't opened", new CancelMenuForm().isPresent(BaseWingsForm.BaseButton.EDIT));

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Check, if there is already unresulted referral
     */
    public void checkUnresultedReferral() {
        info("We will check, if there is already unresulted referral");
        BaseWingsSteps.logInAs(Roles.STAFF);
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        if (!staffHomeForm.checkUnresultedReferralsPresent()) {
            Participant participant = new Participant(AccountUtils.getParticipantAccount());
            JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            User staff = new User(Roles.STAFF);
            ReferralSteps.createReferral(participant, jobOrder, staff);
            ReferralSteps.setUnresultedReferralCreationDate(participant);
            BaseWingsSteps.logInAs(Roles.STAFF);
        }

        logStep("Select unresulted referral->Details->Edit");
        staffHomeForm.selectUnresultedReferrals();
    }
}
