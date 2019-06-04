package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of training waiver module of Trade.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11026")
public class TC_22_03_04_Trade_Training_Waiver_Details_Seach_Pages extends BaseWingsTest {
    String type = "TrainingWaivers";

    public void main() {
        TrainingWaiver trWaiver = new TrainingWaiver();
        TrainingSteps.createTrainingWaiver(trWaiver);
        WIAEnrollment wia = new WIAEnrollment(trWaiver.getTradeEnrollment().getParticipant());

        CIPSteps.validateCIP(type, Constants.TRADE, trWaiver.getTradeEnrollment().getParticipant(), wia, trWaiver.getTradeEnrollment());

    }
}
