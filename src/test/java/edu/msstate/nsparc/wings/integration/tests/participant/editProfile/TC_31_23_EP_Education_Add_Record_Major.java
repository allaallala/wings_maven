package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, add education record with 'Master Degree', fill 'What was your major?', fill other fields and save. Check, that all changes are saved.
 * Created by a.vnuchko on 14.11.2016.
 */

@TestCase(id = "WINGS-11172")
public class TC_31_23_EP_Education_Add_Record_Major extends TC_31_20_EP_Education_Add_Attending_Yes {

    public void main() {
        String degree = "Master's Degree";
        String major = "Communication, General";
        String date = CommonFunctions.getCurrentDate();
        Participant participant = precondition();

        openAddSection(participant);

        logStep("Select 'Master's Degree' the Which did you earn/attempt?");
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.selectDegree(degree);

        logStep("Fill 'What was your major?'");
        recordPage.proceedMajorField(major);

        logStep("Fill all another fields with valid data");
        recordPage.fillOtherEducationFields(participant, date, date);

        logStep("Save changes");
        recordPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant).validateDegree(participant.getFirstName(), participant.getAddress().getCity()
                + " , " + participant.getAddress().getZipCode(), Constants.FALSE, date, degree, Constants.EMPTY);
    }
}
