package edu.msstate.nsparc.wings.integration.steps.ParticipantSteps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.*;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.*;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import framework.CommonFunctions;
import framework.Logger;

import static edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps.logout;

public class ParticipantCreationSteps extends BaseWingsSteps {

    //STAFF
    private static void createBaseParticipant(Participant participant, Boolean isVeteran, Boolean isNgrd, Boolean isDriver) {
        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, isVeteran, isNgrd);

        if (isVeteran) {
            new MilitaryInformationForm().fillInEligibleVeteranAndContinue();
        }

        if (isNgrd) {
            new MilitaryInformationForm().fillInNotEligibleVeteranAndContinue();
        }

        new ContactInformationForm().fillThirdPage(participant);
        new AdditionalInformationForm().fillFourthPageAndContinue(isDriver);
        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();
        new EmploymentHistoryForm().clickContinue();
        new EmploymentPreferencesForm().clickContinue();
        AccessUsernameForm accessUsernameForm = new AccessUsernameForm();
        accessUsernameForm.fillMsAccessAccountAndSave(participant);

        accessUsernameForm.clickButton(Buttons.Done);
    }

    public static void createParticipantRoleWithEmploymentHistory(User user, Participant participant, Boolean ifVeteran) {
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, ifVeteran, Constants.FALSE);
        if (ifVeteran) {
            new MilitaryInformationForm().fillInEligibleVeteranAndContinue();
        }
        new ContactInformationForm().fillThirdPage(participant);
        new AdditionalInformationForm().fillFourthPageNotDriverAndContinue();

        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();

        if (user.getParticipant().getParticipantPermissions().getPpCreateEmploymentHistory()) {
            Logger.getInstance().info("Create with employment history");
            new EmploymentHistoryForm().addEmploymentHistory();
            ParticipantAddEmploymentForm participantAddEmploymentForm = new ParticipantAddEmploymentForm();
            participantAddEmploymentForm.addRecordStaff();
            new EmploymentHistoryForm().clickButton(Buttons.Continue);
        } else {
            new EmploymentHistoryForm().clickButton(Buttons.Continue);
        }
        new EmploymentPreferencesForm().clickContinue();
        new AccessUsernameForm().fillMsAccessAccountAndSave(participant);
        BaseNavigationSteps.logout();


    }

    public static void createParticipantDriver(Participant participant, Boolean isVeteran, Boolean isNgrd) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        createBaseParticipant(participant, isVeteran, isNgrd, true);
        logout();
    }

    public static void createParticipant(Participant participant, Boolean isVeteran, Boolean isNgrd, Boolean isDriver) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        createBaseParticipant(participant, isVeteran, isNgrd, isDriver);
        logout();
    }

    public static void createRegularParticipant(Participant participant, Boolean isDriver) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        createBaseParticipant(participant, false, false, isDriver);
        logout();
    }

    public static void createVeteranParticipantNotDriver(Participant participant) {
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        createBaseParticipant(participant, true, false, true);
        logout();
    }

    public static void createIncompleteParticipant(Participant participant, Boolean isVeteran, Boolean isNgrd) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);
        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        PersonalInformationForm personalInformationForm = new PersonalInformationForm();
        personalInformationForm.fillSecondPage(participant, isVeteran, isNgrd);
        personalInformationForm.saveForLater();
        logout();

    }

    public static void completeIncompleteParticipant(Participant participant, Boolean isVeteran, Boolean isNgrd) {
        new PersonalInformationForm().clickContinue();

        if (isVeteran) {
            new MilitaryInformationForm().fillInEligibleVeteranAndContinue();
        }
        if (isNgrd) {
            new MilitaryInformationForm().fillInNotEligibleVeteranAndContinue();
        }
        new ContactInformationForm().fillThirdPage(participant);
        new AdditionalInformationForm().fillFourthPageNotDriverAndContinue();
        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();
        new EmploymentHistoryForm().skipEmploymentHistorySection();
        new EmploymentPreferencesForm().clickContinue();
        new AccessUsernameForm().fillMsAccessAccountAndSave(participant);
    }

    public static void createParticipantWithEmploymentByUser(Participant participant, User user) {

        //(!) Create new participant, if user has permissions to do it.
        if (user.getParticipant().getParticipantPermissions().getParticipantCreate()) {
            Logger.getInstance().info("Create new participant");
            createParticipantRoleWithEmploymentHistory(user, participant, false);
        }
    }





    public static void createAndPrepareParticipantForProgramOutcomes(Participant participant, boolean youth, int days) {
        // You need to create WIA Training and complete it more than 90 days prior to current date
        String type = Constants.COMPLETED;
        createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createOldWIAEnrollment(participant, youth, days);
        TrainingSteps.createOldWIATraining(participant, days);
        TrainingSteps.editWIATraining(participant, type, CommonFunctions.getDaysAgoDate(days));
    }


    //SELF SERVICE

    public static void fillFirstSSPages(Participant participant, Boolean veteran, Boolean ngrd) {
        new EligibilitySSForm().fillFirstPageSelfServicesAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new IdentificationSSForm().fillSecondPageSelfServicesAndContinue(participant.getFirstName(), participant.getLastName());
        new ClassificationSSForm().fillCertificationSectionAndContinue(veteran, ngrd);
    }

    public static void createCompleteSSParticipant(Participant participant, Boolean veteran, Boolean ngrd) {
        fillFirstSSPages(participant, veteran, ngrd);

        new AccomplishmentsSSForm().fillAccomplishmentSectionAndContinue();
        new ContactInformationSSForm().fillContactInformationFormAndContinue(participant);
        new EmploymentPreferencesSSForm().clickContinue();

        if (veteran) {
            new MilitaryRecordSSForm().fillMilitarySelfServicePageAndContinue();
        }

        if (ngrd) {
            new MilitaryRecordSSForm().fillInNotEligibleVeteranAndContinue();
        }

        new AcademicRecordSSForm().fillEducationSelfServicePageAndContinue(participant);
        EmploymentRecordSSForm employmentRecordSSForm = new EmploymentRecordSSForm();
        employmentRecordSSForm.fillEmploymentSelfServicePageAndSave(participant);

        new ParticipantHomePage(Constants.PARTICIPANT_SS);
        logout();
    }

    public static Participant createParticipantSelfRegistration() {
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);
        detailsForm.changeHighestGradeSelfService(participant.getGrade());
        logout();
        return participant;
    }
}
