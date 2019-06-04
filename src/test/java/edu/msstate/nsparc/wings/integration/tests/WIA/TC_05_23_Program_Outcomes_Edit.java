package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10633")
public class TC_05_23_Program_Outcomes_Edit extends BaseWingsTest {

    private static final String EDITED_METHOD_FOR_FIRST_QUARTER = "Compared Occupational Codes";


    public void main() {


        TC_05_13_Program_Outcomes_Create_First_Quarter createProgramOutcomes =
                new TC_05_13_Program_Outcomes_Create_First_Quarter();
        createProgramOutcomes.main();

        logStep("Log in WINGS as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Participants->WIA->Program Outcomes");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);
        ProgramOutcomesDetailsForm programOutcomesDetailsForm = new ProgramOutcomesDetailsForm();

        logStep("Select a participant");
        programOutcomesDetailsForm.selectParticipant(createProgramOutcomes.participant);

        logStep("Select Enrollment");
        programOutcomesDetailsForm.selectFirstPartipPeriod();

        logStep("Click on Edit button");
        programOutcomesDetailsForm.editProgram();

        logStep("Change some parameters on Program Outcomes form");
        ProgramOutcomesManagementForm programOutcomesManagementForm = new ProgramOutcomesManagementForm();
        programOutcomesManagementForm.selectProgramMethod(EDITED_METHOD_FOR_FIRST_QUARTER);
        programOutcomesManagementForm.clickButton(Buttons.Save);

        logStep("Check required text");
        assertTrue("Assert Method Used to Determine First Quarter failed",
                programOutcomesDetailsForm.getMethodFirstQuarter().contains(EDITED_METHOD_FOR_FIRST_QUARTER));

        logEnd();
    }
}
