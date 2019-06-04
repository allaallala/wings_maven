package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantBaseForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.AddDocumentStaffForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreasSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * This form is opened via Participants -> Participant Profiles -> Search for record -> Open record
 */
public class BaseParticipantDetailsForm extends ParticipantBaseForm {

    private Button btnEditPersonalInformation = new Button("id=editPersonalInformation",
            "Edit Personal Information");
    private Button btnEditIdentificationInformation = new Button("id=editIdentification",
            "Edit Identification Information");
    private Button btnEditAcademicHistory = new Button("id=editAcademicHistory",
            "Edit Academic History");
    private Button btnEditAdditionalInformation = new Button("id=editAdditionalInformation",
            "Edit Academic History");
    private Button btnConvertVet = new Button(By.id("convert2Vet"), "Convert to veteran");
    private Button btnConvertNationalGuard = new Button(By.id("convert2MSNG"), "Convert to the national guard");
    private Button btnEditContactInfo = new Button(By.id("editContactInformation"), "Edit contact information");
    private RadioButton rbDriverLicense = new RadioButton(By.id("haveDriversLicense1"), "Drivers License - Yes");

    //Veteran participantSSDetails
    private String veteranXpath = "//tr[@id='veteranDetails']//tbody//td[contains(.,'%1$s')]/following-sibling::td";
    private Div dvVeteranStatus = new Div(By.xpath(String.format(veteranXpath, "Eligible Veteran Status")),
            "Eligible Veteran Status");
    private Div dvCampaignVeteran = new Div(By.xpath(String.format(veteranXpath, "Campaign Veteran")),
            "Campaign Veteran");
    private Div dvServiceMember = new Div(By.xpath(String.format(veteranXpath, "Transitioning Service Member")),
            "Transitioning Service Member");
    private Div dvMilitarySeparation = new Div(By.xpath(String.format(veteranXpath, "Date of Actual Military Separation")),
            "Date of Actual Military Separations");
    private Div dvDisabledVeteran = new Div(By.xpath(String.format(veteranXpath, "Disabled Veteran")),
            "Disabled Veteran");
    private RadioButton rbFirstMarineRecord = new RadioButton(By.id("mrselectedRadio1"),
            "First military record");
    private Button btnEditVeteranDetails = new Button("id=editVeteranDetails",
            "Edit veteran participantSSDetails");

    //Expand
    private String expandXpath = "//a[@class='expand'][contains(.,'%1$s')]";
    private Button btnExpandAdditionalInformation = new Button(By.xpath(String.format(expandXpath, "Additional Information")),
            "Expand additional information");
    private Button btnExpandVeteranDetails = new Button(By.xpath(String.format(expandXpath, "Veteran Details")),
            "Expand Veteran Details");
    private Button btnExpandUnmploymentService = new Button(By.xpath("//span[contains(.,'Unemployment Services')]"),
            "Expand Unemployment Service");
    private Button btnExpandAcademicHistory = new Button(By.xpath(String.format(expandXpath, "Academic History")),
            "Expand Academic History");
    private Button btnExpandCertifications = new Button(By.xpath(String.format(expandXpath, "Certifications")),
            "Expand Certifications");
    private Button btnExpandEmployerHistory = new Button(By.xpath(String.format(expandXpath, "Employment History")),
            "Expand Employer History");
    private Button btnExpandEmploymentPref = new Button(By.xpath(String.format(expandXpath, "Employment Preferences")),
            "Expand Employment Preferences");
    private Button btnExpandUnemploymentInfo = new Button(By.xpath(String.format(expandXpath, "Unemployment Services")),
            "Unemployment Services System Login Information");
    private Button btnExpandDocuments = new Button(By.xpath(String.format(expandXpath, "Documents")),
            "Documents");
    private Button btnExpandOperations = new Button(By.xpath(String.format(expandXpath, "Operations")),
            "Expand operations");
    private Button btnExpandTradeEnrl = new Button(By.xpath(String.format(expandXpath, "Trade Enrollments")),
            "Expand trade enrollments section");
    private Button btnExpandIEP = new Button(By.xpath(String.format(expandXpath, "Individual Employment Plans")),
            "Expand individual employment plans");
    private Button btnExpandParticipantPeriods = new Button(By.xpath(String.format(expandXpath, "Participation Periods")),
            "Expand participant periods");

    //
    private String xpath = "//tr[@id='basicInfo']//tbody//td[contains(text(),'%1$s')]";
    private String devPath = xpath + "/following-sibling::td";
    private Link lnkTitle = new Link(By.xpath("//div[@id='user-name'] | //h1//span"), "Participant name");
    private Link lnkSSN = new Link(By.xpath(String.format(xpath, "Social Security Number")), "SSN");
    private Link lnkCitizenship = new Link(By.xpath(String.format(xpath, "Legally Able to Work")), "Citizenship");
    private Link lnkDateOfBirth = new Link(By.xpath(String.format(xpath, "Date of Birth")), "Date of birth");
    private Link lnkGender = new Link(By.xpath(String.format(xpath, "Gender")), "Gender");
    private Link lnkEthnicity = new Link(By.xpath(String.format(xpath, "Ethnicity")), "Ethnicity");
    private Link lnkEmployment = new Link(By.xpath(String.format(devPath, "Employment Status")), "Employment");
    private Link lnkVeteran = new Link(By.xpath(String.format(devPath, "Veteran")), "Veteran");
    private Link lnkGuard = new Link(By.xpath(String.format(devPath, "Mississippi National Guard")),
            "Mississippi National Guard");
    private Link lnkDisabled = new Link(By.xpath(String.format(devPath, "Disabled")), "Disabled");
    private Link lnkEmail = new Link(By.xpath("//td[contains(.,'Email Address')]/a"), "Email address");
    private Link lnkWIAEnrollmentView = new Link("css=table#wiaEnrollments-results-table a",
            "WIA enrollment link");
    private Link lnkQuickReferrals = new Link("css=span#QRLinkDiv a", "Quick Referrals");
    private Link lnkUnresultedServices = new Link("css=table[id='periods-table'] a",
            "View unresulted service(s)");
    private Button btnPartnerCenter = new Button(By.xpath("//input[@value='JobCenter']/following-sibling::button"),
            "Search for partner center");
    private Button btnLwia = new Button(By.xpath("//input[@value='Lwia']/following-sibling::button"), "Search for lwia");

