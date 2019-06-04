package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.YouthGoalsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of Youth Goal module of WIA.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11021")
public class TC_22_02_01_CIP_Yoth_WIA_Details_Seach_Pages extends BaseWingsTest {

    public void main() {
        String module = "wia";
        String[] wiaModules = {"YouthGoals"};

        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        WIAEnrollment wia = new WIAEnrollment(participant);
        TradeEnrollment trl = new TradeEnrollment();
        User user = new User(Roles.ADMIN);
        YouthGoalsSteps.createYouthGoals(participant, user);
        CIPSteps.validateCIP(wiaModules[0], module, participant, wia, trl);
    }
}
