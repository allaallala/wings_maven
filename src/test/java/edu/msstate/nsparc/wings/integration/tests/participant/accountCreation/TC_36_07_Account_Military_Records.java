package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Create a participant with a fullfilled military record. Check, that nessessary info
 * is displayed in the Military Section.
 * Created by a.vnuchko on 17.01.2017.
 */

@TestCase(id = "WINGS-11234")
public class TC_36_07_Account_Military_Records extends TC_36_01_Account_General_Create {

    public void main() {
        fillParticipantInfoDontComplete(true, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Start fill all required fields with valid data. In the Veteran option, select Yes.");
        Participant participant = new Participant();

        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(Constants.TRUE, Constants.FALSE);
        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue();
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().clickContinue();
        new MilitaryRecordSSForm().fillMilitarySelfServicePageAndContinue();
        new AcademicRecordSSForm().clickContinue();
        new EmploymentRecordSSForm().clickSave();

        logResult("New account is created. All Military information is saved and displayed in "
                + "the My Profile in the Military History section. Appropriate OSOCs are added to the "
                + "PARTICIPANT_OSOC_INTEREST table in the Database.");
        new ParticipantHomePage(Constants.PARTICIPANT_SS);
        String name = ParticipantSqlFunctions.getOsocNameParticipantName(participant.getFirstName()); //TODO is not displayed in the DB.
    }
}
