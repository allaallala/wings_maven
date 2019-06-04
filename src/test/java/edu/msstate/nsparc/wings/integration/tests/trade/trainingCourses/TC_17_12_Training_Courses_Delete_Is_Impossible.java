package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Create some training courses and use it in different types of training enrollments,
 * open detail page of chosen course and check appropriate message and connected enrollments
 * Created by a.vnuchko on 26.10.2015.
 */

@TestCase(id = "WINGS-10915")
public class TC_17_12_Training_Courses_Delete_Is_Impossible extends BaseWingsTest {

    private static final String COURSE_NAME = "Course28102015";
    private static final String EXPECTED_TEXT = "There are %1$s training enrollments with this assigned course, therefore it cannot be deleted.";
    private Integer enrollmentCountWia = AdvancedSqlFunctions.getCountTrainingConnectedCourse(COURSE_NAME, Constants.WIOA.toUpperCase());
    private Integer enrollmentCountTrade = AdvancedSqlFunctions.getCountTrainingConnectedCourse(COURSE_NAME, Constants.TRADE);

    public void main() {
        BaseWingsSteps.openSearchForm(Roles.WIOA, WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        commonSteps();
        validateRecordsCount(Constants.WIOA.toUpperCase(), enrollmentCountWia);

        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);
        commonSteps();
        validateRecordsCount(Constants.TRADE, enrollmentCountTrade);
    }

    /**
     * Validate records count on page
     * @param type - WIA or Trade
     * @param enrollmentCount - quantity of training enrollments connected with a course.
     */
    private void validateRecordsCount(String type, Integer enrollmentCount) {
        logStep("Click the 'View WIA Training Enrollments' link to view all the training enrollments that are connected with this training course");
        TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
        detailsPage.viewEnrollments(type);

        logStep("A list of all the training enrollments that are connected with this training course is shown to the user.");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), Constants.COUNT_REGEX);
        CustomAssertion.softAssertEquals(recordsCount.replace(",", Constants.EMPTY), enrollmentCount.toString(), "Incorrect quantity of courses records on the search page");
        searchPage.clickButton(Buttons.Cancel);

        int enrollmentSum = enrollmentCountWia + enrollmentCountTrade;

        logResult("The system shows the following message: 'There are <number> training enrollments with this assigned course, therefore it cannot be deleted.");
        CustomAssertion.softAssertContains(detailsPage.getWarningText(), String.format(EXPECTED_TEXT, String.valueOf(enrollmentSum)), "Incorrect warning text");
    }

    private void commonSteps() {
        logStep("Find a course from the preconditions and go to its detail page");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(COURSE_NAME);
        searchPage.clickButton(Buttons.Search);
        searchPage.openFirstSearchResult();
    }
}
