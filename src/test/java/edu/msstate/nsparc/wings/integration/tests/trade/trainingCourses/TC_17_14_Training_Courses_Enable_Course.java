package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Created by a.vnuchko on 08.07.2015.
 * Create some course, enable it, check corresponding message and status
 */

@TestCase(id = "WINGS-10917")
public class TC_17_14_Training_Courses_Enable_Course extends TC_17_13_Training_Courses_Disable_Course {
    String status = "Active";

    public void main(){

        String[] courseDetails = {CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH), CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)};

        repeatedSteps(courseDetails[0], courseDetails[1], courseDetails[2]);

        logStep("Click [Enable]");
        TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
        detailsPage.clickEnableDisable(Constants.FALSE);
        detailsPage.spanDisablePresent();
        detailsPage.clickEnableDisable(Constants.TRUE);

        logResult("The Training Course Detail Screen with correct and actual information is shown. " +
                "'Enable' button is available. After enabling the course, its title is displayed without strikethrough. The status is 'Active'.");
        CustomAssertion.softAssertEquals(detailsPage.getCourseNameText(), courseDetails[0], "Incorrect course name");
        CustomAssertion.softAssertEquals(detailsPage.getJobTitleText(), courseDetails[2], "Incorrect job title");
        CustomAssertion.softAssertEquals(detailsPage.getStatusText(), status, "Incorrect status");
        detailsPage.checkEnableDisablePresent(Constants.FALSE);
    }
}
