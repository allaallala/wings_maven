package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10663")
public class TC_06_19_Participant_Enrollment_Edit_Status extends BaseWingsTest {

    private String serviceName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    String status = Constants.COMPLETED;

    public void main() {
        String newEnrollmentResult = "This service is unresulted";
        logStep("Creating Participant and Service for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, serviceName, Constants.FALSE, Constants.FALSE);
        User staff = new User(Roles.STAFF);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, serviceName, status, Constants.EMPTY);


        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Search);

        logStep("Enter parameters for searching->Search");
        ParticipantEnrollmentSearchForm participantEnrollmentSearchForm = new ParticipantEnrollmentSearchForm();
        participantEnrollmentSearchForm.performSearch(participant, serviceName);

        logStep("Open one of the Service-Enrollments");
        participantEnrollmentSearchForm.openFirstSearchResult();
        ParticipantEnrollmentDetailsForm detailsForm = new ParticipantEnrollmentDetailsForm();

        logStep("Open Edit form");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Select Ended - No status");
        ParticipantEnrollmentEditForm enrollmentEditForm = new ParticipantEnrollmentEditForm();
        enrollmentEditForm.chooseEndedService(false);

        logStep("Save Changes");
        enrollmentEditForm.clickButton(Buttons.Save);

        logStep("Check, that status is saved");
        CustomAssertion.softTrue("Status is not saved", detailsForm.getServiceResult().contains(newEnrollmentResult));
    }
}
