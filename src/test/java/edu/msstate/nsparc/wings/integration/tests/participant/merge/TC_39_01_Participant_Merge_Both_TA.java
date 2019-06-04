package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Create two participants with trade enrollments. Try to merge it and check error message appeared.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-11267")
public class TC_39_01_Participant_Merge_Both_TA extends BaseWingsTest {

    public void main() {
        info("Preconditions: there are some Participant Profiles, which have a Trade Enrollment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        AccountUtils.init();
        TradeEnrollment tradeEnrollment2 = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment2, Roles.ADMIN);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Advanced -> Data Utilities -> Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(tradeEnrollment.getParticipant());

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(tradeEnrollment2.getParticipant());

        logResult("Merge cannot be performed.\n" +
                "The following message is displayed: Both the selected participants are enrolled in Trade, " +
                "therefore this merge cannot be performed. Please contact your Trade Director.");
        mergeForm.validateErrorMerge(ParticipantMergeForm.ErrorMergeType.BOTH_TRADE_ENROLLMENT);
    }
}
