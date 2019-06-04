package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.programOutcomes.ProgramOutcomesManagementForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Check user permissions for the Program Outcomes module.Roles - administrator, area director, manager, staff, intake staff, trade administrator.
 * Created by a.vnuchko on 04.07.2016.
 */

@TestCase(id = "WINGS-10464")
public class TC_26_01_Program_Outcomes_Roles_Admin_AD_Manager_Staff_IS_TA extends TC_05_10_Program_Outcomes_Create {

    public void main() {

        createProgramOutcome();

        //Role - manager
        User user = new User(Roles.STAFF);
        commonProgramOutcomesSteps(user);

        //Role - manager
        user = new User(Roles.MANAGER);
        commonProgramOutcomesSteps(user);

        //Role - manager
        user = new User(Roles.INTAKESTAFF);
        commonProgramOutcomesSteps(user);

        //Role - manager
        user = new User(Roles.TRADEDIRECTOR);
        commonProgramOutcomesSteps(user);

        //Role - administrator
        user = new User(Roles.ADMIN);
        commonProgramOutcomesSteps(user);

        //Role - area director
        user = new User(Roles.AREADIRECTOR);
        commonProgramOutcomesSteps(user);
    }

    /**
     * Check view, edit, manage functionality of the program outcomes
     *
     * @param user - current user.
     */
    public void commonProgramOutcomesSteps(User user) {
        String oldMethod = "Compared Occupational Codes";
        String newMethod = "Other Appropriate Method";
        logStep("Login to the system as " + user.getRole());
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view program outcomes
        if (user.getPrOutcomes().getPoView()) {
            logStep("Check view functionality");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES);

            ProgramOutcomesDetailsForm detailsForm = new ProgramOutcomesDetailsForm();
            detailsForm.selectParticipantByUser(user, participant);
            detailsForm.selectFirstPartipPeriod();
            detailsForm.validateEmployers(employers);

            //(!) Check [Audit] [Edit] buttons present or not on the page
            logStep("Check buttons [Audit], [Edit] on the page");
            detailsForm.checkButtonsPresent(user.getPrOutcomes().getPoEdit(), user.getPrOutcomes().getPoAudit());

            //(!) Check, if user can edit program outcome (can open management form)
            if (user.getPrOutcomes().getPoViewManage() || user.getPrOutcomes().getPoEdit()) {
                logStep("Edit program method, check that program outcome management form can be opened");
                detailsForm.editProgram();
                ProgramOutcomesManagementForm manageForm = new ProgramOutcomesManagementForm();
                manageForm.selectProgramMethod(newMethod);  //edit method, and check that it is successfully changed
                detailsForm.clickButton(Buttons.Save);
                CustomAssertion.softTrue("Incorrect method quarter on the page.", detailsForm.getMethodFirstQuarter().contains(newMethod));

                detailsForm.editProgram(); //return to the old method. To check edit functionality for another user roles.
                manageForm.selectProgramMethod(oldMethod);
                detailsForm.clickButton(Buttons.Save);
                CustomAssertion.softTrue("Incorrect method quarter on the page.", detailsForm.getMethodFirstQuarter().contains(oldMethod));
            }
        } else {
            logStep("User '" + user.getRole() + "' cannot view program outcomes");
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WIA_PROGRAM_OUTCOMES, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
