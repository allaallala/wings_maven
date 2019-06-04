package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10811")
public class TC_13_06_Training_Waivers_Clear_Search_Form extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Staff and open Training Waiver search form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        logStep("Fill out search criteria fields");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        trainingWaiverSearchForm.selectParticipant(trainingWaiver.getTradeEnrollment().getParticipant());
        trainingWaiverSearchForm.selectInactivePetition(trainingWaiver.getTradeEnrollment().getPetition());
        trainingWaiverSearchForm.selectAnyAvailableServiceCenter();
        trainingWaiverSearchForm.inputStatusIssuedDate(CommonFunctions.getCurrentDate());

        logStep("Click 'Clear Form' button");
        trainingWaiverSearchForm.clickButton(Buttons.Clear);

        logStep("Validate search fields are cleared");
        trainingWaiverSearchForm.validateAllFieldsCleared();
    }
}
