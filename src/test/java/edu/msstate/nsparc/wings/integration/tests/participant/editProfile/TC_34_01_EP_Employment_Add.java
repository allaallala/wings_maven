package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant profile, add new record in the employment section, check, that participant is working now. Check, that all changes are saved.
 * Created by a.vnuchko on 19.11.2016.
 */

@TestCase(id = "WINGS-11201")
public class TC_34_01_EP_Employment_Add extends TC_31_01_EP_General_Standard_View {
    String startDate = CommonFunctions.getCurrentDate();

    public void main() {
        createDefaultEmployment();
    }

    /**
     * Open employment section and click [Add]
     *
     * @param participant - participant
     */
    protected void openAddEmploymentSection(Participant participant) {
        logStep("Go to Employment section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logResult("Click the [Add]");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_EMPLOYMENT);
    }

    /**
     * Create default employment record.It's used, if it's necessary to edit or remove employment record.
     *
     * @return created participant.
     */
    protected Participant createDefaultEmployment() {
        Participant participant = precondition();

        openAddEmploymentSection(participant);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.EMPLOYMENT);

        logStep("Add new one");
        editPage.inputJobTools(participant, Constants.FALSE, Constants.EMPTY);
        editPage.addEmploymentRecord(Constants.TRUE, participant, startDate, Constants.EMPTY, Constants.EMPTY,
                Constants.FALSE);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        editPage.checkInternalError();
        new BaseParticipantSsDetailsForm(participant);

        BaseNavigationSteps.logout();
        return participant;
    }
}
