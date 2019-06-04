package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.PopUpMenu;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Created by a.vnuchko on 18.06.2015.
 * Check that in the Training Provider Search form are displayed all required records.
 */

@TestCase(id = "WINGS-10885")
public class TC_16_10_Training_Providers_Search_Without_Parameters extends BaseWingsTest {

    public void main() {
        String regExp = "\\d{1,}?,?\\d{2,}";

        logStep("Log into the system as Staff or Executive or admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced -> Training -> Training Providers");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Choose 'Search' on the pop up");
        PopUpMenu pop = new PopUpMenu();
        pop.clickSearch();

        logStep("Click the [Search] button (don't fill out any field)");
        TrainingProviderSearchForm providerSearchPage = new TrainingProviderSearchForm();
        providerSearchPage.clickButton(Buttons.Search);

        logResult("All the Training Providers are shown in the Search Results form");
        int trainingProviderCount = AdvancedSqlFunctions.getTrainingProviderCount();
        String recordsCount = CommonFunctions.regexGetMatch(providerSearchPage.getSearchedCount(), regExp);
        Assert.assertEquals("Incorrect quantity of training providers records on the search page",
                String.valueOf(trainingProviderCount), recordsCount.replace(",", Constants.EMPTY));
    }
}
