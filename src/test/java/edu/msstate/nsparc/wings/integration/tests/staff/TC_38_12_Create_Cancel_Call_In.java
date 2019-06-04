package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.tests.callIn.TC_07_01_Create_Call_In;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There are some qualified participants.
 * Created by a.vnuchko on 10.03.2017.
 */

@TestCase(id = "WINGS-11252")
public class TC_38_12_Create_Cancel_Call_In extends TC_07_01_Create_Call_In {
    private String answer = "Send Email";

    public void main(){
        info("Precondition: There are qualified  participants for some open jobs");
        JobOrder jobOrder = initData(Constants.FALSE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select All from radio buttons Show->Search");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);
        callInCreationForm.selectRbAll();

        logStep("Select some items from dropdown in qualified participants");
        callInCreationForm.clickButton(Buttons.Search);
        callInCreationForm.selectFirstTwoContacts(answer);

        logStep("Click [Cancel]");
        callInCreationForm.clickButton(Buttons.Cancel);

        logResult("Home page is opened");
        new StaffHomeForm();
    }
}
