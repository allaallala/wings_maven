package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Represents document upload functionality for an administrator and area director.
 * Created by a.vnuchko on 14.09.2016.
 */

@TestCase(id = "WINGS-11149")
public class TC_29_17_Document_Upload_Admin_AD extends BaseWingsTest {
    Participant participant;
    String fileName = "auto.pdf";
    String baseFileName = "wingsAutoPDF.pdf";

    public void main() {
        init();
        User user = new User(Roles.ADMIN);
        docSteps(user, fileName);

        init();
        user.setNewUser(Roles.AREADIRECTOR);
        docSteps(user, fileName);
    }

    /**
     * Represents common steps for checkind document upload (upload, view, delete document; attach to a referral and approve uploaded)
     *
     * @param user     - current user
     * @param fileName - name of the file
     */
    protected void docSteps(User user, String fileName) {


        divideMessage("Open participant detail page");
        BaseWingsSteps.logInAs(user.getRole());

        //If user has permissions to view participant profile.
        if (user.getParticipant().getParticipantPermissions().getPpView()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

            //If user can create a participant, it's necessary to confirm pop-up.
            if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ParticipantSearchForm searchPage = new ParticipantSearchForm();
            //searchPage.performSearchAndOpenByUser(user, participant);

            if (user.getRole().equals(Roles.LWDASTAFF) || user.getRole().equals(Roles.WIOAPROVIDER)) {
                searchPage.performSearchExternal(participant);
                searchPage.clickButton(Buttons.Done);
            } else {
                searchPage.performSearch(participant);
            }

            searchPage.clickOnSearchResult(participant);


            BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();

            //(!) If user can upload documents to the participant profile
            if (user.getDocUpload().getDocumentUpload()) {
                logStep("Upload document");
                detailsPage.addDocument(fileName);
                detailsPage.addDocument(baseFileName); //if we need to delete one document afterwards :)
            }

            //(!) If user can view attached documents
            if (user.getDocUpload().getDocumentViewAttachedDocuments()) {
                logStep("View attached documents");
                detailsPage.validateDocument(fileName);
            }

            //(!) If user can delete attached documents
            if (user.getDocUpload().getDocumentDeleteDocument()) {
                logStep("Delete uploaded document");
                detailsPage.deleteDocument(fileName);
            }

            //(!) If user can attach document refferal
            if (user.getDocUpload().getDocumentAttachDocumentRefferal()) {
                logStep("Attach document to Referral");
                //TODO Now this functionality is only available for admin1/admin1
            }

            //(!) If user can approve/reject SS uploaded document
            if (user.getDocUpload().getDocumentApproveSS()) {
                logStep("Approve uploaded document");
                //TODO Now this functionality is only available for admin1/admin1
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Make some preparations
     */
    protected void init() {
        divideMessage("Create new participant to work with");
        AccountUtils.init();
        participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
    }
}
