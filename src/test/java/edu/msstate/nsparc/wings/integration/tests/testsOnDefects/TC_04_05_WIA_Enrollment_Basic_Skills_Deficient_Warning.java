package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_01_12_WIA_Enrollment_Create_Dislocated_Worker;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10591")
public class TC_04_05_WIA_Enrollment_Basic_Skills_Deficient_Warning extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {
    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String ASSESSMENT_TYPE = "ABLE";
    private static final String ASSESSMENT_SCORE = "800";
    private static final String ASSESSMENT_FUNCTIONAL_AREA = "Reading/Reading for Information";
    private static final String WARNING_MESSAGE = "This participant is not Basic Skills Deficient according to their assessments, but this enrollment indicates they are";


    //sub-task WINGS-2660
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = openEnrollmentCreationForm(participant);

        logStep("Fill in all required fields on the 'Basic Information' and 'Program Information' pages");
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, true);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(true);

        logStep("Fill in all required fields on 'Youth Information' page");
        wiaEnrollmentCreationForm.fillBasicYouthInformation(EDUCATIONAL_STATUS);

        logStep("Set 'Basic Skills Deficient' radio button to 'Yes'");
        wiaEnrollmentCreationForm.clickBasicSkillsYes();

        logStep("Click on Continue");
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);

        logStep("Add Youth Assessment with score, that means Participant is not BSD (refer to the WINGS-1545)");
        wiaEnrollmentCreationForm.addYouthAssessment();
        AssessmentCreationForm youthAssessmentCreationForm = new AssessmentCreationForm();
        youthAssessmentCreationForm.addNotBSDAssessment(ASSESSMENT_TYPE, ASSESSMENT_SCORE, ASSESSMENT_FUNCTIONAL_AREA);

        logStep("Click on Create button");
        wiaEnrollmentCreationForm.clickButton(Buttons.Create);

        logStep("Check, that warning message about 'Basic Skill Deficient'");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        wiaEnrollmentDetailsForm.clickYouthInformation();
        CustomAssertion.softTrue("Basic Skills Deficient warning message wasn't displayed", wiaEnrollmentDetailsForm.getMessagesText().contains(WARNING_MESSAGE));
    }
}
