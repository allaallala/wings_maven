package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of trade training module of Trade.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11025")
public class TC_22_03_03_CIP_Trade_Training_Details_Search_Pages extends BaseWingsTest {
    String type = "TradeTraining";

    public void main() {
        TradeTraining trd = new TradeTraining();
        WIAEnrollment wia = new WIAEnrollment(trd.getTradeEnrollment().getParticipant());
        TrainingSteps.createTradeTraining(trd, Roles.STAFF, Roles.ADMIN);

        CIPSteps.validateCIP(type, Constants.TRADE, trd.getTradeEnrollment().getParticipant(), wia, trd.getTradeEnrollment());
    }
}