    private TableCell tbcAddress = new TableCell(By.xpath("//tr[@id='contactInformation']//tbody/tr[2]/td[1]"), "Address");
    private TableCell tbcGrade = new TableCell(By.xpath("//div[text()='Highest Grade Completed:']/.. | //tr[@id='academicHistory']//td//td"),
            "Highest Grade Completed");
    private TableCell tbcCertificate = new TableCell(By.xpath("//table[@id='arresults-table']//td/following-sibling::td | //a[@id='educationSection']/../..//tbody"),
            "Certificate");
    private TableCell tbcPreviousJob = new TableCell(By.xpath("//table[@id='pjresults-table'] | //a[@id='experienceSection']/../..//tbody"),
            "Previous Job");
    private TableCell tbcAccount = new TableCell(By.xpath("//tr[@id='accessMSInformation']//tbody//td[contains(text(),'Unemployment Services System Username')]/following-sibling::td"),
            "Account");
    private TableCell tbcMilitaryRecord = new TableCell(By.xpath("//table[@id='mrresults-table']/tbody/tr/td[2]"),
            "First added veteran record");

    private ComboBox cmbMiles = new ComboBox("css=select", "Within miles");
    private String miles = "1000";
    private Integer waitSec = 5;
    private String participantRecordsXpath = "css=tr[id='participationPeriods'] tbody:contains('%1$s')";
    private TableCell tbcWIAEnrollmentDetails = new TableCell("css=table[id='wiaEnrollments-results-table'] td + td",
            "WIA Enrollment Details");
    private TableCell tbcHighestGradeCompleted = new TableCell(By.xpath("//tr[@id='academicHistory']//tr[1]/td"),
            "Highest grade completed");

    private Button btnAudit = new Button("css=button#audit", "Audit");
    private Span spNotes = new Span(By.xpath("//span[@id='LinkNotesDiv']//img"), "Notes");
    private Span spnNotesCount = new Span(By.xpath("//span[@id='LinkNotesDiv']/span"), "Notes Count");

    private Button btnManualExit = new Button(By.id("manualExit"), "Manual Exit");
    private Button btnGapInService = new Button(By.id("gapInService"), "Gap in Service");
    private ComboBox cmbGapReason = new ComboBox("css=select[id='eventCode']", "Reason for Gap in Service");
    private ComboBox cmbExitReason = new ComboBox("css=select#eventCode", "Reason for Manual Exit");

    private TableCell tbcParticipationPeriods = new TableCell("css=table[id='periods-table']", "Participation Periods");
    private TableCell tbcParticipationPeriodBeginDate = new TableCell("css=table[id='periods-table'] td",
            "Participation Period Begin Date");
    private TableCell tbcOperations = new TableCell(By.xpath("//tr[@id='operations']"), "Operations");
    private TableCell tbcIEP = new TableCell(By.xpath("//table[@id='individualEmploymentPlans-results-table']//td[2]"),
            "Individual Eployment Plan added");
    private TableCell tbcTrEnrl = new TableCell(By.xpath("//table[@id='tradeEnrollments-results-table']//td[2]"),
            "Trade Enrollment Report added");
    private Div divQuickReferrals = new Div("css=div#QuickReferrals", "Quick Referrals");
    private TableCell tbcQuickReferralJobs = new TableCell("//div[@class='note-page']",
            "Quick Referral Jobs");
    private Span partPeriodHead = new Span("//tr//h2", "Participant period head");
    private Link lnkEverifyMerge = new Link(By.xpath("//img[@title='E-Verified']"), "E-Verify title");

    private String veteranStatus = "Eligible Veteran";
    private String ineligibleVeteran = "Not an Eligible Veteran";
    private String otherPerson = "Other Eligible Person";
    private String answerNo = "No";
    private String answerYes = "Yes";
    private String driverLicense = "B";
    private String editLicense = "C";
    private String ssi = "None";
    private String certificate = "MySQL";

    /**
     * Additional Information
     */
    private Div dvClassDriverLicense = new Div(By.xpath("//tr[@id='addlInfo']//td[contains(.,'Class')]/following-sibling::td"), "Email address");

    /**
     * Employment history
     */
    private String emplXpath = "//table[@id='pjresults-table']//tbody/tr[%1$d]/td[2]";
    private RadioButton rbFirstJob = new RadioButton(By.id("pjselectedRadio1"), "First previous job");
    private Div dvItems = new Div(By.xpath("//span[contains(.,'displaying all items')]"),
            "All items in the employment section");
    private Button btnAddPreviousJob = new Button(By.xpath("//button[@id='addPreviousJob'] | //button[@id='addEmployment']"),
            "Add Previous Job");
    private Button btnEditPreviosJob = new Button(By.id("editPreviousJob"), "Edit previous job");
    private Button btnVerifyPriviosJob = new Button(By.id("verifyPreviousJob"), "Verify previous job");
    private RadioButton rbPreviousJob = new RadioButton("css=table[id='pjresults-table'] input", "Previous Job record");
    private Button btnRemovePreviousJob = new Button(By.xpath("//div[@id='jobButtons']/input[3]"), "Remove Previous Job");

    /**
     * Academic History
     */
    private Button btnAddAcademicRecord = new Button(By.xpath("//button[@id='addAcademicRecord'] "
            + "| //button[@id='addEducation']"), "Add Academic Record");
    private Button btnUnverify = new Button(By.id("unverifyAcademicRecord"), "Unferify academic record");
    private Button btnVerify = new Button(By.id("verifyAcademicRecord"), "Verify academic record");
    private RadioButton rbAcademicHistory = new RadioButton(By.id("arselectedRadio1"), "First academic history record");

    /**
     * Veteran participantSSDetails
     */
    private Button btnAddMilRecord = new Button(By.id("addMilitaryRecord"), "Add military record");
    private Button btnEditMilRecord = new Button(By.id("editMilitaryRecord"), "Edit military record");
    private Button btnRemoveMilRecord = new Button(By.xpath("//input[@title='Remove']"), "Remove military record");

    //Certification
    private Button btnAddCertification = new Button(By.id("addCertification"), "Add certification");
    private Button btnAdd = new Button(By.id("add"), "Add");
    private ComboBox cbCertificationType = new ComboBox(By.id("tmpCertification.certificationTypeId"), "Certification type");
    private Button btnEditCertification = new Button(By.id("editCertification"), "Edit certification");
    private Button btnRemove = new Button(By.xpath("//input[@value='Remove']"), "Remove certification");
    private TableCell tbcCertAdded = new TableCell(By.xpath(".//table[@id='certresults-table']//td[2]"), "Added cert");
    private RadioButton rbCertRadio = new RadioButton(By.id("certselectedRadio1"), "Cert radio button");

    //Employment Preferences
    private Button btnEditEmploymentPreferences = new Button(By.id("editEmploymentPreferences"),
            "Edit employment preferences");
    private TableCell tbcAddedPreference = new TableCell(By.xpath("//table[@id='interests-results-table']/tbody//td"),
            "Added new employment preference");

