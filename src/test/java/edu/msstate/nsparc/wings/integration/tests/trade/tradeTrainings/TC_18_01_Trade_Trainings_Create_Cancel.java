package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 09.07.2015.
 * Fill out [Creation Form], click [Cancel] button, check that trade training is not created, [Staff Home Form] is shown.
 */

@TestCase(id = "WINGS-10924")
public class TC_18_01_Trade_Trainings_Create_Cancel extends BaseWingsTest {
    String trainingResult = "Dropped Out/Quit";

    public void main() {

        Participant partip = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partip, Boolean.TRUE, Boolean.FALSE);

        logStep("Login as a Admin and open Creation Form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Fill out some fields");
        TradeTrainingCreateForm creationPage = new TradeTrainingCreateForm();
        creationPage.selectTrainingResult(trainingResult);
        creationPage.selectParticipant(partip.getFirstName(), partip.getLastName());

        logStep("Click the [Cancel] button");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown.  a new Trade Training isn't created ");
        //new StaffHomeForm().assertIsOpen();
        BaseNavigationSteps.logout();
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Search);
        TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
        searchPage.selectParticipant(partip.getFirstName(), partip.getLastName());
        searchPage.selectTrainingResult(trainingResult);
        searchPage.clickButton(Buttons.Search);
        searchPage.noSearchResults();
    }
}
