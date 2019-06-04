package edu.msstate.nsparc.wings.integration.forms.trainingCourse;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.OsocSearchForm;
import framework.CommonFunctions;
import framework.elements.Button;
import org.openqa.selenium.By;

/**
 * This form is only available for Project Code Admin
 * This form is opened via Advanced -> Courses -> Create
 */
public class TrainingCourseCreationForm extends TrainingCourseBaseForm {

    private Button btnOsocLookup = new Button("css=td[id='osocLookup'] button", "Osoc Lookup");
    private String osocCode = "Cooks, Restaurant";
    private String major = "Computer Occupations";

    /**
     * Default constructor
     */
    public TrainingCourseCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Course Creation')]"), "Training Course Creation");
    }


    /**
     * This method is user for creating new Training course (all included)
     *
     * @param courseName - name of the course
     * @param courseCode - course code
     * @param jobTitle   - job title
     */
    public void fillFieldsAndCreate(String courseName, String courseCode, String jobTitle) {
        inputCourseName(courseName);
        if (courseCodePresent()) {
            inputCourseCode(courseCode);
        }
        btnOsocLookup.clickAndWait();
        OsocSearchForm osocSearchForm = new OsocSearchForm();
        osocSearchForm.selectAndReturnOsoc(osocCode);
        inputJobTitle(jobTitle);
        cmbMajor.select(major);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        clickAndWait(BaseButton.CREATE);
    }

    /**
     * This method is used for filling other fields during creation process
     */
    public void fillOtherFields() {
        if (btnOsocLookup.isPresent()) {
            btnOsocLookup.clickAndWait();
            OsocSearchForm osocSearchForm = new OsocSearchForm();
            osocSearchForm.selectAndReturnOsoc(osocCode);
        }
        inputJobTitle(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));
        cmbMajor.select(major);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }
}
