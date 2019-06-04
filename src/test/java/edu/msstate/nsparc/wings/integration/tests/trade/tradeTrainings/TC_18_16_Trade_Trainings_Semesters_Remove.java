package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSemesterForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Add a semester on creating trade training, choose semester and click [Remove] button. Check, that semester is not displayed.
 * Created by a.vnuchko on 18.08.2015.
 */

@TestCase(id = "WINGS-10939")
public class TC_18_16_Trade_Trainings_Semesters_Remove extends BaseWingsTest {
    public void main() {

        info("Preconditions: generate trade training data");
        TradeTraining training = new TradeTraining();

        logStep("Login to the system and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Add a semester");
        TradeTrainingCreateForm creationPage = new TradeTrainingCreateForm();
        creationPage.addSemester();
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.addSemester(training.getTradeTrainingSemesters().get(0));

        logStep("Select a Semester and click [Remove]");
        creationPage.chooseFirstSemester();
        creationPage.removeSemester();

        logResult("The selected Semester is removed from the list");
        creationPage.noSearchResults();
    }
}
