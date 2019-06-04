package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Login with this account and create participant with all fields filled.
 * Created by a.vnuchko on 17.01.2017.
 */

@TestCase(id = "WINGS-11233")
public class TC_36_06_Account_All_Fields_Filled extends TC_36_01_Account_General_Create {

    public void main() {
        fillParticipantInfoDontComplete(true, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        Participant participant = new Participant();

        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(Constants.TRUE, Constants.FALSE);
        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue();
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().fillEmploymentPrefAndContinue();
        new MilitaryRecordSSForm().fillMilitarySelfServicePageAndContinue();
        new AcademicRecordSSForm().fillEducationSelfServicePageAndContinue(participant);
        new EmploymentRecordSSForm().fillEmploymentSelfServicePageAndSave(participant);

        new ParticipantHomePage(Constants.PARTICIPANT_SS).checkInternalError();

    }
}
