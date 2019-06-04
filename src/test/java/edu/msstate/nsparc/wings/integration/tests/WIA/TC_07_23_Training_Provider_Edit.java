package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderEditForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10686")
public class TC_07_23_Training_Provider_Edit extends BaseWingsTest {

    public void main () {
        TrainingProvider trainingProvider = new TrainingProvider();
        AdvancedSqlFunctions.resetTrainingProviderCode(trainingProvider.getCode());

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill in all required fields");
        TrainingProviderCreationForm trainingProviderCreationForm = new TrainingProviderCreationForm();
        trainingProviderCreationForm.fillAllFields(trainingProvider);

        logStep("Create");
        trainingProviderCreationForm.clickButton(Buttons.Create);
        logStep("Edit");
        TrainingProviderDetailsForm trainingProviderDetailsForm = new TrainingProviderDetailsForm();
        trainingProviderDetailsForm.editProvider();
        logStep("Edit Training Provider Details");
        TrainingProvider editedTrainingProvider = new TrainingProvider();
        editedTrainingProvider.setFein(trainingProvider.getFein());
        TrainingProviderEditForm trainingProviderEditForm = new TrainingProviderEditForm();
        trainingProviderEditForm.editTrainingProviderDetails(editedTrainingProvider);
        trainingProviderEditForm.clickButton(Buttons.Save);
        logStep("Check all changes are saved");
        trainingProviderDetailsForm = new TrainingProviderDetailsForm();
        trainingProviderDetailsForm.validateInformation(editedTrainingProvider);
    }
}
