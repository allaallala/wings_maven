package edu.msstate.nsparc.wings.integration.forms.employerEnrollment;

import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.BaseElement;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
This form is opened via Employers -> Employer Service Enrollment -> Search
 */
public class EmployerEnrollmentSearchForm extends EmployerEnrollmentBaseForm {

    private Link lnkEmployerLabel = new Link(By.xpath("//table[@id='results-table']//td/a"), "Employer");
    private TableCell tbcServiceLabel = new TableCell(By.xpath("//table[@id='results-table']//td[3]"), "Service");
    private TableCell tbcCreationDateLabel = new TableCell(By.xpath("//table[@id='results-table']//td[4]"),"Creation Date");
    private TableCell tbcScheduledDate = new TableCell(By.xpath("//table[@id='results-table']//td[5]"),"Schedule Date");
    private ComboBox cmbStatus = new ComboBox(By.id("result"), "Result");

    /**
     * Default constructor
     */
    public EmployerEnrollmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Search')]"), "Enrollment Search");
    }
    /**
     * This method is used to search for enrollment with provided employer and service title
     * @param employer Employer for searching
     * @param serviceName Service title for searching
     */
    public void performSearch(Employer employer, String serviceName) {
        selectEmployer(employer);
        selectService(serviceName);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Get searched count with regex
     * @return formatted count
     */
    public String getSearchedCountRegex() {
        return CommonFunctions.regexGetMatch(getSearchedCount(), "\\d{1,}(.|,)?\\d{1,}");
    }

    /**
     * This method is used for selecting Service
     * @param serviceTitle Service that will be selected
     */
    public void selectService(String serviceTitle) {
        info("Selecting Service");
        clickAndWait(BaseButton.SERVICE_LOOK_UP);
        ServiceSearchForm searchForm = new ServiceSearchForm();
        searchForm.selectAndReturnEmployerService(serviceTitle);
    }

    /**
     * Get employer label text
     * @param employer - employer
     * @param serviceTitle - service title
     * @param creationDate - date of the creation.
     */
    public void checkLabelData(Employer employer, String serviceTitle, String creationDate) {
        CustomAssertion.softAssertContains(lnkEmployerLabel.getText().trim(), employer.getCompanyName(), "Incorrect employer name");
        CustomAssertion.softAssertContains(tbcServiceLabel.getText().trim(), serviceTitle,"Incorrect service title");
        CustomAssertion.softAssertContains(tbcCreationDateLabel.getText().trim(), creationDate,"Incorrect creation date");
    }

    /**
     * Get scheduled date text
     * @param scheduledDate - scheduled date
     */
    public void checkScheduledDate(String scheduledDate) {
        Assert.assertTrue(tbcScheduledDate.getText().trim().contains(scheduledDate));
    }

    /**
     * Select result
     * @param result - result
     */
    public void selectResult(String result) {
        cmbStatus.select(result);
    }
}
