package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 21.08.2015.
 * Fill out all fields to create an individual employment plan, add assessment, click [Create] button and check, that IEP was created.
 */

@TestCase(id = "WINGS-10995")
public class TC_21_03_IEP_Create_Add_Assessments extends BaseWingsTest {

    String program = "Wagner-Peyser";


    public void main() {


        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Constants.TRUE, Constants.FALSE);
        Assessment assm = new Assessment(plan.getParticipant(), program);
        assm.setParticipantPrePopulated(true);

        IndividualEmploymentPlanCreationForm creationPage = IEPSteps.openFillOutCreationForm(new User(Roles.STAFF), plan);

        logStep("Add assessment");
        creationPage.addEditRemoveAssessment(IndividualEmploymentPlanCreationForm.AssessmentAction.ADD, plan, assm, creationPage, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY);

        logStep("Validate information about assessment");
        creationPage.validateAssessment(IndividualEmploymentPlanCreationForm.AssessmentAction.ADD_EDIT, Constants.RANDOM_TWO.toString(), assm.getType(), assm.getFunctionalArea(), assm.getDateAdministered());

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The Individual Employment Plan Detail Screen is shown. A new Individual Employment Plan was created and contains the same data you have entered");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.validateInformation(plan);
    }


}
