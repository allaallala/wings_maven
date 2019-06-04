package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


/**
 * Created by a.vnuchko on 30.06.2015.
 * Check, that is possible to create Training provider without provider location code in the location.
 */

@TestCase(id = "WINGS-10892")
public class TC_16_18_Training_Providers_Add_Locations_Without_PLC extends BaseWingsTest {

    public void main(){


        info("Precondition: generate some random data for training provider and for location");
        TrainingProviderLocation loc = new TrainingProviderLocation(Constants.FALSE, Constants.FALSE);
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Click [Add] button in the Locations area");
        TrainingProviderCreationForm creationPage = new TrainingProviderCreationForm();
        creationPage.clickLocationAdd();

        logStep("Fill out the required fields with valid data, add a location");
        TrainingProviderLocationForm locPage = new TrainingProviderLocationForm();
        locPage.addLocation(loc);
        creationPage.fillTextFields(tr);
        SearchResultsForm resultsForm = new SearchResultsForm();
        Assert.assertEquals("Incorrect value of the loc name",  loc.getName(), resultsForm.getFirstRowRecordText(3)) ;
        Assert.assertEquals("Incorrect value of the loc address", loc.getCity()+", "+loc.getState()+" "+loc.getZipCode(), resultsForm.getFirstRowRecordText(4));

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("A new location is added without the Provider Location Code. It is possible to save such a training provider. " +
                "All the participantSSDetails are displayed correctly after the record is saved.");
        creationPage.clickButton(Buttons.Done);

        logResult("All the participantSSDetails are displayed correctly after the record is saved.");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();
        searchPage.performSearch(tr);
        searchPage.checkProviderValues(tr.getName(), tr.getFein(), tr.getDfa());
    }
}
