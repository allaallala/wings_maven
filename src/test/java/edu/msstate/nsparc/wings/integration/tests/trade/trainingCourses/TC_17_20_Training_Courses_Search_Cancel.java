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
import webdriver.Browser;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Fill out [Search Course Form], click [Cancel] button and check, that [Staff Home Form Screen] is shown.
 */

@TestCase(id = "WINGS-10923")
public class TC_17_20_Training_Courses_Search_Cancel extends BaseWingsTest {

    public void main(){


        logStep("Log into the system as Staff or Executive or admin and open Search Course Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));
        searchPage.inputCourseCode(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));

        logStep("Click the [Cancel] button");
        searchPage.clickButton(Buttons.Cancel);

        logResult("The Staff Home Screen is shown");
        Browser.getInstance().waitForPageToLoad();
        //new StaffHomeForm().assertIsOpen();
    }
}
