package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
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
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10521")
public class TC_01_32_WIA_Enrollment_Edit_One_Parameter extends BaseWingsTest {

    private static final String TEXT = "Automation Test";


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), Constants.TRUE);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        openWiaEnrollmentDetails(participant);
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();

        logStep("Click Edit");
        participantDetailsForm.openWiaEnrollmentDetailsForm();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        wiaEnrollmentDetailsForm.clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);
        WIAEnrollmentEditForm wiaEnrollmentEditForm = new WIAEnrollmentEditForm();

        //sub-task WINGS-2736
        info("Checking validation for WINGS-2736");
        wiaEnrollmentEditForm.checkDataValidation();
        //-----------------------------------------------------------

        logStep("Edit one parameters->Save changes");
        wiaEnrollmentEditForm.inputContactPerson(TEXT);
        wiaEnrollmentEditForm.clickButton(Buttons.Save);

        logStep("Search changed WIA Enrollment and be sure that all changes are saved");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();
        wiaEnrollmentSearchForm.performSearch(participant);
        wiaEnrollmentSearchForm.openFirstSearchResult();

        logStep("Check, that changes are saved");
        assertEquals("Changes aren't saved", TEXT, wiaEnrollmentDetailsForm.getContactPersonText());

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Open WIA enrollment participantSSDetails page.
     * @param participant - participant
     */
    protected void openWiaEnrollmentDetails(Participant participant) {
        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Participants->Participant Records->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Enter parameters for searching");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);

        logStep("Select some Participant record and open it");
        participantSearchForm.clickOnSearchResult(participant);
    }
}
