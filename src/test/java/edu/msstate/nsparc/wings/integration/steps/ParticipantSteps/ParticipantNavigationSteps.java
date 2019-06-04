package edu.msstate.nsparc.wings.integration.steps.ParticipantSteps;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import framework.Logger;

/**
 * This class describes some common steps for participant.
 * Created by a.vnuchko on 19.11.2015.
 */
public class ParticipantNavigationSteps extends BaseWingsSteps {

    /**
     * 1. Login as a role
     * 2. Participant -> Participant Profiles -> Search
     * 3. Search for existed participant and open its participantSSDetails page.
     * @param role - user role
     * @param partip - participant
     */
    public static void openParticipantDetailsPage(Roles role, Participant partip) {
        ParticipantSearchForm searchPage = openParticipantSearchPage(role);
        searchPage.performSearchAndOpen(partip);
    }

    /**
     * Open participant search form
     * 1. Login as a role
     * 2. Participant -> Participant Profiles -> Search
     * @param role - role
     * @return Participant Search Form.
     */
    public static ParticipantSearchForm openParticipantSearchPage(Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        return new ParticipantSearchForm();
    }

    /**
     * Open participant creation form
     * 1. Login as a role
     * 2. Participant -> Participant Profiles -> Create
     * @param role - role
     * @return - Participant Creation Form.
     */
    public static ParticipantCreationForm openParticipantCreationPage(Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);

        return new ParticipantCreationForm();
    }

    /**
     * Open participant enrollment creation page and fill it
     * @param partip - participant
     * @param serviceName - name of the service
     * @return Participant Enrollment Creation Form
     */
    public static ParticipantEnrollmentCreationForm openFillEnrlCreationPage(Participant partip, String serviceName) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Create);

        Logger.getInstance().info("Select participant");
        ParticipantEnrollmentCreationForm creationForm = new ParticipantEnrollmentCreationForm();
        creationForm.selectParticipant(partip);

        Logger.getInstance().info("Fill in all required fields");
        creationForm.selectService(serviceName);
        return new ParticipantEnrollmentCreationForm();
    }



}
