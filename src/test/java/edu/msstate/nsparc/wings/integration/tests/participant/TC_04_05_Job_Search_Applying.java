package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantJobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10430")
public class TC_04_05_Job_Search_Applying extends BaseWingsTest {

    public void main() {
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        info("We need to create Job Order too");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder, true, false);
        String jobOrderTitle = jobOrder.getJobTitle();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Job Search");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.searchForJobs();

        logStep("Enter some parameters for searching->Search");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);

        logStep("Open one of the found job orders");
        jobFindForm.openJobOrderDetailsNew(jobOrderTitle);

        logStep("Apply");
        ParticipantJobOrderDetailsForm participantJobOrderDetailsForm = new ParticipantJobOrderDetailsForm(jobOrderTitle);
        participantJobOrderDetailsForm.apply();
        participantJobOrderDetailsForm.inputInitialsConfirm(participant.getInitials());

        logStep("Make sure the Apply button is no longer displayed");
        homePage.searchForJobs();
        jobFindForm.performSearch(jobOrderTitle);
        jobFindForm.openJobOrderDetailsNew(jobOrderTitle);
        participantJobOrderDetailsForm.checkApplyNotPresent();

        BaseNavigationSteps.logout();

        logStep("Login as a staff and make sure that referral is created");
        ReferralSteps.searchReferral(participant);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Search for Participant and open his profile");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndOpen(participant);

        logStep("Check, that Referral did not begin participation period");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandParticipationPeriods();
        detailsForm.checkParticipationPeriod("Nothing found to display");
    }
}
