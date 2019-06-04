package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Created by a.vnuchko on 13.08.2015.
 * Create some trade training, delete it and check that deletion was successful.
 */

@TestCase(id = "WINGS-10932")
public class TC_18_09_Trade_Trainings_Edit_Delete extends BaseWingsTest {

    public void main() {

        info("Create training and open search training page");
        TradeTraining training = TrainingSteps.createTrainingAndOpenSearchPage(Roles.TRADEDIRECTOR);

        logStep("Fill out any search criteria field with the data of existing Trade Training");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.selectParticipant(training.getTradeEnrollment().getParticipant());

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logStep("Click the 'Participant's name' of any Trade Training shown in the Search Results");
        searchPage.openFirstSearchResult();

        logStep("Click the [Delete] button");
        TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
        detailsPage.deleteRecord();

        logStep("Confirm record deletion");
        detailsPage.confirmDeletion();
        detailsPage.areYouSure(Popup.Yes);

        logResult("The Trade Training record is deleted");
        searchPage.clickButton(Buttons.Search);
        searchPage.noSearchResults();
    }
}
