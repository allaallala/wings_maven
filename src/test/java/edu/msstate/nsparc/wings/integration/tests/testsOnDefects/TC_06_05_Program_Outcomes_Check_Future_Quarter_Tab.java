package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10653")
public class TC_06_05_Program_Outcomes_Check_Future_Quarter_Tab extends BaseWingsTest {

    private static final String WARNING_MESSAGE = "Youth program outcome quarters may not be edited until the given quarter has elapsed";
    private static final Integer TEST_DATE = 199;

    //Bug WINGS-2713, sub-task WINGS-2860
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, true, TEST_DATE);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Participants->WIA->Program Outcomes");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);
        ProgramOutcomesDetailsForm detailsForm = new ProgramOutcomesDetailsForm();

        logStep("Select a participant");
        detailsForm.selectParticipant(participant);

        logStep("Select Enrollment");
        detailsForm.selectFirstPartipPeriod();
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();

        //Bug WINGS-2900, sub-task WINGS-2901
        logStep("Check that 'Exit Date' and 'Education Status at Exit' are displayed");
        CustomAssertion.softTrue("Exit Date isn't displayed", detailsForm.checkExitDate());
        CustomAssertion.softTrue("Education Status at Exit isn't displayed", detailsForm.checkEducationStatus());


        logStep("Click on Edit button");
        detailsForm.editProgram();
        ProgramOutcomesManagementForm editForm = new ProgramOutcomesManagementForm();

        logStep("Open 'Fourth Quarter' tab");
        editForm.clickFourthQuarter();

        logStep("Make sure that warning message is displayed");
        CustomAssertion.softTrue("Warning message wasn't displayed", editForm.getFourthWarningMessage().contains(WARNING_MESSAGE));

        logEnd();
    }
}
