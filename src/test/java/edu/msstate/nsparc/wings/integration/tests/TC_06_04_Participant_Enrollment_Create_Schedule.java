package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10649")
public class TC_06_04_Participant_Enrollment_Create_Schedule extends TC_06_01_Participant_Enrollment_Create {

    private static final String SCHEDULED_DATE = CommonFunctions.getTomorrowDate();
    private static final String SCHEDULED_TIME = "12:24 AM";


    public void main() {
        logStep("Creating Participant and Service for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, Constants.FALSE, Constants.FALSE);

        logStep("Select participant");
        ParticipantEnrollmentCreationForm creationForm = openCreationFormInputDate(participant, Constants.TRUE);

        logStep("Select Scheduled Date and Time");
        creationForm.inputScheduledDateTime(SCHEDULED_DATE, SCHEDULED_TIME);

        logStep("Select Ended - No status");
        creationForm.chooseEndedService(Constants.FALSE);

        logStep("Create");
        creationForm.completeCreation();

        logStep("Find created service");
        creationForm.findCreatedServiceCheckDate(participant, SERVICE_NAME, CREATION_DATE);
    }
}
