package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check the functionality of the Assessment module. User roles: administrator, area director, manager, staff.
 * Created by a.vnuchko on 24.06.2016.
 */

@TestCase(id = "WINGS-10966")
public class TC_19_21_Roles_Admin_AD_Manager_Staff extends TC_19_01_Assessment_Create_Cancel {
    private Assessment assessment;
    private String assmFunctionalArea = "Writing";
    private String assmType = "CASAS";
    private String assmScore = "90";

    public void main() {

        //Role administrator
        User user = new User(Roles.ADMIN);
        commonAssmSteps(user);

        //Role area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonAssmSteps(user);

        //Role manager
        user.setNewUser(Roles.MANAGER);
        commonAssmSteps(user);

        //Role staff
        user.setNewUser(Roles.STAFF);
        commonAssmSteps(user);
    }

    /**
     * Common steps for checking user permissions. Assessment module.
     *
     * @param user - current user
     */
    protected void commonAssmSteps(User user) {
        //(!) Create new assessment if possible
        if (user.getAssessment().getAsmCreate()) {
            logStep("Create a new assessment");
            AccountUtils.init();
            assessment = new Assessment(user.getRole());
            ParticipantCreationSteps.createParticipantDriver(assessment.getParticipant(), Boolean.TRUE, Boolean.FALSE);
            if (Constants.WIOA.equalsIgnoreCase(assessment.getProgram())) {
                WiaEnrollmentSteps.createWIAEnrollmentByExternal(user, assessment.getParticipant(), Constants.FALSE, Constants.FALSE);
            }
            AssessmentSteps.createAssessment(user, assessment);
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check following:
     * - view functionality (if possible)
     * - check [Audit] and [Edit] buttons
     * - edit functionality (if possible)
     * - delete functionality (if possible).
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");

        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view assessment and make other things.
        if (user.getAssessment().getAsmView()) {
            logStep("Check view functionality");

            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);

            //(!) If user can create an assessment -> he should confirm pop-up window.
            if (user.getAssessment().getAsmCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            AssessmentSearchForm searchPage = new AssessmentSearchForm();
            searchPage.performSearchAndOpen(assessment);
            AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
            detailsPage.validateAssessmentInformation(assessment);

            logStep("Check buttons present on the page");
            detailsPage.checkButtonsPresent(user.getAssessment().getAsmViewEditButton(), user.getAssessment().getAsmViewAuditButton());

            //(!) If user can edit assessment
            if (user.getAssessment().getAsmEdit()) {
                logStep("Check edit functionality");
                assessment.setFunctionalArea(assmFunctionalArea);
                assessment.setScore(assmScore);
                assessment.setType(assmType);
                detailsPage.clickButton(Buttons.Edit);
                AssessmentEditForm editPage = new AssessmentEditForm();
                editPage.editAssessment(assmFunctionalArea, assmType);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateAssessmentInformation(assessment);
            }

            //(!) If user can delete assessment
            if (user.getAssessment().getAsmDelete()) {
                logStep("Check delete functionality");
                detailsPage.deleteAssessment();
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
