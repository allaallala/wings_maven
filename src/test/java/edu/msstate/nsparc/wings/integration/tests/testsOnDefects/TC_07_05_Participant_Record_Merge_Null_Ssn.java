package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.PopUpMenu;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_12_88_Participant_Record_Merge;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10671")
public class TC_07_05_Participant_Record_Merge_Null_Ssn extends TC_12_88_Participant_Record_Merge {
    private String nullValue = "NULL";

    public void main() {
        String expectedDegree = "Bachelor's Degree";

        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        Participant discardParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(discardParticipant);
        EverifySteps.eVerifyParticipant(discardParticipant, new User(Roles.STAFF));
        ParticipantDetailSteps.addAcademicRecord(discardParticipant, CommonFunctions.getYesterdayDate());

        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating second Participant");
        Participant keepParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);

        info("Settings Keep Participant SSN to NULL");
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, nullValue);

        ParticipantMergeForm mergeForm = mergeDiscardKeepParticipant(discardParticipant, keepParticipant);

        logStep("Select some parameters for merging");
        mergeForm.addEmployment();
        mergeForm.addVerify();

        mergeForm.addAcademicHistoryAuto();

        logStep("Merge");
        mergeForm.completeMerging();

        logStep("Open merged record");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        PopUpMenu popUpMenu = new PopUpMenu();
        popUpMenu.clickSearch();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(keepParticipant);

        logStep("Check, that information is merged");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        info("Employment");
        detailsForm.expandEmploymentHistory();
        assertTrue("Previous job assert fail", detailsForm.getPreviousJobPageText().contains("Cook at Automation"));
        info("E-Verify");
        detailsForm.checkEverifyMerged();
        info("Academic");
        detailsForm.expandAcademicHistory();
        detailsForm.checkStatusCertificateParticipant(keepParticipant,expectedDegree);
    }
}
