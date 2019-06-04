package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;


@TestCase(id = "WINGS-10708")
public class TC_08_07_Job_Search_Applying_Academic_Validation extends BaseWingsTest {
    private static final String ERROR_MESSAGE = "You cannot apply to this job. See below to find out why.";
    private String degree = "Doctorate Degree";
    private String major = "Communication, General";

    public void main() {
        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        ParticipantDetailSteps.addAcademicRecord(participant, CommonFunctions.getCurrentDate());
        info("We need to create Job Order too");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        jobOrder.setIfAcademicRequired(true);
        jobOrder.setRequiredAcademic("Doctorate Degree");
        jobOrder.setRequiredApplyOnline(true);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, false);

        logStep("Login as participant");
        LoginForm loginForm = new LoginForm();
        loginForm.loginParticipant();

        logStep("Job Search");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.searchForJobs();

        logStep("Enter some parameters for searching->Search");
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);
        jobFindForm.validateNewJobOrderFormTitle(jobOrder, participant);

        logStep("Open Job Order");
        jobFindForm.openJobOrderDetailsNew(jobOrder.getJobTitle());

        logStep("Try to apply");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        jobOrderDetailsForm.checkApplyNotPresent();

        logStep("Make sure error message is displayed");
        CustomAssertion.softTrue("Details job order does not contains apply error message",
                jobOrderDetailsForm.getApplyErrorMessage().contains(ERROR_MESSAGE));

        logStep("Navigate to the Home page");
        BaseNavigationSteps.home();

        logStep("Start editing Participant profile");
        participantHomePage.openMyProfile();
        BaseParticipantSsDetailsForm participantDetailsForm = new BaseParticipantSsDetailsForm(participant);

        logStep("Click 'Add' in Education section");
        participantDetailsForm.addAcademicRecord();

        logStep("Fill out fields and select 'Doctorate Degree' type");
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.selectDegree(degree);
        recordPage.proceedMajorField(major);
        recordPage.selectCompletedProgram(Constants.TRUE);
        recordPage.fillOtherEducationFields(participant, CommonFunctions.getDaysAndYearsAgoDate(1,1), CommonFunctions.getYesterdayDate());
        recordPage.clickButton(Buttons.Save);

        logStep("Check that 'Highest Grade Completed' has automatically changed to 'Doctorate Diploma or Degree '");

        final TableCell ACOMPLISHMENT = new TableCell("//div[contains(.,'Accomplishments')]"
                + "/following-sibling::div[contains(.,'Doctorate Diploma or Degree')]", "Highest Grade Completed");
        ACOMPLISHMENT.assertIsPresentAndVisible();

        logStep("Open Job Search page again");
        BaseNavigationSteps.home();
        participantHomePage.searchForJobs();

        logStep("Search for the Job Order");
        jobFindForm.performSearch(jobID);
        CustomAssertion.softTrue("Job title on the page does not contain job title", jobFindForm.getJobTitleTextForm().contains(jobOrder.getJobTitle()));

        logStep("Open Job Order");
        jobFindForm.openJobOrderDetails();

        logStep("Apply");
        jobOrderDetailsForm.apply();
        jobOrderDetailsForm.inputInitialsConfirm(participant.getInitials());
        BaseNavigationSteps.logout();

        logStep("Login as a staff and make sure that referral is created");
        ReferralSteps.searchReferral(participant);
    }
}
