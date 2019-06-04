package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingCourses;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Create some courses with different statuses. Check, that corresponding record is displayed in the search results table.
 */

@TestCase(id = "WINGS-10921")
public class TC_17_18_Training_Courses_Search_Different_Statuses extends BaseWingsTest {

    public void main() {
        info("Precondition: Create a training course");
        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};
        CourseSteps.createCourse(courseDetails[0], courseDetails[1], courseDetails[2], Roles.ADMIN);

        logStep("Log into the system as Staff or Executive or admin and open Search Course Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Fill out any search criteria fields with the data of existing Training Course, status - active");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        logStep("Fill out any search criteria fields with the data of existing Training Course (try different statuses: active)");
        searchPage.searchWithCourseName(courseDetails[0], TrainingCourses.CourseType.ACTIVE);
        searchPage.validateCourseNameStatus(courseDetails[0], TrainingCourses.CourseType.ACTIVE.getValue());
        BaseNavigationSteps.logout();

        logStep("Change course status");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);
        searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(courseDetails[0]);
        searchPage.clickButton(Buttons.Search);
        searchPage.openFirstSearchResult();
        TrainingCourseDetailsForm detailsForm = new TrainingCourseDetailsForm();
        detailsForm.clickEnableDisable(Constants.FALSE);
        detailsForm.clickButton(Buttons.Done);

        logStep("Fill out any search criteria fields with the data of existing Training Course, status - inactive, any");
        searchPage.searchWithCourseName(courseDetails[0], TrainingCourses.CourseType.INACTIVE);
        searchPage.validateCourseNameStatus(courseDetails[0], TrainingCourses.CourseType.INACTIVE.getValue());

        searchPage.searchWithCourseName(courseDetails[0], TrainingCourses.CourseType.ANY);
        searchPage.validateCourseNameStatus(courseDetails[0], TrainingCourses.CourseType.INACTIVE.getValue());
    }
}
