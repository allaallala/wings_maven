package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10439")
public class TC_07_09_Training_Provider_Create extends BaseWingsTest {


    public void main() {


        TrainingProvider trainingProvider = createTrainingProvider(true);

        logStep("Find new created Training Provider");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingProviderSearchForm trainingProviderSearchForm = new TrainingProviderSearchForm();
        trainingProviderSearchForm.performSearch(trainingProvider);
        assertTrue("Training provider wasn't found", trainingProviderSearchForm.isFirstSearchResultPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Create training provider
     * @param activeCourse - active course
     * @return training provider
     */
    public TrainingProvider createTrainingProvider(boolean activeCourse) {
        TrainingProvider trainingProvider = new TrainingProvider();
        AdvancedSqlFunctions.resetTrainingProviderCode(trainingProvider.getCode());

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill in all required fields");
        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.fillAllFields(trainingProvider, activeCourse);

        logStep("Create");
        trainingProviderCreationForm.clickButton(Buttons.Create);
        trainingProviderCreationForm.clickButton(Buttons.Done);
        return trainingProvider;
    }
}
