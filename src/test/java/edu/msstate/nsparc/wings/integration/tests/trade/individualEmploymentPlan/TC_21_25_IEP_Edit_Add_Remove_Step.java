package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create an invidividual employment  plan, add step, remove step and save changes. Check, that changes are saved (step is added or step is removed).
 * Created by a.vnuchko on 01.09.2015.
 */

@TestCase(id = "WINGS-11015")
public class TC_21_25_IEP_Edit_Add_Remove_Step extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some Individual Employment Plan");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Edit] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Add a new Step");
        IndividualEmploymentPlanEditForm editPage = new IndividualEmploymentPlanEditForm();
        plan.generatePlanSteps();
        editPage.addSinglePlanStep(plan.getPlanSteps().get(plan.getPlanSteps().size() - 1));

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("The Individual Employment Plan Detail Screen is shown. The changes are saved (step is added)");
        detailsPage.validateLastStep(plan.getPlanSteps().get(plan.getPlanSteps().size() - 1), detailsPage.getNumberOfPlanSteps(), true);

        logStep("Remove an existing Step");
        detailsPage.clickButton(Buttons.Edit);
        detailsPage.chooseDefinedStep();
        detailsPage.removeStep();

        logStep("Click the [Save Changes] button");
        detailsPage.clickButton(Buttons.Save);

        logResult("The Individual Employment Plan Detail Screen is shown. The changes are saved (step is removed)");
        detailsPage.validateLastStep(plan.getPlanSteps().get(plan.getPlanSteps().size() - 1), detailsPage.getNumberOfPlanSteps() + 1, false);
    }
}
