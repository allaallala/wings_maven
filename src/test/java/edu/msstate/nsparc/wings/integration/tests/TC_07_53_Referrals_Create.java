package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10433")
public class TC_07_53_Referrals_Create extends BaseWingsTest {
    protected Participant participant;
    protected JobOrder jobOrder;

    public void main() {

        info("Init test data");
        initData(Constants.TRUE, Constants.TRUE);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        ReferralCreationForm referralCreationForm = repeatedCommonSteps(participant, jobOrder);

        logStep("Create");
        referralCreationForm.createReferral();
        referralCreationForm.clickButton(Buttons.Done);

        logStep("Find created referral");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);
    }

    /**
     * Init test data.
     *
     * @param everyfied - if you need to create e-verify
     * @param veteran   - if participant is veteran.
     */
    protected void initData(Boolean everyfied, Boolean veteran) {
        info("Participant");
        participant = new Participant(AccountUtils.getParticipantAccount());
        if (veteran) {
            ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        } else {
            ParticipantCreationSteps.createParticipantDriver(participant, Constants.FALSE, Constants.FALSE);
        }
        if (everyfied) {
            info("E-Verify Participant");
            EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));
        }

        info("Employer");
        jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
    }

    /**
     * Includes several steps into one method, that can be found in different tests.
     *
     * @param participant - participant
     * @param order       - job order
     */
    protected ReferralCreationForm repeatedCommonSteps(Participant participant, JobOrder order) {
        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();

        logStep("Select a participant");
        referralCreationForm.selectParticipant(participant);

        logStep("Select Job Order");
        referralCreationForm.selectJobOrder(order);

        logStep("Fill in all required fields with correct data");
        referralCreationForm.clickAgree();
        referralCreationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        return referralCreationForm;
    }
}
