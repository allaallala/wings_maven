package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this case, we are checking all values in the category block.
 * Created by a.korotkin
 */

@TestCase(id = "WINGS-10951")
public class TC_19_04_03_Assessment_Create_Category_Values extends TC_19_01_Assessment_Create_Cancel {
    String firstCategory = "first";
    String secondCategory = "second";

    public void main() {
        info("Store some test data");
        String[] testData = {"CASAS", "WorkKeys", "85"};

        info("Precondiiton: Create new Participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(partp, WAGNER_PEYSER);

        AssessmentSteps.openFillCreationForm(assessment);

        logStep("Select diffrent values in the Category");
        logResult("Category radio buttons consists of the following values:- ABE, - ESL");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        creationPage.selectType(testData[0]);
        creationPage.selectCategory(firstCategory);
        creationPage.selectCategory(secondCategory);

        creationPage.selectFunctionalArea("Reading/Reading for Information");
        creationPage.selectType(testData[1]);
        assessment.setType(testData[1]);
        creationPage.typeScore(testData[2]);
        assessment.setScore(testData[2]);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The  Assessment Creation screen is shown. Select Pre Test or it should be seleceted by default if Pre and Post Test switch availible."
                + "New Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
