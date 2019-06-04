package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.Address;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * (Add new employment record to a participant)
 * Open participant profile, change all data of the employment record and save. Check, that all changes are saved.
 * Created by a.vnuchko on 27.11.2016.
 */
@TestCase(id = "WINGS-10426")
public class TC_34_05_EP_Employment_Edit extends TC_34_01_EP_Employment_Add {
    Participant participant;

    public void main() {
        String[] dataEdit = {CommonFunctions.getDaysAgoDate(10), "60", "60", "Day", "Sales managers"};

        info("Precondition: create new participant");
        participant = precondition();

        logStep("Login the system, navigate to My Profile and go to Employment section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);

        logStep("Click [Edit] of any Employment Record");
        detailsPage.editEmploymentSelfService();

        logStep("Change all data");
        ParticipantAddEmploymentForm emplPage = new ParticipantAddEmploymentForm(participant.getFirstName());
        settersChange();
        emplPage.addEmployRecord(participant, dataEdit);

        logStep("Save changes");
        emplPage.clickButton(Buttons.Save);

        logResult("All changes are saved.");
        ParticipantDetailSteps.validateEmployment(participant, dataEdit);
    }

    /**
     * Participant setters.
     */
    private void settersChange() {
        String employerName = "Schersal";
        String jobTitle = "Owner";
        String participantAccount = "King Sun";
        this.participant.setNewAddress(new Address());
        this.participant.setEmployerName(employerName);
        this.participant.setJobTitle(jobTitle);
        this.participant.setParticipantAccount(participantAccount);
    }
}
