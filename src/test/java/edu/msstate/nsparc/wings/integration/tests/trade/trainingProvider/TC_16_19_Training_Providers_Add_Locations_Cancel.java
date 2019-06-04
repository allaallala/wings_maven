package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 25.06.2015.
 * Cancel adding of the location and check, that new one isn't appeared
 */

@TestCase(id = "WINGS-10893")
public class TC_16_19_Training_Providers_Add_Locations_Cancel extends BaseWingsTest {

    public void main(){
        info("Creating new training provider location");
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Click [Add] button in the Locations area");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.clickLocationAdd();

        logStep("Fill out any data");
        TrainingProviderLocationForm providerLocationPage = new TrainingProviderLocationForm();
        providerLocationPage.fillOtherInformation(loc);

        logStep("Click the [Cancel] button");
        providerLocationPage.clickButton(Buttons.Cancel);
        providerLocationPage.areYouSure(Popup.Yes);

        logResult("The Training Provider Creation Screen is shown. A new Location wasn't' added");
        providerCreationPage.noSearchResults();
    }
}
