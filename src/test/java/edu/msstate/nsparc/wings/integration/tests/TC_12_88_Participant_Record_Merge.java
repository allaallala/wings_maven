package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10803")
public class TC_12_88_Participant_Record_Merge extends BaseWingsTest {
    protected Participant keepParticipant;
    private String previousJob = "Cook at Automation";

    public void main() {

        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        Participant discardParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Constants.TRUE, Constants.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(discardParticipant);
        EverifySteps.eVerifyParticipant(discardParticipant, new User(Roles.STAFF));
        ParticipantDetailSteps.addAcademicRecord(discardParticipant, CommonFunctions.getYesterdayDate());

        createSecondParticipant(participantAccount, discardParticipant);

        ParticipantMergeForm mergeForm = mergeDiscardKeepParticipant(discardParticipant, keepParticipant);

        logStep("Select some parameters for merging");
        mergeForm.addEmployment();
        mergeForm.addVerify();

        mergeForm.addAcademicHistoryAuto();

        logStep("Merge");
        mergeForm.completeMerging();

        logStep("Open merged record");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearch(keepParticipant);
        searchForm.openFirstSearchResult();

        logStep("Check, that information is merged");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        info("Employment");
        detailsForm.expandEmploymentHistory();
        CustomAssertion.softTrue("Previous job assert fail", detailsForm.getPreviousJobPageText().contains(previousJob));
        info("E-Verify");
        detailsForm.checkEverifyMerged();
        info("Academic");
        detailsForm.expandAcademicHistory();
        detailsForm.checkStatusCertificateParticipant(keepParticipant,"Bachelor's Degree");
    }

    /**
     * Create second participant
     * @param participantAccount - participant account
     * @param discardParticipant - discard participant
     */
    protected void createSecondParticipant(String participantAccount, Participant discardParticipant) {
        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating second Participant");
        keepParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Constants.TRUE, Constants.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());
    }

    /**
     * Select discard and keep participant to merge
     * @param discardParticipant - discard participant
     * @param keepParticipant - keep participant
     */
    protected ParticipantMergeForm mergeDiscardKeepParticipant(Participant discardParticipant, Participant keepParticipant) {
        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Data Utilities->Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);
        return mergeForm;
    }
}
