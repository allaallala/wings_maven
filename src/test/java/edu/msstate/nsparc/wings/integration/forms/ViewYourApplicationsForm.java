package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.customassertions.CustomAssertion;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * To open this form you need to log in as Participant and click on 'View Applications' link
 */
public class ViewYourApplicationsForm extends BaseWingsForm {

    private TextBox txbJobID = new TextBox(By.id("jobOrderNumber"), "Job ID");
    private TableCell tbcJobTitle = new TableCell(By.xpath("//table[@id='job-matches-table']//td"), "Job Title");
    private Link lnkJobDetails = new Link(By.xpath("//tbody/tr[1]/td[1]/a"), "Job Details");
    private TextBox txbJobTitle = new TextBox(By.id("jobOrder.jobTitle"), "Job Title");
    private TextBox txbEmployer = new TextBox(By.id("jobOrder.employer.employerName"), "Employer");
    private Link lnkLastPageResult = new Link(By.xpath("//ul[contains(@class,'pagination')]//span[contains(.,'»')]"), "Last");
    private Link lnkFirstPageResult = new Link(By.xpath("//ul[contains(@class,'pagination')]//li[contains(.,'1')]"), "First");
    private Link lnkPrevPageResult = new Link(By.xpath("//ul[contains(@class,'pagination')]//span[contains(.,'«')]"), "Previous");
    private Div divPagination = new Div(By.xpath("//ul[contains(@class,'pagination')]"), "Pagination Pane");
    private Link lnkJobTitleSort = new Link(By.xpath("//a[.='Job Title']"), "Sorting by Job Title");
    private Link lnkEmployerSort = new Link(By.xpath("//a[.='Employer']"), "Sorting by Employer");
    private Link lnkDateAppliedSort = new Link(By.xpath("//a[.='Date Applied']"), "Sorting by Date Applied");
    private String locatorOfLinks = "//tbody/tr[%1$d]//td[1]";
    private String locatorOfEmployer = "//tbody/tr[%1$d]//td[2]";
    private String locatorOfStatus = "//tbody/tr[%1$d]//td[4]";

    /**
     * Default constructor
     */
    public ViewYourApplicationsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'View Applications')]"), "View Your Applications");
    }

    /**
     * Input job order Id
     *
     * @param jobOrderId - job order id
     */
    public void inputJobOrderId(String jobOrderId) {
        txbJobID.type(jobOrderId);
    }

    public void inputJobOrderTitle(String jobOrderTitle) {
        txbJobTitle.type(jobOrderTitle);
    }

    public void inputEmployer(String employer) {
        txbEmployer.type(employer);
    }

    /**
     * Get job title
     *
     * @return job title
     */
    public String getJobTitle() {
        return tbcJobTitle.getText().trim();
    }

    public String getJobTitleAndID() {
        return lnkJobDetails.getText();
    }

    /**
     * Open job order participantSSDetails form.
     */
    public void openJobOrderDetailsForm() {
        lnkJobDetails.clickAndWait();
    }

    public void clearJobID() {
        txbJobID.clear();
    }

    public void clearJobTitle() {
        txbJobTitle.clear();
    }

    public void checkPaginationIsPresent() {
        scrollDown();
        CustomAssertion.softTrue("Pagination is not present", divPagination.isPresent());
        CustomAssertion.softTrue("Pagination: Previous page button is not present", lnkPrevPageResult.isPresent());
        CustomAssertion.softTrue("Pagination: First page button is not present", lnkFirstPageResult.isPresent());
        CustomAssertion.softTrue("Pagination: Last page button is not present", lnkLastPageResult.isPresent());
    }

    public void sortByJobTitle() {
        lnkJobTitleSort.clickAndWait();
    }

    public String getRecord(int i) {
        Link lnkJobOrderDetails = new Link(By.xpath(String.format(locatorOfLinks, i + 1)), "Application");
        return lnkJobOrderDetails.getText();
    }

    public String getStatusOfApplication(int i) {
        Link lnkJobOrderDetails = new Link(By.xpath(String.format(locatorOfStatus, i + 1)), "Status of Application");
        return lnkJobOrderDetails.getText();

    }

    public String getEmployerOfRecord(int i) {
        Div divEmployerInfo = new Div(By.xpath(String.format(locatorOfEmployer, i + 1)), "Employer of Application");
        return divEmployerInfo.getText();
    }

    public void sortByEmployer() {
        lnkEmployerSort.clickAndWait();
    }

    public void sortByDateApplied() {
        lnkDateAppliedSort.clickAndWait();
    }
}
