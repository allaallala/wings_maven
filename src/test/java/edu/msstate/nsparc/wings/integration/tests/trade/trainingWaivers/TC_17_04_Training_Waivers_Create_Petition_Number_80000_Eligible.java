package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 01.07.2015.
 * Create training_waiver (Eligible), Use a Participant with a Trade Enrollment Record which include Petition number more than 80000.
 */

@TestCase(id = "WINGS-10908")
public class TC_17_04_Training_Waivers_Create_Petition_Number_80000_Eligible extends BaseWingsTest {

    public void main(){


        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Input valid participant and select valid trade enrollment");
        TrainingWaiverCreationForm creationPage = new TrainingWaiverCreationForm();
        creationPage.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Select 'Ineligible' radio button in the 'Is this participant eligible?' string");
        creationPage.clickEligible();

        logResult("- The following values available in the 'Reason for Waiver' drop down list: Training will start in 60 days, Funds not available, Health");
        creationPage.selectWaiverReason("Training Will Start in 60 Days");
        creationPage.selectWaiverReason("Health - Training not Possible/Able to Work");
        creationPage.selectWaiverReason("Funds Not Available or at a Reasonable Cost");
    }
}
