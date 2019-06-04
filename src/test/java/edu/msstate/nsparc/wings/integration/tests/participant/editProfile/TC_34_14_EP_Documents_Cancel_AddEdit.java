package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.common.HeaderForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * Open participant profile, add information for document to upload, click [Cancel]. Check, that changes are not saved.
 * Add document and approve it. Open participant profile and edit document. Click [Cancel].Check, that changes are not saved.
 * Created by a.vnuchko on 28.11.2016.
 */

@TestCase(id = "WINGS-11212")
public class TC_34_14_EP_Documents_Cancel_AddEdit extends TC_34_09_EP_Documents_Upload_Pdf_Word_Rtf {

    final String fileNameRegExp = "[^\\.pdf]{1,}";
    final String exactName = CommonFunctions.regexGetMatch(pathPdf, fileNameRegExp);
    final String newTitle = CommonFunctions.getRandomLiteralCode(5);
    final String expectedStatus = "In Review";

    public void main() {
        Participant participant = precondition();

        logStep("Go to My Documents section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep("Add a new documents");
        ParticipantDetailSteps.inputUploadData(pathPdf,documentType);
        detailsPage.clickButton(Buttons.Save);

        logStep("On this screen click [Cancel] button");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.REMOVE_DOCUMENT);
        detailsPage.areYouSure(Popup.No);

        logStep("Check that the document has not been removed");
        ParticipantDetailSteps.validateUploadedDocument(pathPdf, expectedStatus, 1);

        logStep("Open the document and start to edit it");
        new HeaderForm().logOut();
        BaseNavigationSteps.loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandDocuments();
        participantDetailsForm.chooseDocument(pathPdf);
        Browser.getInstance().waitForPageToLoad();
        participantDetailsForm.clickEditDocument();
        participantDetailsForm.fillInDocTitle(newTitle);

        logStep("On this screen click [Cancel] button");
        participantDetailsForm.waitClickAndWait(BaseWingsForm.BaseButton.CANCEL);
        detailsPage.areYouSure(Popup.Yes);
        participantDetailsForm.expandDocuments();

        logResult("Changes on this page are not applied");
        TableCell tbcDocName = new TableCell(By.xpath(String.format(participantDetailsForm.DOC_XPATH, exactName)));
        CustomAssertion.softTrue("The document has been changed", !tbcDocName.getText().contains(newTitle));

    }
}
