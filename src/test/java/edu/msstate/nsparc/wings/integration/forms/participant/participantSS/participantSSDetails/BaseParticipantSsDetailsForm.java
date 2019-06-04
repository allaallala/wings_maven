package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddAcademicRecordForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * Describes participant participantSSDetails form for external participant (SS)
 * Created by a.vnuchko on 29.10.2016.
 */
public class BaseParticipantSsDetailsForm extends ParticipantHomePage {

    private Button btnResumeView = new Button(By.xpath("//a[contains(.,'Resume View')]"), "Resume View");

    private Button btnStandardView = new Button(By.xpath("//a[contains(.,'Standard View')]"), "Standard View");
    private String sectionPath = "//strong[contains(.,'%1$s')]";
    private Button btnPrint = new Button(By.partialLinkText("Print"), "Print button");

    protected String nonRecordsXpath = "//div[@class='panel-heading'][contains(.,'%1$s')]/following-sibling::div";
    private Div dvMilitaryText = new Div(By.xpath(String.format(nonRecordsXpath, "Military")), "Provided military records");

    private Link lnkTitle = new Link(By.xpath("//div[@id='user-name'] | //h1//span"), "Participant name");

    //Details fields data
    protected String parameterPath = "//div[@class='detail-label'][contains(.,'%1$s')]/..";
    protected String titlePath = "//div[@id='user-name'][contains(.,'%1$s')]";

    private TableCell tbcPreviousJob = new TableCell(By.xpath("//table[@id='pjresults-table'] "
            + "| //a[@id='experienceSection']/../..//tbody"), "Previous Job");

    protected String militaryDescriptionXpath = "//td[contains(.,\"%1$s\")]";

    //All edit (remove) buttons
    private Button btnAddPreviousJob = new Button(By.xpath("//button[@id='addPreviousJob'] "
            + "| //button[@id='addEmployment']"), "Add Previous Job");
    private Button btnRemoveEducation = new Button(By.xpath("//a[@href[contains(.,'editEducation')]]/following-sibling::a/small"),
            "Remove education record");
    private Button btnAddAcademicRecord = new Button(By.xpath("//button[@id='clickAddAcademicRecord'] "
            + "| //button[@id='addEducation']"), "Add Academic Record");
    private Button btnEditEmploymentSelfServices = new Button(By.xpath("//a[@id='experienceSection']/../.."
            + "//tbody//a[contains(@class,'btn-info')]"), "Edit Employment");
    private Div dvNoHistoryProvided = new Div(By.xpath("//div[contains(.,'No Employment History provided.')]"),
            "Employment is not present");

    //All sections
    private Div dvEligibilitySection = new Div(By.xpath(String.format(sectionPath, "Eligibility Information")),
            "Eligibility Information section");
    private Div dvIdentificationInfo = new Div(By.xpath(String.format(sectionPath, "Identification Information")),
            "Identification Information");
    private Div dvClassificationInfo = new Div(By.xpath(String.format(sectionPath, "Classification Information")),
            "Classification Information");
    private Div dvAccomplishments = new Div(By.xpath(String.format(sectionPath, "Accomplishments")),
            "Accomplishments");
    private Div dvContactInfo = new Div(By.xpath(String.format(sectionPath, "Contact Information")),
            "Contact Information");
    private Div dvEmploymentPreferences = new Div(By.xpath(String.format(sectionPath, "Employment Preferences")),
            "Employment Preferences");
    private Div dvMilitary = new Div(By.xpath(String.format(sectionPath, "Military")), "Military");
    private Div dvMyDocuments = new Div(By.xpath(String.format(sectionPath, "My Documents")), "My Documents");
    private Div dvEducation = new Div(By.xpath(String.format(sectionPath, "Education")), "Education");
    private Div dvEmployment = new Div(By.xpath(String.format(sectionPath, "Employment")), "Employment");

    //Resume edit buttons.
    private String xpathResumeEdit = "//a[@href[contains(.,'%1$s')]]";
    private Button penParticipantEdit = new Button(By.xpath(String.format(xpathResumeEdit, "editIdentification")),
            "pen Participant edit");
    private Button penAddressEdit = new Button(By.xpath(String.format(xpathResumeEdit, "editContactInformation")),
            "pen Address edit");
    private Button penEducationEdit = new Button(By.xpath(String.format(xpathResumeEdit, "editEmployment")),
            "pen Education edit");
    private Button penGradeEdit = new Button(By.xpath(String.format(xpathResumeEdit, "editAccomplishments")),
            "pen Grade edit");
    private Button penDiplomaEdit = new Button(By.xpath(String.format(xpathResumeEdit, "editEducation")),
            "pen Diploma edit");

