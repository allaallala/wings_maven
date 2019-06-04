package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant profile, add new record in the employment section, add osoc and tools. Check, that all changes are saved
 * Created by a.vnuchko on 27.11.2016.
 */
@TestCase(id = "WINGS-11203")
public class TC_34_03_EP_Employment_Add_Tools_Used extends TC_34_01_EP_Employment_Add {

    private String toolsElement = "Blenders";

    public void main() {
        Participant participant = precondition();

        openAddEmploymentSection(participant);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.EMPLOYMENT);

        logStep("Add new one. Fill the 'Jobs you performed.' field. Check correctness of OSOC "
                + "search and add any OSOC.  Select 'Tools and Technologies'");
        editPage.addEmploymentRecord(Constants.TRUE, participant, startDate, Constants.EMPTY, Constants.EMPTY,
                Constants.FALSE);
        editPage.inputJobTools(participant, Constants.TRUE, toolsElement);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);
        editPage.checkInternalError();

        logResult("All changes are saved");
        new BaseParticipantSsDetailsForm(participant);
    }
}
