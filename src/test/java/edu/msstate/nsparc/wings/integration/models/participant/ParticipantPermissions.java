package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

public class ParticipantPermissions {
    private Boolean ppCreate;
    private Boolean ppCreateEmploymentHistory;
    private Boolean ppView;
    private Boolean ppEditFullSSN;
    private Boolean ppEditReferralButton;
    private Boolean ppEditPersonalInformationButton;
    private Boolean ppEditConvertVetButton;
    private Boolean ppEditConvertMSNGButton;
    private Boolean ppEditContactInfoButton;
    private Boolean ppEditAdditionalInfoButton;
    private Boolean ppEditVeteranInfoButton;
    private Boolean ppEditVetEligibilityOption;
    private Boolean ppEditAcademicHistoryButton;
    private Boolean ppEditEditAcademicHistoryButton;
    private Boolean ppEditAddAcademicHistoryButton;
    private Boolean ppEditRemoveAcademicHistoryButton;
    private Boolean ppEditVerifyAcademicHistoryButton;
    private Boolean ppEditUnverifyAcademicHistoryButton;
    private Boolean ppEditAddCertifications;
    private Boolean ppEditRemoveCertifications;
    private Boolean ppEditAddEmploymentHistoryButton;
    private Boolean ppEditEditEmploymentHistoryTradeButton;
    private Boolean ppEditEditEmploymentHistoryButton;
    private Boolean ppEditRemoveEmploymentHistoryTradeButton;
    private Boolean ppEditRemoveEmploymentHistoryButton;
    private Boolean ppEditVerifyEmploymentHistory;
    private Boolean ppEditUnVerifyEmploymentHistory;
    private Boolean ppEditEditEmploymentPreferencesButton;
    private Boolean ppEditEditAccessMSInfoButton;
    private Boolean ppEditClearAccessMSUsernameButton;
    private Boolean ppEditSpecialProgramms;
    private Boolean ppEditOperationsButton;
    private Boolean ppEditNotContactButton;
    private Boolean ppEditNotContactUndoButton;
    private Boolean ppEditManualExitButton;
    private Boolean ppEditGapServiceButton;
    private static final String PP_CREATE = "ppCreate";
    private static final String PP_CREATE_EMPLOYMENT_HISTORY = "ppCreateEmploymentHistory";
    private static final String PP_VIEW = "ppView";
    private static final String PP_EDIT_FULL_SSN = "ppEditFullSSN";
    private static final String PP_EDIT_REFERRAL = "ppEditReferralButton";
    private static final String PP_EDIT_PERSONAL_INFORMATION = "ppEditPersonalInformationButton";
    private static final String PP_EDIT_CONVERT_VETERAN = "ppEditConvertVetButton";
    private static final String PP_EDIT_CONVERT_MSNG = "ppEditConvertMSNGButton";
    private static final String PP_EDIT_CONTACT_INFORMATION = "ppEditContactInfoButton";
    private static final String PP_EDIT_ADDITIONAL_INFORMATION = "ppEditAdditionalInfoButton";
    private static final String PP_EDIT_VETERAN_INFORMATION = "ppEditVeteranInfoButton";
    private static final String PP_EDIT_VETERAN_ELIGIBILITY = "ppEditVetEligibilityOption";
    private static final String PP_EDIT_ACADEMIC_HISTORY = "ppEditAcademicHistoryButton";
    private static final String PP_EDIT_EDIT_ACADEMIC_HISTORY = "ppEditEditAcademicHistoryButton";
    private static final String PP_EDIT_ADD_ACADEMIC_HISTORY = "ppEditAddAcademicHistoryButton";
    private static final String PP_EDIT_REMOVE_ACADEMIC_HISTORY = "ppEditRemoveAcademicHistoryButton";
    private static final String PP_EDIT_VERIFY_ACADEMIC_HISTORY = "ppEditVerifyAcademicHistoryButton";
    private static final String PP_EDIT_UNVERIFY_ACADEMIC_HISTORY = "ppEditUnverifyAcademicHistoryButton";
    private static final String PP_EDIT_ADD_CERTIFICATIONS = "ppEditAddCertifications";
    private static final String PP_EDIT_REMOVE_CERTIFICATIONS = "ppEditRemoveCertifications";
    private static final String PP_EDIT_ADD_EMPLOYMENT_HISTORY = "ppEditAddEmploymentHistoryButton";
    private static final String PP_EDIT_EDIT_EMPLOYMENT_HISTORY_TRADE = "ppEditEditEmploymentHistoryTradeButton";
    private static final String PP_EDIT_EDIT_EMPLOYMENT_HISTORY = "ppEditEditEmploymentHistoryButton";
    private static final String PP_EDIT_REMOVE_EMPLOYMENT_HISTORY_TRADE = "ppEditRemoveEmploymentHistoryTradeButton";
    private static final String PP_EDIT_REMOVE_EMPLOYMENT_HISTORY = "ppEditRemoveEmploymentHistoryButton";
    private static final String PP_EDIT_VERIFY_EMPLOYMENT_HISTORY = "ppEditVerifyEmploymentHistory";
    private static final String PP_EDIT_UNVERIFY_EMPLOYMENT_HISTORY = "ppEditUnVerifyEmploymentHistory";
    private static final String PP_EDIT_EDIT_EMPLOYMENT_PREFERENCES = "ppEditEditEmploymentPreferencesButton";
    private static final String PP_EDIT_EDIT_ACCESS_MS_INFO = "ppEditEditAccessMSInfoButton";
    private static final String PP_EDIT_CLEAR_ACCESS_MS_USERNAME = "ppEditClearAccessMSUsernameButton";
    private static final String PP_EDIT_SPECIAL_PROGRAMMS = "ppEditSpecialProgramms";
    private static final String PP_EDIT_OPERATIONS = "ppEditOperationsButton";
    private static final String PP_EDIT_NOT_CONTACT = "ppEditNotContactButton";
    private static final String PP_EDIT_NOT_CONTACT_UNDO = "ppEditNotContactUndoButton";
    private static final String PP_EDIT_MANUAL_EXIT = "ppEditManualExitButton";
    private static final String PP_EDIT_GAP_SERVICE = "ppEditGapServiceButton";

