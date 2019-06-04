package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of trade enrollments modules of Trade.
 * Created by a.vnuchko on 29.09.2015.
 */

@TestCase(id = "WINGS-11023")
public class TC_22_03_01_CIP_Trade_Enroll_Trade_Details_Search_Pages extends BaseWingsTest {
    private static final String MODULE_NAME = "trade";
    private String[] tradeModules = {"TradeEnrollments"};

    public void main() {
        TradeEnrollment tradeEnrl = new TradeEnrollment();
        WIAEnrollment wia = new WIAEnrollment(tradeEnrl.getParticipant());
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrl, Roles.ADMIN);

        CIPSteps.validateCIP(tradeModules[0], MODULE_NAME, tradeEnrl.getParticipant(), wia, tradeEnrl);
    }
}
