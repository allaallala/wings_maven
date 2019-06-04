package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 26.06.2015.
 * Check, that if location is not chosen, buttons [Edit], [Remove] are not available for clicking.
 */

@TestCase(id = "WINGS-10897")
public class TC_16_23_Training_Providers_Edit_Locations_Security extends BaseWingsTest {

    public void main() {


        info("Creating new training provider loc");
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Add a Location");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.addLoc(loc);

        logResult("Do Not Select a location.The buttons [Edit] and [Remove] are inactive until a radio-button of the corresponding Location is selected");
        providerCreationPage.checkEditRemoveDisabled();
    }
}
