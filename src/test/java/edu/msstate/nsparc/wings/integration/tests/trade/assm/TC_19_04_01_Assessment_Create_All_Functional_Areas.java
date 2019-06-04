package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this case, we are checking all values in the functional area on creating Assessment.
 */

@TestCase(id = "WINGS-10949")
public class TC_19_04_01_Assessment_Create_All_Functional_Areas extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Generate some test data");
        String[] functionalAreas = {"Reading/Reading for Information", "Writing", "Language", "Mathematics", "Speaking", "Oral", "Listening"};

        info("Precondition: Create new Participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Constants.TRUE, Constants.FALSE);
        Assessment assessment = new Assessment(partp, WAGNER_PEYSER);

        AssessmentSteps.openFillCreationForm(assessment);

        logStep("Select different values in the Function Area");
        logResult("Functional Area drop down list consists of the following values: Area drop down list, Reading, Writing, Language, Mathematics, Speaking, Oral, Listening");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        for (int i = 0; i < functionalAreas.length; i++) {
            creationPage.selectFunctionalArea(functionalAreas[i]);
        }
        creationPage.selectFunctionalArea(functionalAreas[0]);
        creationPage.selectType(assessment.getType());
        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The  Assessment Creation screen is shown. Select Pre Test or it should be seleceted by default if Pre and Post Test switch availible. "
                + "Every value could be selected. New Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
