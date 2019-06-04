package edu.msstate.nsparc.wings.integration.forms.trainingCourse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Training Course forms
 */
public class TrainingCourseBaseForm extends BaseWingsForm {

    private TextBox txbCourseName = new TextBox("css=input#courseName", "Course Name");
    private TextBox txbCourseCode = new TextBox("css=input#courseCode", "Course Code");
    private TextBox txbJobTitle = new TextBox("css=input#jobTitle", "Job Title");
    protected ComboBox cmbMajor = new ComboBox(By.xpath("//select[@id='majorId'] | //select[@id='major.id']"), "Major"); // for spurui and CORE-QA

    /**
     * Default constructor
     *
     * @param locator   - locator
     * @param formTitle - title of the page
     */
    public TrainingCourseBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Input name of the course
     *
     * @param courseName - course name
     */
    public void inputCourseName(String courseName) {
        txbCourseName.type(courseName);
    }

    /**
     * Input course code
     *
     * @param code - course code
     */
    public void inputCourseCode(String code) {
        txbCourseCode.type(code);
    }

    /**
     * Input job title
     *
     * @param jobTitle - job title
     */
    public void inputJobTitle(String jobTitle) {
        txbJobTitle.type(jobTitle);
    }

    /**
     * Get course text
     *
     * @return course text
     */
    public String getCourseText() {
        return txbCourseCode.getText();
    }

    /**
     * Get course name text
     *
     * @return course name
     */
    public String getCourseName() {
        return txbCourseName.getText();
    }

    /**
     * If textbox course code is present on the page.
     *
     * @return - true, if course code text box is present on the page.
     */
    public Boolean courseCodePresent() {
        return txbCourseCode.isPresent();
    }
}
