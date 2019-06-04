package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of ataa/rtaa enrollments modules of Trade.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11024")
public class TC_22_03_02_Trade_Atrt_Enroll_Details_Search_Pages extends BaseWingsTest {
    private String type = "ATRTEnrollments";

    public void main() {
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.RTAA);
        WIAEnrollment wia = new WIAEnrollment(atrt.getParticipant());
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(atrt, Roles.TRADEDIRECTOR);

        CIPSteps.validateCIP(type, Constants.TRADE, atrt.getParticipant(), wia, atrt.getTradeEnrollment());
    }
}
