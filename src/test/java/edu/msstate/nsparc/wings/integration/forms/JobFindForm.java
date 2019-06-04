package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * To open this form you need to log in as Participant -> CLick on 'Job Search'
 */
public class JobFindForm extends BaseWingsForm {

    private TextBox txbJobTitle = new TextBox("css=input[id='keyword']", "Job Title");
    private TableCell tbcJobTitle = new TableCell(By.xpath("//div[@id='job-matches-results']//div[1]"), "Job Order search result");
    private TextBox txbJobID = new TextBox("css=input[id='searchId']", "Job ID");
    private TextBox txbZipCode = new TextBox(By.id("searchId"), "ZipCode Field");
    private TextBox txbSearchJob = new TextBox(By.id("keyword"), "Search for Job");
    private Button btnPrevious = new Button(By.id("previous"), "Return to Search");
    private ComboBox cmbDistance = new ComboBox(By.id("maxDistance"), "Distance");
    private TextBox txbOSOC = new TextBox(By.id("osocMatch"), "OSOC");
    private Div dvOsocTags = new Div(By.xpath("//div[@class='result-tags']//span[@class='result-tag-title'][1]"), "Osoc Tags");
    private Link lnkLastPageResult = new Link(By.xpath("//li[contains(@aria-label,'Last')]"), "Last Page Of Results");
    private Link lnkLastPageResultItem = new Link(By.xpath("//li[contains(@aria-label,'Last')]/a"), "Last Page Of Results");
    private Link lnkFirstPageResult = new Link(By.xpath("//a[contains(@title,'First Page')]"), "First Page Of Results");
    private Link lnkNextPageResult = new Link(By.xpath("//ul[@class='pagination']//a[contains(@title,'Next Page')]"), "Next Page Of Results");
    private Link lnkOccupationTab = new Link(By.xpath("//div[@class='categories']//li[contains(.,'Occupations')]"), "Occupations tab");
    private Link lnkJobOpenedSort = new Link(By.xpath("//div[@class='top-sort']//a[contains(.,'Job Opened Date')]"), "Job Opened Date");
    private Link lnkPreviousPageResult = new Link(By.xpath("//a[contains(@title,'Previous Page')]"), "Previous Page Of Results");
    private Div divPagination = new Div(By.xpath("//ul[@class='pagination']"), "Pagination");
    private Div dvJobResult = new Div(By.xpath("//div[@class='search-result']"), "search-result");
    private Link lnkActivePageResult = new Link(By.xpath("//ul[@class='pagination']/li[@class='active']/a"), "Active Page");
    private Div divResultTable = new Div(By.id("job-matches-results"), "Result Table");
    private String locatorOfLinks = "//div[@id='job-matches-results']/a[%1$d]/div[@class='search-result']";
    private String tabJobXpath = "//li[contains(.,'%1$s')]";
    private Link lnkJobTitles = new Link(By.xpath(String.format(tabJobXpath, "Job Titles")), "Job Titles tab");
    private Link lnkKeyword = new Link(By.xpath(String.format(tabJobXpath, "Keyword")), "Keyword tab");
    private Div dvSearchResult = new Div(By.xpath("//div[@class='search-result']"), "Search Result");
    private Div dvPageTitle = new Div(By.xpath("//div[@class='search-result']//span[@class='result-title']"), "Search Result");
    private Div dvSearchPageTitle = new Div(By.xpath("//div[@id='heading-title']"), "Search Page Title");
    private String openJobOrderDetailsNew = "//div[@class='search-result']//span[@class='result-title'][contains(.,'%1$s')]";
    private ComboBox cmbWithin = new ComboBox(By.xpath("//select[@id='maxDistance']"), "Within");
    private String matchingTitle = "//div[@class='search-sentence']//span[@class='search-highlight'][contains(.,'%1$s')]";

    public void checkPaginationIsDisplayed() {
        scrollDown();
        CustomAssertion.softTrue("Pagination is not present", divPagination.isPresent());
        CustomAssertion.softTrue("Pagination: Last page button is not present", lnkLastPageResult.isPresent());
        CustomAssertion.softTrue("Pagination: First page button is not present", lnkFirstPageResult.isPresent());
        CustomAssertion.softTrue("Pagination: Next page button is not present", lnkNextPageResult.isPresent());
        CustomAssertion.softTrue("Pagination: Previous page button is not present", lnkPreviousPageResult.isPresent());
    }

    public void clearKeyword() {
        txbJobTitle.clear();
    }

    public void clearJobID() {
        txbJobTitle.clear();
    }

    /**
     * Counting number of the displayed jobs on the current page (last page)
     */
    private Integer countRecordsOnLastPage() {
        return dvJobResult.getAllElements().size();
    }

    public String getJobResultNumber() {
        final int jobsOnPage = 10;
        Integer num;
        scrollDown();
        if (lnkLastPageResult.getAttribute("class").contains("disabled")) {
            num = countRecordsOnLastPage();
        } else {
            lnkLastPageResultItem.clickAndWait();
            num = countRecordsOnLastPage() + (Integer.parseInt(lnkActivePageResult.getText()) - 1) * jobsOnPage;
        }
        return num.toString();
    }

    /**
     * Opening several jobs and returning the number of opened jobs.
     */
    public String viewingSeveralJobs() {
        Integer t = 0;
        Link lnkJobOrderDetails;
        while (t < 8) {
            t++;
            lnkJobOrderDetails = new Link(By.xpath(String.format(locatorOfLinks, t)), "Job Order Details");
            lnkJobOrderDetails.clickAndWait();
            btnPrevious.clickAndWait();
        }
        return t.toString();
    }

