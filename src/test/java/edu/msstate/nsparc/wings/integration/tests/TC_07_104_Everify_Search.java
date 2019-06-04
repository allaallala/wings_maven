package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10678")
public class TC_07_104_Everify_Search extends BaseWingsTest {

    private static final String CASE_NUMBER = "12345";

    public void main() {
        info("For E-Verify search we need:");
        info("Verified Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        EverifySteps.openEverifySearchPage(participant, Roles.STAFF, CASE_NUMBER);

        BaseNavigationSteps.logout();
        logEnd();
    }
}
