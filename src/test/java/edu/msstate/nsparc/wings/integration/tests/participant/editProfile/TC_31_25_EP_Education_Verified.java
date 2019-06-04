package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * As a precontion: we have to verify education record by Staff, and then try to edit or remove education record from Participant S-S profile.
 * It must be impossible to edit/remove this verified record
 * Created by a.vnuchko on 16.11.2016.
 */

@TestCase(id = "WINGS-11173")
public class TC_31_25_EP_Education_Verified extends TC_31_20_EP_Education_Add_Attending_Yes {

    public void main() {
        info("Precondition: create participant and verify its academic record by Staff user");
        Participant participant = precondition();

        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandAcademicHistory();
        detailsPage.selectVerify();
        BaseNavigationSteps.logout();

        logStep("Go to Education section");
        BaseParticipantSsDetailsForm partSsDetails = openParticipantDetail(participant);

        logStep("Try to edit the varified Education record");
        partSsDetails.editEducationNotPresent();

        logResult("It is impossible to edit/remove verified Education record");
        ParticipantDetailSteps.validateEducationCannotChanged();
    }
}
