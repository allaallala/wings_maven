package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Create course, search by one parameter and check, that course is displayed.
 */

@TestCase(id = "WINGS-10920")
public class TC_17_17_Training_Courses_Search_By_One_Parameter extends BaseWingsTest {
    private String status = "Active";

    public void main() {
        info("Precondition: Create some Courses");
        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};
        CourseSteps.createCourse(courseDetails[0], courseDetails[1], courseDetails[2], Roles.ADMIN);

        logStep("Log into the system as Staff or Executive or admin and open Search Course Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Fill out any search criteria fields with the data of existing Training Course (all the fields one by one)");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(courseDetails[0]);
        searchPage.clickButton(Buttons.Search);

        logResult("The suitable Training Courses are shown in the Search Results form");
        CustomAssertion.softAssertContains(searchPage.getCourseNameText(), courseDetails[0], "Incorrect course name in the search result table");
        CustomAssertion.softAssertEquals(searchPage.getCourseStatusText(), status, "Incorrect status in the search result table");
    }

}
