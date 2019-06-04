package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Creating of ineligible participant with invalid Issue Date and ineligible reason
 * Created by a.vnuchko on 01.07.2015.
 */

@TestCase(id = "WINGS-10906")
public class TC_17_02_Training_Waivers_Create_Ineligible_Participant_With_Invalid_Issue_Date_And_Reason extends BaseWingsTest {

    public void main(){

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Input valid participant and select valid trade enrollment");
        TrainingWaiverCreationForm waiverPage = new TrainingWaiverCreationForm();
        waiverPage.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Select 'Ineligible' radio button in the 'Is this participant eligible?' string");
        waiverPage.clickIneligible();

        logStep("Input Issue Date more than Latest Possible Issue Date, but less than present date");
        waiverPage.inputIssueDate(CommonFunctions.getDaysAgoDate(1));

        logStep("Mark 'Deadline for getting waiver has passed.' check box");
        waiverPage.clickDeadlinePassed();

        logStep("Click the [Create] button");
        waiverPage.clickButton(Buttons.Create);
        waiverPage.clickButton(Buttons.Done);
        BaseNavigationSteps.logout();

        logResult("A new Training Waiver was created and contains the same data you have entered");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);
        TrainingWaiverSearchForm searchPage = new TrainingWaiverSearchForm();
        searchPage.selectParticipant(tradeEnrollment.getParticipant());
        searchPage.selectInactivePetition(tradeEnrollment.getPetition());
        searchPage.clickButton(Buttons.Search);
        searchPage.validateSearchResult(tradeEnrollment, CommonFunctions.getDaysAgoDate(1));
    }
}
