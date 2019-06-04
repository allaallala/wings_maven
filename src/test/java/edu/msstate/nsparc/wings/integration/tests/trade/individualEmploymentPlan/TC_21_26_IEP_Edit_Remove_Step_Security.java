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
 * Search for the existing individual employment plan, open it participantSSDetails page. Add step and unselect/select it. [Remove] button is inactive/active.
 * Created by a.vnuchko on 26.08.2015.
 */

@TestCase(id = "WINGS-11016")
public class TC_21_26_IEP_Edit_Remove_Step_Security extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Edit] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Add a Step");
        IndividualEmploymentPlanEditForm editPage = new IndividualEmploymentPlanEditForm();
        editPage.addSinglePlanStep(plan.getPlanSteps().get(0));

        logStep("Unselect/ Select a Step");
        info("Do NOT select a step");

        logResult("The button [Remove] is inactive");
        editPage.checkRemoveDisabledPresent();

        logStep("Select a step");
        logResult("The button [Remove] is active");
        editPage.chooseFirstPlanStep();
    }
}
