package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;
import framework.CommonFunctions;


/**
 * Fill out training courses create form and click [Cancel] button
 * Created by a.vnuchko on 02.07.2015.
 */

@TestCase(id = "WINGS-10913")
public class TC_17_10_Training_Courses_Create_Cancel extends BaseWingsTest {

    public void main() {
        String courseName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);

        logStep("Log into the system as Staff");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced -> Training -> Courses");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

        logStep("Choose 'Create' on the pop up");
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Fill out some fields");
        TrainingCourseCreationForm creationPage = new TrainingCourseCreationForm();
        creationPage.inputCourseName(courseName);
        creationPage.fillOtherFields();

        logStep("Click the [Cancel] button");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown.  a new Training Course isn't created ");
        Browser.getInstance().waitForPageToLoad();
        //new StaffHomeForm().assertIsOpen();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        BaseWingsSteps.popClick(Popup.Search);
        TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
        searchPage.inputCourseName(courseName);
        searchPage.clickButton(Buttons.Search);
        searchPage.noSearchResults();
    }
}
