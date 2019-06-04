package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;

/**
 * Created by a.vnuchko on 13.07.2015.
 * Fill out some fields in the search trade trainings form, clear it, check that all fields are cleared.
 */

@TestCase(id = "WINGS-10927")
public class TC_18_04_Trade_Trainings_Search_Clear_Form extends BaseWingsTest {
    String trainingResult = "Dropped Out/Quit";
    String trainingType = "Prerequisite";

    public void main(){

        logStep("Login as a Admin and open Search Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.selectTrainingResult(trainingResult);
        searchPage.selectTrainingType(trainingType);

        logStep("Click the [Clear Form] button");
        searchPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        CustomAssertion.softAssertEquals(searchPage.getSelectedTrainingResult(), Constants.ANY,"Field 'Status of training is not empty'");
        CustomAssertion.softAssertEquals(searchPage.getSelectedTrainingType(), Constants.ANY, "Field 'Status of training is not empty'");
    }
}
