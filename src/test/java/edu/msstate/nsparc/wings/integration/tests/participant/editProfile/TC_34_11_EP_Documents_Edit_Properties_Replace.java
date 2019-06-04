package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Upload and approve this document. Edit its type. Check, that changes are saved. Replace this document by other one.
 * Created by a.vnuchko on 28.11.2016.
 */

@TestCase(id = "WINGS-11209")
public class TC_34_11_EP_Documents_Edit_Properties_Replace extends TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf {

    public void main() {
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        ParticipantDetailSteps.uploadDocumentApprove(participant, pathPdf, documentType);
    }
}
