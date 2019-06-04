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


@TestCase(id = "WINGS-10681")
public class TC_07_13_Create_Call_In_Distance_Non_Veteran extends TC_07_01_Create_Call_In {

    public void main() {
        divideMessage("Initialize required test data");
        JobOrder jobOrder = initData(Constants.FALSE, Constants.TRUE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select Job Order");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);

        logStep("Select 'Only Non-Veterans' from radio buttons Show");
        callInCreationForm.selectVeterans(Constants.FALSE);

        logStep("Select some DISTANCE->Search");
        callInCreationForm.selectDistance(DISTANCE);
        callInCreationForm.clickButton(Buttons.Search);

        repeatedSteps(jobOrder);
    }
}
