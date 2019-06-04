package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open individual employment creation form, add some step and select it. Check, that button [Remove] is active if step is selected.
 * Created by a.vnuchko on 25.08.2015.
 */

@TestCase(id = "WINGS-11001")
public class TC_21_09_IEP_Create_Remove_Step_Security extends BaseWingsTest {

    public void main() {


        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        logStep("Add a step");
        IndividualEmploymentPlanCreationForm creationPage = new IndividualEmploymentPlanCreationForm();
        creationPage.addSinglePlanStep(plan.getPlanSteps().get(0));

        logStep("Unselect/Select a step");
        info("Do NOT select a radiobutton of the step");

        logResult("The button [Remove] is inactive if a radio-button of the corresponding Assessment isn't selected");
        creationPage.checkRemoveDisabledPresent();

        info("Select a radiobutton of the step");
        creationPage.selectFirstStep();

        logResult("The button [Remove] is active when a radio-button of the corresponding Assessment is selected");
        creationPage.removeStep();
        creationPage.firstStepNotPresent();
    }
}
