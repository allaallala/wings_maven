package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10613")
public class TC_05_01_Participant_Search extends BaseWingsTest {


    public void main() {

        info("Creating Participant record for Search");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);

        //sub-task 3053
        logStep("Type Participant Full SSN");
        ParticipantSearchForm searchForm = ParticipantNavigationSteps.openParticipantSearchPage(Roles.STAFF);
        searchForm.inputSSN(participant.getSsn());

        logStep("Click on Search button");
        searchForm.clickButton(Buttons.Search);

        logStep("Check that Participant is found");
        searchForm.checkParticipantFound();

        BaseNavigationSteps.logout();
        logEnd();
    }
}
