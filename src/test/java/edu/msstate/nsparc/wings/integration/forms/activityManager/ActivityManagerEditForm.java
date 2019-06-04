package edu.msstate.nsparc.wings.integration.forms.activityManager;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import framework.CommonFunctions;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Activity Manager Accounts -> Search for Manager -> Open -> Click  Edit
 */
public class ActivityManagerEditForm extends ActivityManagerBaseForm {
    /**
     * Default constructor
     */
    public ActivityManagerEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Staff Edit')]"), "Staff Edit");
    }

    /**
     * Edit first name, last name and username
     * @param staff - staff object
     */
    public void editFirstLastUsername(Staff staff) {
        String firstName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        String lastName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        inputUsername(staff.getStaffAccount());
        inputFirstLastName(firstName, lastName);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
    }

}
