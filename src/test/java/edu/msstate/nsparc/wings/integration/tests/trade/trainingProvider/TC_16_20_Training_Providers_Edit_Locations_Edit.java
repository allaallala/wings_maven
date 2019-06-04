package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
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
 * Created by a.vnuchko on 25.06.2015.
 * Check that is possible to change added locations.
 */

@TestCase(id = "WINGS-10894")
public class TC_16_20_Training_Providers_Edit_Locations_Edit extends BaseWingsTest {
    TrainingProviderLocation loc;

    public void main() {

        String newLocationName = "Derpt";

        TrainingProviderLocationForm providerLocationPage = repeatedSteps();

        logStep("Edit any parameter");
        providerLocationPage.inputLocationName(newLocationName);

        logStep("Click the [Save changes] button");
        providerLocationPage.clickButton(Buttons.Save);

        logResult("The Training Provider Creation Screen is show. The changes are saved");
        CustomAssertion.softAssertEquals(new SearchResultsForm().getFirstRowRecordText(3), newLocationName, "Incorrect location name in the result table");
    }

    /**
     * Repeated steps for the test
     *
     * @return - training provider location form.
     */
    protected TrainingProviderLocationForm repeatedSteps() {
        info("Creating new training provider");
        loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Add a Location");
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        TrainingProviderLocationForm providerLocationPage = providerCreationPage.addLoc(loc);

        logStep("Select a location and click [Edit]");
        new SearchResultsForm().openSelectedResult();
        providerLocationPage.clickButton(Buttons.Edit);
        return providerLocationPage;
    }

}
