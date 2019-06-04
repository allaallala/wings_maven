package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.ParticipantType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;


/**
 * Check functionaly [Participant Service Enrollment] using different internal user roles: administrator, area director, manager, staff, intake staff.
 * Created by a.vnuchko on 14.03.2016.
 */

@TestCase(id = "WINGS-11083")
public class TC_25_06_Participant_Service_Enrollment_Roles_Admin_AD_Manager_Staff_IS extends BaseWingsTest {
    private static final String SERVICE_NAME = "Automation Required Service";
    private Participant participant;

    public void main() {
        logStep("Roles - administrator");
        User user = new User(Roles.ADMIN);
        commonStepsParticipantServiceEnrollment(user);

        logStep("Roles - area director");
        user.setNewUser(Roles.AREADIRECTOR);

        commonStepsParticipantServiceEnrollment(user);

        logStep("Roles - manager");
        user.setNewUser(Roles.MANAGER);
        commonStepsParticipantServiceEnrollment(user);

        logStep("Roles = staff");
        user.setNewUser(Roles.STAFF);
        commonStepsParticipantServiceEnrollment(user);

        logStep("Roles = intake staff");
        user.setNewUser(Roles.INTAKESTAFF);
        commonStepsParticipantServiceEnrollment(user);

    }

    /**
     * Includes common steps for checking participant service enrollment (Create, View, Edit).
     * 1. Create participant service enrollment (if possible)
     * 2. Open its participantSSDetails page
     * 3. Check buttons (Audit, edit, delete) and functionality.
     *
     * @param user - current user.
     */
    protected void commonStepsParticipantServiceEnrollment(User user) {

        //if the current user has permissions to create a new participant service enrollment - it should be created.
        if (user.getServiceEnrollment().getPseCreate()) {
            AccountUtils.init();
            participant = new Participant(ParticipantType.YOUTH);
            ParticipantCreationSteps.createVeteranParticipantNotDriver(participant);
            logStep("Create participant service enrollment");
            ServiceSteps.createParticipantServiceEnrollment(user, participant, SERVICE_NAME, Constants.COMPLETED, CommonFunctions.getYesterdayDate());

        } else {
            if (participant == null) {
                BaseEntity.getErrorsList().add(user.getRoleString() + ": participant is not created, try again. Edit, delete and audit functionality is not checked");
            }
        }
        logStep("Open participant service enrollment participantSSDetails page and check if possible to edit, delete or audit record.");
        checkDetailFunctionality(user, participant);
        BaseNavigationSteps.logout();
    }


    /**
     * Open participant participantSSDetails page
     *
     * @param user   - current user
     * @param partip - participant
     */
    protected void checkDetailFunctionality(User user, Participant partip) {
        BaseWingsSteps.logInAs(user.getRole());

        //Check, if it's possible to view a record.
        if (user.getServiceEnrollment().getPseView()) {
            divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);

            //Check, if popup is displayed
            if (user.getServiceEnrollment().getPseCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
            searchPage.performSearchAndOpenByUser(user, partip, SERVICE_NAME);

            searchPage.openFirstSearchResult();

            //Check some buttons are present on the detail page (Edit, Audit, Delete enrollment button).
            ParticipantEnrollmentDetailsForm detailsPage = new ParticipantEnrollmentDetailsForm();

            detailsPage.checkButtons(user, partip, SERVICE_NAME);

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Constants.ZERO);
        }
    }
}
