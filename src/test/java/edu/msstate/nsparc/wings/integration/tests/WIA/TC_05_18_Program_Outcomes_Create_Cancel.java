package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ProgramOutcomesSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10631")
public class TC_05_18_Program_Outcomes_Create_Cancel extends BaseWingsTest {

    private static final String METHOD_FOR_FIRST_QUARTER = "Other Appropriate Method";
    private static final Integer DAYS = 99;


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, DAYS);
        // getting quarters for Program Outcomes
        String[] dates = ProgramOutcomesSteps.getProgramOutcomesQuarters(DAYS);
        // adding Academic Record for first quarter
        ParticipantDetailSteps.addAcademicRecord(participant, dates[0]);
        // adding Employment for first quarter
        ParticipantDetailSteps.addEmployment(participant, dates[0], true);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Participants->WIA->Program Outcomes");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);
        ProgramOutcomesDetailsForm detailsForm = new ProgramOutcomesDetailsForm();

        logStep("Select a participant");
        detailsForm.selectParticipant(participant);

        logStep("Select Enrollment");
        detailsForm.selectFirstPartipPeriod();

        //sub-task WINGS-2901
        logStep("Check that 'Exit Date' is displayed");

        assertTrue("Exit Date isn't displayed", detailsForm.checkExitDate());

        logStep("Click on Edit button");
        detailsForm.editProgram();

        logStep("Fill 1st quarter tab with correct data");
        ProgramOutcomesManagementForm programOutcomesManagementForm = new ProgramOutcomesManagementForm();
        programOutcomesManagementForm.fillAdultFirstQuarter(METHOD_FOR_FIRST_QUARTER);

        logStep("Click on Cancel button");
        programOutcomesManagementForm.clickButton(Buttons.Cancel);
        programOutcomesManagementForm.areYouSure(Popup.Yes);

        logStep("Validate, that Program Outcomes isn't saved");
        assertFalse("Assert Method Used to Determine First Quarter failed", detailsForm.getMethodFirstQuarter().contains(METHOD_FOR_FIRST_QUARTER));

        logEnd();
    }
}
