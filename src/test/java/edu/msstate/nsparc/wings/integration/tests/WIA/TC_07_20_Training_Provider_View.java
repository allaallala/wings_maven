package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


// Author: d.poznyak
@TestCase(id = "WINGS-10685")
public class TC_07_20_Training_Provider_View extends TC_07_01_Training_Provider_Search {

    public void main() {
        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);

        TrainingProviderSearchForm trainingProviderSearchForm = openTrainingProviderSearch();
        trainingProviderSearchForm.performSearch(trainingProvider);

        logStep("Select some Training Provider and open it");
        trainingProviderSearchForm.openFirstSearchResult();

        logStep("Validate displayed information");
        TrainingProviderDetailsForm trainingProviderDetailsForm = new TrainingProviderDetailsForm();
        trainingProviderDetailsForm.validateInformation(trainingProvider);

        BaseNavigationSteps.logout();
        logEnd();
    }
}
