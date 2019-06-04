package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * (Add new employment record to a participant)
 * Open participant profile, remove employment record. Check, that record does not exist.
 * Created by a.vnuchko on 27.11.2016.
 */

@TestCase(id = "WINGS-11205")
public class TC_34_06_EP_Employment_Remove extends TC_34_01_EP_Employment_Add {

    public void main() {
        info("Precondition: create new participant");
        Participant participant = precondition();

        logStep("Login the system, navigate to My Profile and go to Employment section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep("Click [Remove] of any Employment Record");
        detailsPage.removeEmploymentAndConfirm();

        logResult("Employment Record is removed.");
        detailsPage.checkRemoveEmploymentNotPresent();
    }
}
