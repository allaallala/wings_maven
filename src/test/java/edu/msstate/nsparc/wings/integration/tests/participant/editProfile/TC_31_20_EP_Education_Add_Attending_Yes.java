package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, add education record with currently attending "Yes", save changes. Check, that changes are applied.
 * Created by a.vnuchko on 11.11.2016.
 */

@TestCase(id = "WINGS-11169")
public class TC_31_20_EP_Education_Add_Attending_Yes extends TC_31_01_EP_General_Standard_View {
    private String startDate = CommonFunctions.getYesterdayDate();
    protected String endDate = CommonFunctions.getCurrentDate();
    private String attending;
    private String degree = "High School Diploma";

    public void main() {
        mainSteps(Constants.TRUE);
    }

    /**
     * Remove education record (if present)
     *
     * @param participant - current participant
     */
    protected void openAddSection(Participant participant) {
        logStep("Go to Education section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        info("Remove education record to make clear test");
        ParticipantDetailSteps.removeEducationRecord();

        logStep("Click the [Add]");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_EDUCATION);
    }

    /**
     * Main steps for adding educational record for a participant
     *
     * @param currentlyAttending - if participant is attending school.
     */
    protected void mainSteps(Boolean currentlyAttending) {
        if (currentlyAttending) {
            attending = Constants.YES_ANSWER;
        } else {
            attending = Constants.NO_ANSWER;
        }
        Participant participant = precondition();
        openAddSection(participant);

        divideMessage("Select 'Are you currently attending?' - " + attending);
        ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        recordPage.selectParticipantSchool(currentlyAttending);

        logStep("Select 'High School Diploma' in the Which did you earn/attempt?");
        recordPage.selectDegree(degree);

        logStep("Fill all another fields with valid data");
        recordPage.fillOtherEducationFields(participant, startDate, endDate);

        logStep("Save changes");
        recordPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant).validateDegree(participant.getFirstName(),
                participant.getAddress().getCity() + " , " + participant.getAddress().getZipCode(), currentlyAttending, endDate, degree, Constants.EMPTY);
    }
}
