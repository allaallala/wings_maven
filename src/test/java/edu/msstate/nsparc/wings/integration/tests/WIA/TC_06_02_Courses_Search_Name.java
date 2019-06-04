package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10644")
public class TC_06_02_Courses_Search_Name extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);


    public void main() {
        CourseSteps.createCourse(courseName, courseCode, jobTitle, Roles.ADMIN);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Advanced->Courses->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

        logStep("Enter Course name->Search");
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.inputCourseName(courseName);
        trainingCourseSearchForm.clickButton(Buttons.Search);

        logStep("Validate Course name");
        assertTrue("Assert Course Name failed", trainingCourseSearchForm.getCourseNameText().contains(courseName));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
