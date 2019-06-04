package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Create some training_provider and check system message
 * "There are <number> training enrollments with this assigned training provider, therefore it cannot be deleted."
 * Created by a.vnuchko on 29.06.2015.
 */

@TestCase(id = "WINGS-10890")
public class TC_16_15_Training_Provider_Delete_Impossible extends BaseWingsTest {
    private static final String EXPECTED_TEXT = "There are %1$s training enrollments with this assigned training provider, therefore it cannot be deleted.";
    private static final String PROVIDER_WIA_NAME = "AHTVMSPDQN";
    private static final String PROVIDER_TRADE_NAME = "AFJLQHNDCA";

    public void main() {
        info("Precondition: Create a Training Provider and use it in different types of training enrollments (Trade + WIA)");

        logStep("Log into the system as an Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced -> Training -> Training Providers");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find a training provider from the preconditions and go to its detail page");
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();
        searchPage.performSearch(PROVIDER_WIA_NAME);
        searchPage.openFirstSearchResult();

        validateRecordsCount(PROVIDER_WIA_NAME, Constants.WIOA);

        logStep("Advanced -> Training -> Training Providers");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        searchPage.performSearch(PROVIDER_TRADE_NAME);
        searchPage.openFirstSearchResult();

        validateRecordsCount(PROVIDER_TRADE_NAME, Constants.TRADE);
    }

    /**
     * Validate training provider records
     * @param providerName - name of the chosen training provider
     * @param providerType - WIA training provider, Trade training provider
     */
    private void validateRecordsCount(String providerName, String providerType) {
        logStep("Click the 'View Trade Training Enrollments' link to view all the training enrollments that are connected with this training provider");
        TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();
        Integer enrollmentCount = AdvancedSqlFunctions.getCountTrainingConnectedProvider(providerName, providerType);
        detailsPage.viewProviderEnrollments(providerType);

        logStep("A list of all the training enrollments that are connected with this training provider is shown to the user.");
        TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();

        String recordsCount = searchPage.getSearchedCount().contains("One item")
                ? "1"
                : CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), Constants.COUNT_REGEX);

        CustomAssertion.softAssertEquals(recordsCount.replace(",", Constants.EMPTY), enrollmentCount.toString(), "Incorrect quantity of courses records on the search page");
        searchPage.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();
        CustomAssertion.softAssertContains(detailsPage.getWarningText(), String.format(EXPECTED_TEXT, (recordsCount + enrollmentCount).toString()), "Incorrect warning text");
    }
}
