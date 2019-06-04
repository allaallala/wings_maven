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


@TestCase(id = "WINGS-10682")
public class TC_07_14_Create_Call_In_Non_Veteran_Not_Driver extends TC_07_01_Create_Call_In {

    public void main() {
        divideMessage("Initialize required test data");
        JobOrder jobOrder = initData(Constants.FALSE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select 'Only Non-Veterans' from radio buttons Show");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);
        callInCreationForm.selectVeterans(Constants.FALSE);

        logStep("Participants not meeting Drivers License Requirements ->Search");
        callInCreationForm.selectDriverLicenseRequirement(Constants.TRUE);
        callInCreationForm.clickButton(Buttons.Search);

        repeatedSteps(jobOrder);
    }
}
