package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that preview for location can be opened from training provider and contains correct information
 * Created by a.vnuchko on 14.10.2015.
 */

@TestCase(id = "WINGS-11032")
public class TC_22_07_Collection_Location_Test extends BaseWingsTest {

    public void main(){


        info("Precondition: create training provider");
        TrainingProvider provider = new TrainingProvider();
        TrainingSteps.createTraining(provider, Roles.PCADMIN);

        logStep("Log in WINGS as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Check that the preview can be successfully opened for the Location (from the Training Provider participantSSDetails page) and contains the correct information");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();
        searchPage.performSearch(provider);
        searchPage.openFirstSearchResult();
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        detailsPage.validateLocation(provider);
    }
}
