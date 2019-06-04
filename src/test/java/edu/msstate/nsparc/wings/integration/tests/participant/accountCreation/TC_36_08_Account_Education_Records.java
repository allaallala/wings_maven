package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Create a participant with a fullfilled education record. Check,
 * that nessessary info is displayed in the education section.
 * Created by a.vnuchko on 18.01.2017.
 */

@TestCase(id = "WINGS-11235")
public class TC_36_08_Account_Education_Records extends TC_36_01_Account_General_Create {

    public void main() {
        fillParticipantInfoDontComplete(true, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Start fill all required fields with valid data. Fill all required fields with valid data until "
                + "Educatin page appears.");
        Participant participant = new Participant();
        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(Constants.TRUE, Constants.FALSE);
        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue(participant);
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().clickContinue();
        new MilitaryRecordSSForm().clickContinue();
        new AcademicRecordSSForm().fillEducationSelfServicePageAndContinue(participant);
        new EmploymentRecordSSForm().clickSave();

        logResult("New account is created. All Education information "
                + "is saved and displayed in the Education section of the My Profile page");
        ParticipantHomePage homePage = new ParticipantHomePage("participant S-S");
        homePage.openMyProfile();
        ParticipantDetailSteps.validateEducation(participant);
    }
}
