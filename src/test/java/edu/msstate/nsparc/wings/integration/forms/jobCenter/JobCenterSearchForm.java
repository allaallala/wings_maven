package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * To open this form you need to be on Home page for any user and click on 'Find a WIN Job Center' link in the menu
 */
public class JobCenterSearchForm extends CenterBaseForm {

    private TableCell tbcJobCenter = new TableCell(By.xpath("//form[@id='address']//small[text()='(Based on your address)']/../..//address"),
            "Job Center value");
    private TableCell tbcJobCenterResultTable = new TableCell("css=table[id='job-matches-table'] > tbody", "Job Center search result");
    private TableCell tbcJobCenterName = new TableCell(By.xpath("//form[@id='address']//address//strong"), "Center Name");
    private Div divNearestAddress = new Div(By.xpath("//form[@id='address']/div/div/address"), "Nearest Address");
    private Div divFirstAddress = new Div(By.xpath("//table[@id='job-matches-table']/tbody/tr[1]/td[2]/address"), "Address of the first record");
    private Div divPageTitle = new Div(By.id("heading-title"), "Title of the page");
    private TextBox txbCity = new TextBox(By.id("city"), "City");
    private TextBox txbZipCode = new TextBox(By.id("zipcode"), "ZipCode");
    private Div divMile = new Div(By.xpath("//table[@id='job-matches-table']/thead/tr/th[1]"), "Miles Header");
    private Div divAddress = new Div(By.xpath("//table[@id='job-matches-table']/thead/tr/th[2]"), "Address Header");
    private Div divContact = new Div(By.xpath("//table[@id='job-matches-table']/thead/tr/th[3]"), "Contact Information Header");
    private Div divHours = new Div(By.xpath("//table[@id='job-matches-table']/thead/tr/th[4]"), "Hours Open Header");
    private String locatorRecords = "//tbody/tr[%1$d]";

    public Integer getNumberOfDisplayedRecords() {
        Integer num = 1;
        Link lnkJobOrderDetails = new Link(By.xpath(String.format(locatorRecords, num)), "Job Center Record");
        while (lnkJobOrderDetails.isPresent()) {
            num++;
            lnkJobOrderDetails = new Link(By.xpath(String.format(locatorRecords, num)), "Job Center Record");
        }
        num--;
        return num;
    }

    public Boolean checkHeadersArePresent() {
        return divMile.isPresent() && divAddress.isPresent() && divContact.isPresent() && divHours.isPresent();
    }

    public void typeCity(String str) {
        txbCity.type(str);
    }

    public void clearCity() {
        txbCity.clear();
    }

    public void typeZipCode(String str) {
        txbZipCode.type(str);
    }

    public String getPageTitle() {
        return divPageTitle.getText();
    }

    public String getNearestAddress() {
        return divNearestAddress.getText();
    }

    public String getFirstAddress() {
        return divFirstAddress.getText();
    }

    private boolean jobCenterFound;
    private String locatorJobRows = "//table[@id='job-matches-table']//tbody/tr";
    private String jobRowsData = locatorJobRows + "[%1$d]/td[2]";
    private String errorText = "Nothing found to display.";

    public JobCenterSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,' ')]"), "Job Search Form");
    }

    public JobCenterSearchForm(String employer) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Your Nearest WIN Job Center')]"), "Nearest Win Job Center for " + employer);
    }

    private void validateSearchResultsOnOnePage(String name) {
        TableCell tableCellJobCenter;
        assertFalse("Job Center wasn't found", tbcJobCenterResultTable.getText().contains(errorText));
        // search results on page
        int pageResultsCount =
                getNumberOfElementsPresent(By.xpath(locatorJobRows));
        for (int j = 1; j <= pageResultsCount; j++) {
            // Forming table cell locator
            tableCellJobCenter = new TableCell(By.xpath(String.format(jobRowsData, j)), "Job Center");
            // if job center was found stop checking
            if (tableCellJobCenter.getText().contains(name)) {
                jobCenterFound = true;
                return;
            }
        }
    }

    @Override
    public void validateSearchResults(String name) {
        jobCenterFound = false;
        // Check if there is more than 1 page
        if (TablePaginationForm.isPresent()) {
            // getting total pages number
            TablePaginationForm paginationForm = new TablePaginationForm();
            int pageNumber = paginationForm.getPagesCount();
            for (int i = 1; i <= pageNumber; i++) {
                // Validating results on each page
                validateSearchResultsOnOnePage(name);
                // if job center was found stop checking
                if (jobCenterFound) {
                    break;
                }
                paginationForm.openNextPage();
            }
        } else {
            validateSearchResultsOnOnePage(name);
        }
        assertTrue("Job Center was not found", jobCenterFound);
    }

    private void getJobCenterNamesOnOnePage(List<String> jobCenters) {
        TableCell tableCellJobCenter;
        assertFalse("Job Center isn't found", tbcJobCenterResultTable.getText().contains(errorText));
        // search results on page
        int pageResultsCount = getNumberOfElementsPresent(By.xpath(locatorJobRows));
        for (int j = 1; j <= pageResultsCount; j++) {
            // Forming table cell locator
            tableCellJobCenter = new TableCell(By.xpath(String.format(jobRowsData, j)), "Job Center");
            jobCenters.add(tableCellJobCenter.getText().split("\\r?\\n")[0]);
        }
    }

    public List<String> getJobCentersNames() {
        List<String> jobCenters = new ArrayList<>();

        if (TablePaginationForm.isPresent()) {
            // total pages count
            TablePaginationForm paginationForm = new TablePaginationForm();
            int pageNumber = paginationForm.getPagesCount();
            for (int i = 1; i <= pageNumber; i++) {
                // get names from page
                getJobCenterNamesOnOnePage(jobCenters);
                paginationForm.openNextPage();
            }
        } else {
            getJobCenterNamesOnOnePage(jobCenters);
        }
        return jobCenters;
    }

    public String getJobCenterName() {
        return tbcJobCenterName.getText();
    }

    public void checkJobCenterAddress() {
        tbcJobCenter.assertIsPresentAndVisible();
    }
}