    public BaseParticipantSsDetailsForm(Participant participant) {
        super(By.xpath(String.format("//div[@id='user-name'][contains(.,'%1$s')]", participant.getFirstName())),
                "Participant SS participantSSDetails form");
    }

    public BaseParticipantSsDetailsForm(By xpath, String s) {
        super(xpath, s);
    }

    public BaseParticipantSsDetailsForm() {
        super(By.xpath("//div[@id='user-name']"), "Participant SS participantSSDetails form");
    }

    public enum ParticipantDetailsButtons {
        EDIT_ELIGIBILITY(By.id("editEligibility"), "Edit eligibility information"),
        EDIT_IDENTIFICATION(By.id("editIdentification"), "Edit identification information"),
        EDIT_CLASSIFICATION(By.id("editClassification"), "Edit classification information"),
        EDIT_ACCOMPLISHMENTS(By.id("editAccomplishments"), "Edit accomplishments"),
        EDIT_CONTACTS(By.id("editContactInformation"), "Edit contact information"),
        EDIT_PREFERENCES(By.id("editEmploymentPreferences"), "Edit employment preferences"),
        EDIT_MILITARY(By.xpath("//a[@href[contains(.,'editMilitary')]]"), "Edit military information"),
        EDIT_DOCUMENTS(By.xpath("//div[@class='panel-heading'][contains(., 'My Documents')]/following-sibling::table"
                + "//a[contains(., 'Edit')]"), "Edit document"),
        EDIT_EDUCATION(By.xpath("//a[@href[contains(.,'editEducation')]]"), "Edit identification information"),
        EDIT_EMPLOYMENTS(By.xpath("//a[@href[contains(.,'editEmployment')]]"), "Edit employment information"),

        ADD_MILITARY(By.id("addMilitary"), "Add military record"),
        ADD_DOCUMENT(By.id("addDocument"), "Add document"),
        ADD_EDUCATION(By.id("addEducation"), "Add Education"),
        ADD_EMPLOYMENT(By.id("addEmployment"), "Add Employment"),

        REMOVE_MILITARY(By.xpath("//a[@href[contains(.,'editMilitary')]]/following-sibling::a/small"), "Remove military"),
        REMOVE_DOCUMENT(By.xpath("//div[@class='panel-heading'][contains(., 'My Documents')]/following-sibling::table"
                + "//a[contains(., 'Remove')]"), "Remove document"),
        REMOVE_EDUCATION(By.xpath("//a[@href[contains(.,'editEducation')]]/following-sibling::a/small"),
                "Remove education"),
        REMOVE_EMPLOYMENT(By.xpath("//a[@href[contains(.,'editEmployment')]]/following-sibling::a/small"),
                "Remove employment");

        private By locator;
        private String name;

        ParticipantDetailsButtons(By loc, String name) {
            this.locator = loc;
            this.name = name;
        }

        protected String getName() {
            return name;
        }

        protected By getLocator() {
            return locator;
        }
    }

    public void clickButton(ParticipantDetailsButtons button) {
        new Button(button.getLocator(), button.getName()).clickAndWait();
    }

    public void resumeStandardView() {
        clickResumeView();
        btnStandardView.clickAndWait();
    }

    public String getLinkTitle() {
        return lnkTitle.getText().trim();
    }

    public void addPreviousJobSelfServices() {
        clickAddPreviousJob();

        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm(Constants.PARTICIPANT_SS);
        addEmploymentForm.addRecordSelfService();
    }

    public void addAcademicRecord() {
        btnAddAcademicRecord.clickAndWait();
    }

    public void clickAddPreviousJob() {
        btnAddPreviousJob.clickAndWait();
    }

    public void editEmploymentSelfService() {
        btnEditEmploymentSelfServices.clickAndWait();
    }

    public void removeEmploymentAndConfirm() {
        clickButton(ParticipantDetailsButtons.REMOVE_EMPLOYMENT);
        clickAndWait(BaseButton.ARE_YOU_SURE_YES);
    }

    public void checkRemoveEmploymentNotPresent() {
        dvNoHistoryProvided.isPresent();
    }

    public String getPreviousJobPageText() {
        return tbcPreviousJob.getText().trim();
    }

    public void checkButtonsResumeView() {
        CustomAssertion.softTrue("Pen participant edit is not visible", penParticipantEdit.isPresent());
        CustomAssertion.softTrue("Pen address edit is not visible", penAddressEdit.isPresent());
        CustomAssertion.softTrue("Pen education edit is not visible", penEducationEdit.isPresent());
        CustomAssertion.softTrue("Pen grade edit is not visible", penGradeEdit.isPresent());
        CustomAssertion.softTrue("Pen diploma edit is not visible", penDiplomaEdit.isPresent());
    }

