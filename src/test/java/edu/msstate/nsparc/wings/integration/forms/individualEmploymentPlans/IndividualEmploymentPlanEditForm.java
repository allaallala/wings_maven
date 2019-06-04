package edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans;

import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Individual Employment Plans -> Search and Open record -> Edit
 */
public class IndividualEmploymentPlanEditForm extends IndividualEmploymentPlanBaseForm {

    private TextBox tbDatePlanCreated = new TextBox("id=dateCreation", "Creation date");

    /**
     * Default constructor
     */
    public IndividualEmploymentPlanEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Individual Employment Plan Edit')]"), "Individual Employment Plan Edit");
    }

    /**
     * Change some date in the fields on the form.
     *
     * @param date - date to edit
     */
    public void editSomeData(String date) {
        tbDatePlanCreated.type(date);
    }
}
