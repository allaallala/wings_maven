package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10544")
public class TC_02_06_Participant_Check_45_Day_Back_Rule_Enrollment extends BaseWingsTest {

    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String CREATION_DATE = "10/10/2004";


    //Bug WINGS-2369 , sub-task WINGS-2438
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, serviceName, false, false);
        User staff = new User(Roles.STAFF);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, serviceName, Constants.COMPLETED, CREATION_DATE);
    }
}
