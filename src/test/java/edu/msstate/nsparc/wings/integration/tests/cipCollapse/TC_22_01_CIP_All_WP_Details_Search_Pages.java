package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of different modules of Wagner-Peyser.
 * Created by a.vnuchko on 29.09.2015.
 */

@TestCase(id = "WINGS-11020")
public class TC_22_01_CIP_All_WP_Details_Search_Pages extends BaseWingsTest {

    public void main() {
        String module = "wp";
        String[] wpModules = {"Everify", "Referrals", "ReferralResulting", "PrtpServiceEnrollment", "IEP", "AssessmentWP"};

        info("Precondition: create new job order, referral, assessment, individual employment plan");
        Participant participant = new Participant();
        Assessment assessment = new Assessment(participant, "Wagner-Peyser");
        IndividualEmploymentPlan iep = new IndividualEmploymentPlan(participant);
        WIAEnrollment wia = new WIAEnrollment(participant);
        TradeEnrollment trl = new TradeEnrollment();
        JobOrder order = new JobOrder(AccountUtils.getEmployerAccount());
        User staff = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, order, staff);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);

        CallInSteps.createCallIn(participant, order, Roles.STAFF);

        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), iep);
        AssessmentSteps.createAssessment(staff, assessment);

        CIPSteps.validateCIP(wpModules[1], module, participant, wia, trl);

        CIPSteps.validateCIP(wpModules[0], module, participant, wia, trl);
        CIPSteps.validateCIP(wpModules[2], module, participant, wia, trl);
        CIPSteps.validateCIP(wpModules[3], module, participant, wia, trl);
        CIPSteps.validateCIP(wpModules[4], module, participant, wia, trl);
        CIPSteps.validateCIP(wpModules[5], module, participant, wia, trl);

    }
}
