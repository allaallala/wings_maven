package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create First-test assessment (which has no Re-Tests/Post-tests based on it), open its detailed page and check fields, which can be edited.
 * Created by a.vnuchko on 11.09.2015.
 */

@TestCase(id = "WINGS-10964")
public class TC_19_19_Assessment_Edit_FT_No_RePostTest extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Generate some test data");
        String functionalArea = "Writing";
        String type = "SPL";

        info("Precondition: Create First-Test assessment, which has no Re-Tests/Post-Tests based on it");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(partp, WAGNER_PEYSER);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentEditForm editPage = AssessmentSteps.openEditAssessment(assessment, Constants.RANDOM_ONE);

        logResult("The  Assessment Edit  screen is shown. User is able to edit its 'Functional Area'; 'Type'; 'Category' parameters");
        editPage.selectFunctionalArea(functionalArea);
        editPage.selectType(type);
        editPage.selectCategory("second");
        editPage.clickButton(Buttons.Save);
        //new AssessmentDetailsForm().assertIsOpen();
    }
}
