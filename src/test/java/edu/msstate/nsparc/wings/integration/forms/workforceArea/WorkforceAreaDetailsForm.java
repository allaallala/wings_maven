package edu.msstate.nsparc.wings.integration.forms.workforceArea;

import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Workforce Area -> Search for record -> Open record
 */
public class WorkforceAreaDetailsForm extends WorkforceAreaBaseForm {

    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcAreaName = new TableCell(By.xpath(String.format(detailPath, "Area Name")), "Area Name");
    private TableCell tbcAreaCode = new TableCell(By.xpath(String.format(detailPath, "Area Code")), "Area Code");
    private TableCell tbcAreaDirector = new TableCell(By.xpath(String.format(detailPath, "Area Director")), "Area Director");
    private TableCell tbcAreaOfficeAddress = new TableCell(By.xpath(String.format(detailPath, "Area Office Address")), "Area Office Address");
    private TableCell tbcPhoneNumber = new TableCell(By.xpath(String.format(detailPath, "Phone Number")), "Phone Number");
    private TableCell tbcEmail = new TableCell(By.xpath(String.format(detailPath, "Email Address")), "Email");
    private Button btnCreateAnother = new Button(By.id("createAnother"), "Create Another LWIA");

    /**
     * Default constructor
     */
    public WorkforceAreaDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'LWIA Detail')]"), "Workforce Area Detail Form");
    }

    /**
     * Validate workforce are data on the detail form
     *
     * @param lwia - LWIA object
     */
    public void validateWorkforceArea(LWIA lwia) {
        CustomAssertion.softAssertContains(tbcAreaName.getText(), lwia.getDetails()[0], "Incorrect workforce area name");
        CustomAssertion.softAssertContains(tbcAreaCode.getText(), lwia.getDetails()[1], "Incorrect workforce area code");
        CustomAssertion.softAssertContains(tbcAreaDirector.getText(), lwia.getDetails()[2], "Incorrect workforce area director");
        CustomAssertion.softAssertContains(tbcAreaOfficeAddress.getText(), lwia.getAddress()[0] + "\n" + lwia.getAddress()[1] + "\n" + lwia.getAddress()[2] + ", "
                + lwia.getAddress()[4] + " " + lwia.getAddress()[3] + "\n" + lwia.getAddress()[5], "Incorrect area office address");
        CustomAssertion.softAssertContains(tbcPhoneNumber.getText(), lwia.getDetails()[5] + "   Ext.: " + lwia.getDetails()[3], "Incorrect phone number");
        CustomAssertion.softAssertContains(tbcEmail.getText(), lwia.getDetails()[4], "Incorrect email");
    }

    /**
     * Click [Create Another]
     */
    public void clickAnother() {
        btnCreateAnother.clickAndWait();
    }
}

