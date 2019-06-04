package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Fill some search criteria in the Search Training Provider Form , click [Cancel] button and check, that Staff Home Screen is shown.
 * Created by a.vnuchko on 24.06.2015.
 */

@TestCase(id = "WINGS-10888")
public class TC_16_13_Training_Providers_Search_Cancel extends TC_16_12_Training_Providers_Search_Clear_Form {

    public void main(){

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.inputProviderName(providerName);
        providerSearchPage.inputFein(fein);
        providerSearchPage.inputVendorNumber(vendorNumber);

        logStep("Click the [Cancel] button");
        providerSearchPage.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logResult("The Staff Home Screen is shown");
        //new StaffHomeForm().assertIsOpen();
    }
}
