package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10463")
public class TC_06_10_Courses_Create extends BaseWingsTest {


    public void main() {
        String courseName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
        String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        AdvancedSqlFunctions.resetCourseCode(courseCode);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.PCADMIN, WingsTopMenu.WingsStaffMenuItem.A_COURSES, Popup.Create);

        logStep("Fill out fields in creation form");
        TrainingCourseCreationForm trainingCourseCreationForm = new TrainingCourseCreationForm();
        trainingCourseCreationForm.fillFieldsAndCreate(courseName, courseCode, jobTitle);

        logStep("Check training course participantSSDetails data");
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        trainingCourseDetailsForm.validateTrainingCourseDetails(courseName, courseCode, jobTitle, Roles.PCADMIN);
    }
}
