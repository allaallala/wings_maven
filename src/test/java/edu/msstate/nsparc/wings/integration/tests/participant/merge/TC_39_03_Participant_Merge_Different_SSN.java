package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create two participants with different ssn. Open the participant merge form and check,that warning message
 * is displayed.
 * Created by a.vnuchko on 07.04.2017.
 */

@TestCase(id = "WINGS-11269")
public class TC_39_03_Participant_Merge_Different_SSN extends BaseWingsTest {

    public void main() {
        String degree = "Bachelor's Degree";
        info("Precondition: there are some Participant Profiles with different SSNs");
        Participant discardParticipant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        AccountUtils.init();
        Participant keepParticipant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        ParticipantDetailSteps.addAcademicRecord(discardParticipant, CommonFunctions.getYesterdayDate());

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Advanced -> Data Utilities -> Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);

        logStep("Verify that warning message is displayed.");
        mergeForm.validateErrorMerge(ParticipantMergeForm.ErrorMergeType.DIFFERENT_SSN);

        logStep("Merge records");
        mergeForm.addAcademicHistoryAuto();
        mergeForm.completeMerging();
        BaseNavigationSteps.logout();

        logResult("The Participant Profile is successfully merged.");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
        new ParticipantSearchForm().performSearchAndOpen(keepParticipant);
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandAcademicHistory();
        detailsPage.checkStatusCertificateParticipant(keepParticipant, degree);
    }
}
