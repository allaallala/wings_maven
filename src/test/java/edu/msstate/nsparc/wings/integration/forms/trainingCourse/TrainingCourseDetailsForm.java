package edu.msstate.nsparc.wings.integration.forms.trainingCourse;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Courses -> Search for record -> Open record
 */
public class TrainingCourseDetailsForm extends TrainingCourseBaseForm {

    private String osoc = "Cooks, Restaurant";
    private String occupations = "Computer Occupations";
    private String xpath = "//form[@id='command']//td[contains(.,'%1$s')]/following-sibling::td";
    private Span spnWarningText = new Span("//tr/td[@class='blue-border'][2]", "Warning text about deletion");
    private TableCell tbcCourseName = new TableCell(By.xpath(String.format(xpath, "Course Name")), "Course Name");
    private TableCell tbcCourseCode = new TableCell(By.xpath(String.format(xpath, "Course Code")), "Course Code");
    private TableCell tbcStatus = new TableCell(By.xpath(String.format(xpath, "Status")), "Status");
    private TableCell tbcJobTitle = new TableCell(By.xpath(String.format(xpath, "Job Title")), "Job Title");
    private TableCell tbcOsoc = new TableCell(By.xpath(String.format(xpath, "Osoc")), "Osoc title");
    private TableCell tbcIndustryTrainingType = new TableCell(By.xpath(String.format(xpath, "Industry Training Type")), "Industry Training Type");
    private Button btnDelete = new Button("//input[@value='Delete']", "Delete button");
    private Button btnDisable = new Button("id=disableCourse", "Button Disable");
    private Button btnEnable = new Button("id=enableCourse", "Button Enable");
    private Span spnDisableText = new Span(By.xpath("//span[@id='notesLink']/s"), "Disabled course name");
    private Button btnViewWiaEnrl = new Button("//a[.='View WIA Training Enrollments']", "View WIA training enrollments");
    private Button btnViewTradeEnrl = new Button("//a[.='View Trade Training Enrollments']", "View Trade training enrollments");

    private String statusActive = "Active";
    private String wia = "WIA";

    /**
     * Default constructor
     */
    public TrainingCourseDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Course Detail')]"), "Training Course Detail");
    }

    /**
     * This method is used for comparing expected values with data on Details form
     *
     * @param courseName - name of the course
     * @param courseCode - name of the code
     * @param jobTitle   - job title
     * @param role       - role
     */
    public void validateTrainingCourseDetails(String courseName, String courseCode, String jobTitle, Roles role) {
        CustomAssertion.softAssertEquals(getCourseNameText(), courseName, "Incorrect course name");
        if (role.equals(Roles.ADMIN) || role.equals(Roles.PCADMIN)) {
            CustomAssertion.softAssertEquals(getCourseCodeText(), courseCode, "Incorrect course code");
        }
        CustomAssertion.softAssertEquals(getJobTitleText(), jobTitle, "Incorrect job title");
        CustomAssertion.softAssertEquals(getStatusText(), statusActive, "Incorrect status");
    }

    /**
     * This method is used for validate OSOC title and Industry training type (occupations).
     */
    public void validateOsocOccupations() {
        CustomAssertion.softAssertEquals(getOsoc(), osoc, "Incorrect OSOC");
        CustomAssertion.softAssertEquals(getIndustryTrainingType(), occupations, "Incorrect computer occupations");
    }

    /**
     * Check course participantSSDetails data.
     *
     * @param courseName  - name of the course
     * @param courseTitle - course title.
     */
    public void validateTrainingCourseDetail(String courseName, String courseTitle) {
        CustomAssertion.softAssertEquals(getCourseNameText(), courseName, "Incorrect course name");
        CustomAssertion.softAssertEquals(getJobTitleText(), courseTitle, "Incorrect course title");
        CustomAssertion.softAssertEquals(getStatusText(), statusActive, "Incorrect course status");
    }

    /**
     * This method is used for comparing expected values with data on Details form
     *
     * @param courseName - name of the course
     * @param courseCode - course code
     * @param jobTitle   - job title
     * @return true, if page contains required parameters.
     */
    public boolean validateTrainingCourseSearchedDetails(String courseName, String courseCode, String jobTitle) {
        return getCourseNameText().contains(courseName)
                && getCourseCodeText().contains(courseCode)
                && getJobTitleText().contains(jobTitle);
    }

    /**
     * This method is used for getting Training course data from Details form
     *
     * @return String array with Training Course data
     */
    public String[] getTrainingCourseDetails() {
        return new String[]{getCourseNameText(),
                getCourseCodeText(),
                tbcIndustryTrainingType.getText()};
    }

    /**
     * Delete training course
     */
    public void deleteButton() {
        btnDelete.click();
    }

    /**
     * View all training enrollments connected with chosen course
     *
     * @param type - type of enrollments.
     */
    public void viewEnrollments(String type) {
        if (type.contains(wia)) {
            btnViewWiaEnrl.clickAndWait();
        } else {
            btnViewTradeEnrl.clickAndWait();
        }
    }

    /**
     * Get warning text on the page
     *
     * @return warning text
     */
    public String getWarningText() {
        return spnWarningText.getText();
    }

    /**
     * Click [Enable] or [Disable] buttons
     *
     * @param on - if true - click [Enable], else - [Disable]
     */
    public void clickEnableDisable(Boolean on) {
        if (on) {
            btnEnable.clickAndWait();
        } else {
            btnDisable.clickAndWait();
        }
    }

    /**
     * Check if enable or disable buttons are present.
     *
     * @param on - if true - check Enable button, else - Disable button.
     */
    public void checkEnableDisablePresent(Boolean on) {
        if (on) {
            btnEnable.isPresent();
        } else {
            btnDisable.isPresent();
        }
    }

    /**
     * Check, that the [Disable] span is present and visible on the page.
     */
    public void spanDisablePresent() {
        spnDisableText.isPresent();
    }

    /**
     * Check, that specified buttons are present or not on the page
     *
     * @param btnEdit - Edit button present
     * @param audit   Audit button present
     */
    public void checkButtonsPresent(Boolean btnEdit, Boolean audit) {
        divideMessage("Edit");
        ifButton(btnEdit, BaseButton.EDIT.getButton());
        divideMessage("Audit");
        ifButton(audit, BaseButton.AUDIT.getButton());
    }

    /**
     * Get course name text
     *
     * @return - course name on the page
     */
    public String getCourseNameText() {
        return tbcCourseName.getText();
    }

    /**
     * Get course code text
     *
     * @return course code text on the page.
     */
    public String getCourseCodeText() {
        return tbcCourseCode.getText();
    }

    /**
     * Get status text
     *
     * @return status text on the page.
     */
    public String getStatusText() {
        return tbcStatus.getText();
    }

    /**
     * Get job title text
     *
     * @return job title on the page
     */
    public String getJobTitleText() {
        return tbcJobTitle.getText();
    }

    /**
     * Get OSOC title on the page
     *
     * @return - OSOC title
     */
    public String getOsoc() {
        return tbcOsoc.getText();
    }

    /**
     * Get industry training type title on the page
     *
     * @return - industry training type.
     */
    public String getIndustryTrainingType() {
        return tbcIndustryTrainingType.getText();
    }
}
