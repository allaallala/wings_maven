package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 19.06.2015.
 * Fill out some data in the 'Search' form, click [Clear] button, check that all entries are cleared.
 */

@TestCase(id = "WINGS-10887")
public class TC_16_12_Training_Providers_Search_Clear_Form extends BaseWingsTest {
    protected String providerName = "Achtung";
    protected String fein = "Uwaga";
    protected String vendorNumber = "Pozor";

    public void main(){

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.inputProviderName(providerName);
        providerSearchPage.inputFein(fein);
        providerSearchPage.inputVendorNumber(vendorNumber);

        logStep("Click the [Clear Form] button");
        providerSearchPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        providerSearchPage.checkProviderValues(Constants.EMPTY, Constants.EMPTY, Constants.EMPTY);
    }
}
