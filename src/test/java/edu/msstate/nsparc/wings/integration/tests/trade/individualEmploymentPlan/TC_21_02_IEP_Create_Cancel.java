package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


/**
 * Fill out individual employment plan creation form, click [Cancel] and check, that new IEP isn't created.
 * Created by a.vnuchko on 20.08.2015.
 */

@TestCase(id = "WINGS-10994")
public class TC_21_02_IEP_Create_Cancel extends BaseWingsTest {

    public void main() {
        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Boolean.TRUE, Boolean.FALSE);

        IndividualEmploymentPlanCreationForm creationPage = IEPSteps.openFillOutCreationForm(new User(Roles.STAFF), plan);

        logStep("Click the [Cancel] button");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown.  a new Individual Employment Plan isn't created");
        Browser.getInstance().waitForPageToLoad();
        //new StaffHomeForm().assertIsOpen();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS);
        BaseWingsSteps.popClick(Popup.Search);
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.performSearch(new User(Roles.STAFF), plan);
        searchPage.noSearchResults();

    }
}
