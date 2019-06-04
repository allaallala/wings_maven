package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

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
import org.testng.Assert;


/**
 * Created by a.vnuchko on 13.07.2015.
 * Check pop-up, that should appear wnen we click [Notes] button
 */

@TestCase(id = "WINGS-10929")
public class TC_18_06_Trade_Trainings_View_Notes extends BaseWingsTest {
    TradeTraining training;

    public void main(){
        repeatedSteps();

        logStep("Click the [Notes] button");
        TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
        detailsPage.openNotesBlock();

        logResult("A pop-up appears and contains the notes for a selected  participant");
        Assert.assertTrue(detailsPage.getPopNoteText().contains(training.getTradeEnrollment().getParticipant().getFirstName()));
    }

    /**
     * Represents steps, that can be represented in other tests.
     */
    protected void repeatedSteps(){
        info("Precondition: create some trade training");
        training = new TradeTraining();
        TrainingSteps.createTradeTraining(training, Roles.STAFF, Roles.ADMIN);

        logStep("Login as a Admin and open Search Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);

        logStep("Fill out any search criteria field with the data of existing Trade Training, Click the [Search] button");
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.performSearchAndOpen(training);
    }

}
