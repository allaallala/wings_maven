package edu.msstate.nsparc.wings.integration.forms.programOutcomes;

import framework.elements.ComboBox;
import framework.elements.Div;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> Program Outcomes -> Enter Edit mode
 */
public class ProgramOutcomesManagementForm extends ProgramOutcomesBaseForm {

    private Div divFourthQuarter = new Div("css=div#fourthQuarter", "Fourth Quarter tab");

    //First Quarter
    private RadioButton rbNonTraditionalEmployment = new RadioButton("css=input#enteredNonTraditionalEmployment1", "Entered Non-Traditional Employment");
    private RadioButton rbTrainingRelatedEmployment = new RadioButton("css=input#enteredTrainingRelatedEmployment1", "Entered Training Related Employment");
    private ComboBox cmbMethod = new ComboBox("css=select#methodDetermineTrainingRelated", "Method For Determining Training Related Employment");

    /**
     * Default constructor
     */
    public ProgramOutcomesManagementForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Program Outcomes Management')]"), "Program Outcomes Management");
    }

    /**
     * This method is used for fill First Quarter tab with correct
     *
     * @param method Method Used to Determine Training Related Employment
     */
    public void fillAdultFirstQuarter(String method) {
        rbNonTraditionalEmployment.waitForIsElementPresent();
        rbNonTraditionalEmployment.click();
        rbTrainingRelatedEmployment.waitForIsElementPresent();
        rbTrainingRelatedEmployment.click();
        cmbMethod.waitForIsElementPresent();
        cmbMethod.select(method);
    }

    /**
     * Select program method
     *
     * @param value - value to select
     */
    public void selectProgramMethod(String value) {
        cmbMethod.select(value);
    }

    /**
     * Get warning message in the fourth quarter
     *
     * @return warning message
     */
    public String getFourthWarningMessage() {
        return divFourthQuarter.getText();
    }
}
