package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10809")
public class TC_13_04_Training_Waivers_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {
        logStep("Log in as Staff and open Training Waiver search form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        logStep("Click Search with no parameters entered");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        trainingWaiverSearchForm.clickButton(Buttons.Search);

        logStep("Validate at least one Training Waiver was found");
        new SearchResultsForm().assertFirstSearchResultLinkPresent();
    }
}
