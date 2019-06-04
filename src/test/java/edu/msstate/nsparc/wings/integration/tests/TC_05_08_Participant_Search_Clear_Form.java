package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10622")
public class TC_05_08_Participant_Search_Clear_Form extends BaseWingsTest {


    public void main() {


        Participant participant = new Participant();

        logStep("Enter any data to all fields for searching");
        ParticipantSearchForm participantSearchForm = ParticipantNavigationSteps.openParticipantSearchPage(Roles.STAFF);
        participantSearchForm.fillFields(participant);

        logStep("Clear Form");
        participantSearchForm.clickButton(Buttons.Clear);

        logStep("Check, that all fields are empty");
        participantSearchForm.checkFields();

        //logout
        BaseNavigationSteps.logout();
        logEnd();
    }
}
