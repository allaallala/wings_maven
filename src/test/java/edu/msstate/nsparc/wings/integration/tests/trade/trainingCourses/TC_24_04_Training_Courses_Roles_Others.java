package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseEditForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CourseSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check actions, which can be done by the specified user role (All of these, except administrator and trade administrator)
 * Created by a.vnuchko on 05.02.2016.
 */

@TestCase(id = "WINGS-11063")
public class TC_24_04_Training_Courses_Roles_Others extends BaseWingsTest {
    private String courseName;
    private String courseCode;
    private String jobTitle;
    private String mainOccupation;

    public void main() {

        //Role - Project code administrator
        User user = new User(Roles.PCADMIN);
        commonTrainingCoursesSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonTrainingCoursesSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonTrainingCoursesSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonTrainingCoursesSteps(user);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonTrainingCoursesSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonTrainingCoursesSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonTrainingCoursesSteps(user);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonTrainingCoursesSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonTrainingCoursesSteps(user);

        //Role - MDES Manager
        user.setNewUser(Roles.MANAGER);
        commonTrainingCoursesSteps(user);

        //Role - MDES Staff
        user.setNewUser(Roles.STAFF);
        commonTrainingCoursesSteps(user);

        //Role - Intake Staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonTrainingCoursesSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonTrainingCoursesSteps(user);

        //Role - E-verify administrator
        user.setNewUser(Roles.EVERIFY);
        commonTrainingCoursesSteps(user);
    }

    /**
     * Common steps for checking user permissions
     *
     * @param user - current user
     */
    protected void commonTrainingCoursesSteps(User user) {
        //(!) Create training course, if it's possible
        if (user.getTrainCourses().getCourseCreate()) {
            logStep("Create new training course");
            generateCourseData();
            CourseSteps.createCourse(courseName, courseCode, jobTitle, user.getRole());
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality: edit, view, audit.
     *
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");
        logStep("Check View functionality");
        BaseWingsSteps.logInAs(user.getRole());

        if (user.getTrainCourses().getCourseView()) {
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.A_COURSES.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_COURSES);

            //(!) If user can create training course - > he should confirm pop-up window.
            if (user.getTrainCourses().getCourseCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            //(!) If user can view search results.
            TrainingCourseSearchForm searchPage = new TrainingCourseSearchForm();
            searchPage.performSearch(courseName, courseCode, Constants.FALSE);


            searchPage.openFirstSearchResult();
            TrainingCourseDetailsForm detailsPage = new TrainingCourseDetailsForm();
            detailsPage.validateTrainingCourseDetails(courseName, courseCode, jobTitle, user.getRole());
            detailsPage.validateOsocOccupations();

            //(!) Check, that [Audit], [Edit]
            logStep("Check [Audit], [Edit] buttons are present or not on the page");
            detailsPage.checkButtonsPresent(user.getTrainCourses().getCourseViewEditButton(), user.getTrainCourses().getCourseViewAuditButton());

            //(!) Check edit functionality
            if (user.getTrainCourses().getCourseEdit()) {
                logStep("Check edit functionality");
                generateCourseData(); //generate new data to edit.
                AdvancedSqlFunctions.resetCourseCode(courseCode);
                detailsPage.clickButton(Buttons.Edit);
                TrainingCourseEditForm trainingCourseEditForm = new TrainingCourseEditForm();
                trainingCourseEditForm.editTrainingCourseDetails(courseName, courseCode, jobTitle, mainOccupation, user.getRole());
                trainingCourseEditForm.clickButton(Buttons.Save);
                detailsPage.validateTrainingCourseDetails(courseName, courseCode, jobTitle, user.getRole());
                detailsPage.validateOsocOccupations();
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.A_COURSES, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }


    /**
     * Generate data for the specified course
     */
    protected void generateCourseData() {
        courseName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        courseCode = CommonFunctions.getRandomLiteralCode(Constants.FAMILY_LENGTH);
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        mainOccupation = "Computer Occupations";
    }
}
