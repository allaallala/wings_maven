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
 * Create individual employment plan and add secondary case manager for it. Check that button [Remove] is active/inactive until scm is chosen/unchosen.
 * Created by a.vnuchko on 03.09.2015.
 */

@TestCase(id = "WINGS-11019")
public class TC_21_29_IEP_Edit_Remove_SCM_Security extends TC_21_10_IEP_Create_Add_Secondary_Case_Manager {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Edit] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Add a Secondary Case Manager");
        detailsPage.selectSecondaryCaseManager(firstName, lastName);

        info("Do  Not Select / Select a Secondary Case Manager");

        logResult("The button [Remove] is inactive");
        detailsPage.checkRemoveManagerDisabled();

        info("Select a Secondary Case Manager");
        logResult("The button [Remove] is active");
        detailsPage.chooseFirstCaseManager();
    }
}
