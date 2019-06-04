package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;


@TestCase(id = "WINGS-10637")
public class TC_05_51_Participant_Edit_Employment_Add extends BaseWingsTest {

    public void main() {
        logStep("Creating Participant for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        logStep("Find a participant record and open it");
        ParticipantSearchForm searchForm = ParticipantNavigationSteps.openParticipantSearchPage(Roles.STAFF);
        searchForm.performSearch(participant);
        searchForm.clickOnSearchResult(participant);

        logStep("Add Previous Job record");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.addPreviousJob();

        logStep("Validate, that changes are saved");
        detailsForm.expandEmploymentHistory();
        Assert.assertTrue(detailsForm.getPreviousJobPageText().contains("Cook at Automation"));
    }
}
