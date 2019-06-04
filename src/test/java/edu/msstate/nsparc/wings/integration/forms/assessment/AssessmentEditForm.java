package edu.msstate.nsparc.wings.integration.forms.assessment;

import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> Youth Assessments -> Search for record -> Open record -> Click on Edit
 */
public class AssessmentEditForm extends AssessmentBaseForm {
    private String scoreEdit = "90";

    /**
     * Constructor
     */
    public AssessmentEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Assessment Edit')]"), "Assessment Edit");
    }

    /**
     * Edit some parameter (at this moment we required only one. any)
     * @param faValue - functional area value
     * @param tpValue - type value
     */
    public void editAssessment(String faValue, String tpValue) {
        cmbFunctionalArea.select(faValue);
        selectType(tpValue);
        typeScore(scoreEdit);
    }

    /**
     * Edit all parameters of the Assessments.
     * @param program - assessment program
     * @param functionalArea - functional area
     * @param type - type
     * @param date - new date
     * @param score - new score
     */
    public void editAllAssmParameters(String program, String functionalArea, String type, String date, String score) {
        selectProgram(program);
        selectPreTest();
        cmbFunctionalArea.select(functionalArea);
        selectType(type);
        inputDateAdministered(date);
        typeScore(score);
        fillInEducationalFunctionalArea();
    }

    private void fillInEducationalFunctionalArea() {
        if (cmbEducationalFunctionalArea.isPresent()) {
            cmbEducationalFunctionalArea.selectFirst();
        }
    }

    /**
     * Change score of the assessment
     * @param score - score
     */
    public void changeScore(String score) {
        typeScore(score);
        saveChanges();
    }


}
