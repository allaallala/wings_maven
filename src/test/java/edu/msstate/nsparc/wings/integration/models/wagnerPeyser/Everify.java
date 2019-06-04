package edu.msstate.nsparc.wings.integration.models.wagnerPeyser;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Everify object
 * Created by a.vnuchko on 29.04.2016.
 */
public class Everify {
    //User permission fields
    private Boolean everifyCreate;
    private Boolean everifyView;
    private Boolean everifyViewEditButton;
    private Boolean everifyViewAuditButton;
    private Boolean everifyViewDeleteButton;
    private Boolean everifyEdit;
    private Boolean everifyEditCurrentDocs;
    private Boolean everifyEditEstablishmentSection;
    private Boolean everifyEditDateSelection;
    private Boolean everifyEditCaseNumberInput;
    private Boolean everifyCertificationLetter;
    private Boolean everifyReferralNotification;
    private static final String EVERIFY_CREATE = "everifyCreate";
    private static final String EVERIFY_VIEW = "everifyView";
    private static final String EVERIFY_VIEW_EDIT = "everifyViewEditButton";
    private static final String EVERIFY_VIEW_AUDIT = "everifyViewAuditButton";
    private static final String EVERIFY_VIEW_DELETE = "everifyViewDeleteButton";
    private static final String EVERIFY_EDIT = "everifyEdit";
    private static final String EVERIFY_EDIT_DOCS = "everifyEditCurrentDocs";
    private static final String EVERIFY_EDIT_ESTABLISHMENT = "everifyEditEstablishmentSection";
    private static final String EVERIFY_EDIT_DATE_SELECTION = "everifyEditDateSelection";
    private static final String EVERIFY_EDIT_CASE_NUMBER = "everifyEditCaseNumberInput";
    private static final String EVERIFY_CERTIFICATION = "everifyCertificationLetter";
    private static final String EVERIFY_REFERRAL_NOTIFICATION = "everifyReferralNotification";

    //Generate fields
    private String documentIdentityType;
    private String documentIdentityTypeNumber;
    private String documentEmploymentType;
    private String documentEmploymentTypeNumber;
    private String everifyDate;
    private String caseNumber;
    private String statusDate;
    private String status;
    private String workerStatus;


    /**
     * Default constructor, defines user permissions.
     * @param role - user role.
     */
    public Everify(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        everifyCreate = Boolean.valueOf(prop.getProperty(EVERIFY_CREATE));
        everifyView = Boolean.valueOf(prop.getProperty(EVERIFY_VIEW));
        everifyViewEditButton = Boolean.valueOf(prop.getProperty(EVERIFY_VIEW_EDIT));
        everifyViewAuditButton = Boolean.valueOf(prop.getProperty(EVERIFY_VIEW_AUDIT));
        everifyViewDeleteButton = Boolean.valueOf(prop.getProperty(EVERIFY_VIEW_DELETE));
        everifyEdit = Boolean.valueOf(prop.getProperty(EVERIFY_EDIT));
        everifyEditCurrentDocs = Boolean.valueOf(prop.getProperty(EVERIFY_EDIT_DOCS));
        everifyEditEstablishmentSection = Boolean.valueOf(prop.getProperty(EVERIFY_EDIT_ESTABLISHMENT));
        everifyEditDateSelection = Boolean.valueOf(prop.getProperty(EVERIFY_EDIT_DATE_SELECTION));
        everifyEditCaseNumberInput = Boolean.valueOf(prop.getProperty(EVERIFY_EDIT_CASE_NUMBER));
        everifyCertificationLetter = Boolean.valueOf(prop.getProperty(EVERIFY_CERTIFICATION));
        everifyReferralNotification = Boolean.valueOf(prop.getProperty(EVERIFY_REFERRAL_NOTIFICATION));
    }

    /**
     * Default constructor
     */
    public Everify() {
        documentIdentityType = "School ID Card";
        documentIdentityTypeNumber = "123";
        documentEmploymentType = "Certification of Birth Abroad";
        documentEmploymentTypeNumber = "123";
        everifyDate = CommonFunctions.getYesterdayDate();
        caseNumber = "12345";
        statusDate = CommonFunctions.getYesterdayDate();
        status = "Authorized";
        workerStatus = "United States Citizen";
    }

    public String getDocumentIdentityType() {
        return documentIdentityType;
    }

    public String getDocumentIdentityTypeNumber() {
        return documentIdentityTypeNumber;
    }

    public String getDocumentEmploymentType() {
        return documentEmploymentType;
    }

    public String getDocumentEmploymentTypeNumber() {
        return documentEmploymentTypeNumber;
    }

    public String getEverifyDate() {
        return everifyDate;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkerStatus() {
        return workerStatus;
    }

    public void setDocumentIdentityType(String newDocIdentityType) {
        this.documentIdentityType = newDocIdentityType;
    }

    public void setDocumentEmploymentType(String newDocEmploymentType) {
        this.documentEmploymentType = newDocEmploymentType;
    }

    public void setDocumentIdentityTypeNumber(String newDocIdentityTypeNumber) {
        this.documentIdentityTypeNumber = newDocIdentityTypeNumber;
    }

    public void setDocumentEmploymentTypeNumber(String newDocEmploymentTypeNumber) {
        this.documentEmploymentTypeNumber = newDocEmploymentTypeNumber;
    }

    public void setEverifyDate(String newDate) {
        this.everifyDate = newDate;
    }

    public void setStatusDate(String newStatusDate) {
        this.statusDate = newStatusDate;
    }

    public void setCaseNumber(String newCaseNumber) {
        this.caseNumber = newCaseNumber;
    }

    public Boolean getEverifyCreate() {
        return everifyCreate;
    }

    public Boolean getEverifyView() {
        return everifyView;
    }

    public Boolean getEverifyViewEditButton() {
        return everifyViewEditButton;
    }

    public Boolean getEverifyViewAuditButton() {
        return everifyViewAuditButton;
    }

    public Boolean getEverifyViewDeleteButton() {
        return everifyViewDeleteButton;
    }

    public Boolean getEverifyEdit() {
        return everifyEdit;
    }

    public Boolean getEverifyEditCurrentDocs() {
        return everifyEditCurrentDocs;
    }

    public Boolean getEverifyEditEstablishmentSection() {
        return everifyEditEstablishmentSection;
    }

    public Boolean getEverifyEditDateSelection() {
        return everifyEditDateSelection;
    }

    public Boolean getEverifyEditCaseNumberInput() {
        return everifyEditCaseNumberInput;
    }

    public Boolean getEverifyCertificationLetter() {
        return everifyCertificationLetter;
    }

    public Boolean getEverifyReferralNotification() {
        return everifyReferralNotification;
    }
}
