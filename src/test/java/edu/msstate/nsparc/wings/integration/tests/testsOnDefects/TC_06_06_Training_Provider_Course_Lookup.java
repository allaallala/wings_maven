package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderLocationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10654")
public class TC_06_06_Training_Provider_Course_Lookup extends BaseWingsTest {


    public void main() {
        TrainingProvider trainingProvider = new TrainingProvider();
        AdvancedSqlFunctions.resetTrainingProviderCode(trainingProvider.getCode());

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Popup.Create);

        logStep("Fill in required fields");
        TrainingProviderCreationForm creationForm = new TrainingProviderCreationForm();
        creationForm.fillAllFields(trainingProvider);

        logStep("Edit the location you just added");
        new SearchResultsForm().openSelectedResult();
        creationForm.clickButton(Buttons.Edit);

        logStep("Use the full course lookup to add another course");
        TrainingProviderLocationForm trainingProviderLocationForm = new TrainingProviderLocationForm();
        trainingProviderLocationForm.selectRandomWiaCourse();
        checkSavePresent();

        logStep("Open the full course lookup again");
        trainingProviderLocationForm.clickCourseLookup();
        TrainingCourseSearchForm courseSearch = new TrainingCourseSearchForm();
        courseSearch.clickButton(Buttons.Search);

        logStep("Click on 'Cancel'");
        courseSearch.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();
        checkSavePresent();

        logStep("Click on Cancel button");
        courseSearch.clickButton(Buttons.Cancel);
        trainingProviderLocationForm.areYouSure(Popup.Yes);

        logStep("Finish Training Provider creation");
        courseSearch.clickButton(Buttons.Create);

        logStep("Search for created Training Provider");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

        BaseWingsSteps.popClick(Popup.Search);
        TrainingProviderSearchForm searchForm = new TrainingProviderSearchForm();
        searchForm.performSearch(trainingProvider);

        logStep("Open Training Provider for Edit");
        searchForm.openFirstSearchResult();
        TrainingProviderDetailsForm detailsForm = new TrainingProviderDetailsForm();

        logStep("Choose one of the training provider locations to edit");
        new SearchResultsForm().openSelectedResult();

        logStep("Open Edit form");
        detailsForm.clickEditLocation();

        logStep("Use the full course lookup to add another course");
        trainingProviderLocationForm = new TrainingProviderLocationForm(Constants.EDIT);
        trainingProviderLocationForm.selectRandomWiaCourse();
        checkSavePresent();

        logStep("Open the full course lookup again");
        trainingProviderLocationForm.clickCourseLookup();
        trainingProviderLocationForm.clickButton(Buttons.Search);

        logStep("Click on 'Cancel'");
        trainingProviderLocationForm.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();
        checkSavePresent();

        logEnd();
    }

    /**
     * Check, that 'Save Changes' button is displayed.
     */
    private void checkSavePresent() {
        divideMessage("Check that 'Save Changes' button is displayed");
        assertTrue("'Save Changes' button isn't displayed", new TrainingProviderLocationForm().isPresent(BaseWingsForm.BaseButton.SAVE_CHANGES));
    }
}
