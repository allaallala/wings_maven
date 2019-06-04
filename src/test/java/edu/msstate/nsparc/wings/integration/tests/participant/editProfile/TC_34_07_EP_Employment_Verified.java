package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Preconditions: User has Employment Record which is verified by Staff
 * Open participant profile, try to edit or remove employment record.
 * Created by a.vnuchko on 27.11.2016.
 */

@TestCase(id = "WINGS-11206")
public class TC_34_07_EP_Employment_Verified extends TC_34_01_EP_Employment_Add{

    public void main(){
        info("Preconditions: User has Employment Record which is verified by Staff");
        Participant participant = createDefaultEmployment();
        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandEmploymentHistory();
        BaseNavigationSteps.logout();

        logStep("Go to Employment section");
        BaseParticipantSsDetailsForm detailsSS = openParticipantDetail(participant);

        logStep("Try to edit the verified Employment record");
        detailsSS.editEmploymentSelfService();

        logResult("It is impossible to edit verified Education record");

        logStep("Try to remove varified Employment record");

        logResult("It is impossible to delete verified Education record");
    }
}
