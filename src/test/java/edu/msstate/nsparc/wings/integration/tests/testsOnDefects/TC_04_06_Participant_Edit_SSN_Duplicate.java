package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10592")
public class TC_04_06_Participant_Edit_SSN_Duplicate extends BaseWingsTest {


    //sub-task WINGS-2643
    public void main() {
        String participantAccount = AccountUtils.getParticipantAccount();

        info("Creating first Participant");
        Participant firstParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(firstParticipant, Constants.TRUE, Constants.FALSE);
        AdvancedSqlFunctions.resetAccount(participantAccount);
        info("Creating second Participant");
        Participant secondParticipant = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(secondParticipant, Constants.TRUE, Constants.FALSE);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Find Participant record and open it");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(firstParticipant);
        participantSearchForm.openFirstSearchResult();
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();

        logStep("Edit SSN to already existing");
        participantDetailsForm.editPersonalInformation();
        ParticipantEditForm participantEditForm = new ParticipantEditForm();
        participantEditForm.inputSsn(secondParticipant.getSsn());

        logStep("Press 'Save Changes' button and validate, that error message is displayed");
        participantEditForm.clickButton(Buttons.Save);
        assertTrue("Error message wasn't displayed", participantEditForm.checkErrorPresent());

        logEnd();
    }
}
