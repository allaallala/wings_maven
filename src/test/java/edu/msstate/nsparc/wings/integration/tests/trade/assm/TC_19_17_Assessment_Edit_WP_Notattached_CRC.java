package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create some W-P Assessments NOT attached to CRC, open its detailed page and check, that all fields can be edited
 * Created by a.vnuchko on 15.09.2015.
 */

@TestCase(id = "WINGS-10962")
public class TC_19_17_Assessment_Edit_WP_Notattached_CRC extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Generate test data");
        String functionalArea = "Writing";
        String type = "SPL";
        String date = CommonFunctions.getCurrentDate();
        String score = "88";

        info("Precondition: Create some W-P Assessments attached to CRC");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Constants.TRUE, Constants.FALSE);
        Assessment assessment = new Assessment(partp, WAGNER_PEYSER);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentSteps.openEditAssessment(assessment, Constants.RANDOM_ONE);

        logStep("Edit all the parameters");
        AssessmentEditForm editPage = new AssessmentEditForm();
        editPage.editAllAssmParameters(WAGNER_PEYSER, functionalArea, type, date, score);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("All the parameters can be changed. The Assessment Detail Screen is shown. The changes are saved");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateData(WAGNER_PEYSER, functionalArea, type, date, score);
    }
}
