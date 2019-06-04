package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile, click [Add] in the employment section and change some data. Do not save changes and check,
 * that they are not saved. Then add employment record, click [Edit] and change some data. Do not save changes and check,
 * that they are not saved.
 * Created by a.vnuchko on 27.11.2016.
 */
@TestCase(id = "WINGS-11207")
public class TC_34_08_EP_Employment_Cancel_AddEdit extends TC_34_01_EP_Employment_Add {

    public void main() {
        Participant participant = precondition();

        openAddEmploymentSection(participant);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.EMPLOYMENT);

        logStep("Enter or change any data.");
        editPage.inputJobTools(participant, Constants.FALSE, Constants.EMPTY);

        logStep("Click [Cancel].");
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("Entered data are not saved");
        BaseParticipantSsDetailsForm detailsPage = new BaseParticipantSsDetailsForm(participant);
        detailsPage.checkInternalError();

        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_EMPLOYMENT);
        editPage.inputJobTools(participant, Constants.FALSE, Constants.EMPTY);
        editPage.addEmploymentRecord(Constants.TRUE, participant, startDate, Constants.EMPTY, Constants.EMPTY, Constants.FALSE);
        editPage.clickButton(Buttons.Save);

        logStep("Click [Edit]. Change some data");
        detailsPage.editEmploymentSelfService();
        ParticipantAddEmploymentForm addPage = new ParticipantAddEmploymentForm("participant SS");
        participant.setJobTitle("DondeEstasYolanda");
        addPage.inputJobTitle(participant.getJobTitle());
        //TODO

        logStep("Click [Cancel].");
        editPage.clickButton(Buttons.Cancel);

        logResult("Changed data are not saved");

    }
}
