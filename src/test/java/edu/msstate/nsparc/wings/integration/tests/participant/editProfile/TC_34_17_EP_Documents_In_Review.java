package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile and upload document. Try to edit or download it. It's impossible to edit and download document 'In Review'.
 * Created by a.vnuchko on 19.12.2016.
 */

@TestCase(id = "WINGS-11215")
public class TC_34_17_EP_Documents_In_Review extends TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf {

    public void main(){
        divideMessage("Upload at least one document");
        Participant participant = precondition();
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        uploadDoc(detailsPage, pathPdf, documentType, expectedStatus, 1);
        //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9311
        logStep("Try to edit document in the  'In Review' status");

        logResult("It is impossible to edit document in the  'In Review' status");

        logStep("Try to download document in the  'In Review' status");

        logResult("It is impossible to download document in the  'In Review' status");
    }
}
