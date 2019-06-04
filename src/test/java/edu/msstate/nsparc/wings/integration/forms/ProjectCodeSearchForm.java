package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form can be opened during WIA Training creation to search for Project Code
 */
public class ProjectCodeSearchForm extends BaseWingsForm {

    private RadioButton rbFirstProjectCode = new RadioButton("css=table#results-table input", "First Searched Project Code");
    private RadioButton rbActiveProjectCode = new RadioButton("css=input#isActive1", "Active Project Codes");
    private RadioButton rbSearchByTrainingProvider = new RadioButton("css=input#searchByCode2", "Search by Training Provider");
    private Button btnTrainingProviderLookup = new Button("css=span[id='providerLookup'] button", "Training Provider Lookup");
    private Button btnCourseLookup = new Button(By.xpath("//span[@id='courseLookup']//button[1]"), "Course Lookup");
    private TableCell tbcCourse = new TableCell(By.xpath("//table[@id='results-table']//tbody//tr[1]//td[5]"), "Course");

    /**
     * Default constructor
     */
    public ProjectCodeSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Project Code Search')]"), "Project Code Search");
    }

    /**
     * This method is used for Training Provider selection from look-up with requested Provider name
     *
     * @param providerName Requested Provider name
     */
    public void selectProvider(String providerName) {
        rbSearchByTrainingProvider.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        btnTrainingProviderLookup.clickAndWait();
        TrainingProviderSearchForm providerSearchForm = new TrainingProviderSearchForm();
        providerSearchForm.searchAndReturnTrainingProvider(providerName);
    }

    public void selectCourse(String course) {
        btnCourseLookup.clickAndWait();
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.performSearchAndReturn(course);
    }

    /**
     * This method is used for Project Code selection from look-up with requested Provider name
     *
     * @param providerName Requested Provider name
     * @return Course number
     */
    public String performSearchAndSelect(String providerName) {
        selectProvider(providerName);
        clickButton(Buttons.Search);
        String course = tbcCourse.getText().trim();
        rbFirstProjectCode.click();
        clickAndWait(BaseButton.RETURN);
        return course;
    }

    /**
     * This method is used for random Project Code selection from look-up
     */
    public void performSearchAndSelectFirst() {
        rbActiveProjectCode.click();
        clickButton(Buttons.Search);
        rbFirstProjectCode.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * Validate data on the project code search form
     *
     * @param trp - training provider
     */
    public void validateData(TrainingProvider trp) {
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softTrue("Incorrect project code", resultsForm.getFirstSearchResultLinkText().trim().contains(trp.getCode()));
        CustomAssertion.softTrue("Incorrect training provider name", getTrimmedRecordText(2).contains(trp.getName()));
        CustomAssertion.softTrue("Incorrect location", getTrimmedRecordText(3).contains(trp.getLocations().get(0).getName()));
        CustomAssertion.softTrue("Incorrect course name", getTrimmedRecordText(4).contains(trp.getCourseName()));
        CustomAssertion.softTrue("Incorrect status", getTrimmedRecordText(5).contains(trp.getStatus()));
    }

    private String getTrimmedRecordText(int columnNumber) {
        return new SearchResultsForm().getFirstRowRecordText(columnNumber).trim();
    }
}
