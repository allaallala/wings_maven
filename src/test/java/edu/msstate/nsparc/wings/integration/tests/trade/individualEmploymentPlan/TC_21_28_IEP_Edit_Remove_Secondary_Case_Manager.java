package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Search for the existing individual employment plan, open it participantSSDetails page. Look up for a secondary case manager. Select it and then remove. Click [Save Changes] button. The required form is shown,
 * changes are saved.
 * Created by a.vnuchko on 27.08.2015.
 */

@TestCase(id = "WINGS-11018")
public class TC_21_28_IEP_Edit_Remove_Secondary_Case_Manager extends TC_21_10_IEP_Create_Add_Secondary_Case_Manager {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Edit] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Select a Secondary Case Manager");
        detailsPage.selectSecondaryCaseManager(firstName, lastName);

        logStep("Click [Remove] in the Secondary Case Manager area");
        detailsPage.chooseFirstCaseManager();
        detailsPage.removeCaseManager();

        logStep("Click the [Save Changes] button");
        detailsPage.clickButton(Buttons.Save);

        logResult("The Individual Employment Plan Detail Screen is shown. The changes are saved");
        detailsPage.validateInformation(plan);
    }
}
