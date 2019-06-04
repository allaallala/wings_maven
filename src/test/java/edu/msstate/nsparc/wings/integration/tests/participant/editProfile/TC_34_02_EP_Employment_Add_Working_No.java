package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant profile, add new record in the employment section, check, that participant is not working now.
 * Check, that all changes are saved.
 * Created by a.vnuchko on 19.11.2016.
 */

@TestCase(id = "WINGS-11202")
public class TC_34_02_EP_Employment_Add_Working_No extends TC_34_01_EP_Employment_Add {
    String endDate = CommonFunctions.getCurrentDate();
    String reasonLeaving = "Terminated";

    public void main(){
        Participant participant = precondition();

        openAddEmploymentSection(participant);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.EMPLOYMENT);

        logStep("Add new one");
        editPage.inputJobTools(participant, Constants.FALSE, Constants.EMPTY);
        editPage.addEmploymentRecord(Constants.FALSE, participant, startDate, endDate, reasonLeaving, Constants.FALSE);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);
        editPage.checkInternalError();

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant);
    }
}
