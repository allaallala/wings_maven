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
 * Created by a.vnuchko on 01.07.2015.
 * Creating of ineligible participant with ineligible reasons (Create Training Waiver:- with a few marked check boxes)
 */

@TestCase(id = "WINGS-10903")
public class TC_17_01_Training_Waivers_Create_Ineligible_Participant_With_Ineligible_Reasons extends BaseWingsTest {
    String type = "some";
    String other = "other";

    public void main(){
        makeActions(type);
    }

    /**
     * Make actions
     * @param type - training waiver type
     */
    public void makeActions(String type){
        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Input valid participant and select valid trade enrollment");
        TrainingWaiverCreationForm waiverPage = new TrainingWaiverCreationForm();
        waiverPage.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Select 'Ineligible' radio button in the 'Is this participant eligible?' string");
        waiverPage.clickIneligible();

        logStep("Create Training Waiver:- with a few marked check boxes");
        createType(type);

        logResult("A new Training Waiver was created and contains the same data you have entered");
        finalAction(tradeEnrollment);
    }

    /**
     * Create some training waiver using different types (few checkbox, all checkbox, without checkbox chosen)
     * @param type type
     */
    private void createType(String type){
        TrainingWaiverCreationForm waiverPage = new TrainingWaiverCreationForm();
        switch (type){
            case "some":
                waiverPage.selectSomeCheckboxType();
                break;
            case "all":
                waiverPage.selectAllCheckboxType();
                break;
            case "other":
                waiverPage.inputOtherReason(other);
                break;
            default: break;
        }
        waiverPage.inputIssueDate(CommonFunctions.getCurrentDate());
        waiverPage.clickButton(Buttons.Create);
        waiverPage.clickButton(Buttons.Done);
        BaseNavigationSteps.logout();
    }

    /**
     * some steps to check, that new training waiver is created.
     * @param enrl trade enrollment
     */
    private void finalAction(TradeEnrollment enrl) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);
        TrainingWaiverSearchForm searchPage = new TrainingWaiverSearchForm();
        searchPage.selectParticipant(enrl.getParticipant());
        searchPage.selectInactivePetition(enrl.getPetition());
        searchPage.clickButton(Buttons.Search);
        searchPage.validateSearchResult(enrl, CommonFunctions.getCurrentDate());
        BaseNavigationSteps.logout();
    }
}
