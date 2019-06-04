package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 25.06.2015.
 * Edit location, click [Cancel] and check, that changes are not saved.
 */

@TestCase(id = "WINGS-10896")
public class TC_16_22_Training_Providers_Edit_Locations_Cancel extends TC_16_20_Training_Providers_Edit_Locations_Edit{
    private static final String locationName = "Boh nam raic'!";

    public void main(){

        TrainingProviderLocationForm providerLocationPage = repeatedSteps();

        logStep("Edit any parameter");
        providerLocationPage.inputLocationName(locationName);

        logStep("Click the [Cancel] button");
        providerLocationPage.clickButton(Buttons.Cancel);
        providerLocationPage.areYouSure(Popup.Yes);

        logResult("The Training Provider Creation Screen is shown, the changes are not saved");
        CustomAssertion.softAssertEquals(new SearchResultsForm().getFirstRowRecordText(3), loc.getName(), "Incorrect location name in the result table");
    }

}
