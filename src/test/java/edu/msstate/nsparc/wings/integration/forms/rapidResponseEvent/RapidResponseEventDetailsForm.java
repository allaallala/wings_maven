package edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent;

import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Rapid Response Events -> Search for record -> Open record
 */
public class RapidResponseEventDetailsForm extends RapidResponseEventBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String xpathExp = "//form[@id='command']" + detailPath;
    private TableCell tbcEmployer = new TableCell(By.xpath("//form[@id='command']//td[contains(.,'Employer')]/following-sibling::td/span"), "Employer");
    private TableCell tbcWorkforceArea = new TableCell(By.xpath(String.format(xpathExp, "Workforce Area")), "Workforce Area");
    private TableCell tbcNotificationDate = new TableCell(By.xpath(String.format(xpathExp, "Notification Date")), "Notification Date");
    private TableCell tbcImpactDate = new TableCell(By.xpath(String.format(xpathExp, "Impact Date")), "Impact Date");
    private TableCell tbcAffectedEmployees = new TableCell(By.xpath(String.format(xpathExp, "Number of Potentially Affected Employees")), "Number of Potentially Affected Employees");
    private TableCell tbcServedEmployees = new TableCell(By.xpath(String.format(xpathExp, "Number of Employees Served")), "Number of Employees Served");
    private TableCell tbcEventDescription = new TableCell(By.xpath(String.format(xpathExp, "Event Description")), "Event Description");

    /**
     * Default constructor
     */
    public RapidResponseEventDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Rapid Response Event Detail')]"), "Rapid Response Event Detail");
    }

    /**
     * Validating displayed Rapid Response Event information
     *
     * @param event Object with Rapid Response Event information
     */
    public void validateInformation(RapidResponseEvent event) {
        CustomAssertion.softAssertEquals(tbcEmployer.getText(), event.getEmployer().getCompanyName(), "Incorrect employer company name");
        CustomAssertion.softAssertContains(tbcWorkforceArea.getText(), event.getWorkforceArea(), "Incorrect workforce area");
        CustomAssertion.softAssertEquals(tbcNotificationDate.getText(), event.getNotificationDate(), "Incorrect notification date");
        CustomAssertion.softAssertEquals(tbcImpactDate.getText(), event.getImpactDate(), "Incorrect impact date");
        CustomAssertion.softAssertEquals(tbcAffectedEmployees.getText(), event.getNumberOfPotentiallyAffectedEmployees(), "Incorrect affected employees");
        CustomAssertion.softAssertEquals(tbcServedEmployees.getText(), event.getNumberOfEmployeesServed(), "Incorrect served employees");
        CustomAssertion.softAssertEquals(tbcEventDescription.getText(), event.getDescription(), "Incorrect event description");
    }
}
