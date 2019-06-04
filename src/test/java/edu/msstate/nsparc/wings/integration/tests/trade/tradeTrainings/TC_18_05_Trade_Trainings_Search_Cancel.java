package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


/**
 * Created by a.vnuchko on 13.07.2015.
 * Fill out some search criteria, click [Cancel] button, check that staff home form is shown.
 */

@TestCase(id = "WINGS-10928")
public class TC_18_05_Trade_Trainings_Search_Cancel extends BaseWingsTest {
    String trainingResult = "Dropped Out/Quit";
    String type = "Prerequisite";

    public void main(){

        logStep("Login as a Admin and open Search Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.selectTrainingResult(trainingResult);
        searchPage.selectTrainingType(type);

        logStep("Click the [Cancel] button");
        searchPage.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();

        logResult("The Staff Home Screen is shown");
        //new StaffHomeForm().assertIsOpen();
    }
}
