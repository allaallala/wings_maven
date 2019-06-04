package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ProgramOutcomesSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10595")
public class TC_04_09_Participant_Record_Merge_WIA extends BaseWingsTest {

    private static final String METHOD_FOR_FIRST_QUARTER = "Other Appropriate Method";


    //sub-task WINGS-2564
    //sub-task WINGS-2728
    public void main() {
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_OUTCOME_QUARTER + 1);
        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        Participant discardParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(discardParticipant, false, Constants.DAYS_OUTCOME_QUARTER);
        String[] dates = ProgramOutcomesSteps.getProgramOutcomesQuarters(Constants.DAYS_OUTCOME_QUARTER);

        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating second Participant");
        Participant keepParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Data Utilities->Participant Profile Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);
        mergeForm.validateSeHasNoValues(date + "/" + discardParticipant.getType() + "/"
                + discardParticipant.getWorkforceArea());
        mergeForm.addAllWiaEnrollment();

        logStep("Merge");
        mergeForm.completeMerging();

        // fix for Program Outcomes changes
        BaseNavigationSteps.logout();
        ParticipantDetailSteps.addAcademicRecord(keepParticipant, dates[0]);
        String employer = ParticipantDetailSteps.addEmployment(keepParticipant, dates[0], true);
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Open WIA Enrollment search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();

        logStep("Try to find Enrollment for keep Participant");
        wiaEnrollmentSearchForm.selectParticipant(keepParticipant);
        wiaEnrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Validate, that WIA Enrollment is found");
        assertTrue("WIA Enrollment wasn't found for keep Participant", wiaEnrollmentSearchForm.isFirstSearchResultPresent());

        info("Checking issue WINGS-2728");
        logStep("Open 'Program Outcomes' form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);
        ProgramOutcomesDetailsForm detailsForm = new ProgramOutcomesDetailsForm();

        logStep("Select a participant");
        detailsForm.clearParticipant();
        detailsForm.selectParticipant(keepParticipant);

        logStep("Select Enrollment");
        detailsForm.selectFirstPartipPeriod();

        logStep("Fill in 1st quarter tab with correct data");
        detailsForm.editProgram();
        ProgramOutcomesManagementForm programOutcomesManagementForm = new ProgramOutcomesManagementForm();
        programOutcomesManagementForm.fillAdultFirstQuarter(METHOD_FOR_FIRST_QUARTER);

        logStep("Click on Save Changes button");
        programOutcomesManagementForm.clickButton(Buttons.Save);

        logStep("Check created Program Outcomes");
        detailsForm.checkCreatedProgramOutcomes(employer, METHOD_FOR_FIRST_QUARTER);
    }
}
