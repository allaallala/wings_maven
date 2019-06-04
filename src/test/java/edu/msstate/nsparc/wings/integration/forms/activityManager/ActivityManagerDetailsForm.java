package edu.msstate.nsparc.wings.integration.forms.activityManager;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Activity Manager Accounts -> Search -> Open Details
 */
public class ActivityManagerDetailsForm extends ActivityManagerBaseForm {

    private String xpath = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcUserType = new TableCell(By.xpath(String.format(xpath, "User Type:")), "User Type");
    private TableCell tbcAccessMSUsername = new TableCell(By.xpath(String.format(xpath, "System Username:")), "Access MS username");
    private TableCell tbcFirstName = new TableCell(By.xpath(String.format(xpath, "First Name:")),"First name table cell");
    private TableCell tbcLastName = new TableCell(By.xpath(String.format(xpath, "Last Name:")), "Last name table cell");
    private TableCell tbcJobTitle = new TableCell(By.xpath(String.format(xpath, "Job Title:")), "Job title table cell");
    private TableCell tbcEmail = new TableCell(By.xpath(String.format(xpath, "Email Address:")), "e-mail tablecell");
    private Button btnCreateAnother = new Button(By.id("createAnother"), "Create another");
    private Button btnResetUsername = new Button(By.id("clearUsername"), "Reset username");
    private String notProvided = "Not Provided";

    /**
     * Default constructor
     */
    public ActivityManagerDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Staff Detail')]"), "Staff Detail");
    }

    /**
     * This method is used for compare data on form with provided data
     * @param staff Data for comparison
     */
    public void validateActivityManagerDetails(Staff staff) {
        checkField(tbcAccessMSUsername, staff.getStaffAccount(), Constants.FALSE);
        validateData(staff);
    }

    /**
     * Validate data
     * @param staff - staff.
     */
    public void validateData(Staff staff) {
        CustomAssertion.softAssertEquals(tbcFirstName.getText(), staff.getFirstName(), "Assert First Name Failed");
        CustomAssertion.softAssertEquals(tbcLastName.getText(), staff.getLastName(), "Assert Last Name Failed");
        CustomAssertion.softAssertEquals(tbcJobTitle.getText(), staff.getJobTitle(), "Assert Job Title Failed");
        CustomAssertion.softAssertEquals(tbcEmail.getText(), staff.getEmail(), "Assert Email Failed");
        CustomAssertion.softAssertEquals(tbcUserType.getText(), staff.getUserType(), "Assert User Type failed");
    }

    /**
     * validate username
     * @param staffAccount - expected username
     */
    public void validateUsername(String staffAccount) {
        CustomAssertion.softAssertContains(tbcAccessMSUsername.getText(), staffAccount, "Incorrect username");
    }

    /**
     * Get title of the user type
     * @return user type
     */
    public String getUserType() {
        return tbcUserType.getText();
    }

    /**
     * Click [Create Another]
     */
    public void clickCreateAnother() {
        btnCreateAnother.clickAndWait();
    }

    /**
     * Check, that the [Unemployment Services System Username] field is present or not on the page.
     * @param staff - staff
     * @param present - present or not.
     */
    public void checkAccessUsername(Staff staff, Boolean present) {
        if (present) {
            validateUsername(staff.getStaffAccount());
        } else {
            tbcAccessMSUsername.assertIsNotPresent();
        }
    }

    /**
     * Check, that it's possilbe to reset username or validate, that [Reset Username] is not present on the page.
     * @param present -true, if present
     */
    public void checkResetUsername(Boolean present) {
        if (present) {
            btnResetUsername.assertIsPresentAndVisible();
            btnResetUsername.clickAndWait();
            info(tbcAccessMSUsername.getText());
            CustomAssertion.softTrue("Access MS Username has not been reset", tbcAccessMSUsername.getText().contains(notProvided));
        } else {
            btnResetUsername.assertIsNotPresent();
        }
    }
}
