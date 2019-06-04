package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Check, that is possible to add location for a training provider
 */

@TestCase(id = "WINGS-10884")
public class TC_16_09_Training_Providers_Add_Locations extends BaseWingsTest {

    public void main(){


        info("Creating new training provider");
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Click [Add] button in the Locations area");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.clickLocationAdd();

        logStep("Fill out the required fields with valid data and click Add");
        TrainingProviderLocationForm providerLocationPage = new TrainingProviderLocationForm();
        providerLocationPage.fillOtherInformation(loc);

        logStep("Click [Add]");
        providerLocationPage.clickAdd();

        logResult("The Training Provider Creation Screen is shown. A new Location was added and contains the same data you have just entered");
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softAssertEquals(resultsForm.getFirstRowRecordText(3), loc.getName(), "Incorrect value of the loc name");
        CustomAssertion.softAssertEquals(resultsForm.getFirstRowRecordText(4), loc.getCity() + ", " + loc.getState() + " " + loc.getZipCode(),"Incorrect value of the loc address");
    }
}