    /**
     * Default constructor
     */
    public JobFindForm() {
        super(By.xpath("//div[contains(@class,'search-sentence')]//span[contains(.,'Showing all jobs')]"), "Job Search for");
    }

    /**
     * Constructor for participant S-S.
     *
     * @param participantSS - participant SS
     */
    public JobFindForm(String participantSS) {
        super(By.xpath("//input[@id='keyword'] | //div[contains(.,'Job Matches')]"), "Job Search for " + participantSS);
    }

    /**
     * This method waits for updating search results
     *
     * @param jobTitle Job Order id for search
     */
    public void waitMatchingTitlePresent(String jobTitle) {
        Div matchingJobOrderTitle = new Div(By.xpath(String.format(matchingTitle, jobTitle)), "Matching the title ");
        matchingJobOrderTitle.waitForIsElementPresent();
    }

    /**
     * This method is used for Job Order searching by ID
     *
     * @param jobTitle Job Order id for search
     */
    public void performSearch(String jobTitle) {
        txbSearchJob.type(jobTitle);
        txbSearchJob.submitByEnter();
        checkInternalError();

        info("Validate search result");
        checkField(tbcJobTitle, jobTitle, Constants.FALSE);
    }

    /**
     * This method is used for Job Order searching by Job Order title
     *
     * @param jobOrder Job Order object with data for search
     */
    public void performSearch(JobOrder jobOrder) {
        txbJobTitle.type(jobOrder.getJobTitle());
        txbJobTitle.submitByEnter();
    }

    public void performSearchByID(String jobID) {
        Span spJobMatchingTitle = new Span(By.xpath(String.format(matchingTitle, jobID)));
        txbJobTitle.type(jobID);
        txbJobTitle.submitByEnter();
        spJobMatchingTitle.waitForIsElementPresent();

    }

    public void performSearchByOSOC(String osoc) {
        lnkOccupationTab.click();
        txbJobTitle.type(osoc);
        txbJobTitle.submitByEnter();

        lnkJobOpenedSort.clickAndWait();

        CustomAssertion.softAssertContains(dvOsocTags.getText(), osoc, "Osoc tag is not correct");
    }

    /**
     * Performing job search by using job title
     */
    public void performSearchByTitle(String jobTitle) {
        final String within = "Nationwide";
        cmbWithin.select(within);
        cmbWithin.checkValue(within);
        txbJobTitle.type(jobTitle);
    }

    /**
     * Input job ID on job find form
     *
     * @param jobId - job ID
     */
    public void inputJobId(String jobId) {
        txbJobID.type(jobId);
    }

    /**
     * Click on the first record found in the job find form
     */
    public void openJobOrderDetails() {
        tbcJobTitle.clickAndWait();
    }

    /**
     * Click the record found on the new job search form.
     */
    public void openJobOrderDetailsNew(String jobTitle) {
        final String selectedItem = "Nationwide";
        cmbWithin.select(selectedItem);
        Link lnk = new Link(By.xpath(String.format(openJobOrderDetailsNew, jobTitle)), "Job order created");
        lnk.clickAndWait();
    }

    public void openJobOrderFromMultipleResult(String jobID) {
        Integer num = 1;
        Link lnkJobOrderDetails;
        lnkJobOrderDetails = new Link(By.xpath(String.format(locatorOfLinks, num)), "Job result");
        while (num <= 10) {
            if (lnkJobOrderDetails.getText().contains(jobID)) {
                lnkJobOrderDetails.clickAndWait();
                return;
            }
            lnkJobOrderDetails = new Link(By.xpath(String.format(locatorOfLinks, num)), "Job result");
            num++;
        }
        scrollDown();
        if (!lnkNextPageResult.isPresent()) {
            Assert.assertFalse(false);
        }
        lnkNextPageResult.clickAndWait();
        openJobOrderFromMultipleResult(jobID);
    }

    /**
     * Get job title on the search form.
     *
     * @return job title on the form.
     */
    public String getJobTitleTextForm() {
        return tbcJobTitle.getText().trim();
    }

    /**
     * Checking that all criteria fields are empty
     */
    public Boolean checkCriteriaIsEmpty() {
        String baseDistance = "Radius";
        return txbJobTitle.getValue().equals("") && txbJobID.getValue().equals("") &&
                cmbDistance.checkValue(baseDistance) && txbZipCode.getValue().equals("") && txbOSOC.isPresent();
    }

    /**
     * @return the page title
     */
    public String getSearchPageTitle() {
        return dvPageTitle.getText();
    }

    /**
     * @return the page title
     */
    public String getPageTitle() {
        return dvSearchPageTitle.getText();
    }

    public boolean checkResultsAreEmpty() {
        String str = "No jobs found.";
        return divResultTable.getText().equals(str);
    }

    public void validateNewJobOrderFormTitle(JobOrder order, Participant participant) {
        //TODO job occupations redirects to keyword tab. Is it ok?
        //Job Titles
        lnkJobTitles.click();
        validateSearchNewJobTitles(order, participant);
        //Job Tools
        lnkKeyword.click();
        validateSearchNewJobTitles(order, participant);
    }

    /**
     * Validate search result in the job titles tab (keyword)
     *
     * @param order       - job order to check.
     * @param participant - created participant
     */
    private void validateSearchNewJobTitles(JobOrder order, Participant participant) {
        CustomAssertion.softAssertContains(dvSearchResult.getText(),
                order.getEmployer().getCompanyName() + " in " + order.getEmployer().getAddress()
                        .getCity() + ", " + order.getEmployer().getAddress().getState().toUpperCase(), "Incorrect search job order result");
        CustomAssertion.softAssertContains(dvSearchResult.getText(), "Job Opened on "
                + CommonFunctions.getCurrentDate(), "Incorrect date posted format");
    }
}
