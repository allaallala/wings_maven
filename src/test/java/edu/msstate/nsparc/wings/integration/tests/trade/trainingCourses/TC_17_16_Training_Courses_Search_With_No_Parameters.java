package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Created by a.vnuchko on 08.07.2015.
 * Create training course, check, that all records are displayed on the page.
 */

@TestCase(id = "WINGS-10919")
public class TC_17_16_Training_Courses_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {
        String regExp = "\\d{1,}?,?\\d{2,}";


        info("Precondition: Create some Courses");
        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};
        CourseSteps.createCourse(courseDetails[0], courseDetails[1], courseDetails[2], Roles.ADMIN);

        logStep("Log into the system as Staff or Executive or admin and open Search Course Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Click the [Search] button (Don't fill out any parameter)");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.clickButton(Buttons.Search);

        logResult("All the Training Courses are shown in the Search Results form");

        int trainingProviderCount = EmployerSqlFunctions.getTradeTrainingCount();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), regExp);
        Assert.assertEquals("Incorrect quantity of training providers records on the search page",
                String.valueOf(trainingProviderCount), recordsCount.replace(",", Constants.EMPTY));
    }
}
