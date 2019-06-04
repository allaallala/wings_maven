package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 26.06.2015.
 * In thic case, check that location may be deactivated or activated.
 */

@TestCase(id = "WINGS-10899")
public class TC_16_25_Training_Provider_Edit_Locations_Activate_Deactivate extends BaseWingsTest {

    public void main(){
        info("Creating new training provider");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();

        info("Creating new training provider loc");
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.TRUE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Add a location and create Training Provider");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.addLoc(loc);
        providerCreationPage.fillTextFields(tr);
        providerCreationPage.clickButton(Buttons.Create);

        logStep("Click the 'Lock' icon next to  the location several times");
        providerCreationPage.clickDisable();

        logResult("the lock icon should change from 'Unlocked' to 'Locked");
        providerCreationPage.clickEnable();
        providerCreationPage.checkDisable();
    }
}
