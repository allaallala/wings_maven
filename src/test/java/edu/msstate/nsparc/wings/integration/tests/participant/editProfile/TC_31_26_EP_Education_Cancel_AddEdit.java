package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, click [Add/Edit], change or ented some data, but do not save it. Check, that information hasn't been changed.
 * Created by a.vnuchko on 16.11.2016.
 */

@TestCase(id = "WINGS-11174")
public class TC_31_26_EP_Education_Cancel_AddEdit extends TC_31_20_EP_Education_Add_Attending_Yes {

    private String date = CommonFunctions.getCurrentDate();
    private String changedDate = CommonFunctions.getYesterdayDate();
    private String degree = "High School Diploma";

    public void main() {
        Participant participant = precondition();

        logStep("Go to Education section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep("In the Education section, click [Edit]");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_EDUCATION);
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.fillOtherEducationFields(participant, changedDate, changedDate);

        logStep("Click [Cancel]");
        recordPage.clickCancelAndConfirm();

        logResult("Entered/changed data are not saved.");
        detailsPage.validateDegree(participant.getCertification(), participant.getAddress().getCity()+ " , " + participant.getAddress().getZipCode(), Constants.FALSE, date, degree, Constants.EMPTY);

        info("Remove education record to check add education");
        ParticipantDetailSteps.removeEducationRecord();

        logStep("In the Education section, click [Add]");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_EDUCATION);
        recordPage.fillOtherEducationFields(participant, changedDate, changedDate);

        logStep("Click [Cancel]");
        recordPage.clickCancelAndConfirm();

        logResult("Entered data are not saved.");
        ParticipantDetailSteps.removeEducationRecord();
    }
}
