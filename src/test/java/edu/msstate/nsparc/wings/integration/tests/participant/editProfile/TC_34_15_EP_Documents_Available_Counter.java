package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile and upload document. Check counter. Add one more document. Check counter. Remove one document. Check counter.
 * Created by a.vnuchko on 28.11.2016.
 */

@TestCase(id = "WINGS-11213")
public class TC_34_15_EP_Documents_Available_Counter extends TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf {

    public void main() {
        Participant participant = precondition();

        logStep("Go to My Documents section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep("Check the Uploads Available counter. Uploads Available: 21 of 21");
        ParticipantDetailSteps.validateUploadedText(21);
        uploadDoc(detailsPage, pathPdf, documentType, expectedStatus, 1);
        uploadDoc(detailsPage, pathPng, documentType, expectedStatus, 2);

        logStep("Check the Uploads Available counter.  Uploads Available: 19 of 21");
        ParticipantDetailSteps.validateUploadedText(19);

        logStep("Check the Uploads Available counter.  Uploads Available: 20 of 21");
    }
}
