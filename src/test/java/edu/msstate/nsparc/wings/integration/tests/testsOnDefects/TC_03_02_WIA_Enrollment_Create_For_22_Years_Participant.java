package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10560")
public class TC_03_02_WIA_Enrollment_Create_For_22_Years_Participant extends BaseWingsTest {

    String date = CommonFunctions.getDaysAgoDate(Constants.FAMILY_LENGTH);
    String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);

    private static final Integer YEARS_PARTICIPANT = 22;


    //Bug WINGS-2504 , sub-task WINGS-2543
    public void main() {
        logStep("Create participant account with needed date of birth");
        Participant participant = new Participant();
        participant.getParticipantBioInfo().setDateOfBirth(CommonFunctions.getDaysAndYearsAgoDate(1, YEARS_PARTICIPANT));
        participant.setParticipantAccount(AccountUtils.getParticipantAccount());

        logStep("Create WIA Enrollment for created participant");
        WiaEnrollmentSteps.createWIAEnrollment(participant, true, date,contactPerson, contactPhone, relation);

        logEnd();
    }
}
