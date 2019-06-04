package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


/**
 * Create some training provider , do not connect it with trade/wia enrollment and check, that deletion is possible
 * Created by a.vnuchko on 29.06.2015.
 */

@TestCase(id = "WINGS-10889")
public class TC_16_14_Training_Provider_Delete_Possible extends BaseWingsTest {
    private static final Integer SYMBOL_NUMBER = 147;

    public void main(){
        String expectedText = "There are no training enrollments with this assigned training provider. If you would like to delete this training provider, click the button below.";


        info("Precondition: Create a Training Provider and DO NOT use it in different types of training enrollments (Trade + WIA)");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();
        TrainingSteps.createTraining(tr, Roles.ADMIN);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Search);

        logStep("Find a training provider from the preconditions and go to its detail page");
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();
        searchPage.performSearch(tr);
        searchPage.openFirstSearchResult();

        logResult("The system shows the following message: 'There are no training enrollments with this assigned training provider. If you would like to delete this training provider, click the button below.");
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        Assert.assertEquals("Incorrect warning text about deletion", expectedText, detailsPage.getWarningText().substring(0,SYMBOL_NUMBER));

        logResult("Check that deletion is success");
        detailsPage.deleteRecord();
        searchPage.performSearch(tr);
        searchPage.noSearchResults();

    }
}
