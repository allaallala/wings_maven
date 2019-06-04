package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 25.06.2015.
 * Add location, edit location and check that for mississippi state county drop-down is displayed.
 */

@TestCase(id = "WINGS-10895")
public class TC_16_21_Training_Providers_Edit_Locations_Mississippi extends TC_16_20_Training_Providers_Edit_Locations_Edit {

    public void main(){

        String alabamaState = "Alabama";

        TrainingProviderLocationForm providerLocationPage = repeatedSteps();

        logStep("Select a  state != Mississippi, e.g Alabama and check, that county drop-down is not appeared");
        providerLocationPage.selectState(alabamaState);
        providerLocationPage.checkCountyPresent(Constants.FALSE);

        logStep("Select a  state = Mississippi");
        providerLocationPage.selectState(alabamaState);

        logResult("A County drop-down should appear on the Creation screen");
        providerLocationPage.checkCountyPresent(Constants.TRUE);
    }
}
