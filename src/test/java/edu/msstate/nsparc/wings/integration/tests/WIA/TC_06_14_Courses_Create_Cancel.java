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
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10660")
public class TC_06_14_Courses_Create_Cancel extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);

    public void main () {


        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Create);

        logStep("Fill inn all fields you need->Cancel'");
        TrainingCourseCreationForm trainingCourseCreationForm = new TrainingCourseCreationForm();
        trainingCourseCreationForm.inputCourseName(courseName);
        trainingCourseCreationForm.inputCourseCode(courseCode);
        trainingCourseCreationForm.fillOtherFields();
        trainingCourseCreationForm.clickButton(Buttons.Cancel);
        trainingCourseCreationForm.areYouSure(Popup.Yes);

        logStep("Try to find Course");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearch(courseName, courseCode, false);

        assertFalse("Course was created", trainingCourseSearchForm.getCourseNamePresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
