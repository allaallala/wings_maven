package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Create some participant profile. One has a WIA assessment. Merge this participant with one, who has no
 * assessment and check, that it's merged.
 * Created by a.vnuchko on 11.04.2017.
 */

@TestCase(id = "WINGS-11273")
public class TC_39_07_Participant_Merge_Assessment_WIA extends TC_39_06_Participant_Merge_Assessment_WP {

    public void main() {
        info("Precondition: there are some Participant Profiles. One of them has: - WIA Assessment");
        info("Create participant to discard");
        Participant discardParticipant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        AdvancedSqlFunctions.resetAccount(AccountUtils.getParticipantAccount());
        Participant keepParticipant = new Participant(AccountUtils.getParticipantAccount());

        info("Create participant to keep");
        ParticipantCreationSteps.createParticipantDriver(keepParticipant, Boolean.TRUE, Boolean.FALSE);
        keepParticipant.setSsn(discardParticipant.getSsn());
        ParticipantSqlFunctions.setParticipantSSN(keepParticipant, discardParticipant.getSsn());

        info("Create WIA Assessment");
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), discardParticipant, Constants.FALSE, Constants.FALSE);
        Assessment assessment = new Assessment(discardParticipant, Constants.WIOA.toUpperCase());
        User admin = new User(Roles.ADMIN);
        AssessmentSteps.createAssessment(admin, assessment);

        repeatedSteps(discardParticipant, keepParticipant, assessment);
    }
}
