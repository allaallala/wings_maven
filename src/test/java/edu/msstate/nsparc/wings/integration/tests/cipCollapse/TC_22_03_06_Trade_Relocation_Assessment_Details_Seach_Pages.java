package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of relocation and assessment modules of Trade.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11028")
public class TC_22_03_06_Trade_Relocation_Assessment_Details_Seach_Pages extends BaseWingsTest {
    private static final String assessmentType = "Trade";
    private String[] tradeModules = {"Relocation", "AssessmentTrade"};
    public void main() {

        Relocation reloc = new Relocation();
        Assessment assm = new Assessment(reloc.getTradeEnrollment().getParticipant(), assessmentType);
        RelocationCreationSteps.createRelocation(reloc, Roles.ADMIN);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assm);
        WIAEnrollment wia = new WIAEnrollment(reloc.getTradeEnrollment().getParticipant());

        CIPSteps.validateCIP(tradeModules[0], Constants.TRADE, reloc.getTradeEnrollment().getParticipant(), wia, reloc.getTradeEnrollment());
        CIPSteps.validateCIP(tradeModules[1], Constants.TRADE, reloc.getTradeEnrollment().getParticipant(), wia, reloc.getTradeEnrollment());
    }
}
