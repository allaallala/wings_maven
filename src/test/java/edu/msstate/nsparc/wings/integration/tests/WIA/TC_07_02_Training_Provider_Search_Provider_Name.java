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

@TestCase(id = "WINGS-10667")
public class TC_07_02_Training_Provider_Search_Provider_Name extends TC_07_01_Training_Provider_Search {

    public void main() {


        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);

        TrainingProviderSearchForm trainingProviderSearchForm = openTrainingProviderSearch();
        trainingProviderSearchForm.inputProviderName(trainingProvider.getName());
        trainingProviderSearchForm.clickButton(Buttons.Search);

        logStep("Validate provider name");
        assertEquals("Provider name assert fail", trainingProvider.getName(), trainingProviderSearchForm.getTrainingProviderName());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
