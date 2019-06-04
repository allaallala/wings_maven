package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile, in the document section upload .pdf file, .doc file, .png file, .jpg, .docx. Check that document is uploaded and has 'In Review' status.
 * Created by a.vnuchko on 28.11.2016.
 */
@TestCase(id = "WINGS-10427")
public class TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf extends TC_31_01_EP_General_Standard_View {
    protected String pathPdf = "wingsAutoPDF.pdf";
    protected String pathWord = "AlexCore.doc";
    protected String pathPng = "TiradoRRo.png";
    protected String pathJpg = "TiradoRRo.jpg";
    protected String pathDocx = "TiradoRRo.docx";
    protected String documentType = "Other";
    protected String expectedStatus = "In Review";

    public void main() {
        Participant participant = precondition();

        logStep("Go to My Documents section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        uploadDoc(detailsPage, pathWord, documentType, expectedStatus, 1);
        uploadDoc(detailsPage, pathPdf, documentType, expectedStatus, 2);
        uploadDoc(detailsPage, pathPng, documentType, expectedStatus, 3);
        uploadDoc(detailsPage, pathJpg, documentType, expectedStatus, 4);
        uploadDoc(detailsPage, pathDocx, documentType, expectedStatus, 5);
    }


    protected void uploadDoc(BaseParticipantSsDetailsForm detailsPage, String filePath, String type, String expectedStatus, Integer quantity) {
        logStep("Add new one with required type and Save Changes");
        ParticipantDetailSteps.uploadDocument(filePath, type);

        logResult("All changes are saved. Document has 'In Review' status.");
        ParticipantDetailSteps.validateUploadedDocument(filePath, expectedStatus, quantity);
    }
}
