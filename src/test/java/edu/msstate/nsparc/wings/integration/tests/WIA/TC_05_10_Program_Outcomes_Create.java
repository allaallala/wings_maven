package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ProgramOutcomesSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;

import java.util.ArrayList;
import java.util.List;

@TestCase(id = "WINGS-10462")
public class TC_05_10_Program_Outcomes_Create extends BaseWingsTest {

    protected List<String> employers = new ArrayList<>();
    private Integer programsDays = 370;
    private static final String METHOD_FOR_FIRST_QUARTER = "Other Appropriate Method";
    private String occupationCode = "Cooks, Restaurant";
    private String degree = "BA or BS Diploma/Degree";
    Participant participant;


    public void main() {
        createProgramOutcome();

        logStep("Log in WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Open Program Outcomes form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);

        logStep("Select Participant");
        ProgramOutcomesDetailsForm programOutcomesDetailsForm = new ProgramOutcomesDetailsForm();
        programOutcomesDetailsForm.selectParticipant(participant);

        logStep("Select Enrollment");
        programOutcomesDetailsForm.selectFirstPartipPeriod();

        logStep("Click on Edit button");
        programOutcomesDetailsForm.editProgram();

        logStep("Fill information for all four quarters");
        ProgramOutcomesManagementForm programOutcomesManagementForm = new ProgramOutcomesManagementForm();
        programOutcomesManagementForm.fillAdultFirstQuarter(METHOD_FOR_FIRST_QUARTER);

        logStep("Click on Save Changes button");
        programOutcomesManagementForm.clickButton(Buttons.Save);

        logStep("Check that information was saved");
        CustomAssertion.softTrue("Assert Occupation Code failed", programOutcomesDetailsForm.getOccupationCode().contains(occupationCode));
        CustomAssertion.softTrue("Assert Method Used to Determine First Quarter failed", programOutcomesDetailsForm.getMethodFirstQuarter().contains(METHOD_FOR_FIRST_QUARTER));
        CustomAssertion.softTrue("Assert Recognized Credential (from Training) failed", programOutcomesDetailsForm.getRecognizedCredentials().contains(degree));
        programOutcomesDetailsForm.validateEmployers(employers);
        logEnd();
    }

    /**
     * Create program outcome.
     */
    public void createProgramOutcome() {
        participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, programsDays);
        // getting quarters for Program Outcomes
        String[] dates = ProgramOutcomesSteps.getProgramOutcomesQuarters(programsDays);
        // adding data for First Quarter
        ParticipantDetailSteps.addAcademicRecord(participant, dates[0]);
        employers.add(ParticipantDetailSteps.addEmployment(participant, dates[0], true));
        // adding data for Second Quarter
        employers.add(ParticipantDetailSteps.addEmployment(participant, dates[2], true));
        // adding data for Third Quarter
        employers.add(ParticipantDetailSteps.addEmployment(participant, dates[4], true));
        // adding data for Forth Quarter
        employers.add(ParticipantDetailSteps.addEmployment(participant, dates[6], true));

    }

}
