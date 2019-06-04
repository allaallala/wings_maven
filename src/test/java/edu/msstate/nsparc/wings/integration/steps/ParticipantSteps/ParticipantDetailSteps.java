package edu.msstate.nsparc.wings.integration.steps.ParticipantSteps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.*;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.EditAcademicHistoryForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import framework.CommonFunctions;
import framework.Logger;
import org.testng.Assert;

import java.util.List;

public class ParticipantDetailSteps extends BaseWingsSteps  {

    public static void checkEditButtons() {
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ELIGIBILITY);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_IDENTIFICATION);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CLASSIFICATION);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ACCOMPLISHMENTS);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CONTACTS);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_EMPLOYMENTS);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_MILITARY);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_DOCUMENTS);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_EDUCATION);
        new BaseParticipantSsDetailsForm().checkButtons(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_PREFERENCES);
    }

    public static void addEmploymentParticipantSelfService(Participant participant) {
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();
        BaseParticipantSsDetailsForm detailsPage = new BaseParticipantSsDetailsForm(participant);
        detailsPage.addPreviousJobSelfServices();
        BaseNavigationSteps.logout();
    }

    public static void addAcademicRecordSelfService(Participant participant) {
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.openMyProfile();
        BaseParticipantSsDetailsForm detailsForm = new BaseParticipantSsDetailsForm(participant);
        detailsForm.changeHighestGradeSelfService(participant.getGrade());
        detailsForm.addAcademicRecordSelfService();
        BaseNavigationSteps.logout();
    }

    public static void changeDriversLicense(Participant participant) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.editAdditionalInfo();
        ParticipantEditForm participantEditForm = new ParticipantEditForm();
        participantEditForm.checkNoDriverLicense();
        participantEditForm.clickButton(Buttons.Save);
        BaseNavigationSteps.logout();
    }

    public static void addAcademicRecord(Participant participant, String date) {
        String academicRecord = "Bachelor's Degree";
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.editAcademicHistory();
        EditAcademicHistoryForm editAcademicHistoryForm = new EditAcademicHistoryForm();
        editAcademicHistoryForm.selectDegree(participant.getGrade());
        editAcademicHistoryForm.clickButton(Buttons.Save);

        detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandAcademicHistory();
        detailsForm.clickAddAcademicRecord();
        ParticipantAddAcademicRecordForm participantAddAcademicRecordForm = new ParticipantAddAcademicRecordForm();
        participantAddAcademicRecordForm.addRecord(date, academicRecord, false);
        if (participantAddAcademicRecordForm.isPresent(BaseWingsForm.BaseButton.SAVE_CHANGES)) {
            participantAddAcademicRecordForm.clickButton(Buttons.Save);
        }
        participantAddAcademicRecordForm.clickButton(Buttons.Add);
        BaseNavigationSteps.logout();
    }

    public static void addEmployment(Participant participant, PreviousJob job) {
        BaseNavigationSteps.openAddNewEmploymentRecordForm(participant);
        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        addEmploymentForm.addRecord(job);
        BaseNavigationSteps.logout();
    }

    public static void addEmployment(Participant participant, List<PreviousJob> previousJobs) {
        BaseNavigationSteps.openAddNewEmploymentRecordForm(participant);
        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        for (PreviousJob job : previousJobs) {
            Logger.getInstance().info(String.format("Adding previous job - '%1$s'", job.getJobTitle()));
            addEmploymentForm.addRecord(job);
            BaseNavigationSteps.expandAndOpenNewEmploymentRecordForm();
        }
        BaseNavigationSteps.logout();
    }

    public static void uploadDocumentApprove(Participant participant, String filePath, String type) {
        Logger.getInstance().info("Login to the System");
        BaseNavigationSteps.loginParticipant();
        Logger.getInstance().info("Navigate to My Profile");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.openMyProfile();
        BaseParticipantSsDetailsForm detailsPage = new BaseParticipantSsDetailsForm(participant);
        Logger.getInstance().info("Add new one with required type and Save Changes");
        ParticipantDetailSteps.uploadDocument(filePath, type);
        BaseNavigationSteps.logout();
        Logger.getInstance().info("Log in to the system as admin1/admin1 to approve uploaded documents");
        LoginForm loginPage = new LoginForm();
        loginPage.loginAdmin1();
    }

    public static void attachLwdaComplete(User user, Participant participant) {
        if (user.getRole().equals(Roles.LWDASTAFF) || user.getRole().equals(Roles.WIOAPROVIDER) || user.getRole().equals(Roles.ADMIN)) {
            //It's necessary to check LWDA staff and WIOA provider, because participant should be unlocked to the partner center and LWIA.

            BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
            ParticipantSearchForm searchPage = new ParticipantSearchForm();
            searchPage.performSearchAndOpen(participant);
            BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
            participantDetailsForm.attachLwdaWioa(participant);
            BaseNavigationSteps.logout();
        }
    }

    public static void validateParticipant(User user, Participant participant) {
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.validateParticipantInformation(participant);
        if (user.getParticipant().getParticipantPermissions().getPpCreateEmploymentHistory()) {
            detailsPage.expandEmploymentHistory();
            Assert.assertTrue(detailsPage.getPreviousJobPageText().contains("Cook at Automation"));
        }
    }

    public static void checkEditFunctionality(User user, Participant participant) {
        final String emailAddress = CommonFunctions.getTimestamp() + "@gmail.com";
        final String militaryBranch = "Marine Corps";
        final String beginDate = CommonFunctions.getDaysAgoDate(Constants.DAYS_YEAR);
        final String endDate = CommonFunctions.getYesterdayDate();
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();

        //(!) If user has permission to edit ssn.
        Logger.getInstance().info("Check edit full SSN");
        detailsPage.editSsn(user, participant);

        //(!) If user has permission to view quick referral button
        Logger.getInstance().info("Check quick referral button");
        detailsPage.checkReferralPresent(user.getParticipant().getParticipantPermissions().getPpEditReferralButton());

        //(!) editPersonal
        Logger.getInstance().info("Edit personal information");
        detailsPage.editPersonal(user, participant);

        //(!) convert to Veteran
        Logger.getInstance().info("Convert to veteran");
        detailsPage.convertVeter(user);

        //(!) convert to National Guard
        Logger.getInstance().info("Convert to the national guard");
        detailsPage.convertNationalGuard(user, militaryBranch, beginDate, endDate);

        //(!) edit contact information
        Logger.getInstance().info("Edit contact information");
        detailsPage.editContactInfo(user, emailAddress);

        //(!) edit additional information
        Logger.getInstance().info("Edit additional information");
        detailsPage.editAdditionalInformation(user);

        //(!) edit veteran information
        Logger.getInstance().info("Edit veteran information");
        detailsPage.editVeteranDetails(user);

        //(!) academic history
        Logger.getInstance().info("Edit academic history (add, edit, remove, verify, unverify)");
        detailsPage.academicHistoryManipulation(user);

        //(!) certifications
        Logger.getInstance().info("Add, edit or remove certifications");
        detailsPage.certifications(user, participant);

        //(!) Employment history
        Logger.getInstance().info("Edit employment history (add, edit, remove, verify, unverify");
        detailsPage.employmentHistoryManipulation(user);

        //(!) Employment Preferences
        Logger.getInstance().info("Edit Employment Preferences");
        detailsPage.editPreferences(user, participant);

        //(!) Operations
        Logger.getInstance().info("Operations");
        detailsPage.checkOperationsEmpty(user);
    }

    public static void validateEligibilitySection(Participant participant) {
        new EligibilityInformationSsSectionForm().validateEligibilitySection(participant);
    }

    public static void validateIdentificationSection(Participant participant, Boolean base) {
        new IdentificationSsSectionForm().validateIdentificationSection(participant, base);
    }

    public static void validateClassificationSection(String disabled, String employmentStatus, String typeDisability) {
        new ClassificationInformationSsSectionForm().validateClassificationSection(disabled, employmentStatus, typeDisability);
    }

    public static void checkStatusCertificateParticipant(Participant participant, String expectedCertif) {
        new AccomplishmentsSsSectionForm().checkStatusCertificateParticipant(participant, expectedCertif);
    }

    public static void validateAccomplishmentsSection(String grade, String licenceExist) {
        new AccomplishmentsSsSectionForm().validateAccomplishmentsSection(grade, licenceExist);
    }

    public static void validateContactInformationSection(Participant participant, Boolean addParam) {
        new ContactInformationSsSectionForm().validateContactInformationSection(participant, addParam);
    }

    public static void validateResidenceMailingAddress(Participant participant) {
        new ContactInformationSsSectionForm().validateResidenceMailingAddress(participant);
    }

    public static void validatePreferences(Boolean checkAll, String hoursWeek, String desiredSalary,
                                           String distanceRelocate, String distanceCommute) {
        new PreferencesSsSectionForm().validatePreferences(checkAll, hoursWeek, desiredSalary, distanceRelocate, distanceCommute);
    }

    public static void preferencesAreNotPresent() {
        new PreferencesSsSectionForm().preferencesAreNotPresent();
    }

    public static void validateEducation(Participant participant) {
        new EducationSsSectionForm().validateEducation(participant);
    }

    public static void validateEducationCannotChanged() {
        new EducationSsSectionForm().validateEducationCannotChanged();
    }

    public static void validateEmployment(Participant participant) {
        new EmploymentSsSectionForm().validateEmployment(participant);
    }

    public static void validateEmployment(Participant participant, String[] dataCheck) {
        new EmploymentSsSectionForm().validateEmployment(participant, dataCheck);
    }

    public static void validateMilitary(String militaryType, String discharge, String serviceStart, String serviceEnd) {
        new MilitarySsSectionForm().validateMilitary(militaryType, discharge, serviceStart, serviceEnd);
    }

    public static void uploadDocument(String pathFile, String documentType) {
        new DocumentSsSectionForm().uploadDocument(pathFile, documentType);
    }

    public static void inputUploadData(String pathFile, String documentType) {
        new DocumentSsSectionForm().inputUploadData(pathFile, documentType);
    }

    public static void validateUploadedDocument(String file, String state, Integer quantity) {
        new DocumentSsSectionForm().validateUploadedDocument(file, state, quantity);
    }

    public static void validateUploadedText(Integer uploadedQuantity) {
        new DocumentSsSectionForm().validateUploadedText(uploadedQuantity);
    }

    public static String addEmployment(Participant participant, String date, boolean ended) {
        String employer;
        BaseNavigationSteps.openAddNewEmploymentRecordForm(participant);
        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        employer = addEmploymentForm.addRecord(date, ended);
        BaseNavigationSteps.logout();
        return employer;
    }

    public static void removeEducationRecord() {
        new EducationSsSectionForm().removeEducationRecord();
    }
}
