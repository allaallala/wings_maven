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
 * Check, that training provider can be created, searched, viewed and edited.
 * Created by a.vnuchko on 28.01.2016.
 */

@TestCase(id = "WINGS-10902")
public class TC_16_29_Training_Providers_Full_Workflow extends BaseWingsTest {

    public void main() {
        info("Generate data for creating new training provider");
        TrainingProvider tr = new TrainingProvider();
        TrainingProvider tr1 = new TrainingProvider();
        tr.convertToTrade();

        //Create
        logStep("Log into the system as Trade Director. Click 'Training Providers' and click [Create] in the pop-up. Fill out required fields and click [Create]");
        TrainingSteps.createTraining(tr, Roles.ADMIN);

        //Search
        logStep("Log into the system as Staff or Executive. Click 'Training Providers' and click [Search] in the pop-up");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Search);
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();

        logStep("Fill out any search criteria field with the data of existing Training Provider and click [Search] button");
        searchPage.performSearch(tr);

        logResult("The suitable  Training Providers are shown in the Search Results form");
        searchPage.validateSearchResult(tr);

        //View
        logStep("Click the 'Training Provider Name' of any Training Provider shown in the Search Results");
        searchPage.openFirstSearchResult();

        logResult("The Training Provider Detail Screen with correct and actual information is shown");
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        detailsPage.validateInformation(tr);
        searchPage.openFirstSearchResult();

        //Edit
        logStep("Click the [Edit] button");
        new SearchResultsForm().openSelectedResult();
        detailsPage.editProvider();

        logStep("Edit any parameter");
        TrainingProviderEditForm editPage = new TrainingProviderEditForm();
        editPage.inputProviderName(tr1.getName());
        editPage.inputFein(tr1.getFein());
        editPage.inputVendorNumber(tr1.getDfa());

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("The Training Provider Detail Screen is shown. The changes are saved");
        detailsPage.validateInformation(tr1);
    }
}
