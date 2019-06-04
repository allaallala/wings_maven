package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10814")
public class TC_13_09_Training_Waivers_Expiration_Date_Displaying extends BaseWingsTest {

    public void main() {

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        logStep("Select each STATUS and check 'Expiration Date' displaying according status");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        trainingWaiverSearchForm.selectStatusAndCheckExpirationDate();
    }
}
