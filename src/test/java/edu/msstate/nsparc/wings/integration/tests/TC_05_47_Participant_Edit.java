package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10636")
// Author: d.poznyak
public class TC_05_47_Participant_Edit extends BaseWingsTest {

    public void main() {
        logStep("Creating Participant for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, participant);

        logStep("Edit one parameter of record");
        // we will edit personal information
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.editPersonalInformation();
        ParticipantEditForm participantEditForm = new ParticipantEditForm();
        // new name
        String firstName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        participantEditForm.inputFirstName(firstName);

        logStep("Save changes");
        participantEditForm.clickButton(Buttons.Save);

        logStep("Verify the changes are saved");
        Assert.assertTrue(participantDetailsForm.getLinkTitle().contains(firstName));
    }
}
