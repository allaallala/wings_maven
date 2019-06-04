package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.LookupProfileForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.MilitaryInformationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.PersonalInformationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Try to create a participant, that is a veteran, but select 'Not Eligible Veteran' in the Veteran section.
 * Check, that error message is displayed.
 * Created by a.vnuchko on 01.03.2017.
 */

@TestCase(id = "WINGS-11244")
public class TC_38_03_Participant_Create_NFVet_Status extends BaseWingsTest {
    String vetStatus = "Not an Eligible Veteran";

    public void main() {
        Participant participant = new Participant();
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);

        logStep("Select Veteran status as 'Yes'");
        ParticipantCreationForm participantCreationForm = new ParticipantCreationForm();

        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, Constants.TRUE, Constants.FALSE);

        logStep("On the Veteran Details page select 'Not an Eligible Veteran' status");
        new MilitaryInformationForm().selectVeteranStatus(vetStatus);

        logResult("Warning message regarding the incorrect Veteran status on the previous page is displayed. User can't proceed with the registration.");
        new ParticipantCreationForm().waitForNotVisible(BaseWingsForm.BaseOtherElement.LOADING);
        participantCreationForm.validateErrorMessage();
    }
}
