package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this case, we are checking all values in the types area on creating Assessment.
 * Created by a.korotkin, modified by a.vnuchko
 */

@TestCase(id = "WINGS-10950")
public class TC_19_04_02_Assessment_Create_All_Types extends BaseWingsTest {

    public void main() {

        info("Generate some test data");
        String[] functionalAreas = {"TABE 7-8, 9-10, 11-12", "CASAS", "ABLE", "WorkKeys", "SPL", "BEST Plus", "BEST Literacy", "TABE Class E", "Wonderlic", "BEST",};

        info("Precondiiton: Create new Participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(partp, "Wagner-Peyser");

        AssessmentSteps.openFillCreationForm(assessment);

        logStep("Select different values in the Function Area");
        logResult("Type drop down list consists of the following values: TABE 7-8, 9-10, CASAS, ABLE, WorkKeys, SPL, BEST, BEST Plus, BEST Literacy, "
                + "TABE Class E, Wonderlic");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        for (int i = 0; i < functionalAreas.length - 3; i++) {
            creationPage.selectType(functionalAreas[i]);
        }
        creationPage.selectFunctionalArea("Reading & Writing");
        creationPage.selectType(functionalAreas[7]);
        creationPage.selectFunctionalArea("English");
        creationPage.selectType(functionalAreas[8]);
        creationPage.selectFunctionalArea("Oral");
        creationPage.selectType(functionalAreas[6]);
        creationPage.selectFunctionalArea("Reading/Reading for Information");
        creationPage.selectType(functionalAreas[2]);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The  Assessment Creation screen is shown. Select Pre Test or it should be seleceted by default if Pre and Post Test switch availible. "
                + "Every value could be selected. New Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
