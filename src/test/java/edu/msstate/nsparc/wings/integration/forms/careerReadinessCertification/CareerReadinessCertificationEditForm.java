package edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification;

import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Career Readiness Certifications -> Search for record -> Open record -> Click on Edit
 */
public class CareerReadinessCertificationEditForm extends CareerReadinessCertificationBaseForm {

    /**
     * Constructor
     */
    public CareerReadinessCertificationEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Career Readiness Certification Edit')]"), "Career Readiness Certification Edit");
    }

    /**
     * This method is used for finish editing
     */
    public void finishEditing() {
        clickAndWait(BaseButton.SAVE_CHANGES);
        passParticipationRecalculationPage();
    }
}
