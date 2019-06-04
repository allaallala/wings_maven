package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10519")
public class TC_01_24_WIA_Enrollment_View extends TC_01_10_WIA_Enrollment_Create {

    public void main() {
        Participant participant = createWIAE();

        openWiaEnrollmentDetails(participant);

        logStep("Validate program data");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        wiaEnrollmentDetailsForm.validateBasicInformation(applicationDate, contactPerson, relation);
        wiaEnrollmentDetailsForm.validateProgramInformation(familyMember, wages, familyIncome);
    }

    /**
     * Open WIA enrollment participantSSDetails form
     * @param participant - participant
     */
    protected void openWiaEnrollmentDetails(Participant participant){
        logStep("Participants->Participant Records->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search for participant");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Open WiA enrollment participantSSDetails form");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.openWiaEnrollmentDetailsForm();
    }

}
