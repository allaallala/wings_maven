package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, click [Edit] and change all data. Check, that data is changed.
 * Created by a.vnuchko on 15.11.2016.
 */
@TestCase(id = "WINGS-10425")
public class TC_31_24_EP_Education_Edit_Record extends TC_31_20_EP_Education_Add_Attending_Yes {

    private String dateStart = CommonFunctions.getYesterdayDate();
    private String dateEnd = CommonFunctions.getCurrentDate();
    private String degree = "General Equivalency Degree (GED)";
    private String gpa = "4.0";

    public void main() {
        Participant participant = precondition();

        logStep("Go to Education section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep(" Click [Edit] of any Education Record");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_EDUCATION);

        logStep("Change all data");
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.fillOtherEducationFields(participant, dateStart, dateEnd);
        recordPage.selectDegree(degree);
        recordPage.inputGpa(gpa);

        logStep("Save changes");
        recordPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        detailsPage.validateDegree(participant.getFirstName(), participant.getAddress().getCity()+ " , " + participant.getAddress().getZipCode(), Constants.FALSE,
                endDate, degree, Constants.EMPTY);
    }
}
