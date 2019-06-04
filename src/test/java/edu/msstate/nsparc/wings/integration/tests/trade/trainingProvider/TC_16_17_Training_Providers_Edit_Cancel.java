package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderEditForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 24.06.2015.
 * Find any Training provider, edit any data, click [Cancel] button and check, that data isn't changed.
 */

@TestCase(id = "WINGS-10891")
public class TC_16_17_Training_Providers_Edit_Cancel extends BaseWingsTest {

    public void main(){


        info("Precondition: Create some Training Providers");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();
        TrainingSteps.createTraining(tr, Roles.ADMIN);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Search);

        logStep("Fill out any search criteria field with the data of existing Training Provider");
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.inputProviderName(tr.getName());

        logStep("Click the [Search] button");
        providerSearchPage.clickButton(Buttons.Search);

        logStep("Click the 'Training Provider name' of any Training Provider shown in the Search Results");
        providerSearchPage.openFirstSearchResult();

        logStep("Click the [Edit] button");
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        new SearchResultsForm().openSelectedResult();
        detailsPage.editProvider();

        logStep("Edit any parameters");
        TrainingProviderEditForm providerEditPage = new TrainingProviderEditForm();
        providerEditPage.inputProviderName(tr.getName());
        providerEditPage.inputFein(tr.getFein());
        providerEditPage.inputVendorNumber(tr.getDfa());

        logStep("Click the [Cancel] button");
        providerEditPage.clickButton(Buttons.Cancel);
        providerEditPage.areYouSure(Popup.Yes);

        logResult("The Training Provider Detail Screen is shown, the changes are not saved");
        detailsPage.validateInformation(tr);
    }

}
