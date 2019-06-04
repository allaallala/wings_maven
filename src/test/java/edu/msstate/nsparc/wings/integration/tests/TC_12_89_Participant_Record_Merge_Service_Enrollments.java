package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10804")
public class TC_12_89_Participant_Record_Merge_Service_Enrollments extends TC_12_88_Participant_Record_Merge {

    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    public void main() {


        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating discard Participant");
        Participant discardParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, serviceName, Constants.TRUE, Constants.FALSE);
        User staff = new User(Roles.STAFF);
        ServiceSteps.createParticipantServiceEnrollment(staff, discardParticipant, serviceName, Constants.COMPLETED, Constants.EMPTY);


        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating keep Participant");
        Participant keepParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());

        ParticipantMergeForm mergeForm = mergeDiscardKeepParticipant(discardParticipant, keepParticipant);

        logStep("Move Service Enrollment from discard to keep participant");
        mergeForm.fromDiscardToParticipant(serviceName);

        logStep("Merge");
        mergeForm.completeMerging();

        logStep("Open merged record");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.performSearch(keepParticipant, serviceName);

        logStep("Check, that information is merged");
        searchForm.checkSomeFields(keepParticipant.getNameForSearchPages(), serviceName, CommonFunctions.getYesterdayDate());

        //sub-task WINGS-2563
        logStep("Open Participant profiles search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for keep participant");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(keepParticipant);

        logStep("Open Participant participantSSDetails form");
        participantSearchForm.openFirstSearchResult();

        logStep("Expand Participation Periods section");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandParticipationPeriods();

        logStep("Check participation periods for keep Participant");
        Assert.assertTrue(detailsForm.getPartipPeriodsBeginDate().contains(CommonFunctions.getYesterdayDate()));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
