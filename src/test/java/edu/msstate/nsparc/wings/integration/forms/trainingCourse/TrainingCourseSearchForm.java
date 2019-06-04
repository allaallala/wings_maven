package edu.msstate.nsparc.wings.integration.forms.trainingCourse;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingCourses;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Courses -> Search
 */
public class TrainingCourseSearchForm extends TrainingCourseBaseForm {

    private String xpath = "//table[@id='results-table']//td[%1$d]";
    private String xpathDiv = xpath + "/div";
    private String xpathA = xpath + "/a";
    private RadioButton rbActive = new RadioButton("css=input[id='isDisabled1']", "Active Status");
    private RadioButton rbInactive = new RadioButton("css=input[id='isDisabled2']", "Inactive Status");
    private RadioButton rbAny = new RadioButton("css=input[id='isDisabled3']", "Any Status");
    private CheckBox chkCourse = new CheckBox("//input[@type='checkbox']", "First Course");
    private RadioButton rbCourse = new RadioButton("css=table#results-table input.radio", "Course");
    private Button btnReturnMultiple = new Button("id=returnMultiple", "Return");
    private Button btnReturnOne = new Button("id=return", "Return");
    private TableCell tbcCourseCode = new TableCell(By.xpath(String.format(xpath, 3)), "Course Code");
    private TableCell tbcTrainingType = new TableCell(By.xpath(String.format(xpathDiv, 4)), "Training Type");
    private TableCell tbcCourseStatus = new TableCell(By.xpath(String.format(xpath, 5)), "Course Status (first record)");
    private Link lnkCourseName = new Link(By.xpath(String.format(xpathA, 2)), "Link course name");

    private Span spnNumberOfRecordsFound = new Span("//span[@class='pagebanner']", "Number of records found");
    private static final String COURSE_NAME = "Course28102015";

    /**
     * Constructor
     */
    public TrainingCourseSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Course Search')]"), "Training Course Search");
    }

    /**
     * Click [Active] or [Inactive] radio buttons
     *
     * @param active - if true, click on [Active] radio button.
     */
    public void clickActiveInactive(Boolean active) {
        if (active) {
            rbActive.click();
        } else {
            rbInactive.click();
        }
    }

    /**
     * Click course name
     */
    public void clickCourseName() {
        lnkCourseName.clickAndWait();
    }

    /**
     * Gets course name text
     */
    public String getCourseNameText() {
        return lnkCourseName.getText();
    }

    /**
     * Gets course name is present
     *
     * @return lnk course name is present
     */
    public Boolean getCourseNamePresent() {
        return lnkCourseName.isPresent();
    }

    /**
     * Clicks the [Any] radiobutton
     */
    public void clickAny() {
        rbAny.click();
    }

    /**
     * This method is used for active course searching and returning course from look-up
     */
    public void performSearchAndReturnFirst() {
        inputCourseName(COURSE_NAME);
        performSearchAndReturnFirst(true);
    }

    /**
     * This method is used for course searching and returning course from look-up
     *
     * @param active Indicates if course is active
     */
    public void performSearchAndReturnFirst(boolean active) {
        if (active) {
            rbActive.click();
        } else {
            rbInactive.click();
        }
        clickAndWait(BaseButton.SEARCH);
        chkCourse.click();
        btnReturnMultiple.clickAndWait();
    }

    /**
     * This method is used for Course searching by name and code
     *
     * @param name   Course Name
     * @param code   Course code
     * @param course - true/false
     */
    public void performSearch(String name, String code, boolean course) {
        inputCourseName(name);
        if (course) {
            inputCourseCode(code);
        }
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for Course searching by name and returning it to look-up
     *
     * @param name Course name
     */
    public void performSearchAndReturn(String name) {
        inputCourseName(name);
        clickAndWait(BaseButton.SEARCH);
        rbCourse.click();
        btnReturnOne.clickAndWait();
    }


    public void searchWithCourseName(String courseName, TrainingCourses.CourseType type) {
        inputCourseName(courseName);
        switch (type) {
            case ANY:
                clickAny();
                break;
            case ACTIVE:
                clickActiveInactive(Constants.TRUE);
                break;
            case INACTIVE:
                clickActiveInactive(Constants.FALSE);
                break;
            default:
                info("You didn't select any search type");
                break;
        }
        clickButton(Buttons.Search);

    }

    /**
     * Gets course text on the page
     *
     * @return - course text.
     */
    public String getCourseTextTable() {
        return tbcCourseCode.getText();
    }

    /**
     * Gets course status on the page
     *
     * @return - course status
     */
    public String getCourseStatusText() {
        return tbcCourseStatus.getText();
    }

    /**
     * This method is used for filling Course name and code search fields with provided values
     *
     * @param courseName - name of the course
     * @param courseCode - course code
     */
    public void fillSearchCriteria(String courseName, String courseCode) {
        inputCourseName(courseName);
        inputCourseCode(courseCode);
    }

    /**
     * This method is used for validating search results
     *
     * @param courseName   - course name
     * @param courseCode   - course code
     * @param trainingType - training type.
     * @return True if actual data matches expected
     */
    public boolean validateSearchResults(String courseName, String courseCode, String trainingType) {
        return lnkCourseName.getText().contains(courseName) &&
                getCourseTextTable().contains(courseCode) &&
                tbcTrainingType.getText().contains(trainingType);
    }

    /**
     * Validate course name and status, after searching
     *
     * @param courseName   - course name
     * @param courseStatus - course status
     */
    public void validateCourseNameStatus(String courseName, String courseStatus) {
        CustomAssertion.softAssertContains(getCourseNameText(), courseName, "Incorrect course name in the search result table");
        CustomAssertion.softAssertEquals(getCourseStatusText(), courseStatus, "Incorrect status in the search result table");
    }

    /**
     * This method is used for getting number of search results
     *
     * @return Search results items count
     */
    public String getNumberOfRecordsFound() {
        String x;
        x = CommonFunctions.regexGetMatch(spnNumberOfRecordsFound.getText(), "[\\d]+,?[\\d]*");
        return x.replaceFirst("[,]", "");
    }

    /**
     * This method is used for comparing number of Inactive Records with Database
     *
     * @param inactiveRecords Inactive records from form
     * @param activeRecords   Active records from form
     */
    public void compareWithDBInactiveRecordsNumber(String inactiveRecords, String activeRecords) {
        int inactiveCount = AdvancedSqlFunctions.getActiveInactiveCoursesNumber(1);
        int activeCount = AdvancedSqlFunctions.getActiveInactiveCoursesNumber(0);
        info("Displayed inactive Records = [" + inactiveRecords + "], Displayed active Records = [" + activeRecords + "]");
        info("Database Inactive Records = [" + inactiveCount + "], Database Active Records = [" + activeCount + "]");
        CustomAssertion.softTrue("Active records number is not the same as in database ",
                activeRecords.contains(String.format("%1$s", activeCount)));
        CustomAssertion.softTrue("Inactive records number is not the same as in database",
                inactiveRecords.contains(String.format("%1$s", inactiveCount)));
    }
}
