package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Check,
 * that nessessary info is displayed in the employment section.
 * Created by a.vnuchko on 18.01.2017.
 */

@TestCase(id = "WINGS-11236")
public class TC_36_09_Account_Employment_Records extends TC_36_01_Account_General_Create {

    public void main() {
        String[] dataCheck = {};
        fillParticipantInfoDontComplete(true, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Start fill all required fields with valid data. Fill all required fields with valid data until "
                + "Employment page appears.");
        Participant participant = new Participant();

        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(Constants.TRUE, Constants.FALSE);
        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue();
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().clickContinue();
        logStep("Add any Employment record.");
        new MilitaryRecordSSForm().fillMilitarySelfServicePageAndContinue();
        new AcademicRecordSSForm().clickContinue();
        logStep("Complete registration.");
        new EmploymentRecordSSForm().fillEmploymentSelfServicePageAndSave(participant);

        logResult("New account is created. All employment information is saved and displayed in the Employment "
                + "section of the My Profile page");
        new ParticipantHomePage("participant SS").openMyProfile();
        ParticipantDetailSteps.validateEmployment(participant);
    }
}
