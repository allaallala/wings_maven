package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 *
 * Created by a.vnuchko on 11.06.2015.
 */

@TestCase(id = "WINGS-10882")
public class TC_16_07_Training_Providers_Add_Locations_Mississippi extends BaseWingsTest {
    public void main(){
        String mississippi = "Mississippi";
        String alabama = "Alabama";

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Click [Add] button in the Locations area");
        TrainingProviderCreationForm tpcf = new TrainingProviderCreationForm();
        tpcf.clickLocationAdd();

        logStep("Check, that county drop-down isn't appear if state(e.g Alabama) is chosen");
        TrainingProviderLocationForm providerLocationPage = new TrainingProviderLocationForm();
        providerLocationPage.selectState(alabama);
        providerLocationPage.checkCountyPresent(Constants.FALSE);

        logStep("Select a state = Mississippi");
        providerLocationPage.selectState(mississippi);

        logResult("A County drop-down should appear on the Creation screen");
        providerLocationPage.checkCountyPresent(Constants.TRUE);
    }
}
