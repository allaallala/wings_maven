package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
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


/**
 * Created by a.vnuchko on 19.06.2015.
 * In this case we check Training Provider Search using only one parameter (criteria).
 */
@TestCase(id = "WINGS-10886")
public class TC_16_11_Training_Providers_Search_One_Criteria extends BaseWingsTest {

    public void main() {
        info("Preconditions: Creating new training provider");
        TrainingProvider tr = new TrainingProvider();
        tr.convertToTrade();
        TrainingSteps.createTraining(tr, Roles.ADMIN);

        logStep("Log into the system as Staff or Executive or admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced -> Training -> Training Providers");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        performSearchAndValidate(tr, TrainingProvider.TrainingProviderCriteria.TRAINING_PROVIDER_CODE);
        performSearchAndValidate(tr, TrainingProvider.TrainingProviderCriteria.FEIN_LENGTH);
        performSearchAndValidate(tr, TrainingProvider.TrainingProviderCriteria.DFA_NUMBER);
        performSearchAndValidate(tr, TrainingProvider.TrainingProviderCriteria.WIA_APPROVED_NO);
        performSearchAndValidate(tr, TrainingProvider.TrainingProviderCriteria.TRADE_APPROVED_YES);



    }
    /**
     * Method for searching and validating rapid response events.
     * @param tr - Training Provider object
     * @param searchType - search criteria.
     */
    private void performSearchAndValidate(TrainingProvider tr, TrainingProvider.TrainingProviderCriteria searchType) {
        logStep("Fill out any search criteria field with the data of existing Training Provider (all the fields one by one)- "+searchType);
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.performSearchCriteria(tr, searchType);

        logResult("The suitable Rapid  Response Events are shown in the Search Results form");
        providerSearchPage.validateSearchResult(tr);
    }

}
