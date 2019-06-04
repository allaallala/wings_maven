package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile, add new record in the employment section with existing rapid response event. Check, that all changes are saved.
 * Created by a.vnuchko on 27.11.2016.
 */

@TestCase(id = "WINGS-11204")
public class TC_34_04_EP_Employment_Add_Rre_Yes extends TC_34_01_EP_Employment_Add{

    public void main(){
        Participant participant = precondition();

        openAddEmploymentSection(participant);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.EMPLOYMENT);

        logStep("Add new one. Select the Did you receive Rapid Response? = YES");
        editPage.inputJobTools(participant, Constants.FALSE, Constants.EMPTY);
        editPage.addEmploymentRecord(Constants.TRUE, participant, startDate,
                Constants.EMPTY, Constants.EMPTY, Constants.TRUE);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);
        editPage.checkInternalError();

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant);

    }
}
