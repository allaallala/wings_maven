package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10506")
public class TC_01_07_Course_Search_All_Active_And_Inactive extends BaseWingsTest {

    private static final String ERROR_MESSAGE = "Nothing found to display.";


    // sub-task WINGS-2397
    // sub-task WINGS-2550
    public void main() {
        logStep("Log in to WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Advanced->Training->Courses->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

        logStep("Select Status = Inactive");
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.clickActiveInactive(false);
        trainingCourseSearchForm.clickButton(Buttons.Search);
        CustomAssertion.softTrue(ERROR_MESSAGE, !(new SearchResultsForm().getTableContentText().contains(ERROR_MESSAGE)));
        String inactiveRecords = trainingCourseSearchForm.getNumberOfRecordsFound();
        
        logStep("Select Status = Active");
        trainingCourseSearchForm.clickActiveInactive(true);
        trainingCourseSearchForm.clickButton(Buttons.Search);
        CustomAssertion.softTrue(ERROR_MESSAGE, !(new SearchResultsForm().getTableContentText().contains(ERROR_MESSAGE)));
        String activeRecords = trainingCourseSearchForm.getNumberOfRecordsFound();
        
        logStep("Check number of active and inactive records with database");
        trainingCourseSearchForm.compareWithDBInactiveRecordsNumber(inactiveRecords, activeRecords);
    }
}
