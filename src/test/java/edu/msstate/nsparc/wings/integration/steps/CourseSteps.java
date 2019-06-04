package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseCreationForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;

public class CourseSteps extends BaseWingsSteps {

    public static void createCourse(String courseName, String courseCode, String jobTitle, Roles role) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Create);
        AdvancedSqlFunctions.resetCourseCode(courseCode);
        TrainingCourseCreationForm trainingCourseCreationForm = new TrainingCourseCreationForm();
        trainingCourseCreationForm.fillFieldsAndCreate(courseName, courseCode, jobTitle);
        trainingCourseCreationForm.clickButton(Buttons.Done);

        BaseNavigationSteps.logout();
    }
}
