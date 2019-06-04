package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that preview for the semesters can be opened from the trade training and contains correct information
 * Created by a.vnuchko on 14.10.2015.
 */

@TestCase(id = "WINGS-11035")
public class TC_22_11_Collection_Semesters_Test extends BaseWingsTest {

    public void main() {
        info("Precondition: create trade training");
        TradeTraining train = new TradeTraining();
        TrainingSteps.createTradeTraining(train, Roles.STAFF, Roles.ADMIN);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        logStep("Check that the preview can be successfully opened for the Semesters (from the Trade Training participantSSDetails page) and contains the correct information");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.performSearchAndOpen(train);

        TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
        detailsPage.validateSemestersData(train);
    }
}
