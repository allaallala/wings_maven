package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Create some participant profile. One has a Trade assessment. Merge this participant with one, who has no
 * assessment and check, that it's merged.
 * Created by a.vnuchko on 12.04.2017.
 */

@TestCase(id = "WINGS-11274")
public class TC_39_08_Participant_Merge_Assessment_Trade extends TC_39_06_Participant_Merge_Assessment_WP {

    public void main() {
        info("Precondition: there are some Participant Profiles. One of them has: - Trade Assessment");
        info("Create a new trade enrollment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        info("Create participant to keep");
        AdvancedSqlFunctions.resetAccount(AccountUtils.getParticipantAccount());
        Participant keepParticipant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Constants.TRUE, Constants.FALSE);
        keepParticipant.setSsn(tradeEnrollment.getParticipant().getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, tradeEnrollment.getParticipant().getSsn());

        info("Create trade assessment");
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        User admin = new User(Roles.ADMIN);
        AssessmentSteps.createAssessment(admin, assessment);

        repeatedSteps(tradeEnrollment.getParticipant(), keepParticipant, assessment);
    }
}
