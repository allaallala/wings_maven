package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open the individual employment creation form, add a secondary case manager. Unselect / Select it - [Remove] button is incative/[Remove] button is active.
 * Created by a.vnuchko on 25.08.2015.
 */

@TestCase(id = "WINGS-11004")
public class TC_21_12_IEP_Create_Remove_SCM_Security extends TC_21_10_IEP_Create_Add_Secondary_Case_Manager {

    public void main() {
        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        logStep("Add a Secondary Case Manager");
        IndividualEmploymentPlanCreationForm creationPage = new IndividualEmploymentPlanCreationForm();
        creationPage.selectSecondaryCaseManager(firstName, lastName);

        logStep("Unselect/ Select a Secondary Case Manager");
        info("Do NOT select a secondary case manager");

        logResult("The button [Remove] is inactive");
        creationPage.checkRemoveManagerDisabled();

        info("Select a secondary case manager");
        logResult("The button [Remove] is active");
        creationPage.chooseFirstCaseManager();
    }
}
