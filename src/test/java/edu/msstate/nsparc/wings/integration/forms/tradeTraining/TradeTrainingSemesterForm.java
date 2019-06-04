package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTrainingSemester;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened from Trade Training creation/edit pages by clicking on 'Add'/'Edit' button in Semesters section
 */
public class TradeTrainingSemesterForm extends TradeTrainingBaseForm {

    private TextBox txbStartDate = new TextBox("css=input[id='tmpTrainingSemester.dateBegin']", "Start Date");
    private TextBox txbEndDate = new TextBox("css=input[id='tmpTrainingSemester.dateEnd']", "End Date");
    private TextBox txbCreditHours = new TextBox("css=input[id='tmpTrainingSemester.creditHours']", "Credit Hours");
    private RadioButton rbPartTimeYes = new RadioButton("css=input[id='tmpTrainingSemester.isPartTimeTraining1']", "Part-Time Training - Yes");
    private RadioButton rbPartTimeNo = new RadioButton("css=input[id='tmpTrainingSemester.isPartTimeTraining2']", "Part-Time Training - No");
    private Button btnAdd = new Button("css=button[id='add']", "Add");

    /**
     * Default constructor
     */
    public TradeTrainingSemesterForm() {
        super(By.xpath("//h1[contains(.,'Semester')]"), "Trade Training Semester Creation-Edit");
    }

    /**
     * Adding Semester information to Trade Training
     *
     * @param semester Semester data
     */
    public void addSemester(TradeTrainingSemester semester) {
        inputStartDate(semester.getBeginDate());
        txbEndDate.type(semester.getEndDate());
        txbCreditHours.type(semester.getCreditHours());
        if (semester.isPartTimeTraining()) {
            rbPartTimeYes.click();
        } else {
            rbPartTimeNo.click();
        }
        btnAdd.clickAndWait();
    }

    /**
     * Edit some field in semester
     *
     * @param startDate - start of the semester
     * @param endDate   - finish of the semester
     * @param hours     - duration
     */
    public void editSemester(String startDate, String endDate, String hours) {
        fillSemester(startDate, endDate, hours);
    }

    /**
     * Fill some data for semester
     *
     * @param startDate - start date of the semester
     * @param endDate   - end date of the semester
     * @param hours     - duration
     */
    public void fillSemester(String startDate, String endDate, String hours) {
        inputStartDate(startDate);
        txbEndDate.type(endDate);
        txbCreditHours.type(hours);
        rbPartTimeNo.click();
    }

    public enum SEMESTER_ACT { ADD, EDIT, CANCEL }

    /**
     * make action depends on target: add, edit, cancel
     *
     * @param startDate - start of the semester
     * @param endDate   - finish of the semester
     * @param hours     - hours or the semester
     * @param type      - add, edit, cancel
     */
    public void semesterActions(String startDate, String endDate, String hours, SEMESTER_ACT type) {
        fillSemester(startDate, endDate, hours);
        switch (type) {
            case ADD:
                btnAdd.clickAndWait();
                break;
            case EDIT:
                clickAndWait(BaseButton.SAVE_CHANGES);
                break;
            case CANCEL:
                clickAndWait(BaseButton.CANCEL);
                break;
            default:
                break;
        }
    }

    /**
     * Input start date for semester
     *
     * @param date - start date
     */
    public void inputStartDate(String date) {
        txbStartDate.type(date);
    }
}
