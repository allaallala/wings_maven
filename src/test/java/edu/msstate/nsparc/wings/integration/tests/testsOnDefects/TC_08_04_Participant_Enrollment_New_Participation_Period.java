package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10706")
public class TC_08_04_Participant_Enrollment_New_Participation_Period extends BaseWingsTest {

    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final Integer TEST_DATE_CREATION = 111;
    private static final Integer TEST_DATE_OUTCOME = 199;
    private static final String CREATION_DATE = CommonFunctions.getDaysAgoDate(TEST_DATE_CREATION);

    public void main() {


        info("Creating Participant and Service for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createAndPrepareParticipantForProgramOutcomes(participant, false, TEST_DATE_OUTCOME);
        ServiceSteps.createService(Roles.ADMIN, serviceName, true, false);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Create);

        logStep("Select participant");
        ParticipantEnrollmentCreationForm creationForm = new ParticipantEnrollmentCreationForm();
        creationForm.selectParticipant(participant);

        logStep("Select service");
        creationForm.selectService(serviceName);

        logStep("Select status and creation date");
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(CREATION_DATE);
        creationForm.chooseEndedService(true);
        creationForm.inputDateResult(CREATION_DATE);
        creationForm.selectResult(Constants.COMPLETED);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make sure validation message is displayed");
        creationForm.checkField(BaseWingsForm.BaseOtherElement.ERROR_MESSAGE.getElement(), "This data may not be accepted because it results in the exit date being changed for one or more exited participation periods", false);
    }
}
