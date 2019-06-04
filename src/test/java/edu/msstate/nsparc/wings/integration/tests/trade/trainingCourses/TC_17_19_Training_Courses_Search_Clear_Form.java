package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Fill out the search form, clear it and check that all entries are cleared.
 */

@TestCase(id = "WINGS-10922")
public class TC_17_19_Training_Courses_Search_Clear_Form extends BaseWingsTest {

    public void main(){


        logStep("Log into the system as Staff or Executive or admin and open Search Course Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));
        searchPage.inputCourseCode(CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH));

        logStep("Click the [Clear Form] button");
        searchPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        CustomAssertion.softAssertEquals(searchPage.getCourseName(), Constants.EMPTY, "The field course name is not cleared.");
        CustomAssertion.softAssertEquals(searchPage.getCourseText(), Constants.EMPTY, "The field course code is not cleared.");
    }
}
