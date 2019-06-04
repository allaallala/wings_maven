package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10815")
public class TC_13_10_Training_Waivers_Reason_Displaying extends BaseWingsTest {

    public void main() {


        logStep("Log in as Staff and open Training Waiver search form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        logStep("Select each Status and check column 'Reason' displaying according status");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        // will be selected each status one by one and checked 'Reason' column
        trainingWaiverSearchForm.selectStatusAndCheckReasonColumn();
    }
}
