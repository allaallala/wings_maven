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
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10684")
public class TC_07_18_Training_Provider_Create_Cancel extends BaseWingsTest {


    public void main() {


        TrainingProvider trainingProvider = new TrainingProvider();

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill in all fields you need->Cancel");
        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.fillAllFields(trainingProvider);
        trainingProviderCreationForm.clickButton(Buttons.Cancel);
        trainingProviderCreationForm.areYouSure(Popup.Yes);

        logStep("Try to find Training Provider");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingProviderSearchForm trainingProviderSearchForm = new TrainingProviderSearchForm();
        trainingProviderSearchForm.performSearch(trainingProvider);
        assertFalse("Training provider was found", trainingProviderSearchForm.isFirstSearchResultPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
