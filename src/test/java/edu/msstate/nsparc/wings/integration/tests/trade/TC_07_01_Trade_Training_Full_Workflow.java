package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10450")
public class TC_07_01_Trade_Training_Full_Workflow extends BaseWingsTest {


    public void main() {

        TradeTraining tradeTraining = new TradeTraining();
        TrainingSteps.createTraining(tradeTraining.getTrainingProvider(), Roles.TRADEDIRECTOR);
        TradeEnrollmentSteps.createTradeEnrollment(tradeTraining.getTradeEnrollment(), Roles.ADMIN);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Fill out the creation form");
        TradeTrainingCreateForm creationForm = new TradeTrainingCreateForm();
        creationForm.fillOutCreationForm(tradeTraining);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        TradeTrainingDetailsForm detailsForm = new TradeTrainingDetailsForm();
        detailsForm.validateInformation(tradeTraining);

        logStep("Navigate to Participants - Trade - Trade Training - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Trade Training and open it");
        TradeTrainingSearchForm searchForm = new TradeTrainingSearchForm();
        searchForm.performSearchAndOpen(tradeTraining);

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        tradeTraining.changeResult(Constants.COMPLETED);
        TradeTrainingEditForm editForm = new TradeTrainingEditForm();
        editForm.editDetails(tradeTraining);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(tradeTraining);
    }
}

