package edu.msstate.nsparc.wings.integration.forms.activityManager;

import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import framework.elements.Link;
import framework.elements.Span;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Activity Manager Accounts -> Create
 */
public class ActivityManagerCreateForm extends ActivityManagerBaseForm {

    private TextBox txbJobTitle = new TextBox("css=input#title","Job Title");
    private TextBox txbPhoneNumber = new TextBox("css=input[id='contactInformation.primaryPhone']","Phone Number");
    private TextBox txbEmailAddress = new TextBox("css=input[id='contactInformation.emailAddress']","Email Address");
    private Link btnPrimaryWorkplaceLookUp = new Link("css=span[id='jclookup'] button", "Primary Workplace Lookup");
    private Span spnAccessMSUsernameErrorMessage = new Span("css=span[id='tmpUsername.errors']", "Access MS Username error message");
    private Span spnPrimaryWorkspaceErrorMessage = new Span("css=span[id='homeJobCenter.errors']", "Primary workspace error message");

    /**
     * Default constructor
     */
    public ActivityManagerCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Staff Creation')]"), "Staff Creation");
    }

    /**
     * This method is used for fill all fields on form with provided data
     * @param staff Staff object with data to be filled
     */
    public void fillAllActivityManagerDetails(Staff staff) {
        fillActivityManagerDetails(staff);
        selectUserType(staff.getUserType());
        fillUsernameAndWorkspace(staff);
    }

    /**
     * This method is used for select first Workplace from look-up
     */
    private void selectWorkplace() {
        btnPrimaryWorkplaceLookUp.clickAndWait();
        CenterSearchForm centerSearchForm = new CenterSearchForm();
        centerSearchForm.selectAndReturnCenter();
    }

    /**
     * This method is used for fill all fields on form except MS Username and Workplace
     * @param staff Staff object with data to be filled
     */
    public void fillActivityManagerDetails(Staff staff) {
        inputFirstLastName(staff.getFirstName(), staff.getLastName());
        txbJobTitle.type(staff.getJobTitle());
        txbPhoneNumber.type(staff.getPhoneNumber());
        txbEmailAddress.type(staff.getEmail());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * Simply select workspace and User
     * @param staff Staff object for selecting user
     */
    public void fillUsernameAndWorkspace(Staff staff) {
        selectWorkplace();
        inputUsername(staff.getStaffAccount());
    }

    /**
     * Get Unemployment Services System Username error message
     * @return Unemployment Services System Username error message
     */
    public String getUssUsernameMessage() {
        return spnAccessMSUsernameErrorMessage.getText();
    }

    /**
     * Get Primary Workspace error message
     * @return Primary Workspace error message
     */
    public String getPwMessage() {
        return spnPrimaryWorkspaceErrorMessage.getText();
    }
}
