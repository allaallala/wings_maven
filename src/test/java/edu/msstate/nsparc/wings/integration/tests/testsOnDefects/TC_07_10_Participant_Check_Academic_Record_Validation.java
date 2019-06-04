package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;
import org.testng.Assert;


@TestCase(id = "WINGS-10677")
public class TC_07_10_Participant_Check_Academic_Record_Validation extends BaseWingsTest {

    public void main()  {



        info("Creating Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Search for Participant and open record");
        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);

        logStep("Start adding new Academic History record");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandAcademicHistory();
        detailsForm.clickAddAcademicRecord();

        logStep("Select Currently in school - Yes");
        ParticipantAddAcademicRecordForm addAcademicRecordForm = new ParticipantAddAcademicRecordForm();
        addAcademicRecordForm.selectParticipantSchool(true);
        Browser.getInstance().waitForPageToLoad();

        logStep("Check that Date Left school isn't displayed");
        addAcademicRecordForm.checkDateLeft(false);

        logStep("Select Currently in school - No");
        addAcademicRecordForm.selectParticipantSchool(false);
        Browser.getInstance().waitForPageToLoad();

        logStep("Check that Date Left school is displayed");
        addAcademicRecordForm.checkDateLeft(true);

        logStep("Select Completed Program - Yes");
        addAcademicRecordForm.selectCompletedProgram(true);

        logStep("Check that label text has changed");
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        Assert.assertTrue(addAcademicRecordForm.getTextAcademicRecordForm().contains("Date Degree Obtained"));

        logStep("Select Completed Program - No");
        addAcademicRecordForm.selectCompletedProgram(false);

        logStep("Check that label text has changed");
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        Assert.assertTrue(addAcademicRecordForm.getTextAcademicRecordForm().contains("Date Left School"));
    }
}
