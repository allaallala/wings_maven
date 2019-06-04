package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10615")
public class TC_05_02_Manual_Exit_With_Service extends BaseWingsTest {

    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String MESSAGE_TEXT = "Manual Exit cannot be performed because a service is in progress";


    //Bug WINGS-2028, sub-task WINGS-2653
    public void main() {

        info("Creating participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        info("E-Verify participant");
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));
        info("Creating service enrollment with 'In Progress' status");
        ServiceSteps.createService(Roles.ADMIN, serviceName, false, false);
        User staff = new User(Roles.STAFF);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, serviceName, Constants.IN_PROGRESS, Constants.EMPTY);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Find one of the records and open it");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);

        logStep("Select panel 'Operations'->Manual Exit");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandOperations();

        logStep("Check, that 'Manual Exit' button isn't displayed and warning message is visible");
        CustomAssertion.softTrue("'Manual Exit' button is visible", !(participantDetailsForm.checkManualExitButtonPresent()));
        CustomAssertion.softTrue("Warning text isn't displayed", participantDetailsForm.getOperationsText().contains(MESSAGE_TEXT));

        logEnd();
    }
}
