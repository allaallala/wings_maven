package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


/**
 * Fill out some search criteria fields in the search individual employment form , cancel it and check, that staff home form is shown.
 * Created by a.vnuchko on 26.08.2015.
 */

@TestCase(id = "WINGS-11009")
public class TC_21_17_IEP_Search_Cancel extends BaseWingsTest {

    public void main() {


        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Constants.TRUE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.selectParticipant(plan.getParticipant());

        logStep("Click the [Cancel] button");
        searchPage.clickButton(Buttons.Cancel);

        logResult("The Staff Home Screen is shown");
        Browser.getInstance().waitForPageToLoad();
        //new StaffHomeForm().assertIsOpen();
    }
}
