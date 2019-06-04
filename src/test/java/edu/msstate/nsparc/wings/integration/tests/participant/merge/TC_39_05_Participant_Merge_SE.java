package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
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


/**
 * There is a participant profile with a WIA enrollment.
 * Created by a.vnuchko on 11.04.2017.
 */

@TestCase(id = "WINGS-11271")
public class TC_39_05_Participant_Merge_SE extends BaseWingsTest {
    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String creationDate = CommonFunctions.getDaysAgoDate(1);

    public void main() {
        info("Precondition: there are some Participant Profiles. One of them has any service enrollment, except:\n"
                + "- Background service enrollments\n"
                + "- Trade service enrollments\n"
                + "- WIA service enrollments");
        info("Create participant to discard");
        Participant discardParticipant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        AdvancedSqlFunctions.resetAccount(AccountUtils.getParticipantAccount());
        Participant keepParticipant = new Participant(AccountUtils.getParticipantAccount());

        info("Create participant to keep");
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());
        ServiceSteps.createService(Roles.ADMIN, serviceName, false, false);
        User admin = new User(Roles.ADMIN);
        ServiceSteps.createParticipantServiceEnrollment(admin, discardParticipant, serviceName, Constants.COMPLETED, creationDate);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Data Utilities->Participant Profile Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard from the precondition");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep(" Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);

        logStep("Select service enrollment from the precondition for merging in the 'Service enrollments' field");
        mergeForm.removeServiceKeep();
        mergeForm.fromDiscardToParticipant(serviceName);

        logStep("Merge");
        mergeForm.completeMerging();
        BaseNavigationSteps.logout();

        logResult("The second Participant Profile is successfully merged. The following information was "
                + "successfully merged: - Service enrollment");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Search);
        ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
        searchPage.performSearch(keepParticipant, serviceName);
        searchPage.validateFirstSearchResult(keepParticipant, serviceName, creationDate, Constants.COMPLETED);

    }
}
