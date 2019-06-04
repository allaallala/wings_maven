package edu.msstate.nsparc.wings.integration.forms.activityManager;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import framework.elements.*;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Activity Manager Accounts -> Search
 */
public class ActivityManagerSearchForm extends ActivityManagerBaseForm {

    private RadioButton rbStaffMember = new RadioButton(By.xpath("//table[@id='results-table']/tbody/tr[1]/td[1]/input"), "Select Staff");
    private CheckBox chkStaffMember = new CheckBox("css=table[id='results-table'] input[class=checkbox]", "Staff Member Checkbox");
    private Button btnReturnCheckboxes = new Button("css=button[id='returnMultiple']", "Return");
    private ComboBox cmbUserType = new ComboBox(By.id("applicationUser.role"), "Select a user type");
    private String disabled = "Disable Login (Access Revoked)";
    private Span spnDisabled = new Span(By.xpath(String.format("//option[contains(.,'%1$s')]", disabled)), "Disabled option");

    /**
     * Default constructor
     */
    public ActivityManagerSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Staff Search')]"), "Activity Manager Search");
    }

    /**
     * This method is used for search for staff from look-up and then return it
     *
     * @param staff Staff that will be found
     */
    public void performSearchAndSelect(Staff staff) {
        inputFirstLastName(staff.getFirstName(), staff.getLastName());
        clickAndWait(BaseButton.SEARCH);
        if (chkStaffMember.isPresent()) {
            chkStaffMember.click();
        } else {
            rbStaffMember.click();
        }
        if (btnReturnCheckboxes.isPresent()) {
            btnReturnCheckboxes.clickAndWait();
        } else {
            clickAndWait(BaseButton.RETURN);
        }
    }

    /**
     * This method is used for search for staff from look-up and then return it
     *
     * @param username MS Access username for staff
     */
    public void performSearchAndReturn(String username) {
        inputUsername(username);
        clickAndWait(BaseButton.SEARCH);
        if (rbStaffMember.isPresent()) {
            rbStaffMember.click();
            clickAndWait(BaseButton.RETURN);
        } else {
            openFirstSearchResult();
        }
    }

    /**
     * Check, that is possible to search for disabled accounts
     * @param user - current user
     */
    public void checkSearchDisabled(User user) {
        if (user.getStaff().getStaffSearchDisabledAccount()) {
            spnDisabled.assertIsPresent();
            cmbUserType.select(disabled);
            clickButton(Buttons.Search);
            new SearchResultsForm().assertFirstSearchResultLinkPresent();
            cmbUserType.select(Constants.ANY);
        } else {
            spnDisabled.assertIsNotPresent();
        }

    }

}