    //Documents
    public static final String DOC_XPATH = "//table[@id='docresults-table']//tr[contains(.,'%1$s')]";
    private String rbDocPath = "//table[@id='docresults-table']//tr[contains(.,'%1$s')]//input";
    private String fileNameRegExp = "[^\\.pdf]{1,}";
    private Button btnAddDocument = new Button(By.id("addDocument"), "Add document");
    private Button btnEditDocument = new Button(By.id("editDocument"), "Edit Document");
    private ComboBox cmbDocumentType = new ComboBox(By.id("tempAddEditDocument.document.docType"), "Document type");
    private TextBox tbPathToFile = new TextBox(By.id("tempAddEditDocument.docFile"), "Path to file");
    private TextBox tbDocTitle = new TextBox(By.id("tempAddEditDocument.document.title"), "Document Title");

    //Other
    private Button expandWiaEnrollment = new Button(By.xpath("//a[contains(.,'WIOA Enrollments')]"),
            "Expand WIA enrollment");
    private TableCell tbcUnemployment = new TableCell(By.xpath("//tr[@id='accessMSInformation']//td[contains(.,'Unemployment')]/following-sibling::td"),
            "Unemployment service name");
    private Button btnDoNotContact = new Button(By.id("dontContact"), "Do not contact button");
    private Button btnUndo = new Button(By.id("undoDontContact"), "Undo");
    private Button btnClearUsername = new Button(By.id("clearUsername"), "Clear username");
    private Button btnEditUsername = new Button(By.id("editAccessMSInformation"), "Edit access ms username");
    private RadioButton rbSystemAccountYes = new RadioButton(By.id("isExistingAccessMSAccount1"),
            "Participant have unemplyoment services system account");
    private TextBox tbServicesSystemUsername = new TextBox(By.id("applicationUser.username"),
            "Unemployment Services System Username");
    private H1 h1WeHaveFoundOldInfo = new H1(By.xpath("//h1[contains(.,'We have found some older information')]"),
            "Older Information");
    private Button btnUpdate = new Button(By.id("applyWioahChanges"), "Update");