    public ParticipantPermissions(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        ppCreate = prop.getBooleanProperty(PP_CREATE);
        ppCreateEmploymentHistory = prop.getBooleanProperty(PP_CREATE_EMPLOYMENT_HISTORY);
        ppView = prop.getBooleanProperty(PP_VIEW);
        ppEditFullSSN = prop.getBooleanProperty(PP_EDIT_FULL_SSN);
        ppEditReferralButton = prop.getBooleanProperty(PP_EDIT_REFERRAL);
        ppEditPersonalInformationButton = prop.getBooleanProperty(PP_EDIT_PERSONAL_INFORMATION);
        ppEditConvertVetButton = prop.getBooleanProperty(PP_EDIT_CONVERT_VETERAN);
        ppEditConvertMSNGButton = prop.getBooleanProperty(PP_EDIT_CONVERT_MSNG);
        ppEditContactInfoButton = prop.getBooleanProperty(PP_EDIT_CONTACT_INFORMATION);
        ppEditAdditionalInfoButton = prop.getBooleanProperty(PP_EDIT_ADDITIONAL_INFORMATION);
        ppEditVeteranInfoButton = prop.getBooleanProperty(PP_EDIT_VETERAN_INFORMATION);
        ppEditVetEligibilityOption = prop.getBooleanProperty(PP_EDIT_VETERAN_ELIGIBILITY);
        ppEditAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_ACADEMIC_HISTORY);
        ppEditEditAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_EDIT_ACADEMIC_HISTORY);
        ppEditAddAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_ADD_ACADEMIC_HISTORY);
        ppEditRemoveAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_REMOVE_ACADEMIC_HISTORY);
        ppEditVerifyAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_VERIFY_ACADEMIC_HISTORY);
        ppEditUnverifyAcademicHistoryButton = prop.getBooleanProperty(PP_EDIT_UNVERIFY_ACADEMIC_HISTORY);
        ppEditAddCertifications = prop.getBooleanProperty(PP_EDIT_ADD_CERTIFICATIONS);
        ppEditRemoveCertifications = prop.getBooleanProperty(PP_EDIT_REMOVE_CERTIFICATIONS);
        ppEditAddEmploymentHistoryButton = prop.getBooleanProperty(PP_EDIT_ADD_EMPLOYMENT_HISTORY);
        ppEditEditEmploymentHistoryTradeButton = prop.getBooleanProperty(PP_EDIT_EDIT_EMPLOYMENT_HISTORY_TRADE);
        ppEditEditEmploymentHistoryButton = prop.getBooleanProperty(PP_EDIT_EDIT_EMPLOYMENT_HISTORY);
        ppEditRemoveEmploymentHistoryTradeButton = prop.getBooleanProperty(PP_EDIT_REMOVE_EMPLOYMENT_HISTORY_TRADE);
        ppEditRemoveEmploymentHistoryButton = prop.getBooleanProperty(PP_EDIT_REMOVE_EMPLOYMENT_HISTORY);
        ppEditVerifyEmploymentHistory = prop.getBooleanProperty(PP_EDIT_VERIFY_EMPLOYMENT_HISTORY);
        ppEditUnVerifyEmploymentHistory = prop.getBooleanProperty(PP_EDIT_UNVERIFY_EMPLOYMENT_HISTORY);
        ppEditEditEmploymentPreferencesButton = prop.getBooleanProperty(PP_EDIT_EDIT_EMPLOYMENT_PREFERENCES);
        ppEditEditAccessMSInfoButton = prop.getBooleanProperty(PP_EDIT_EDIT_ACCESS_MS_INFO);
        ppEditClearAccessMSUsernameButton = prop.getBooleanProperty(PP_EDIT_CLEAR_ACCESS_MS_USERNAME);
        ppEditSpecialProgramms = prop.getBooleanProperty(PP_EDIT_SPECIAL_PROGRAMMS);
        ppEditOperationsButton = prop.getBooleanProperty(PP_EDIT_OPERATIONS);
        ppEditNotContactButton = prop.getBooleanProperty(PP_EDIT_NOT_CONTACT);
        ppEditNotContactUndoButton = prop.getBooleanProperty(PP_EDIT_NOT_CONTACT_UNDO);
        ppEditManualExitButton = prop.getBooleanProperty(PP_EDIT_MANUAL_EXIT);
        ppEditGapServiceButton = prop.getBooleanProperty(PP_EDIT_GAP_SERVICE);
    }

    //Participant permissions.

    public Boolean getParticipantCreate() {
        return ppCreate;
    }

    public Boolean getPpCreateEmploymentHistory() {
        return ppCreateEmploymentHistory;
    }

    public Boolean getPpView() {
        return ppView;
    }

    public Boolean getPpEditFullSSN() {
        return ppEditFullSSN;
    }

    public Boolean getPpEditReferralButton() {
        return ppEditReferralButton;
    }

    public Boolean getPpEditPersonalInformationButton() {
        return ppEditPersonalInformationButton;
    }

    public Boolean getPpEditConvertVetButton() {
        return ppEditConvertVetButton;
    }

    public Boolean getPpEditConvertMSNGButton() {
        return ppEditConvertMSNGButton;
    }

    public Boolean getPpEditContactInfoButton() {
        return ppEditContactInfoButton;
    }

    public Boolean getPpEditAdditionalInfoButton() {
        return ppEditAdditionalInfoButton;
    }

    public Boolean getPpEditVeteranInfoButton() {
        return ppEditVeteranInfoButton;
    }

    public Boolean getPpEditVetEligibilityOption() {
        return ppEditVetEligibilityOption;
    }

    public Boolean getPpEditAcademicHistoryButton() {
        return ppEditAcademicHistoryButton;
    }

    public Boolean getPpEditEditAcademicHistoryButton() {
        return ppEditEditAcademicHistoryButton;
    }

    public Boolean getPpEditAddAcademicHistoryButton() {
        return ppEditAddAcademicHistoryButton;
    }

    public Boolean getPpEditRemoveAcademicHistoryButton() {
        return ppEditRemoveAcademicHistoryButton;
    }

    public Boolean getPpEditVerifyAcademicHistoryButton() {
        return ppEditVerifyAcademicHistoryButton;
    }

    public Boolean getPpEditUnverifyAcademicHistoryButton() {
        return ppEditUnverifyAcademicHistoryButton;
    }

    public Boolean getPpEditAddCertifications() {
        return ppEditAddCertifications;
    }

    public Boolean getPpEditRemoveCertifications() {
        return ppEditRemoveCertifications;
    }

    public Boolean getPpEditAddEmploymentHistoryButton() {
        return ppEditAddEmploymentHistoryButton;
    }

    public Boolean getPpEditEditEmploymentHistoryTradeButton() {
        return ppEditEditEmploymentHistoryTradeButton;
    }

    public Boolean getPpEditEditEmploymentHistoryButton() {
        return ppEditEditEmploymentHistoryButton;
    }

    public Boolean getPpEditRemoveEmploymentHistoryTradeButton() {
        return ppEditRemoveEmploymentHistoryTradeButton;
    }

    public Boolean getPpEditRemoveEmploymentHistoryButton() {
        return ppEditRemoveEmploymentHistoryButton;
    }

    public Boolean getPpEditVerifyEmploymentHistory() {
        return ppEditVerifyEmploymentHistory;
    }

    public Boolean getPpEditUnVerifyEmploymentHistory() {
        return ppEditUnVerifyEmploymentHistory;
    }

    public Boolean getPpEditEditEmploymentPreferencesButton() {
        return ppEditEditEmploymentPreferencesButton;
    }

    public Boolean getPpEditEditAccessMSInfoButton() {
        return ppEditEditAccessMSInfoButton;
    }

    public Boolean getPpEditClearAccessMSUsernameButton() {
        return ppEditClearAccessMSUsernameButton;
    }

    public Boolean getPpEditSpecialProgramms() {
        return ppEditSpecialProgramms;
    }

    public Boolean getPpEditOperationsButton() {
        return ppEditOperationsButton;
    }

    public Boolean getPpEditNotContactButton() {
        return ppEditNotContactButton;
    }

    public Boolean getPpEditNotContactUndoButton() {
        return ppEditNotContactUndoButton;
    }

    public Boolean getPpEditManualExitButton() {
        return ppEditManualExitButton;
    }

    public Boolean getPpEditGapServiceButton() {
        return ppEditGapServiceButton;
    }
}
