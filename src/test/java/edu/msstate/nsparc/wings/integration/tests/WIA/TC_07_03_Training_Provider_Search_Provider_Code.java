package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

// Author: d.poznyak

@TestCase(id = "WINGS-10669")
public class TC_07_03_Training_Provider_Search_Provider_Code extends TC_07_01_Training_Provider_Search {

    public void main() {


        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);

        TrainingProviderSearchForm trainingProviderSearchForm = openTrainingProviderSearch();
        trainingProviderSearchForm.inputProviderCode(trainingProvider.getCode());
        trainingProviderSearchForm.clickButton(Buttons.Search);

        logStep("Validate provider code");
        assertEquals("Provider code assert fail", trainingProvider.getCode(), trainingProviderSearchForm.getProviderCodeText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
