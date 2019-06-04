package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some individual employment plan, open search form and search iep using only one criteria. Check, that the results of searching is correct.
 * Created by a.vnuchko on 07.09.2015.
 */

@TestCase(id = "WINGS-11007")
public class TC_21_15_IEP_Search_One_Criteria extends BaseWingsTest {

    public void main() {
        info("Precondition: create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        performSearchAndValidate(plan, IndividualEmploymentPlanSearchForm.IepSearchCriteria.PARTICIPANT);
        performSearchAndValidate(plan, IndividualEmploymentPlanSearchForm.IepSearchCriteria.DATEFROM);
        performSearchAndValidate(plan, IndividualEmploymentPlanSearchForm.IepSearchCriteria.DATETO);
        performSearchAndValidate(plan, IndividualEmploymentPlanSearchForm.IepSearchCriteria.CASEMANAGER);
    }

    /**
     * Perform search by one criteria and check, that the search result is correct.
     * @param plan - individual employment plan
     * @param type - search criteria.
     */
    private void performSearchAndValidate(IndividualEmploymentPlan plan, IndividualEmploymentPlanSearchForm.IepSearchCriteria type) {
        logStep("Fill out any search criteria fields with the data of existing Individual Employment Plan (all the fields one by one)");
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.fillOneCriteria(plan, type);

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logResult("The suitable Individual Employment Plans are shown in the Search Results form");
        searchPage.validateOneCriteria(plan);
    }
}
