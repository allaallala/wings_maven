package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10625")
public class TC_05_09_Participant_Search_Cancel extends BaseWingsTest {


    public void main() {


        Participant participant = new Participant();

        logStep("Enter any data to all fields for searching->Cancel");
        ParticipantSearchForm participantSearchForm = ParticipantNavigationSteps.openParticipantSearchPage(Roles.STAFF);
        participantSearchForm.fillFields(participant);
        participantSearchForm.clickButton(Buttons.Cancel);

        logStep("Check, that Home page is opened");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        staffHomeForm.checkUnfinishedPartipPresent();
        staffHomeForm.checkUnresReferTablePresent();
        staffHomeForm.checkLocationHeadPresent();

        BaseNavigationSteps.logout();
        logEnd();
    }
}
