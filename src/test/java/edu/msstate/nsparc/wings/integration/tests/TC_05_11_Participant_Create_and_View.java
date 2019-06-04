package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10432")
public class TC_05_11_Participant_Create_and_View extends BaseWingsTest {

    public void main() {


        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Find created Participant record");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearch(participant);
        logStep("Open found record");
        searchForm.clickOnSearchResult(participant);

        logStep("Validate Participant information");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.validateParticipantInformation(participant);

        BaseNavigationSteps.logout();
    }
}
