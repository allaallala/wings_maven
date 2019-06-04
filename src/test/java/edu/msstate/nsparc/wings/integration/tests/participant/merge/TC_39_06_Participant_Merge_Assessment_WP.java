package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Create some participant profile. One has a Wagner-Peyser assessment. Merge this participant with one, who has no
 * assessment and check, that it's merged.
 * Created by a.vnuchko on 11.04.2017.
 */

@TestCase(id = "WINGS-11272")
public class TC_39_06_Participant_Merge_Assessment_WP extends BaseWingsTest {

    public void main() {
        String assesmType = "Wagner-Peyser";
        info("Precondition: there are some Participant Profiles. One of them has: - Wagner-Peyser Assessment");
        info("Create participant to discard");
        Participant discardParticipant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        AdvancedSqlFunctions.resetAccount(AccountUtils.getParticipantAccount());
        Participant keepParticipant = new Participant(AccountUtils.getParticipantAccount());

        info("Create participant to keep");
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());

        info("Create Wagner-Peyser assessment");
        Assessment assessment = new Assessment(discardParticipant, assesmType);
        User admin = new User(Roles.ADMIN);
        AssessmentSteps.createAssessment(admin, assessment);

        repeatedSteps(discardParticipant, keepParticipant, assessment);
    }

    /**
     * Repeated steps for merging participant with an assessment
     * @param discardParticipant - participant to discard
     * @param keepParticipant - participant to keep
     * @param assessment - assessment
     */
    protected void repeatedSteps(Participant discardParticipant, Participant keepParticipant, Assessment assessment) {
        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Data Utilities->Participant Profile Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard from the precondition");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);

        logStep("Select parameter (Which corresponds to the Assessment service enrollment from "
                + "the precondition) for merging in the 'Assessments' text box");
        addRecord(assessment);

        logStep("Merge");
        mergeForm.completeMerging();
        BaseNavigationSteps.logout();

        logResult("The second Participant Profile is successfully merged. The following information was "
                + "successfully merged: - Wagner-Peyser Assessment");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);
        AssessmentSearchForm searchPage = new AssessmentSearchForm();
        assessment.setParticipant(keepParticipant);
        searchPage.performSearch(assessment);
        searchPage.validateOneCriteria(assessment);
    }

    /**
     * Add parameter to the keep participant depends on a type of an assessment
     * @param assessment - assessment created
     */
    private void addRecord(Assessment assessment) {
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        switch (assessment.getProgram()) {
            case "WIA":
                mergeForm.addAllWiaEnrollment();
                break;
            case "Trade":
                mergeForm.addAllTradeEnrollment();
                break;
            default:
                mergeForm.addAssessment(assessment);
        }
    }
}
