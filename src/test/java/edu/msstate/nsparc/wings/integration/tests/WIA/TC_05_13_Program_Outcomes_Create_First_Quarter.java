package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ProgramOutcomesSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10629")
public class TC_05_13_Program_Outcomes_Create_First_Quarter extends BaseWingsTest {

    private static final String METHOD_FOR_FIRST_QUARTER = "Other Appropriate Method";
    // declared here for use in TC_05_23
    protected Participant participant;
    private static final Integer DAYS = 99;



    public void main() {
        String occupationCode = "Cooks, Restaurant";
        String degree = "BA or BS Diploma/Degree";
        String yesAnswer = "Yes";
        participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, DAYS);
        // getting quarters for Program Outcomes
        String[] dates = ProgramOutcomesSteps.getProgramOutcomesQuarters(DAYS);
        // adding Academic Record for first quarter
        ParticipantDetailSteps.addAcademicRecord(participant, dates[0]);
        // adding Employment for first quarter
        String employer = ParticipantDetailSteps.addEmployment(participant, dates[0], true);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Participants->WIA->Program Outcomes");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);
        ProgramOutcomesDetailsForm programOutcomesDetailsForm = new ProgramOutcomesDetailsForm();

        logStep("Select a participant");
        programOutcomesDetailsForm.selectParticipant(participant);

        logStep("Select Enrollment");
        programOutcomesDetailsForm.selectFirstPartipPeriod();

        logStep("Click on Edit button");
        programOutcomesDetailsForm.editProgram();

        logStep("Fill 1st quarter tab with correct data");
        ProgramOutcomesManagementForm programOutcomesManagementForm = new ProgramOutcomesManagementForm();
        programOutcomesManagementForm.fillAdultFirstQuarter(METHOD_FOR_FIRST_QUARTER);

        logStep("Click on Save Changes button");
        programOutcomesManagementForm.clickButton(Buttons.Save);

        logStep("View new created participant Program Outcomes");
        CustomAssertion.softTrue("Assert Employed First Quarter failed", programOutcomesDetailsForm.getEmployedFirstQuarter().contains(yesAnswer));
        CustomAssertion.softTrue("Assert Employer First Quarter failed", programOutcomesDetailsForm.getEmployerFirstQuarter().contains(employer));
        CustomAssertion.softTrue("Assert Occupation Code failed", programOutcomesDetailsForm.getOccupationCode().contains(occupationCode));
        CustomAssertion.softTrue("Assert Method Used to Determine First Quarter failed", programOutcomesDetailsForm.getMethodFirstQuarter().contains(METHOD_FOR_FIRST_QUARTER));
        CustomAssertion.softTrue("Assert Recognized Credential (from Training) failed", programOutcomesDetailsForm.getRecognizedCredentials().contains(degree));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
