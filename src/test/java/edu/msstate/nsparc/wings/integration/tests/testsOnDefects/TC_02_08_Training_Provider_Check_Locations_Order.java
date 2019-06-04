package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10546")
public class TC_02_08_Training_Provider_Check_Locations_Order extends BaseWingsTest {

    private TrainingProvider trainingProvider = new TrainingProvider();


    public void main() {
        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill in all required fields and create 10 locations for one training provider");
        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.fillAllFieldsAndCreate10Locations(trainingProvider);

        logStep("Create");
        trainingProviderCreationForm.clickButton(Buttons.Create);

        logStep("Compare DB values with application values");
        TrainingSteps.validateLocationCodes(trainingProvider);
    }
}
