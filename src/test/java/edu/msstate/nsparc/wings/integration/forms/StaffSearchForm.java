package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.CheckBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is used for searching SC Manager.
 * Created by a.vnuchko on 25.08.2015.
 */
public class StaffSearchForm extends BaseWingsForm {
    private TextBox tbFirstName = new TextBox("id=firstName", "First Name");
    private TextBox tbLastName = new TextBox("id=lastName", "Last Name");
    private CheckBox cmbUserFound = new CheckBox("//input[@name='selectedId']", "Choose a user(scm) which was found by searching first name, last name");
    private RadioButton rbUserFound = new RadioButton("//input[@value='true']", "Choose a user, that can be found in the page");
    private Button btnReturn = new Button(By.xpath("//button[@id='return'] | //button[@id='returnMultiple']"), "Return button");

    /**
     * Constructor
     */
    public StaffSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Staff Search')]"), "Staff Search");
    }

    /**
     * This method is used for searching SC Manager.
     *
     * @param firstName - firstName of the user.
     * @param lastName  - lastName of the user.
     */
    public void performSearchAndSelect(String firstName, String lastName) {
        tbFirstName.type(firstName);
        tbLastName.type(lastName);
        clickAndWait(BaseButton.SEARCH);
        if (cmbUserFound.isPresent()) {
            cmbUserFound.click();
        }
        if (rbUserFound.isPresent()) {
            rbUserFound.click();
        }
        btnReturn.clickAndWait();
    }
}
