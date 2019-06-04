package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

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
import org.testng.Assert;


/**
 * Created by a.vnuchko on 02.07.2015.
 * Check that is possible to delete created course
 */

@TestCase(id = "WINGS-10914")
public class TC_17_11_Training_Courses_Delete_Is_Possible extends BaseWingsTest {
    private static final Integer QUANTITY_SYMBOLS = 125;

    public void main() {

        String expectedText = "There are no training enrollments with this assigned course. If you would like to delete this course, click the button below.";
        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};

        info("Precondition: Create a Training Course and DO NOT use it in different types of training enrollments (Trade + WIA)");
        CourseSteps.createCourse(courseDetails[0], courseDetails[1], courseDetails[2], Roles.ADMIN);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Find a course from the preconditions and go to its detail page");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.performSearch(courseDetails[0], courseDetails[1], Constants.FALSE);
        searchPage.clickCourseName();

        logResult("The system shows the following message: There are no training enrollments with this assigned course. If you would like to delete this course, click the button below.");
        TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
        Assert.assertEquals("Incorrect warning text about deletion", expectedText, detailsPage.getWarningText().substring(0, QUANTITY_SYMBOLS));

        logResult("It is possible to delete a training course. A training course is deleted successfully.");
        detailsPage.deleteButton();
        detailsPage.areYouSure(Popup.Yes);
        searchPage.performSearch(courseDetails[0], courseDetails[1], Constants.FALSE);
        searchPage.noSearchResults();
    }
}
