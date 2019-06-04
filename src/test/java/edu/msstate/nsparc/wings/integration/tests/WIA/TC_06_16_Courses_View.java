package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10661")
public class TC_06_16_Courses_View extends BaseWingsTest {

    private String courseName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);


    public void main() {


        CourseSteps.createCourse(courseName, courseCode, jobTitle, Roles.ADMIN);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Perform search for training courses");
        TrainingCourseSearchForm searchForm = new TrainingCourseSearchForm();
        searchForm.performSearch(courseName, courseCode, false);

        logStep("Open course participantSSDetails form");
        searchForm.clickCourseName();
        logStep("Validate training course detail");
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        assertTrue("Assert Training Course Details Failed", trainingCourseDetailsForm.validateTrainingCourseSearchedDetails(courseName, courseCode, jobTitle));
    }
}
