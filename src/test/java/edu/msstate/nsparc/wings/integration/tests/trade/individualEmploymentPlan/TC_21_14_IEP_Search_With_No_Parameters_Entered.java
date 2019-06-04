package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Open individual employment search form, don't fill out any search criteria. Check, that all records are displayed.
 * Created by a.vnuchko on 27.08.2015.
 */

@TestCase(id = "WINGS-11006")
public class TC_21_14_IEP_Search_With_No_Parameters_Entered extends BaseWingsTest {

    public void main() {
        String regex = "\\d{1,}?,?\\d{2,}";

        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        logStep("Click the [Search] button (Don't fill out any search criteria field)");
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.clickButton(Buttons.Search);

        logResult("All the Individual Employment Plans are shown in the Search Results form");
        int iepCount = ParticipantSqlFunctions.getIndividualEmploymentPlanCount();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), regex);
        Assert.assertEquals("Incorrect quantity of individual employment plans records on the search page",
                String.valueOf(iepCount), recordsCount.replace(",", ""));

    }
}
