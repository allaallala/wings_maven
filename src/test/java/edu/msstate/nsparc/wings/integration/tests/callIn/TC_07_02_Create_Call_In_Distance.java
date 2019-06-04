package edu.msstate.nsparc.wings.integration.tests.callIn;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10666")
public class TC_07_02_Create_Call_In_Distance extends TC_07_01_Create_Call_In {

    public void main() {
        divideMessage("Initialize required test data");
        JobOrder jobOrder = initData(Constants.TRUE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select All from radio buttons Show");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);
        callInCreationForm.selectRbAll();

        logStep("Select some DISTANCE->Search");
        callInCreationForm.selectDistance(DISTANCE);
        callInCreationForm.clickButton(Buttons.Search);

        repeatedSteps(jobOrder);
    }
}
