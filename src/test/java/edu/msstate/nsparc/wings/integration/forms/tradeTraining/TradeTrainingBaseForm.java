package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This is the base form for Trade Training forms
 */
public class TradeTrainingBaseForm extends BaseWingsForm {

    protected ComboBox cmbTrainingResult = new ComboBox("css=select[id='trainingResult']", "Status of Training");
    protected ComboBox cmbTrainingType = new ComboBox("css=select[id='trainingType']", "Training type");
    private RadioButton rbFirstSemester = new RadioButton(By.id("selectedRadio1"), "First semester in the list");
    private Button btnAddSemester = new Button("css=button[id='addSemester']", "Add Semester");
    private String semesterPath = "//table[@id='results-table']//tbody//tr[%1$d]//td[%2$d]";

    /**
     * Default constructor with locator
     *
     * @param locator - locator
     */
    public TradeTrainingBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Select trade training result
     *
     * @param result - result
     */
    public void selectTrainingResult(String result) {
        cmbTrainingResult.select(result);
    }

    /**
     * Select training type
     *
     * @param type - training type
     */
    public void selectTrainingType(String type) {
        cmbTrainingType.select(type);
    }

    /**
     * Get selected training result text
     *
     * @return selected training result text
     */
    public String getSelectedTrainingResult() {
        return cmbTrainingResult.getSelectedLabel();
    }

    /**
     * Get selected training type
     *
     * @return training type label selected
     */
    public String getSelectedTrainingType() {
        return cmbTrainingType.getSelectedLabel();
    }

    /**
     * Choose first semester
     */
    public void chooseFirstSemester() {
        rbFirstSemester.click();
    }

    /**
     * Add semester
     */
    public void addSemester() {
        btnAddSemester.clickAndWait();
    }

    /**
     * This method is used for validating data of the semester added.
     *
     * @param startDate   - date start of the semester
     * @param endDate     - date end of the semester
     * @param hours       - duration of the semester
     * @param recordCount - semesters quantity
     */
    public void validateSemesterAdded(String startDate, String endDate, String hours, Integer recordCount) {
        TableCell tbcHours;
        boolean rightSem = false;
        int recordNumber = 0;
        //Check, if the right semester is present on the page.
        for (int i = 1; i <= recordCount; i++) {
            tbcHours = new TableCell(By.xpath(String.format(semesterPath, i, 4)), "Hours of semester");
            if (tbcHours.getText().contains(hours)) {
                rightSem = true;
                recordNumber = i;
            }
        }
        if (!rightSem) {
            fatal("The semester has not been added");
        }
        TableCell tbcBeginDate = new TableCell(By.xpath(String.format(semesterPath, recordNumber, 2)), "Begin date of semester");
        TableCell tbcEndDate = new TableCell(By.xpath(String.format(semesterPath, recordNumber, 3)), "End date of semester");
        CustomAssertion.softAssertEquals(tbcBeginDate.getText(), startDate, "Incorrect begin date of semester");
        CustomAssertion.softAssertEquals(tbcEndDate.getText(), endDate, "Incorrect end date of semester");
    }
}
