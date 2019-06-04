package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10639")
public class TC_06_01_Courses_Search extends BaseWingsTest {


    public void main() {


        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Search);

        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.clickButton(Buttons.Search);
        trainingCourseSearchForm.clickCourseName();
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        String [] courseDetails = trainingCourseDetailsForm.getTrainingCourseDetails();

        logStep("Click on 'Courses' link");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);
        logStep("Click 'Search' in the pop-up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill out search form and click [Search]");
        trainingCourseSearchForm.fillSearchCriteria(courseDetails[0], courseDetails[1]);
        trainingCourseSearchForm.clickButton(Buttons.Search);
        logStep("Validate search results");
        assertTrue("Assert Training Course Search Results Failed",
                trainingCourseSearchForm.validateSearchResults(courseDetails[0], courseDetails[1], courseDetails[2]));
    }
}
