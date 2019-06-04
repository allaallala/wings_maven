package edu.msstate.nsparc.wings.integration.forms.jobOrder;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Wagner-Peyser -> Job Orders -> Search
 */
public class JobOrderSearchForm extends JobOrderBaseForm {

    private RadioButton rbJobItem = new RadioButton("css=input.radio", "Job Item");
    private Button btnJobOrderReturn = new Button("id=return", "Return");
    private String searchXpath = "//table[@id='job-matches-table']//tbody//tr[%1$d]//td[%2$d] | //table[@id='ssJobSearchResults']//tbody//tr[%1$d]//td[%2$d]";
    private Link lnkJobNumber = new Link(By.xpath("//table[@id='ssJobSearchResults']//td[1]/a | //table[@id='results-table']//td[1]/a"), "Job Number");
    private Link lnkJobTitle = new Link(By.xpath("//table[@id='job-matches-table']//td[2]//a | //table[@id='ssJobSearchResults']//td[2]//a | //table[@id='results-table']//td//span"), "Job Title");
    private TableCell tbcCreationDate = new TableCell(By.xpath(String.format(searchXpath, 1, 4)), "Creation Date");
    private TableCell tbcStatus = new TableCell(By.xpath(String.format(searchXpath, 1, 5)), "Job Order status");
    private Link lnkSecondJobTitle = new Link(By.xpath("//table[@id='job-matches-table']//tr[2]//td[2]//a | //table[@id='ssJobSearchResults']//tr[2]//td[2]//a"), "Second Job Title");
    private TableCell tbcSecondCreationDate = new TableCell(By.xpath(String.format(searchXpath, 2, 4)), "Second Creation Date");
    private TableCell tbcSecondStatus = new TableCell(By.xpath(String.format(searchXpath, 2, 5)), "Second Status");
    private TextBox txbJobTitle = new TextBox(By.xpath("//input[@id='title'] | //input[@id='jobTitle']"), "Job Title");
    private TextBox txbCreationDateFrom = new TextBox(By.xpath("//input[@id='createStartRange'] | //input[@id='dateOrderOpen']"), "Creation Date From");
    private TextBox txbCreationDateTo = new TextBox(By.xpath("//input[@id='createEndRange'] | //input[@id='dateOrderClose']"), "Creation Date To");
    private ComboBox cmbStatus = new ComboBox(By.xpath("//select[@id='status'] | //select[@id='jobOrderStatus']"), "Status");
    private Link jobOrderResultTable = new Link(By.xpath("//span[@class='pagebanner']"), "Job order result table");
    private TextBox txbCity = new TextBox("css=input[id='city']", "City");
    private TextBox txbZipCode = new TextBox("css=input[id='zipcode']", "Zip Code");
    private final String defaultSearchFilter = "Any";

