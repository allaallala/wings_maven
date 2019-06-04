package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10665")
public class TC_07_01_Training_Provider_Search extends BaseWingsTest {

    public void main() {


        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);

        TrainingProviderSearchForm searchPage = openTrainingProviderSearch();
        searchPage.performSearch(trainingProvider);

        logStep("Validate provider name");
        assertEquals("Provider name assert fail", trainingProvider.getName(), searchPage.getTrainingProviderName());

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Open training provider search form
     * @return - training provider search form.
     */
    public TrainingProviderSearchForm openTrainingProviderSearch(){
        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Advanced->Training Provider->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Select some data for searching (1 parameter)->Search");
        return new TrainingProviderSearchForm();
    }
}
