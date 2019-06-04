package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.Logger;

/**
 * This class is used for storing some methods with default steps for notes.
 * Created by a.vnuchko on 08.09.2015.
 */
public class NotesSteps extends BaseWingsSteps {
    /**
     * Opens participant participantSSDetails form:
     * Log into the system as Admin
     * Participants -> Participant Profiles
     * Choose "Search"  on the pop up
     * Find the required participant
     * Open its participantSSDetails page.
     * @param partp - participant
     */
     public static void openParticipantDetailsInformation(Participant partp) {
         openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

         ParticipantSearchForm searchPage = new ParticipantSearchForm();
         searchPage.performSearch(partp);

         Logger.getInstance().info("Open participant participantSSDetails information");
         searchPage.openFirstSearchResult();
     }
}
