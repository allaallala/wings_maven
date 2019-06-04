package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile, in the document section upload .gif file, .jpeg file, .png file. Check that document is uploaded and has 'In Review' status.
 * Created by a.vnuchko on 28.11.2016.
 */

@TestCase(id = "WINGS-11208")
public class TC_34_10_EP_Documents_Upload_Images extends TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf {

    public void main() {
        Participant participant = precondition();

        logStep("Go to My Documents section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        uploadDoc(detailsPage, pathPng, documentType, expectedStatus, 1);
        uploadDoc(detailsPage, pathJpg, documentType, expectedStatus, 2);
    }
}
