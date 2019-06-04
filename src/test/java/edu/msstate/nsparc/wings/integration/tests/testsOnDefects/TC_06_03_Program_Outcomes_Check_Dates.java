package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ProgramOutcomesSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10647")
public class TC_06_03_Program_Outcomes_Check_Dates extends BaseWingsTest {

    //Bug WINGS-2470, sub-task WINGS-2741
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, Constants.DAYS_OUTCOME_QUARTER);
        String[] quarters = ProgramOutcomesSteps.getProgramOutcomesQuarters(Constants.DAYS_OUTCOME_QUARTER);

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

        logStep("Check, that Quarters date ranges are displayed on Tabs");
        CustomAssertion.softAssertContains(detailsForm.getFirstQuarterText(), quarters[0], "First Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getFirstQuarterText(), quarters[1], "First Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getSecondQuarterText(), quarters[2], "Second Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getSecondQuarterText(), quarters[3], "Second Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getThirdQuarterText(), quarters[4], "Third Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getThirdQuarterText(), quarters[5], "Third Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getFourthQuarterText(), quarters[6], "Fourth Quarter date ranges failed");
        CustomAssertion.softAssertContains(detailsForm.getFourthQuarterText(), quarters[7], "Fourth Quarter date ranges failed");

        logEnd();
    }
}
