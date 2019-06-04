package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10502")
public class TC_01_04_Job_Matching_Use_Participants_Residence_Address extends BaseWingsTest {

    //sub-task WINGS-2395
    public void main() {
        info("Creating Participant for Job Matching");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createRegularParticipant(participant, Constants.TRUE);

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Participant->Wagner-Peyser->Job matching");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_JOB_MATCHING);

        logStep("Select participant");
        JobMatchingForm jobMatchingForm = new JobMatchingForm();
        assertFalse("Use participant's residence address radiobutton is editable", jobMatchingForm.checkParticipantEditable());
        jobMatchingForm.selectParticipant(participant);

        logStep("Check data");
        jobMatchingForm = new JobMatchingForm();
        jobMatchingForm.choosePartipLoc();
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        jobMatchingForm.chooseLocation();
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }
}
