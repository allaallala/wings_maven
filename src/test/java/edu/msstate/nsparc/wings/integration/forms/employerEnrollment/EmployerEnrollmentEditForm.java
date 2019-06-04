package edu.msstate.nsparc.wings.integration.forms.employerEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import org.openqa.selenium.By;

/**
 * Created by a.vnuchko on 14.07.2016.
 */
public class EmployerEnrollmentEditForm extends EmployerEnrollmentBaseForm {
    /**
     * Constructor of the form with defined locator of the form
     */
    public EmployerEnrollmentEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Edit')]"), "Employer enrollment edit form");
    }

    /**
     * Edit creation date and save changes
     * @param changedDate - changed creation date
     */
    public void editCreationDate(String changedDate) {
        txbCreationDate.type(changedDate);
        clickButton(Buttons.Save);
    }
}