    /**
     * Default constructor
     */
    public JobOrderSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Search')]"), "Job Order Search");
    }

    public JobOrderSearchForm(String jobOrderSS) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'My Openings')]"), "Form for " + jobOrderSS + " search is opened");
    }

    public String getJobTitle() {
        String jobTitle = CommonFunctions.regexGetMatch(lnkJobTitle.getText(), ".+\\(");
        return jobTitle.substring(0, jobTitle.length() - 2);
    }

    public void clickJobTitle() {
        lnkJobTitle.clickAndWait();
    }

    public String getJobTitleText() {
        return lnkJobTitle.getText();
    }

    public Boolean jobTitlePresent() {
        return lnkJobTitle.isPresent();
    }

    public String getJobOrderNumber() {
        if (lnkJobNumber.isPresent()) {
            return lnkJobNumber.getText();
        }

        String jobOrder = lnkJobTitle.getText();
        return CommonFunctions.regexGetMatch(jobOrder, "\\d+");
    }

    public void clickJobItem() {
        rbJobItem.click();
    }

    public EmployerSearchForm clickEmployerLookUpButton() {
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);
        return new EmployerSearchForm();
    }

    public void inputCityZip(String city, String zipCode) {
        txbCity.type(city);
        txbZipCode.type(zipCode);
    }

    public void inputJobTitle(String jobTitle) {
        txbJobTitle.type(jobTitle);
    }

    public void inputTitleDate(String jobTitle, String creationFrom, String creationTo) {
        inputJobTitle(jobTitle);
        txbCreationDateFrom.type(creationFrom);
        txbCreationDateTo.type(creationTo);
    }

    private void validateSearchResultsOnOnePage(String companyName, String jobTitle) {
        if (getJobResultTableText().contains("Nothing found to display.")) {
            info("Nothing found to display, try to change Company Name to find more records");
        } else {
            SearchResultsForm resultsForm = new SearchResultsForm();
            // number of search results on page
            int pageResultsCount = resultsForm.getResultRowsCount();
            for (int j = 1; j <= pageResultsCount; j++) {
                // checking Company name
                String companyNameColumn = "3";
                CustomAssertion.softTrue("Company name assert failed", resultsForm.getRecordText(j, companyNameColumn).contains(companyName));
                // checking Job Order title
                String jobTitleColumn = "2";
                CustomAssertion.softTrue("Assert Job Title Failed", resultsForm.getRecordText(j, jobTitleColumn).contains(jobTitle));
            }
        }
    }

    public void validateSearchResults(String companyName, String jobTitle) {
        // Check if there is more than 1 page
        if (TablePaginationForm.isPresent()) {
            // getting total pages number
            TablePaginationForm paginationForm = new TablePaginationForm();
            int pageNumber = paginationForm.getPagesCount();
            for (int i = 1; i <= pageNumber; i++) {
                // Validating results on each page
                validateSearchResultsOnOnePage(companyName, jobTitle);
                paginationForm.openNextPage();
            }
        } else {
            validateSearchResultsOnOnePage(companyName, jobTitle);
        }
    }

    public String[] performSearchAndReturn(String companyName, String city, String zipCode, String jobTitle) {
        EmployerSearchForm employerSearchForm = clickEmployerLookUpButton();
        employerSearchForm.searchAndReturnEmployer(companyName, zipCode, city);
        txbJobTitle.type(jobTitle);
        txbCreationDateFrom.type(CommonFunctions.getYesterdayDate());
        txbCreationDateTo.type(CommonFunctions.getCurrentDate());
        selectStatus(defaultSearchFilter);
        search();
        clickJobItem();
        String[] jobDetails = {null, null};
        jobDetails[0] = getJobOrderNumber();
        jobDetails[1] = getJobTitle();
        clickJobOrderReturn();
        return jobDetails;
    }

    public void performSearchAndReturn(JobOrder jobOrder) {
        performSearchAndReturn(jobOrder.getEmployer().getCompanyName(),
                jobOrder.getEmployer().getAddress().getCity(), jobOrder.getEmployer().getAddress().getZipCode(), jobOrder.getJobTitle());
    }

    public void performSearch(JobOrder jobOrder) {
        selectEmployer(jobOrder.getEmployer());
        txbJobTitle.type(jobOrder.getJobTitle());
        txbCreationDateFrom.type(CommonFunctions.getCurrentDate());
        txbCreationDateTo.type(CommonFunctions.getNextWeekDate());
        selectStatus(jobOrder.getOrderSearchState());
        search();
        checkInternalError();
        checkSearchResult(jobOrder.getJobTitle());
    }

    public void performSimpleSearch(String jobTitle) {
        inputJobTitle(jobTitle);
        clickButton(Buttons.Search);
        clickJobTitle();
    }

    public void performStatusSearchValidate(String status, JobOrder order) {
        Div divOpenings = new Div(By.xpath(String.format(searchXpath, 1, 3)), "Openings left");
        Div divCreationDate = new Div(By.xpath(String.format(searchXpath, 1, 4)), "Creation Date");
        Div divStatus = new Div(By.xpath(String.format(searchXpath, 1, 5)), "Job Status");
        cmbStatus.select(status);
        clickButton(Buttons.Search);
        CustomAssertion.softTrue("Incorrect job title", lnkJobTitle.getText().contains(order.getJobTitle()));
        CustomAssertion.softTrue("Incorrect job openings", divOpenings.getText().contains(order.getQuantity()));
        CustomAssertion.softTrue("Incorrect job creation date", divCreationDate.getText().contains(order.getCreationDate()));
        CustomAssertion.softTrue("Incorrect job status", divStatus.getText().contains(status));
    }

    public void checkSearchResult(String jobTitle) {
        info("Validate search result");
        CustomAssertion.softTrue("Job Order search failed", lnkJobTitle.getText().contains(jobTitle));
    }

    public void findJobOrder(String jobTitle) {
        txbJobTitle.type(jobTitle);
        selectStatus(defaultSearchFilter);
        search();
    }

    public void selectStatus(String value) {
        cmbStatus.select(value);
    }

    public void clickJobOrderReturn() {
        btnJobOrderReturn.clickAndWait();
    }


    public String getJobResultTableText() {
        return jobOrderResultTable.getText();
    }

    public String getFirstStatusTable() {
        return tbcStatus.getText().trim();
    }

    public String getCreationDate() {
        return tbcCreationDate.getText().trim();
    }

    public void checkSecondJobData(JobOrder jbOrder) {
        CustomAssertion.softAssertContains(lnkSecondJobTitle.getText(), jbOrder.getJobTitle(), "Incorrect job order title");
        CustomAssertion.softAssertEquals(tbcSecondCreationDate.getText(), jbOrder.getCreationDate(), "Incorrect second creation date");
        CustomAssertion.softAssertEquals(tbcSecondStatus.getText(), jbOrder.getStatus(), "Incorrect joborder status");
    }
}
