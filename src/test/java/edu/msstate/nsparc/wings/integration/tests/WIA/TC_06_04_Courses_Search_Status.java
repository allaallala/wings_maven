package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10648")
public class TC_06_04_Courses_Search_Status extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    private static final String COURSE_STATUS = "Active";
    private static final String ERROR_MESSAGE = "Nothing found to display.";


    public void main() {
        CourseSteps.createCourse(courseName, courseCode, jobTitle, Roles.PCADMIN);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Advanced->Courses->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

        logStep("Select incorrect Status->Search");
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.clickActiveInactive(false);
        trainingCourseSearchForm.performSearch(courseName, courseCode, false);

        logStep("Validate Search results");
        assertEquals("Course was found", ERROR_MESSAGE, new SearchResultsForm().getTableContentText());

        logStep("Select correct Status->Search");
        trainingCourseSearchForm.clickActiveInactive(true);
        trainingCourseSearchForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        CustomAssertion.softTrue("Assert Course Name failed", trainingCourseSearchForm.getCourseNameText().contains(courseName));
        CustomAssertion.softAssertEquals(trainingCourseSearchForm.getCourseTextTable(), courseCode, "Assert Course Code failed");
        CustomAssertion.softAssertEquals(trainingCourseSearchForm.getCourseStatusText(), COURSE_STATUS,"Assert Course Status failed");
        logStep("Open course participantSSDetails and check course code, name, status");
        trainingCourseSearchForm.clickCourseName();
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getCourseNameText(), courseName, "Assert Course Name failed");
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getCourseCodeText(), courseCode, "Assert Course Code failed");
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getStatusText(), COURSE_STATUS,"Assert Course Status failed");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
