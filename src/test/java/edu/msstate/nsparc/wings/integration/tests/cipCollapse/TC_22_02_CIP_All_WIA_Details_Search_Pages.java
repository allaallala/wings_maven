package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of different modules of WIA.
 * Created by a.vnuchko on 30.09.2015.
 */

@TestCase(id = "WINGS-11022")
public class TC_22_02_CIP_All_WIA_Details_Search_Pages extends BaseWingsTest {

    public void main() {

        String module = "wia";
        String[] wiaModules = {"WIAEnrollment", "WIATraining", "AssessmentWIA"};


        info("Precondition: create new participant, assessment(WIA), wia enrollment (youth)");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        Assessment assm = new Assessment(participant, "WIA");
        TrainingProvider trainingProvider = new TrainingProvider();
        WIAEnrollment wia = new WIAEnrollment(participant);
        TradeEnrollment trl = new TradeEnrollment();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        TrainingSteps.createWIATraining(participant, trainingProvider);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assm);
        CIPSteps.validateCIP(wiaModules[2], module, participant, wia, trl);
        CIPSteps.validateCIP(wiaModules[0], module, participant, wia, trl);
        CIPSteps.validateCIP(wiaModules[1], module, participant, wia, trl);
    }
}
