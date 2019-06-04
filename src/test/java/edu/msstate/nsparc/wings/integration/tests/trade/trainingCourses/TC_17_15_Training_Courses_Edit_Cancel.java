package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseEditForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 08.07.2015.
 * Create some course, edit information and click [Cancel] button. Check, that information is not changed.
 */

@TestCase(id = "WINGS-10918")
public class TC_17_15_Training_Courses_Edit_Cancel extends TC_17_13_Training_Courses_Disable_Course {

    public void main(){

        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};
        String[] courseDetails2 = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};

        repeatedSteps(courseDetails[0], courseDetails[1], courseDetails[2]);

        logStep("Click the [Edit] button");
        TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        TrainingCourseEditForm editPage = new TrainingCourseEditForm();
        editPage.inputCourseName(courseDetails2[0]);
        editPage.inputJobTitle(courseDetails2[2]);

        logStep("Click the [Cancel] button");
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The Training Course Detail Screen is shown, the changes are not saved");
        CustomAssertion.softAssertEquals(detailsPage.getCourseNameText(), courseDetails[0], "Incorrect course name");
        CustomAssertion.softAssertEquals(detailsPage.getJobTitleText(), courseDetails[2], "Incorrect job title");
    }
}
