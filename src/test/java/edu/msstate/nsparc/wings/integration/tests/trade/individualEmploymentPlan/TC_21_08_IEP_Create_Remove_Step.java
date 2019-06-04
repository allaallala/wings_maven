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
 * Open individual employment creation form, add some step, remove it. Check, that step was removed.
 * Created by a.vnuchko on 24.08.2015.
 */

@TestCase(id = "WINGS-11000")
public class TC_21_08_IEP_Create_Remove_Step extends BaseWingsTest {

    public void main() {


        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        logStep("Add a step");
        IndividualEmploymentPlanCreationForm creationPage = new IndividualEmploymentPlanCreationForm();
        creationPage.addSinglePlanStep(plan.getPlanSteps().get(0));

        logStep("Select a step");
        creationPage.selectFirstStep();

        logStep("Click [Remove] in the Step area");
        creationPage.removeStep();

        logResult("The step is removed from the list");
        creationPage.firstStepNotPresent();
    }
}
