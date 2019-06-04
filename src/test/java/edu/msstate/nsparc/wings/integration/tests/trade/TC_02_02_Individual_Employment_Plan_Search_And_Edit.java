package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanEditForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10539")
public class TC_02_02_Individual_Employment_Plan_Search_And_Edit extends BaseWingsTest {


    public void main() {

        IndividualEmploymentPlan iep = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), iep);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        logStep("Find created Individual Employment Plan");
        IndividualEmploymentPlanSearchForm searchForm = new IndividualEmploymentPlanSearchForm();
        searchForm.performSearchAndOpen(new User(Roles.STAFF), iep);

        logStep("Start Editing IEP record");
        IndividualEmploymentPlanDetailsForm detailsForm = new IndividualEmploymentPlanDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Remove existing Plan Steps");
        IndividualEmploymentPlanEditForm editForm = new IndividualEmploymentPlanEditForm();
        editForm.removeExistingPlanSteps();

        logStep("Add new Plan Steps");
        iep.generatePlanSteps();
        editForm.addPlanSteps(iep);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Make Sure the changes are saved");
        detailsForm.validateInformation(iep);
    }
}
