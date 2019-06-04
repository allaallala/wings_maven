package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Login with this account and try to create participant without filling required fields. Check warning message.
 * Created by a.vnuchko on 17.01.2017.
 */

@TestCase(id = "WINGS-11232")
public class TC_36_04_Account_Without_Required extends TC_36_01_Account_General_Create {

    public void main() {
        fillParticipantInfoDontComplete(false, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Don't fill in all required fields (Try to continue without required fields on each page) and create");
        Participant participant = new Participant();

        EligibilitySSForm eligibilitySSForm = new EligibilitySSForm();
        eligibilitySSForm.clickContinue();
        eligibilitySSForm.checkErrorsFirstPage();
        eligibilitySSForm.fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());


        IdentificationSSForm identificationSSForm = new IdentificationSSForm();
        identificationSSForm.clickContinue();
        identificationSSForm.checkErrorsSecondPage();
        identificationSSForm.fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());


        ClassificationSSForm classificationSSForm = new ClassificationSSForm();
        classificationSSForm.clickContinue();
        classificationSSForm.checkErrorsThirdPage();
        classificationSSForm.fillCertificationSectionAndContinue(false, false);


        AccomplishmentsSSForm accomplishmentsSSForm = new AccomplishmentsSSForm();
        accomplishmentsSSForm.clickContinue();
        accomplishmentsSSForm.checkErrorsFourthPage();
        accomplishmentsSSForm.fillAccomplishmentSectionAndContinue();

        ContactInformationSSForm contactInformationSSForm = new ContactInformationSSForm();
        contactInformationSSForm.clickContinue();
        contactInformationSSForm.checkErrorsAddressPage();
        contactInformationSSForm.fillContactInformationFormAndContinue(participant);

        new EmploymentPreferencesSSForm().clickContinue();
        new AcademicRecordSSForm().clickContinue();
        new EmploymentRecordSSForm().clickSave();
    }
}
