package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10640")
public class TC_06_01_Participant_Enrollment_Create extends BaseWingsTest {

    public static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    public static final String CREATION_DATE = CommonFunctions.getCurrentDate();


    public void main() {
        logStep("Creating Participant and Service for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, Constants.FALSE, Constants.FALSE);

        logStep("Select participant");
        ParticipantEnrollmentCreationForm creationForm = openCreationFormInputDate(participant, Constants.FALSE);

        logStep("Select Ended - Yes status");
        creationForm.chooseEndedService(Constants.TRUE);
        creationForm.inputDateResult(CommonFunctions.getCurrentDate());
        creationForm.selectResult(Constants.COMPLETED);

        logStep("Create");
        creationForm.completeCreation();

        logStep("Find created service");
        creationForm.findCreatedServiceCheckDate(participant, SERVICE_NAME, CREATION_DATE);
    }

    /**
     * Open participant service enrollment creation page
     * @param participant - participant
     * @param scheduledService - scheduled serice.
     */
    public ParticipantEnrollmentCreationForm openCreationFormInputDate(Participant participant, Boolean scheduledService) {
        ParticipantEnrollmentCreationForm creationForm = ParticipantNavigationSteps.openFillEnrlCreationPage(participant, SERVICE_NAME);

        logStep("Select Not Scheduled status");
        creationForm.chooseScheduledService(scheduledService);
        creationForm.inputCreationDate(CREATION_DATE);
        return creationForm;
    }
}
