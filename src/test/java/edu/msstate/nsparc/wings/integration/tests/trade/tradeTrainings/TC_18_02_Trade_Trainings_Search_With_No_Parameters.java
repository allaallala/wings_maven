package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Search with no parameters for trade training and check that all fields are displayed.
 */

@TestCase(id = "WINGS-10925")
public class TC_18_02_Trade_Trainings_Search_With_No_Parameters extends BaseWingsTest {
    private String regex = "\\d{1,}?,?\\d{2,}";

    public void main() {
        logStep("Log in as admin and open search form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        logStep("Don't fill out any search criteria field");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.clickButton(Buttons.Search);

        logResult("All the Trade Trainings are shown in the Search Results form");
        int trainingProviderCount = EmployerSqlFunctions.getTradeTrainingCount();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), regex);
        Assert.assertEquals("Incorrect quantity of training providers records on the search page",
                String.valueOf(trainingProviderCount), recordsCount.replace(",", ""));

    }
}
