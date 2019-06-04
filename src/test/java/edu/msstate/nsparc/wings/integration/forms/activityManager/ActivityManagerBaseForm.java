package edu.msstate.nsparc.wings.integration.forms.activityManager;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is base form for all Activity Manager forms
 */
public class ActivityManagerBaseForm extends BaseWingsForm {

    private TextBox txbFirstName = new TextBox("css=input#firstName","First Name");
    private TextBox txbLastName = new TextBox("css=input#lastName", "Last name");
    private TextBox txbUsername = new TextBox("css=input#tmpUsername","Unemployment Services System Username");
    private ComboBox cmbUserType = new ComboBox("css=select[id='applicationUser.role']", "User Type");

    /**
     * Default constructor
     * @param locator - locator of the form
     * @param formTitle - title of the form
     */
    protected ActivityManagerBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Input first name and last name of the activity manager
     * @param firstName - first name
     * @param lastName - last name
     */
    public void inputFirstLastName(String firstName, String lastName) {
        txbFirstName.type(firstName);
        txbLastName.type(lastName);
    }

    /**
     * Input manager user name
     * @param userName - user name
     */
    public void inputUsername(String userName) {
        txbUsername.type(userName);
    }

    /**
     * Select user type
     * @param userType - user type
     */
    public void selectUserType(String userType) {
        cmbUserType.select(userType);
    }
}
