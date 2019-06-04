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
 * Create training_waiver (Ineligible), Use a Participant with a Trade Enrollment Record which include Petition number more than 80000.
 */

@TestCase(id = "WINGS-10907")
public class TC_17_03_Training_Waivers_Create_Petition_Number_80000_Ineligible extends BaseWingsTest {

    public void main(){


        info("Precondition: create new tradeEnrollment");
        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Input valid participant and select valid trade enrollment");
        TrainingWaiverCreationForm creationPage = new TrainingWaiverCreationForm();
        creationPage.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Select 'Ineligible' radio button in the 'Is this participant eligible?' string");
        creationPage.clickIneligible();

        logResult(" - Check box 'Participant worked less than 23 weeks at $30 or more per week.' displayed as one of the ineligible reasons. ");
        creationPage.checkWorkedLessPresent();
    }
}
