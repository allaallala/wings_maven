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

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10646")
public class TC_06_03_Courses_Search_Code extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);


    public void main() {
        CourseSteps.createCourse(courseName, courseCode, jobTitle, Roles.PCADMIN);

        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Advanced->Courses->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

        logStep("Enter Course code->Search");
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.inputCourseCode(courseCode);
        trainingCourseSearchForm.clickButton(Buttons.Search);

        logStep("Validate Course code");
        assertEquals("Assert Course Code failed", courseCode, trainingCourseSearchForm.getCourseTextTable());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
