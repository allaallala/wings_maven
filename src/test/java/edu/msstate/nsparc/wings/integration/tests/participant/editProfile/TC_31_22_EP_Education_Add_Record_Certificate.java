package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, add education record with 'Occupational certificate', fill other fields and save. Check, that all changes are saved.
 * Created by a.vnuchko on 14.11.2016.
 */

@TestCase(id = "WINGS-11171")
public class TC_31_22_EP_Education_Add_Record_Certificate extends TC_31_20_EP_Education_Add_Attending_Yes {

    public void main() {
        String certificationType = "Occupational Certification";
        String certMajor = "Communication, General";
        String date = CommonFunctions.getCurrentDate();
        Participant participant = precondition();

        openAddSection(participant);

        logStep("Select 'Occupational Certification' in the 'Which did you earn/attempt?'");

        logStep("Fill fields for Certificate information");
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.selectParticipantSchool(Constants.TRUE);
        recordPage.selectDegree(certificationType);
        recordPage.proceedMajorField(certMajor); // WINGS-9916

        logStep("Fill all another fields with valid data");
        recordPage.fillOtherEducationFields(participant, date, date);

        logStep("Save changes");
        recordPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant).validateCert(certificationType, certMajor, date, participant);
    }
}
