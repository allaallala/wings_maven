package edu.msstate.nsparc.wings.integration.forms.employerEnrollment;

import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * Represent employer service enrollment participantSSDetails form
 * Created by a.vnuchko on 14.07.2016.
 */
public class EmployerEnrollmentDetailsForm extends EmployerEnrollmentBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcEmployer = new TableCell(By.xpath(String.format(detailPath, "Employer:")), "Employer");
    private TableCell tbcService = new TableCell(By.xpath(String.format(detailPath, "Service:")), "Service");
    private TableCell tbcServiceCenter = new TableCell(By.xpath(String.format(detailPath, "Service Center:")), "Service Center");
    private TableCell tbcServiceBegan = new TableCell(By.xpath(String.format(detailPath, "Service began on:")), "Service began on");
    private TableCell tbcServiceEnded = new TableCell(By.xpath(String.format(detailPath, "Service ended on:")), "Service ended on");
    private TableCell tbcResultService = new TableCell(By.xpath(String.format(detailPath, "Result service as:")), "Result service as");

    /**
     * Constructor of the form with defined locator of the form
     */
    public EmployerEnrollmentDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Detail')]"), "Employer service enrollment participantSSDetails form");
    }

    /**
     * Validate employer service enrollment data
     * @param employer - employer company name
     * @param service - service name
     * @param beginDate - begin service date
     * @param endDate - end service date
     * @param result - result
     */
    public void validateEnrollmentData(Employer employer, String service, String beginDate, String endDate, String result) {
        CustomAssertion.softAssertEquals(tbcEmployer.getText(), employer.getCompanyName(), "Incorrect employer name");
        CustomAssertion.softAssertEquals(tbcService.getText(), service, "Incorrect service name");
        CustomAssertion.softAssertContains(tbcServiceCenter.getText(), "Golden Triangle Region (310)", "Incorrect service center name");
        CustomAssertion.softAssertEquals(tbcServiceBegan.getText(), beginDate, "Incorrect service begin date");
        CustomAssertion.softAssertEquals(tbcServiceEnded.getText(), endDate, "Incorrect service ended date");
        CustomAssertion.softAssertEquals(tbcResultService.getText(), result, "Incorrect result service");
    }

}