    /**
     * Default constructor
     */
    public BaseParticipantDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Detail')]"), "Participant Detail");
    }

    public BaseParticipantDetailsForm(By xpath, String s) {
        super(xpath, s);
    }

    /*
    Click edit document
     */
    public void clickEditDocument() {
        btnEditDocument.clickAndWait();
    }

    public void fillInDocTitle(String title) {
        tbDocTitle.type(title);
    }


    /**
     * Add partner center and LWDA workforce area to participant to allow WIOA provider and LWDA staff work with participant
     *
     * @param partip - participant
     */
    public void attachLwdaWioa(Participant partip) {
        updateInfoIfNecessary();
        btnEditAdditionalInformation.clickAndWait();
        addPartnerCenter(partip);
        addLwia(partip);
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    private void updateInfoIfNecessary() {
        if (h1WeHaveFoundOldInfo.isPresent(waitSec)) {
            btnUpdate.clickAndWait();
        }
    }

    public void chooseDocument(String fileName) {
        String exactName = CommonFunctions.regexGetMatch(fileName, fileNameRegExp);
        Button btnChooseDocument = new Button(By.xpath(String.format(rbDocPath, exactName)), "Select required document");
        btnChooseDocument.click();
    }

    /**
     * Add partner center to a participant
     *
     * @param partip - participant
     */
    private void addPartnerCenter(Participant partip) {
        if (btnPartnerCenter.isPresent()) {
            btnPartnerCenter.clickAndWait();
            ServiceSearchForm searchPage = new ServiceSearchForm(Constants.PARTICIPANT);
            searchPage.inputServiceCenterName(partip.getServiceCenterName());
            clickAndWait(BaseButton.SEARCH);
            searchPage.selectEmployerService();
            clickAndWait(BaseButton.RETURN);
        }
    }

    /**
     * Add LWIA workforce area
     *
     * @param partip - participant
     */
    private void addLwia(Participant partip) {
        if (btnLwia.isPresent()) {
            btnLwia.clickAndWait();
            WorkforceAreasSearchForm searchPage = new WorkforceAreasSearchForm();
            searchPage.performSearchAndSelect(partip.getWorkforceArea());
        }
    }

    /**
     * Add veteran record
     *
     * @param militaryBranch - military branch
     * @param beginDate      - begin date of the veteran service
     * @param endDate        - end date of the veteran service.
     */
    public void addMilitaryRecord(String militaryBranch, String beginDate, String endDate) {
        btnAddMilRecord.clickAndWait();
        ParticipantEditForm editPage = new ParticipantEditForm();
        editPage.convertNationalGuard(militaryBranch, beginDate, endDate);
    }

    /**
     * Edit added veteran record
     *
     * @param milBranch - new military branch
     */
    public void editMilitaryRecord(String milBranch) {
        rbFirstMarineRecord.click();
        btnEditMilRecord.clickAndWait();
        ParticipantEditForm editPage = new ParticipantEditForm();
        editPage.selectMilitaryBranch(milBranch);
        editPage.clickButton(Buttons.Save);
    }

    public void removeMilitaryRecord() {
        rbFirstMarineRecord.click();
        btnRemoveMilRecord.clickAndWait();
        areYouSure(Popup.Yes);
    }

    /**
     * Check, that E-Verify is merged.
     */
    public void checkEverifyMerged() {
        CustomAssertion.softTrue("E-Verify merge assert fail", lnkEverifyMerge.isPresent());
    }

    /**
     * Edit previous job
     *
     * @param args - arguments to edit previous job.
     */
    public void editPreviousJob(String[] args) {
        rbFirstJob.click();
        btnEditPreviosJob.clickAndWait();
        ParticipantEditForm editPage = new ParticipantEditForm();
        editPage.inputCity(args[0]);
        editPage.clickButton(Buttons.Save);
    }

    /**
     * Click [Manual exit] button.
     */
    public void clickManualExit() {
        btnManualExit.clickAndWait();
    }

    /**
     * Check manual exit button present.
     *
     * @return true if present.
     */
    public Boolean checkManualExitButtonPresent() {
        return btnManualExit.isPresent();
    }

    /**
     * Edit personal information.
     */
    public void editPersonalInformation() {
        btnEditPersonalInformation.clickAndWait();
    }

    /**
     * Edit identification information.
     */
    public void editIdentificationInformation() {
        btnEditIdentificationInformation.clickAndWait();
    }

    /**
     * Edit academic history.
     */
    public void editAcademicHistory() {
        btnEditAcademicHistory.clickAndWait();
    }

    /**
     * Edit additional information.
     */
    public void editAdditionalInfo() {
        btnEditAdditionalInformation.clickAndWait();
    }


    /**
     * Open WIA enrollment participantSSDetails form.
     */
    public void openWiaEnrollmentDetailsForm() {
        expandWIAEnrollments();
        lnkWIAEnrollmentView.clickAndWait();
    }

    /**
     * Show unresulted services.
     */
    public void showUnresultedServices() {
        lnkUnresultedServices.clickAndWait();
    }

    /**
     * Select miles and click referrals link.
     */
    public void showQuickReferrals() {
        cmbMiles.select(miles);
        lnkQuickReferrals.click();
        divQuickReferrals.waitForIsElementPresent();
        tbcQuickReferralJobs.assertIsPresentAndVisible();
    }

    /**
     * Check, that quick referral button is present or not.
     *
     * @param present - true, if present
     */
    public void checkReferralPresent(Boolean present) {
        if (present) {
            lnkQuickReferrals.assertIsPresent();
        } else {
            lnkQuickReferrals.assertIsNotPresent();
        }
    }

    /**
     * Select Gap reason
     *
     * @param reason - gap reason
     */
    public void selectGapReason(String reason) {
        cmbGapReason.select(reason);
    }

    /**
     * Select exit reason
     *
     * @param reason - exit reason
     */
    public void selectExitReason(String reason) {
        cmbExitReason.select(reason);
    }

    /**
     * Add academic record.
     */
    public void clickAddAcademicRecord() {
        btnAddAcademicRecord.clickAndWait();
    }

    /**
     * Click previous job.
     */
    public void clickPreviousJob() {
        btnAddPreviousJob.clickAndWait();
    }

    /**
     * Click [Notes] button.
     */
    public void clickNotes() {
        spNotes.click();
    }

    /**
     * Click gap in service
     */
    public void clickGapInService() {
        btnGapInService.clickAndWait();
    }

    /**
     * Check gap in service button present and visible
     *
     * @return true if present
     */
    public Boolean checkGapServiceButton() {
        return btnGapInService.isPresent();
    }

    /**
     * Open audit form
     */
    public void openAuditForm() {
        btnAudit.clickAndWait();
    }

    /**
     * This method is used for getting Participant last name from Details form
     *
     * @return Last name
     */
    public String getLastName() {
        String text = lnkTitle.getText();
        return CommonFunctions.regexGetMatchGroup(text, "(\\w+)\\s+(\\w+)", 2);
    }

    /**
     * This method is used for getting Participant Date of Birth from Details form
     *
     * @return Date of birth
     */
    public String getDateOfBirth() {
        String text = lnkDateOfBirth.getText();
        return text.replace("Date of Birth: ", "");
    }

    /**
     * This method is used for getting Participant Address - Line one from Details form
     *
     * @return Address - line one
     */
    public String getLineOne() {
        String text = tbcAddress.getText();
        return text.split("\n")[0];
    }

    /**
     * This method is used for getting Participant Address - City from Details form
     *
     * @return Address - City
     */
    public String getCity() {
        String text = tbcAddress.getText();
        text = text.split("\n")[1];
        return CommonFunctions.regexGetMatch(text, "[\\w]+");
    }

    /**
     * This method is used for getting Participant Address - Zip Code from Details form
     *
     * @return Address - Zip Code
     */
    public String getZipCode() {
        String text = tbcAddress.getText();
        text = text.split("\n")[1];
        return CommonFunctions.regexGetMatch(text, "[\\d]+");
    }

    /**
     * Get link title text
     *
     * @return - link title text.
     */
    public String getLinkTitle() {
        return lnkTitle.getText().trim();
    }

    /**
     * Get veteran link text
     *
     * @return - vetaran link text
     */
    public String getVeteranText() {
        return lnkVeteran.getText();
    }

    /**
     * Get previous job text on the page
     *
     * @return job page text.
     */
    public String getPreviousJobPageText() {
        return tbcPreviousJob.getText().trim();
    }

    /**
     * Get WIA enrollment participantSSDetails text
     *
     * @return WIA enrollment participantSSDetails text
     */
    public String getWiaEnrrlDetailsText() {
        return tbcWIAEnrollmentDetails.getText().trim();
    }

    /**
     * Get participation periods text
     *
     * @return participation periods text
     */
    public String getParticipationPeriodsText() {
        return tbcParticipationPeriods.getText().trim();
    }

    /**
     * Get participation period begin date
     *
     * @return participation period begin date
     */
    public String getPartipPeriodsBeginDate() {
        return tbcParticipationPeriodBeginDate.getText().trim();
    }

    /**
     * Get operations text
     *
     * @return operations text
     */
    public String getOperationsText() {
        return tbcOperations.getText();
    }

    /**
     * Get notes count
     *
     * @return notes count.
     */
    public String getNotesCount() {
        return spnNotesCount.getText();
    }

    /**
     * This method is used for expanding Access Login information block
     */
    public void expandAccessLoginInformation() {
        btnExpandUnmploymentService.click();
    }

    /**
     * This method is used for expanding Academic history information block
     */
    public void expandAcademicHistory() {
        if (btnExpandAcademicHistory.isPresent()) {
            btnExpandAcademicHistory.click();
        }
    }

    /**
     * Expand veteran participantSSDetails section
     */
    public void expandVetDetails() {
        if (btnExpandVeteranDetails.isPresent()) {
            btnExpandVeteranDetails.click();
        }
    }

    /**
     * This method is used for expanding Employment history information block
     */
    public void expandEmploymentHistory() {
        if (btnExpandEmployerHistory.isPresent()) {
            btnExpandEmployerHistory.click();
        }
    }

    /**
     * Expand employment preferences section.
     */
    public void expandEmploymentPreferences() {
        if (btnExpandEmploymentPref.isPresent()) {
            btnExpandEmploymentPref.click();
        }
    }

    /**
     * Expand unemployment services system login information
     */
    public void expandUnemploymentSection() {
        if (btnExpandUnemploymentInfo.isPresent()) {
            btnExpandUnemploymentInfo.click();
        }
    }

    /**
     * Expand document section.
     */
    public void expandDocuments() {
        if (btnExpandDocuments.isPresent()) {
            btnExpandDocuments.click();
        }
    }

    /**
     * This method is used for expanding WIA Enrollments information block
     */
    public void expandWIAEnrollments() {
        expandWiaEnrollment.click();
    }

    /**
     * This method is used for expanding Operations information block
     */
    public void expandOperations() {
        if (btnExpandOperations.isPresent()) {
            btnExpandOperations.click();
        } else {
            btnExpandOperations.assertIsNotPresent();
        }
    }

    /**
     * Expand trade enrollment plan section
     */
    public void expandTradeEnrollments() {
        btnExpandTradeEnrl.click();
    }

    /**
     * Expand individual employment plans section
     */
    public void expandIEP() {
        btnExpandIEP.click();
    }

    /**
     * This method is used for expanding Participation Periods information block
     */
    public void expandParticipationPeriods() {
        btnExpandParticipantPeriods.click();
    }


    /**
     * This method is used for checking if text is present in Participation period section
     *
     * @param text Text used for search
     * @return True if text is present
     */
    public boolean checkParticipationPeriod(String text) {
        TableCell tbcParticipantPeriods = new TableCell(By.xpath(String.format(participantRecordsXpath, text)), "Participation periods");
        return tbcParticipantPeriods.isPresent();
    }

    /**
     * This method is used for adding a Previous Job record to Participant
     */
    public void addPreviousJob() {
        expandEmploymentHistory();
        btnAddPreviousJob.clickAndWait();

        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        addEmploymentForm.addRecordStaff();
    }

    /**
     * This method is used for adding Employment Record with specified Start Date and End Date
     *
     * @param employer Employer name
     * @param date     Indicates date when the Job has started
     * @param ended    Indicates if Job has ended
     */
    public void addEmployment(String employer, String date, boolean ended) {
        expandEmploymentHistory();
        btnAddPreviousJob.clickAndWait();

        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        addEmploymentForm.addRecord(employer, date, ended);
        addEmploymentForm.clickAddRecord();
    }


    /**
     * This method is used for removing the first Previous Job record
     */
    public void removePreviousJob() {
        expandEmploymentHistory();
        rbPreviousJob.click();
        btnRemovePreviousJob.click();
        clickAndWait(BaseButton.ARE_YOU_SURE_YES);
    }

    /**
     * Validating information displayed on participantSSDetails page
     *
     * @param participant Object with expected data
     */
    public void validateParticipantInformation(Participant participant) {
        String sex = "Male";
        String ethnicity = "None Specified";
        String employment = "Not Employed";
        info("Validating Participant information");
        CustomAssertion.softAssertContains(getLinkTitle(), participant.getLastName(), "Incorrect participant last name");
        CustomAssertion.softAssertContains(getLinkTitle(), participant.getTruncatedSsn(), "Incorrect participant truncated ssn");
        validateSsn(participant);
        CustomAssertion.softAssertContains(lnkCitizenship.getText(), participant.getCitizenship(), "Incorrect participant citizenship");
        validateBirthday(participant);
        CustomAssertion.softAssertContains(lnkGender.getText(), sex, "Incorrect participant gender");
        CustomAssertion.softAssertContains(lnkEthnicity.getText(), ethnicity, "Incorrect participant ethnicity");
        CustomAssertion.softAssertEquals(lnkEmployment.getText(), employment, "Incorrect employment");
        CustomAssertion.softAssertContains(lnkDisabled.getText(), Constants.NO_ANSWER, "Incorrect disabled link");
        CustomAssertion.softAssertContains(tbcAddress.getText(), participant.getAddress().getLineOne(), "Incorrect address line one");
        CustomAssertion.softAssertContains(tbcAddress.getText(), participant.getAddress().getCity(), "Incorrect city");
        CustomAssertion.softAssertContains(tbcAddress.getText(), participant.getAddress().getZipCode(), "Incorrect zip code");
        expandAcademicHistory();
        CustomAssertion.softAssertContains(tbcGrade.getText(), participant.getGrade(), "Incorrect participant grade");
        expandAccessLoginInformation();
        CustomAssertion.softAssertContains(tbcAccount.getText(), participant.getParticipantAccount(), "Incorrect participant account");
        info("Validation: OK");
    }

    /**
     * Validate only SSN information
     *
     * @param participant - participant.
     */
    private void validateSsn(Participant participant) {
        CustomAssertion.softAssertContains(lnkSSN.getText(), participant.getTruncatedSsn(), "Incorrect participant ssn");
    }

    /**
     * Validate only birthday information
     *
     * @param participant - participant
     */
    private void validateBirthday(Participant participant) {
        CustomAssertion.softAssertContains(lnkDateOfBirth.getText(), participant.getParticipantBioInfo().getDateOfBirth(), "Incorrect participant date of birth");
    }

    /**
     * Validate veteran yes
     *
     * @param state - yes, if participant is veteran or no if not.
     */
    public void validateVeteran(String state) {
        CustomAssertion.softAssertContains(lnkVeteran.getText(), state, "Veteran");
    }

    /**
     * Validate mississippi national guard.
     */
    private void validateGuard() {
        CustomAssertion.softAssertContains(lnkGuard.getText(), Constants.YES_ANSWER, "Mississippi National Guard");
    }

    /**
     * Validate added veteran record
     *
     * @param militaryBranch - military branch of the national guard
     * @param beginDate      - begin date of the veteran service
     * @param endDate        - end date of the veteran service
     */
    public void validateVetAddedRecord(String militaryBranch, String beginDate, String endDate) {
        CustomAssertion.softTrue("Incorrect added record", tbcMilitaryRecord.getText().contains(militaryBranch + " "
                + beginDate + " to " + endDate));
    }

    /**
     * Validate veteran participantSSDetails
     *
     * @param vetStatus      - veteran status
     * @param dateSeparation - date military separation
     * @param disabledState  - disabled state
     */
    public void validateVeteranDetails(String vetStatus, String dateSeparation, String disabledState) {
        btnExpandVeteranDetails.click();
        CustomAssertion.softAssertContains(dvVeteranStatus.getText(), vetStatus, "Incorrect veteran status");
        CustomAssertion.softAssertContains(dvCampaignVeteran.getText(), answerYes, "Incorrect campaign veteran value");
        CustomAssertion.softAssertContains(dvServiceMember.getText(), answerYes, "Incorrect transitioning service member value");
        CustomAssertion.softAssertContains(dvMilitarySeparation.getText(), dateSeparation, "Incorrect date military separation");
        CustomAssertion.softAssertContains(dvDisabledVeteran.getText(), disabledState, "Incorrect disabled state");
    }


    /**
     * Validates basic academic history
     *
     * @param partip - participant
     */
    public void validateAcademicHistory(Participant partip) {
        expandAcademicHistory();
        Assert.assertTrue(tbcHighestGradeCompleted.getText().contains(partip.getGrade()));
    }

    /**
     * Validate data in the Employment History section
     *
     * @param employer - employer to check
     * @param date     - start date.
     */
    public void validateEmploymentHistory(Employer employer, String date) {
        String expectedText = "Cook at " + employer.getEmployerAccount() + " " + date + " to Present for ~ Less than a month." + "\n"
                + employer.getAddress().getCity() + ", " + employer.getAddress().getZipCode() + " Salary: None Provided / None Provided" + "\n"
                + "Occupation Type: Cooks, Restaurant" + "\n" + "Rapid Response Received " + date;
        boolean finded = false;
        expandEmploymentHistory();
        info(dvItems.getText());
        Integer quantity = Integer.valueOf(CommonFunctions.regexGetMatch(dvItems.getText(), Constants.NUMBERS_REGEX));
        for (int i = 1; i <= quantity; i++) {
            Div dvJob = new Div(By.xpath(String.format(emplXpath, i)), "Record found");
            if (dvJob.getText().contains(employer.getEmployerAccount())) {
                CustomAssertion.softTrue("Incorrect previous job text", dvJob.getText().contains(expectedText));
                finded = true;
                i = quantity + 1;
            }
        }
        CustomAssertion.softTrue("Record wasn't found", finded);
    }

    /**
     * Validate participation period
     */
    public void validateParticipantPerios() {
        Assert.assertTrue(partPeriodHead.getText().contains(CommonFunctions.getCurrentDate() + " To " + CommonFunctions.getCurrentDate()));
    }

    /**
     * Validate added individual employment plan in the IEP section of the participant
     *
     * @param plan - individual employment plan to check
     */
    public void validateIEP(IndividualEmploymentPlan plan) {
        CustomAssertion.softTrue("Incorrect IEP added to the participant", tbcIEP.getText().contains(
                "Plan: " + plan.getPlanDescription() + "\n" + "Discussed Non-Traditional Employment: No" + "\n"
                        + "Plan was created on " + plan.getCreationDate() + " and the primary case manager is Auto Staff"
        ));
    }

    /**
     * Validate trade enrollment data in the Trade enrollment section of the chosen participant
     *
     * @param trEnrl - trade enrollment
     */
    public void validateTradeEnrollments(TradeEnrollment trEnrl) {
        CustomAssertion.softTrue("Incorrect IEP added to the participant", tbcTrEnrl.getText().contains(
                "Trade Enrollment eligible on " + trEnrl.getEligibilityDeterminationDate() + "\n" + "Not yet started (No service)"
        ));
    }

    /**
     * Check status and certificate of the participant
     *
     * @param partip         - expected participant.
     * @param expectedCertif - certificate
     */
    public void checkStatusCertificateParticipant(Participant partip, String expectedCertif) {
        CustomAssertion.softAssertContains(tbcGrade.getText(), partip.getGrade(), "Incorrect participant grade");
        CustomAssertion.softAssertContains(tbcCertificate.getText(), expectedCertif, "Incorrect participant certificate");
    }

    /**
     * Edit veteran status
     *
     * @param veteranStatus - veteran status
     * @param disabledState - disabled state
     */
    public void editVeteranStatus(String veteranStatus, String disabledState) {
        btnEditVeteranDetails.clickAndWait();
        cmbEditVeteranStatus.select(veteranStatus);
        if (!veteranStatus.contains(ineligibleVeteran)) {
            rbTransServiceMemberYes.click();
            tbDateActualSeparation.type(CommonFunctions.getYesterdayDate());
            rbCampaingVeteranYes.click();
            cbDisabledVeteran.select(disabledState);
        }
        clickButton(Buttons.Save);
    }

    /**
     * Edit veteran participantSSDetails.
     *
     * @param user - current user
     */
    public void editVeteranDetails(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditVeteranInfoButton()) {
            if (user.getParticipant().getParticipantPermissions().getPpEditVetEligibilityOption()) {
                editVeteranStatus(ineligibleVeteran, answerNo);
                validateVeteran(answerNo);
            } else {
                editVeteranStatus(otherPerson, answerNo);
                validateVeteran(answerYes);
                validateVeteranDetails(otherPerson, CommonFunctions.getYesterdayDate(), answerNo);
            }
        } else {
            btnEditVeteranDetails.assertIsNotPresent();
        }
    }

    /**
     * Edit ssn, if possiblie and check, that changes are applied
     *
     * @param user - current user
     */
    public void editSsn(User user, Participant participant) {
        if (user.getParticipant().getParticipantPermissions().getPpEditFullSSN()) {
            String ssn = CommonFunctions.getRandomSSN();
            editPersonalInformation();
            ParticipantEditForm editPage = new ParticipantEditForm();
            participant.setSsn(ssn);
            editPage.inputSsn(ssn);
            editPage.clickButton(Buttons.Save);
            validateSsn(participant);
        } else if (user.getParticipant().getParticipantPermissions().getPpEditPersonalInformationButton()) {
            editPersonalInformation();
            txbSSN.assertIsNotPresent();
            clickButton(Buttons.Save);
        }
    }

    /**
     * Edit some personal information.
     *
     * @param user        - current user
     * @param participant - participant
     */
    public void editPersonal(User user, Participant participant) {
        Integer from = 30;
        Integer to = 50;
        Integer step = 2;
        if (user.getParticipant().getParticipantPermissions().getPpEditPersonalInformationButton()) {
            Integer age = CommonFunctions.getRandomIntegerInLimits(from, to, step);
            editPersonalInformation();
            participant.getParticipantBioInfo().setDateOfBirth(CommonFunctions.getDaysAndYearsAgoDate(0, age));
            ParticipantEditForm editPage = new ParticipantEditForm();
            editPage.inputDateBirth(CommonFunctions.getDaysAndYearsAgoDate(0, age));
            clickButton(Buttons.Save);
            validateBirthday(participant);
        } else {
            btnEditPersonalInformation.assertIsNotPresent();
        }
    }

    /**
     * Convert participant to the veteran
     *
     * @param user - current user
     */
    public void convertVeter(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditConvertVetButton()) {
            btnConvertVet.clickAndWait();
            cmbEditVeteranStatus.select(veteranStatus);
            rbTransServiceMemberYes.click();
            tbDateActualSeparation.type(CommonFunctions.getYesterdayDate());
            rbCampaingVeteranYes.click();
            cbDisabledVeteran.select(answerNo);
            clickButton(Buttons.Save);
            validateVeteran(answerYes);
        } else {
            btnConvertVet.assertIsNotPresent();
        }
    }

    /**
     * Convert participant to the member of the national guard
     *
     * @param user           - current user
     * @param militaryBranch - military branch
     * @param beginDate      - begin date of the veteran service
     * @param endDate        - end date of the veteran service.
     */
    public void convertNationalGuard(User user, String militaryBranch, String beginDate, String endDate) {
        if (user.getParticipant().getParticipantPermissions().getPpEditConvertMSNGButton()) {
            btnConvertNationalGuard.clickAndWait();
            ParticipantEditForm editPage = new ParticipantEditForm();
            editPage.convertNationalGuard(militaryBranch, beginDate, endDate);
            validateGuard();
        } else {
            btnConvertNationalGuard.assertIsNotPresent();
        }
    }

    /**
     * Edit contact information
     *
     * @param user         - current user
     * @param emailAddress - new email address
     */
    public void editContactInfo(User user, String emailAddress) {
        if (user.getParticipant().getParticipantPermissions().getPpEditContactInfoButton()) {
            btnEditContactInfo.clickAndWait();
            txbEmail.type(emailAddress);
            clickButton(Buttons.Save);
            CustomAssertion.softAssertContains(lnkEmail.getText(), emailAddress, "Incorrect email address");
        } else {
            btnEditContactInfo.assertIsNotPresent();
        }
    }

    /**
     * Edit some (driver' license type) additional information
     *
     * @param user - current user.
     */
    public void editAdditionalInformation(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditAdditionalInfoButton()) {
            btnExpandAdditionalInformation.click();
            btnEditAdditionalInformation.clickAndWait();
            rbDriverLicense.check();
            cmbDriversLicenseClass.select(editLicense);
            clickButton(Buttons.Save);
            btnExpandAdditionalInformation.click();
            CustomAssertion.softAssertContains(dvClassDriverLicense.getText(), editLicense, "Incorrect driver license");
        } else {
            btnEditAdditionalInformation.assertIsNotPresent();
        }
    }

    /**
     * Manipulation with academic history
     *
     * @param user - current user
     */
    public void academicHistoryManipulation(User user) {
        String doctorateDegree = "Doctorate Diploma or Degree";
        Participant participant = user.getParticipant();
        expandAcademicHistory();
        ifButton(participant.getParticipantPermissions().getPpEditAddAcademicHistoryButton(), btnAddAcademicRecord);
        //Add new academic history
        if (user.getParticipant().getParticipantPermissions().getPpEditAddAcademicHistoryButton()) {
            divideMessage("Add new academic record");
            clickAddAcademicRecord();
            ParticipantAddAcademicRecordForm recordPage = new ParticipantAddAcademicRecordForm();
            recordPage.addRecord(CommonFunctions.getYesterdayDate(), user.getParticipant().getDegree(), false);
            recordPage.clickButton(Buttons.Add);
        }
        //Edit new academic history
        editAcademicHistoryRole(user, doctorateDegree);
        //Unverify academic history
        unverify(user);
        //Verify academic history
        verify(user);
        //Remove academic history
        removeAcademicHistory(user);
    }

    /**
     * Select academic record and click Verify
     */
    public void selectVerify() {
        rbAcademicHistory.click();

        btnVerify.clickAndWait();
    }

    /**
     * Edit academic history with specific role
     *
     * @param user       - current user
     * @param editDegree - degree to edit
     */
    private void editAcademicHistoryRole(User user, String editDegree) {
        ifButton(user.getParticipant().getParticipantPermissions().getPpEditEditAcademicHistoryButton(), btnEditAcademicHistory);
        if (user.getParticipant().getParticipantPermissions().getPpEditAcademicHistoryButton()) {
            divideMessage("Edit academic history");
            expandAcademicHistory();
            rbAcademicHistory.click();
            btnEditAcademicHistory.clickAndWait();
            new ParticipantEditForm().selectEducationStatus(editDegree);
            clickButton(Buttons.Save);
            expandAcademicHistory();
            CustomAssertion.softAssertContains(tbcGrade.getText(), editDegree, "Incorrect highest grade completed");
        }
    }

    /**
     * Unverify academic history
     *
     * @param user - current user
     */
    private void unverify(User user) {
        expandAcademicHistory();
        if (user.getParticipant().getParticipantPermissions().getPpEditUnverifyAcademicHistoryButton()) {
            divideMessage("Unverify academic history");
            rbAcademicHistory.clickAndWait();

            btnUnverify.clickAndWait();
            expandAcademicHistory();
            rbAcademicHistory.clickAndWait();
            btnVerify.assertIsPresent();
        } else {
            ifButton(user.getParticipant().getParticipantPermissions().getPpEditUnverifyAcademicHistoryButton(), btnUnverify);
        }
    }

    /**
     * Verify academic history
     *
     * @param user - current user
     */
    private void verify(User user) {
        expandAcademicHistory();
        if (user.getParticipant().getParticipantPermissions().getPpEditVerifyAcademicHistoryButton()) {
            divideMessage("Verify academic history");
            selectVerify();
            expandAcademicHistory();
            rbAcademicHistory.clickAndWait();

            btnUnverify.assertIsPresent();
        } else {
            ifButton(user.getParticipant().getParticipantPermissions().getPpEditUnverifyAcademicHistoryButton(), btnVerify);
        }
    }

    /**
     * Remove academic history
     *
     * @param user - current user
     */
    private void removeAcademicHistory(User user) {
        ifButton(user.getParticipant().getParticipantPermissions().getPpEditRemoveAcademicHistoryButton(), btnRemove);
        if (user.getParticipant().getParticipantPermissions().getPpEditRemoveAcademicHistoryButton()) {
            divideMessage("Remove academic record");
            expandAcademicHistory();
            btnRemove.clickAndWait();
            clickAndWait(BaseButton.ARE_YOU_SURE_YES);
            expandAcademicHistory();
            noSearchResults();
        }
    }

    /**
     * Add new certification
     *
     * @param participant - participant
     */
    private void addCertificate(Participant participant) {
        btnExpandCertifications.click();
        btnAddCertification.clickAndWait();
        cbCertificationType.select(participant.getCertification());
        btnAdd.clickAndWait();
        btnExpandCertifications.click();
        CustomAssertion.softAssertContains(tbcCertAdded.getText(), participant.getCertification(),
                "Incorrect certification on the page");
    }

    /**
     * Edit certificate
     *
     * @param newCert - new certificate
     */
    private void editCertificate(String newCert) {
        rbCertRadio.click();
        btnEditCertification.clickAndWait();
        cbCertificationType.select(newCert);
        clickButton(Buttons.Save);
        btnExpandCertifications.click();
        CustomAssertion.softAssertContains(tbcCertAdded.getText(), newCert, "Incorrect certification on the page");
    }

    /**
     * Remove certificate
     */
    private void removeCertificate() {
        rbCertRadio.click();
        btnRemove.clickAndWait();
        clickAndWait(BaseButton.ARE_YOU_SURE_YES);
        btnExpandCertifications.click();
        noSearchResults();
    }

    /**
     * Do some manipulation with certifications
     *
     * @param user   - current user.
     * @param partip - participant
     */
    public void certifications(User user, Participant partip) {
        if (user.getParticipant().getParticipantPermissions().getPpEditAddCertifications()) {
            divideMessage("First add certification, then edit it");
            addCertificate(partip);
            editCertificate(certificate);
        } else {
            btnAddCertification.assertIsNotPresent();
            btnEditCertification.assertIsNotPresent();
        }
        if (user.getParticipant().getParticipantPermissions().getPpEditRemoveCertifications()) {
            divideMessage("Remove certification");
            removeCertificate();
        } else {
            btnRemove.assertIsNotPresent();
        }
    }

    /**
     * Add new document for a participant
     */
    public void addDocument(String fileName) {
        expandDocuments();
        btnAddDocument.clickAndWait();
        new AddDocumentStaffForm().addDocument(fileName);
        expandDocuments();
    }

    /**
     * Validate uploaded document.
     */
    public void validateDocument(String fileName) {
        expandDocuments();
        String exactName = CommonFunctions.regexGetMatch(fileName, fileNameRegExp);
        TableCell tbcDocName = new TableCell(By.xpath(String.format(DOC_XPATH, exactName)));
        CustomAssertion.softTrue("Incorrect file name", tbcDocName.getText().contains(exactName));
        CustomAssertion.softTrue("Incorrect date", tbcDocName.getText().contains(CommonFunctions.getCurrentDate()));
    }

    public void deleteDocument(String fileName) {
        expandDocuments();
        String exactName = CommonFunctions.regexGetMatch(fileName, fileNameRegExp);
        Button btnChooseDocument = new Button(By.xpath(String.format(rbDocPath, exactName)), "Select required document");
        btnChooseDocument.click();
        Browser.getInstance().waitForPageToLoad();
        btnRemove.clickAndWait();
        clickAndWait(BaseButton.ARE_YOU_SURE_YES);
        expandDocuments();
        TableCell tbcDocName = new TableCell(By.xpath(String.format(DOC_XPATH, exactName)), "Result of doc search");
        tbcDocName.assertIsNotPresent();
    }

    public void approveDocument(String fileName) {
        final String approvedDocStatus = "Approved";
        expandDocuments();
        String exactName = CommonFunctions.regexGetMatch(fileName, fileNameRegExp);
        Button btnChooseDocument = new Button(By.xpath(String.format(rbDocPath, exactName)), "Select required document");
        btnChooseDocument.click();
        Browser.getInstance().waitForPageToLoad();
        btnEditDocument.clickAndWait();
        Browser.getInstance().waitForPageToLoad();
        clickAndWait(BaseButton.SAVE_CHANGES);
        expandDocuments();
        TableCell tbcDocName = new TableCell(By.xpath(String.format(DOC_XPATH, exactName)));
        CustomAssertion.softTrue("The document has not been approved", tbcDocName.getText().contains(approvedDocStatus));
        info(fileName + " has been approved");
    }


    /**
     * Add, edit, verify/unverify, remove employment history
     *
     * @param user - current user.
     */
    public void employmentHistoryManipulation(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditAddEmploymentHistoryButton()) {
            divideMessage("Add employment history");
            expandEmploymentHistory();
            btnAddPreviousJob.clickAndWait();
            ParticipantAddEmploymentForm recordPage = new ParticipantAddEmploymentForm();
            recordPage.addRecord(user.getParticipant());

            checkInternalError();

            divideMessage("Edit employment history");
            expandEmploymentHistory();

            divideMessage("Verify employment history");
            divideMessage("Unverify employment history");
        } else {
            btnAddPreviousJob.assertIsNotPresent();
            btnEditPreviosJob.assertIsNotPresent();
            btnVerifyPriviosJob.assertIsNotPresent();
        }
        if (user.getParticipant().getParticipantPermissions().getPpEditRemoveEmploymentHistoryButton()) {
            divideMessage("Remove employment history");
        } else {
            btnRemovePreviousJob.assertIsNotPresent();
        }
    }

    /**
     * Edit employment preferences
     *
     * @param user        - current user
     * @param participant - participant
     */
    public void editPreferences(User user, Participant participant) {
        if (user.getParticipant().getParticipantPermissions().getPpEditEditEmploymentPreferencesButton()) {
            expandEmploymentPreferences();
            btnEditEmploymentPreferences.clickAndWait();
            ParticipantEditForm editPage = new ParticipantEditForm();
            editPage.selectOsocCode(participant.getOsoc());
            clickButton(Buttons.Save);
            expandEmploymentPreferences();
            CustomAssertion.softAssertContains(tbcAddedPreference.getText(), participant.getOsoc(),
                    "Incorrect employment preference");
        } else {
            btnEditEmploymentPreferences.assertIsNotPresent();
        }
    }

    /**
     * Clear username
     */
    public void clear() {
        btnClearUsername.clickAndWait();
    }

    /**
     * Clear username if possible
     *
     * @param user - current user
     */
    public void clearMs(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditClearAccessMSUsernameButton()) {
            expandUnemploymentSection();
            clear();
            expandUnemploymentSection();
            CustomAssertion.softAssertContains(tbcUnemployment.getText(), "None Provided",
                    "Unemployment service system hasn't been reset");
        } else {
            btnClearUsername.assertIsNotPresent();
        }
    }

    /**
     * Edit username, if possible.
     *
     * @param user - current user
     */
    public void editMs(User user, Participant participant) {
        if (user.getParticipant().getParticipantPermissions().getPpEditEditAccessMSInfoButton()) {
            btnEditUsername.clickAndWait();
            rbSystemAccountYes.click();
            tbServicesSystemUsername.type(participant.getParticipantAccount());
            clickButton(Buttons.Save);
            expandAccessLoginInformation();
            CustomAssertion.softAssertContains(tbcUnemployment.getText(), participant.getParticipantAccount(), "Incorrect username");
        } else {
            btnEditUsername.assertIsNotPresent();
        }

    }

    /**
     * Check, that button [Do not contact] is displayed or not, if user has permissions.
     *
     * @param user - current user
     */
    public void checkOperationsEmpty(User user) {
        if (user.getParticipant().getParticipantPermissions().getPpEditNotContactButton()) {
            expandOperations();
            btnDoNotContact.assertIsPresent();
            btnDoNotContact.click();
            expandOperations();
            btnUndo.assertIsPresent();
            btnUndo.click();
            expandOperations();
            btnDoNotContact.assertIsPresent();
        } else {
            btnDoNotContact.assertIsNotPresent();
            btnUndo.assertIsNotPresent();
        }
    }

    /**
     * Check, that buttons [Manual exit], [Gap in Service] are displayed on the page, if user has permissions to do it.
     *
     * @param user - current user
     */
    public void checkOperationsNotEmpty(User user) {
        expandOperations();
        if (user.getParticipant().getParticipantPermissions().getPpEditManualExitButton()
                || user.getParticipant().getParticipantPermissions().getPpEditGapServiceButton()) {
            btnManualExit.assertIsPresent();
            btnGapInService.assertIsPresent();
        } else {
            btnManualExit.assertIsNotPresent();
            btnGapInService.assertIsNotPresent();
        }
    }
}
