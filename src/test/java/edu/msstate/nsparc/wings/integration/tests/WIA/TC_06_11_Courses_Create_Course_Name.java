package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10659")
public class TC_06_11_Courses_Create_Course_Name extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);

    public void main () {
        String status = "Active";
        AdvancedSqlFunctions.resetCourseCode(courseCode);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Create);

        logStep("Enter correct data to the field 'Course name'");
        TrainingCourseCreationForm trainingCourseCreationForm = new TrainingCourseCreationForm();
        trainingCourseCreationForm.inputCourseName(courseName);

        logStep("Enter correct data to the field 'Course code'");
        trainingCourseCreationForm.inputCourseCode(courseCode);

        logStep("Fill in all required fields->Create");
        trainingCourseCreationForm.fillOtherFields();
        trainingCourseCreationForm.clickButton(Buttons.Create);
        trainingCourseCreationForm.clickButton(Buttons.Done);

        logStep("Find new created Course");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearch(courseName, courseCode, true);
        trainingCourseSearchForm.clickCourseName();

        logStep("Validate displayed data");
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getCourseNameText(), courseName, "Assert Course Name failed");
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getCourseCodeText(), courseCode, "Assert Course Code failed");
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getStatusText(), status, "Assert Course Status failed");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
