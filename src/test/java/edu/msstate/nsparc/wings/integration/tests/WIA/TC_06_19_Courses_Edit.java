package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseEditForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10662")
public class TC_06_19_Courses_Edit extends BaseWingsTest {

    public void main() {
        String courseName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        String courseCode = CommonFunctions.getRandomAlphanumericalCode(Constants.FAMILY_LENGTH);
        String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        String mainOccupation = "Business Occupations";
        AdvancedSqlFunctions.resetCourseCode(courseCode);
        TC_06_10_Courses_Create createCourse = new TC_06_10_Courses_Create();
        createCourse.main();

        logStep("Click on Edit button");
        TrainingCourseDetailsForm trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        trainingCourseDetailsForm.clickButton(Buttons.Edit);

        logStep("Change Training Course participantSSDetails");
        TrainingCourseEditForm trainingCourseEditForm = new TrainingCourseEditForm();
        trainingCourseEditForm.editTrainingCourseDetails(courseName, courseCode, jobTitle, mainOccupation, Roles.PCADMIN);
        trainingCourseEditForm.clickButton(Buttons.Save);

        logStep("Validate that changes are saved");
        trainingCourseDetailsForm = new TrainingCourseDetailsForm();
        trainingCourseDetailsForm.validateTrainingCourseDetails(courseName, courseCode, jobTitle, Roles.PCADMIN);

        logEnd();
    }
}
