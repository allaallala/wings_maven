package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 26.06.2015.
 * In this case, create some training_provider, add location for it, approve it trade and check, that is displayed in the appropriate drop-down
 */

@TestCase(id = "WINGS-10900")
public class TC_16_26_Training_Providers_Approve_Location_With_Trade_Approved extends BaseWingsTest {
    String name = "ATA";

    public void main(){


        info("Precondition: Create some training provider, location");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToFalse();
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);
        createTrainingWithPdo(tr);

        logStep("Advanced -> Training -> Training Providers");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill out any search criteria field with the data of existing Training Provider, Click the [Search] button");
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.performSearch(tr);

        logStep("Click the 'Training Provider name' of Training provider from the precondition");
        providerSearchPage.openFirstSearchResult();

        logStep("Click [Add] button in the Locations area");
        TrainingProviderDetailsForm detailsForm = new TrainingProviderDetailsForm();
        detailsForm.clickLocationAdd();

        logStep("Fill out the required fields with valid data and add Trade Approved Course");
        TrainingProviderLocationForm providerLocationPage = new TrainingProviderLocationForm(Constants.EDIT);
        providerLocationPage.fillOtherInformation(loc);
        providerLocationPage.selectRandomTradeCourse();

        logStep("Click [Add]");
        providerLocationPage.clickAdd();

        logStep("Select created Location and click Approve for Trade button");
        detailsForm.clickApproveForTrade();

        logStep("Go to Participants -> Trade -> Trade Training");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING);

        logStep("Choose 'Create' on the pop up");
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Select valid participant");
        TradeTrainingCreateForm trainingCreatePage = new TradeTrainingCreateForm();
        trainingCreatePage.selectParticipant(name, name);

        logStep("Select Training Provider and Location which was created above.");
        trainingCreatePage.selectTrainingProvider(tr);

        logResult("Approved location can be selected in the appropriate drop-down");
        trainingCreatePage.selectLocation(loc.getName());
    }
    /**
     * create new Training Provider With Pdo.
     * @param tr - training provider
     */
    private  void createTrainingWithPdo(TrainingProvider tr){
        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.clickPdo();
        trainingProviderCreationForm.fillAllFields(tr);

        trainingProviderCreationForm.clickButton(Buttons.Create);
        trainingProviderCreationForm.clickButton(Buttons.Done);
    }

}
