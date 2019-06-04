package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 08.07.2015.
 * Create some course, disable it, check corresponding message and status
 */

@TestCase(id = "WINGS-10916")
public class TC_17_13_Training_Courses_Disable_Course extends BaseWingsTest {
    private String status = "Inactive";

    public void main() {

        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};

        repeatedSteps(courseDetails[0], courseDetails[1], courseDetails[2]);

        logStep("Click [Disable]");
        TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
        detailsPage.clickEnableDisable(false);

        logResult("The Training Course Detail Screen with correct and actual information is shown. "
                + "'Enable' button is available. After disabling a course, its title is displayed in strikethrough. The status is 'Inactive'.");
        String[] courseOnPage = detailsPage.getTrainingCourseDetails();
        CustomAssertion.softAssertEquals(courseOnPage[0], courseDetails[0], "Incorrect course name");
        CustomAssertion.softAssertEquals(detailsPage.getJobTitleText(), courseDetails[2], "Incorrect job title");
        CustomAssertion.softAssertEquals(detailsPage.getStatusText(), status, "Incorrect status");
        detailsPage.checkEnableDisablePresent(true);
        detailsPage.spanDisablePresent();
    }

    /**
     * Represents steps, that are repeated in the other tests.
     *
     * @param courseName - name of the course
     * @param courseCode - code of the course
     * @param jobTitle   - title of the job.
     */
    protected void repeatedSteps(String courseName, String courseCode, String jobTitle) {
        info("Precondition: Create some Courses");
        CourseSteps.createCourse(courseName, courseCode, jobTitle, Roles.ADMIN);

        logStep("Log into the system as Admin and open Search Course Page");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Fill out any search criteria field with the data of existing Training Course");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.performSearch(courseName, courseCode, Constants.FALSE);

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logStep("Click the 'Training Course Name' of any Training Course shown in the Search Results");
        searchPage.clickCourseName();
    }
}
