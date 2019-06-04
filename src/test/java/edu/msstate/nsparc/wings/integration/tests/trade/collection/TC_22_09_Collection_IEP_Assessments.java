package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that preview for assessment can be opened from the individual employment plan and contains correct information
 * Created by a.vnuchko on 14.10.2015.
 */

@TestCase(id = "WINGS-11033")
public class TC_22_09_Collection_IEP_Assessments extends BaseWingsTest {

    public void main() {
        info("Precondition: create new individual employment plan");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        logStep("Check that the preview can be successfully opened for the Assessments "
                + "(from the Individual Employment Plan creation/participantSSDetails pages) and contains the correct information");
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.performSearchAndOpen(new User(Roles.STAFF), plan);

        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.validateAssessmentData(plan);
    }
}
