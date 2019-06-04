package edu.msstate.nsparc.wings.integration.forms.trainingCourse;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import org.openqa.selenium.By;

/**
 * This form is only available for Project Code Admin
 * This form is opened via Advanced -> Courses -> Search for record -> Open record -> Click on Edit button
 */
public class TrainingCourseEditForm extends TrainingCourseBaseForm {
    /**
     * Default constructor
     */
    public TrainingCourseEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Course Edit')]"), "Training Course Edit");
    }

    /**
     * This method is used for editing Training course parameters with provided data
     *
     * @param courseName      - name of the course
     * @param courseCode      - code of the course
     * @param jobTitle        - job title
     * @param mainOccupations - main occupations
     * @param role            - user role
     */
    public void editTrainingCourseDetails(String courseName, String courseCode, String jobTitle, String mainOccupations, Roles role) {
        editTrainingCourseDetailsOnlyRequired(courseName, courseCode, jobTitle, mainOccupations, false, role);
    }

    /**
     * This method is used for editing Training course parameters with provided data
     *
     * @param courseName      - name of the course
     * @param courseCode      - code of the course
     * @param jobTitle        - job title
     * @param mainOccupations - main occupations
     * @param required        - if job title is required
     * @param role            - user role
     */
    public void editTrainingCourseDetailsOnlyRequired(String courseName, String courseCode, String jobTitle, String mainOccupations,
                                                      boolean required, Roles role) {
        inputCourseName(courseName);
        if (role.equals(Roles.ADMIN) || role.equals(Roles.PCADMIN)) {
            inputCourseCode(courseCode);
        }
        if (!required) {
            inputJobTitle(jobTitle);
            cmbMajor.select(mainOccupations);
        }
    }
}
