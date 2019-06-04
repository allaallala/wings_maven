package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10638")
public class TC_05_53_Participant_Edit_Employment_Remove extends BaseWingsTest {

    public void main() {
        logStep("Creating Participant for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);

        logStep("Find a participant record and open it");
        ParticipantSearchForm participantSearchForm = ParticipantNavigationSteps.openParticipantSearchPage(Roles.STAFF);
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);

        logStep("Go to the page with employment history");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.removePreviousJob();

        logStep("Validate, that record is removed");
        detailsForm.expandEmploymentHistory();
        assertFalse("Previous job wasn't removed", detailsForm.getPreviousJobPageText().contains("Cook at Automation"));
    }
}
