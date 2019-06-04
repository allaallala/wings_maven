package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSemesterForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Add some semester on creating trade training. Check that button [Edit/Remove] are not active, until semester radio button is chosen.
 * Created by a.vnuchko on 17.08.2015.
 */

@TestCase(id = "WINGS-10938")
public class TC_18_15_Trade_Trainings_EditRemove_Security extends BaseWingsTest {

    public void main(){

        info("Preconditions: generate trade training data");
        TradeTraining training = new TradeTraining();

        logStep("Login to the system and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Add a semester");
        TradeTrainingCreateForm creationPage = new TradeTrainingCreateForm();
        creationPage.addSemester();
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.addSemester(training.getTradeTrainingSemesters().get(0));

        logResult("The buttons [Edit] and [Remove] are inactive until a radio-button of the corresponding Semester is selected");
        creationPage.disabledRemovePresent();

        logStep("Select a Semester");
        creationPage.chooseFirstSemester();

        logResult("The buttons [Edit] and [Remove] are active");
        creationPage.clickButton(Buttons.Edit);
        //semesterPage.assertIsOpen();
        semesterPage.clickButton(Buttons.Cancel);
        creationPage.chooseFirstSemester();
        creationPage.removeSemester();
        creationPage.noSearchResults();
    }
}
