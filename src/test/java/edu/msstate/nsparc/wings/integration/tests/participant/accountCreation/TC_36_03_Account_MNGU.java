package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Login with this account and create a participant, that is not a veteran, but a mississippi national guard.
 * Created by a.vnuchko on 16.01.2017.
 */

@TestCase(id = "WINGS-11231")
public class TC_36_03_Account_MNGU extends TC_36_01_Account_General_Create {
    public void main() {
        fillParticipantInfoDontComplete(false, true);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("On the Classification page select 'No' from the 'Are you a veteran?' radio-button and select 'Yes' from the Mississippi National Guard radio-button");
        Participant participant = new Participant();


        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(Constants.FALSE, Constants.TRUE);
        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue();
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().clickContinue();
        new MilitaryRecordSSForm().clickContinue();
        new AcademicRecordSSForm().clickContinue();
        new EmploymentRecordSSForm().clickSave();


        logResult("New account is created, it is possible to login to the WINGS and work");
        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginParticipant();
        new ParticipantHomePage(Constants.PARTICIPANT_SS);
    }
}
