package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInDetailForm;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInEditForm;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.CallIns;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check functionaly call-ins using different user roles: administrator, area director, manager.
 * Created by a.vnuchko on 30.06.2016.
 */

@TestCase(id = "WINGS-11090")
public class TC_25_14_Call_Ins_Roles_Admin_AD_Manager extends BaseWingsTest {
    private CallIns callIns;
    Participant participant;
    private String newResult = "Spoke to Participant";

    public void main() {

        //Role - area director
        User user = new User(Roles.AREADIRECTOR);
        commonStepsCallIns(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonStepsCallIns(user);

        user.setNewUser(Roles.ADMIN);
        commonStepsCallIns(user);
    }

    /**
     * User user
     *
     * @param user - current user.
     */
    protected void commonStepsCallIns(User user) {
        //(!) If user has permissions to create call-ins
        if (user.getCallIns().getCallinCreate()) {
            logStep("Create Call-In");
            AccountUtils.init();
            callIns = new CallIns(user.getRole());
            participant = new Participant();
            ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
            ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
            EmployerSteps.createEmployer(callIns.getJobOrder().getEmployer(), Roles.STAFF);
            JobOrderSteps.createJobOrder(callIns.getJobOrder().getEmployer(), callIns.getJobOrder().getJobTitle());
            CallInSteps.createCallIn(participant, callIns.getJobOrder(), user.getRole());
        }

        checkOtherFunctionality(user);
    }

    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");

        BaseWingsSteps.logInAs(user.getRole());

        if (user.getCallIns().getCallinView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS);

            //(!) If user can create Call-in - > he should confirm pop-up window.
            if (user.getCallIns().getCallinCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            CallInSearchForm searchPage = new CallInSearchForm();
            searchPage.performSearch(callIns.getJobOrder(), participant);
            CallInDetailForm detailPage = new CallInDetailForm();


            //(!) Check buttons on the detail Everify page.
            logStep("Check [Audit], [Edit] buttons on the page");
            detailPage.validateCallInData(participant, callIns.getJobOrder(), callIns.getResult());
            detailPage.checkButtonsPresent(user.getCallIns().getCallinViewEdit(), user.getCallIns().getCallinViewAudit());

            //(!) Check edit functionality
            if (user.getCallIns().getCallinEdit()) {
                logStep("Check Edit functionality");
                callIns.setResult(newResult);
                detailPage.clickButton(Buttons.Edit);

                CallInEditForm editPage = new CallInEditForm();
                editPage.editResult(newResult);
                detailPage.validateCallInData(participant, callIns.getJobOrder(), callIns.getResult());
            }

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
