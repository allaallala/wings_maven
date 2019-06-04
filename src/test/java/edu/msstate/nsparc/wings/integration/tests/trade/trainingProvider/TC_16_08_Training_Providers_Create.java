package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


@TestCase(id = "WINGS-10883")
public class TC_16_08_Training_Providers_Create extends BaseWingsTest {

    public void main(){

        info("Creating new training provider");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill out some fields");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.fillTextFields(tr);

        logStep("Click the [Cancel] button");
        providerCreationPage.clickButton(Buttons.Cancel);
        providerCreationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown. a new Training Provider isn't created");
        Browser.getInstance().waitForPageToLoad();
        new StaffHomeForm();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.inputProviderName(tr.getName());
        providerSearchPage.clickButton(Buttons.Search);
        providerSearchPage.noSearchResults();
    }
}

