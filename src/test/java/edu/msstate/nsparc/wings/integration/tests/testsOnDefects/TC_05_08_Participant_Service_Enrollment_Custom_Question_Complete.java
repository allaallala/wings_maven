package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10623")
public class TC_05_08_Participant_Service_Enrollment_Custom_Question_Complete extends BaseWingsTest {

    private static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String QUESTION = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String CREATION_DATE = CommonFunctions.getCurrentDate();
    private static final String ERROR_MESSAGE = "An answer is required";


    //sub-task WINGS-2761
    public void main() {

        info("Creating Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createServiceWithQuestion(SERVICE_NAME, QUESTION);

        ParticipantEnrollmentCreationForm creationForm = ParticipantNavigationSteps.openFillEnrlCreationPage(participant, SERVICE_NAME);

        logStep("Select Creation date and 'Complete' status");
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(CREATION_DATE);
        creationForm.chooseEndedService(true);
        creationForm.inputDateResult(CREATION_DATE);
        creationForm.selectResult(Constants.COMPLETED);

        logStep("Click on Continue");
        creationForm.clickButton(Buttons.Continue);

        logStep("Don't answer the question, click on Create");
        creationForm.clickButton(Buttons.Create);

        logStep("Check that error message is displayed");
        CustomAssertion.softTrue("Error message wasn't displayed", creationForm.getErrorText().contains(ERROR_MESSAGE));

        logStep("Answer the question");
        creationForm.inputAnswer(QUESTION);

        logStep("Click on Create");
        creationForm.clickButton(Buttons.Create);

        logStep("Check that Service is created");
        CustomAssertion.softTrue("Service wasn't created", BaseWingsForm.BaseButton.DONE.getButton().isPresent());

        logEnd();
    }
}
