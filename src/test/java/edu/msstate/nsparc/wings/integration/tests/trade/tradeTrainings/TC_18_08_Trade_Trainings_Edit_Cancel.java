package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

/**
 * Created by a.vnuchko on 13.08.2015.
 * Create some trade training, edit some data, cancel edition and check that changes are not saved.
 */

@TestCase(id = "WINGS-10931")
public class TC_18_08_Trade_Trainings_Edit_Cancel extends BaseWingsTest {

    public void main() {

        info("Create training and open search training page");
        TradeTraining training = TrainingSteps.createTrainingAndOpenSearchPage(Roles.STAFF);

        logStep("Fill out any search criteria field with the data of existing Trade Training");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.selectParticipant(training.getTradeEnrollment().getParticipant());

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logStep("Click the 'Participant's name' of any Trade Training shown in the Search Results");
        searchPage.openFirstSearchResult();

        logStep("Click the [Edit] button");
        TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        TradeTrainingEditForm editPage = new TradeTrainingEditForm();
        editPage.inputDateCompletion(CommonFunctions.getFutureDate(Constants.DAYS_MONTH));

        logStep("Click the [Cancel] button");
        detailsPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The Trade Training Detail Screen is shown, the changes are not saved");
        detailsPage.validateInformation(training);
    }

}
