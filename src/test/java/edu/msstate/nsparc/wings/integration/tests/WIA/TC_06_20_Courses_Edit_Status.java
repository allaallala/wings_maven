package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseEditForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10664")
public class TC_06_20_Courses_Edit_Status extends BaseWingsTest {

    private static final String COURSE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String COURSE_CODE = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    private String status = "Inactive";

    public void main() {


        CourseSteps.createCourse(COURSE_NAME, COURSE_CODE, jobTitle, Roles.PCADMIN);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Enter parameters for searching->Search");
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearch(COURSE_NAME, COURSE_CODE, true);

        logStep("Select some Course and open it");
        trainingCourseSearchForm.clickCourseName();

        logStep("Click Edit");
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        trainingCourseDetailsForm.clickButton(Buttons.Edit);

        logStep("Change status->Save changes");
        TrainingCourseEditForm trainingCourseEditForm = new TrainingCourseEditForm();
        trainingCourseEditForm.clickButton(Buttons.Save);

        logStep("Search changed Course and be sure that all changes are saved");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        BaseWingsSteps.popClick(Popup.Search);
        trainingCourseSearchForm.clickActiveInactive(false);
        trainingCourseSearchForm.clickButton(Buttons.Search);
        CustomAssertion.softTrue("Course wasn't found", trainingCourseSearchForm.getCourseNamePresent());

        logStep("Open record and validate data");
        trainingCourseSearchForm.clickCourseName();
        CustomAssertion.softAssertEquals(trainingCourseDetailsForm.getStatusText(), status, "Assert Status failed");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
