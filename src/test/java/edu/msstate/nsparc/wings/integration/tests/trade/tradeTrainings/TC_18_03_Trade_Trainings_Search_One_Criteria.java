package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Created by a.vnuchko
 * Search trade trainings by one criteria (one by one) and check, that is displayed correctly in the Search Page.
 */

@TestCase(id = "WINGS-10926")
public class TC_18_03_Trade_Trainings_Search_One_Criteria extends BaseWingsTest {

    public void main(){

        info("Precondition: Create some Trade Trainings");
        TradeTraining training = new TradeTraining();
        TrainingSteps.createTradeTraining(training, Roles.STAFF, Roles.ADMIN);

        logStep("Log in as admin and open trade training search form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.PARTICIPANT);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.PETITION);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.COURSE);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.SERVICE_CENTER);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.TRAINING_STATUS);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.TRAINING_TYPE);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.FIRSTDAY_FROM);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.FIRSTDAY_TO);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.COMPLETIONDAY_FROM);
        performSearchAndValidate(training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA.COMPLETIONDAY_TO);
    }

    /**
     * Perform search and validate
     * @param training - trade training data
     * @param type - training type
     */
    private void performSearchAndValidate(TradeTraining training, TradeTrainingSearchForm.TRADE_TRAINING_CRITERIA type){
        logStep("Fill out any search criteria fields with the data of existing Trade Training (all the fields one by one)");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.performSearchCriteria(training, type);

        logResult("The suitable Trade Trainings are shown in the Search Results form");
        searchPage.validateEntireRecord(training);
    }

}
