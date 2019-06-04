package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this test, we check if sections in the standard view are displayed correctly.
 * Created by a.vnuchko on 29.10.2016.
 */

@TestCase(id = "WINGS-11159")
public class TC_31_01_EP_General_Standard_View extends BaseWingsTest {

    String documentType = "Other";

    public void main() {
        String pathFile = "Ghostwolf.pdf";
        String url = "https://goo.gl/5esFmu";
        Participant participant = precondition();

        logStep("Change view to [Standard View]");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.resumeStandardView();

        logResult("All sections displayed. [Edit] button is displayed in all sections. [Print] button is not displayed here.");
        detailsPage.checkSections();
        //to check possibility to edit documents it should be approved by staff.
        ParticipantDetailSteps.uploadDocument(pathFile, documentType);

        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.validateDocument(pathFile);
        participantDetailsForm.approveDocument(pathFile);
        BaseNavigationSteps.logout();
        detailsPage = openParticipantDetail(participant);

        ParticipantDetailSteps.checkEditButtons();
        detailsPage.printNotPresent();
    }

    /**
     * Make 'before' actions - create a participant
     */
    protected Participant precondition() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();

        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);
        return participant;
    }

    /**
     * Open participant S-S detail form
     *
     * @param participant - participant to open
     * @return new participant participantSSDetails form.
     */
    protected BaseParticipantSsDetailsForm openParticipantDetail(Participant participant) {
        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.openMyProfile();

        return new BaseParticipantSsDetailsForm(participant);
    }
}
