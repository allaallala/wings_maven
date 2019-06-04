package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10517")
public class TC_01_13_WIA_Enrollment_Create_Youth extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {
    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";
    private static final String LOW_INCOME_MESSAGE = "This participant is low income";
    String expectedParticipantType = "Youth";


    //Setting Family income to 0 for testing
    //sub-task WINGS-2873
    public void main () {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = openEnrollmentCreationForm(participant);

        logStep("Fill in all required fields on the 'Basic Information' and 'Program Information' pages");
        info("Check, that the 'Offender' radio button have became required");
        CustomAssertion.softTrue("Offender radio button isn't required", wiaEnrollmentCreationForm.getOffenderText().contains("*"));
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, true);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(true);

        logStep("New 'Youth Information page should appear'-> Fill in all required fields on this page->Create");
        CustomAssertion.softTrue("Youth Information page isn't displayed", wiaEnrollmentCreationForm.checkYouthInfoPageNotDisplayed());
        wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, true);

        WIAEnrollmentDetailsForm detailsPage = repeatedSteps(participant, expectedParticipantType);
        CustomAssertion.softTrue("Participant wasn't marked as Low Income", detailsPage.getMessagesText().contains(LOW_INCOME_MESSAGE));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
