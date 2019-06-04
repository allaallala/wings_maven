package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10800")
public class TC_12_70_Participant_Record_Merge_Trade extends BaseWingsTest {

    public void main() {
        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating second Participant");
        Participant keepParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(tradeEnrollment.getParticipant().getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, tradeEnrollment.getParticipant().getSsn());

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Advanced -> Data Utilities -> Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(tradeEnrollment.getParticipant());

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);

        logStep("Select Trade Enrollment for merging");
        mergeForm.validateSeHasNoValues(tradeEnrollment.getPetition().getNumber()
                + " - " + tradeEnrollment.getPetition().getEmployer().getCompanyName()
                + " - Certified " + tradeEnrollment.getPetition().getDecisionDate());
        mergeForm.addTradeEnrollment(tradeEnrollment);

        logStep("Merge");
        mergeForm.completeMerging();
        tradeEnrollment.setParticipant(keepParticipant);

        logStep("Open Trade Enrollment search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find Trade Enrollment with Keep Participant");
        TradeEnrollmentSearchForm searchForm = new TradeEnrollmentSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment);

        logStep("Check, that information is merged");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.validateInformation(tradeEnrollment);
    }
}