    protected void selectHighestGradeCompleted(String grade) {
        selectGradeCompletedStatus(grade);
    }

    public void changeHighestGradeSelfService(String grade) {
        clickButton(ParticipantDetailsButtons.EDIT_ACCOMPLISHMENTS);
        selectHighestGradeCompleted(grade);
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    public void addAcademicRecordSelfService() {
        String eduDegree = "Bachelor's Degree";
        clickButton(ParticipantDetailsButtons.ADD_EDUCATION);
        ParticipantAddAcademicRecordForm addAcademicRecordForm = new ParticipantAddAcademicRecordForm(Constants.PARTICIPANT_SS);
        addAcademicRecordForm.addRecord(CommonFunctions.getCurrentDate(), eduDegree, true);
        clickAndWait(BaseButton.SAVE_CHANGES);

    }

    public void clickResumeView() {
        btnResumeView.clickAndWait();
    }

    public void editEducationNotPresent() {
        new Button(ParticipantDetailsButtons.EDIT_EDUCATION.getLocator(),
                ParticipantDetailsButtons.EDIT_EDUCATION.getName()).assertIsNotPresent();
        btnRemoveEducation.assertIsNotPresent();
    }

    public void checkSections() {
        CustomAssertion.softTrue("Eligibility Information Section is not displayed",
                dvEligibilitySection.isPresent());
        CustomAssertion.softTrue("Identification Information Section is not displayed",
                dvIdentificationInfo.isPresent());
        CustomAssertion.softTrue("Classification Information Section is not displayed",
                dvClassificationInfo.isPresent());
        CustomAssertion.softTrue("Accomplishments Section is not displayed",
                dvAccomplishments.isPresent());
        CustomAssertion.softTrue("Contact Information Section is not displayed",
                dvContactInfo.isPresent());
        CustomAssertion.softTrue("Employment Preferences Section is not displayed",
                dvEmploymentPreferences.isPresent());
        CustomAssertion.softTrue("Military Section is not displayed",
                dvMilitary.isPresent());
        CustomAssertion.softTrue("My Documents Section is not displayed",
                dvMyDocuments.isPresent());
        CustomAssertion.softTrue("Education Section is not displayed",
                dvEducation.isPresent());
        CustomAssertion.softTrue("Employment Section is not displayed",
                dvEmployment.isPresent());
    }

    public void checkButtons(ParticipantDetailsButtons button) {
        CustomAssertion.softTrue(button.getName() + " information is not displayed",
                new Button(button.getLocator(), button.getName()).isPresent());
    }

    public void removeMilitaryRecordPresent() {
        Button btnRemoveButton = new Button(ParticipantDetailsButtons.REMOVE_MILITARY.getLocator(),
                ParticipantDetailsButtons.REMOVE_MILITARY.getName());
        if (btnRemoveButton.isPresent()) {
            btnRemoveButton.click();
            clickAndWait(BaseButton.ARE_YOU_SURE_YES);
        }

        String militaryText = "No Military History provided.";
        CustomAssertion.softTrue("Incorrect text, that military record is not present",
                dvMilitaryText.getText().contains(militaryText));
    }

    public void validateDegree(String attended, String localSchool, Boolean currentlyAttending,
                               String endDate, String degree, String area) {
        Div dvHighDiplomaDescription = new Div(By.xpath(String.format(militaryDescriptionXpath, degree)),
                "High school diploma");
        if (currentlyAttending) {
            CustomAssertion.softAssertContains(dvHighDiplomaDescription.getText(), degree
                    + " Currently in School\nAttending "
                    + attended + " - " + localSchool, "Incorrect education description, if participant attends school");
        } else {
            CustomAssertion.softAssertContains(dvHighDiplomaDescription.getText(), degree + area
                    + " Incomplete, left school on " + endDate + "\nAttended " + attended + " - "
                    + localSchool, "Incorrect education description, if participant doesn't attend school");
        }
    }

    public void validateCert(String certificationType, String majorCert, String issuedDate, Participant participant) {
        Div dvCert = new Div(By.xpath(String.format(militaryDescriptionXpath, certificationType)), "Education certification text");
        CustomAssertion.softAssertContains(dvCert.getText(), certificationType + " Currently in School\n" + "Attending "
                + participant.getFirstName() + " - " + participant.getAddress().getCity() + " , "
                + participant.getAddress().getZipCode(), "Incorrect education description, if participant has a certificate");
        lnkTitle.scrollTo();
    }

    public void printNotPresent() {
        btnPrint.assertIsNotPresent();
    }
}
