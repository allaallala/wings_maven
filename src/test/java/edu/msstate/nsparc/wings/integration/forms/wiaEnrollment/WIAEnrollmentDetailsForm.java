package edu.msstate.nsparc.wings.integration.forms.wiaEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.User;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Enrollments -> Search for record -> Open record
 */
public class WIAEnrollmentDetailsForm extends WIAEnrollmentBaseForm {
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private Button btnCreateAssessment = new Button("id=createAssessment", "Create Assessment");
    private TableCell tbcMessages = new TableCell("css=table[class='blue-border']", "Information Messages");

    //Basic Information
    private TableCell tbcApplicationDate = new TableCell(By.xpath(String.format(detailPath, "Application Date")), "Application Date");
    private TableCell tbcContactPerson = new TableCell(By.xpath(String.format(detailPath, "Contact Person")), "Contact Person");
    private TableCell tbcRelationshipToParticipant = new TableCell(By.xpath(String.format(detailPath, "Relationship to Participant")), "Relationship to Participant");
    private TableCell tbcParticipantType = new TableCell(By.xpath(String.format(detailPath, "Participant Type")), "Participant Type");
    private TableCell tbcWIB = new TableCell(By.xpath(String.format(detailPath, "WIB")), "WIB");
    private Button btnEditProgramInformation = new Button("id=editProgramInformation", "Edit Program Information");
    private Button btnEditYouthInformation = new Button("id=editYouthInformation", "Edit Youth Information");

    //Program Information
    private Span spnProgramInformation = new Span(By.xpath("//a[contains(.,'Program Information')]"), "Expand Program Information");
    private Span spnYouthInformation = new Span(By.xpath("//a[contains(.,'Youth Information')]"), "Expand Youth Information");

    String programPath = "//tr[@id='programInfo']//tr[%1$d]/td[2]";
    private TableCell tbcNumberInFamily = new TableCell(By.xpath(String.format(programPath, 1)), "NumberInFamily");
    private TableCell tbcPreProgramWages = new TableCell(By.xpath(String.format(programPath, 2)), "Preprogram Wages");
    private TableCell tbcAnnualFamilyIncome = new TableCell(By.xpath(String.format(programPath, 3)), "Annual Family Income");
    private Button btnDelete = new Button("id=deleteEnrollment", "Delete button");
    private Button btnCancel = new Button("id=cancel", "Cancel button");


    /**
     * Default constructor
     */
    public WIAEnrollmentDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Enrollment Detail')]"), "Wia Enrollment Detail");
    }

    /**
     * Get messages text
     *
     * @return messages text
     */
    public String getMessagesText() {
        return tbcMessages.getText();
    }

    /**
     * Get application date
     *
     * @return application date text on the page
     */
    public String getApplicationDate() {
        return tbcApplicationDate.getText();
    }

    /**
     * Get contact person text on the page
     *
     * @return contact person text
     */
    public String getContactPersonText() {
        return tbcContactPerson.getText();
    }

    /**
     * Get relationship to participant text on the page
     *
     * @return relationship participant
     */
    public String getRelationshipParticipantText() {
        return tbcRelationshipToParticipant.getText();
    }

    /**
     * Get participant type text
     *
     * @return participant type text
     */
    public String getParticipantTypeText() {
        return tbcParticipantType.getText();
    }

    /**
     * Get WIB text
     *
     * @return WIB text
     */
    public String getWibText() {
        return tbcWIB.getText();
    }

    /**
     * Click Youth information.
     */
    public void clickYouthInformation() {
        spnYouthInformation.click();
    }

    /**
     * This method is used for checking Basic Information of WIA Enrollment
     *
     * @param applicationDate - application date
     * @param contactPerson   - contact person
     * @param relation        - participant relation
     * @return true, if expected data mathes actual
     */
    public boolean validateBasicInformation(String applicationDate, String contactPerson, String relation) {
        info(getApplicationDate());
        info(getContactPersonText());
        info(getRelationshipParticipantText());
        return applicationDate.equals(getApplicationDate()) &&
                contactPerson.equals(getContactPersonText()) &&
                relation.equals(getRelationshipParticipantText());
    }

    /**
     * This method is used for checking Program Information of WIA Enrollment
     *
     * @param numberFamily - number of the family members to check
     * @param wages        - program wages to check
     * @param familyIncome - annual family income to check
     * @return Indicates if expected data matches with actual
     */
    public boolean validateProgramInformation(String numberFamily, String wages, String familyIncome) {
        spnProgramInformation.click();
        return tbcNumberInFamily.getText().contains(numberFamily) &&
                tbcAnnualFamilyIncome.getText().contains(familyIncome) &&
                tbcPreProgramWages.getText().contains(wages);
    }

    /**
     * Click [Create Assessment]
     */
    public void clickCreateAssesm() {
        btnCreateAssessment.clickAndWait();
    }

    /**
     * Edit program information.
     */
    public void clickEditProgramInfo() {
        btnEditProgramInformation.clickAndWait();
    }

    /**
     * Edit youth information.
     */
    public void editYouthInformation() {
        btnEditYouthInformation.clickAndWait();
    }

    /**
     * Check, that buttons is present (or not) on the page
     *
     * @param user - current user
     */
    public void checkButtonsAuditDelete(User user) {
        Boolean audit = user.getWiaEnrollment().getEnrollmentViewAuditButton();
        Boolean delete = user.getWiaEnrollment().getEnrollmentDeleteButton();
        Boolean editBasic = user.getWiaEnrollment().getEnrollmentEditBasicInfo();
        Boolean editProgram = user.getWiaEnrollment().getEnrollmentEditProgramInfo();
        divideMessage("Audit");
        ifButton(audit, BaseButton.AUDIT.getButton());
        divideMessage("Delete");
        ifButton(delete, btnDelete);
        divideMessage("Edit basic information data");
        ifButton(editBasic, BaseButton.EDIT_BASIC_INFORMATION.getButton());
        divideMessage("Edit program information data");
        ifButton(editProgram, btnEditProgramInformation);
        if (audit) {
            audit();
        }
        if (delete) {
            btnDelete.clickAndWait();
            btnCancel.clickAndWait();
        }
    }

    /**
     * Create another WIA enrollment
     *
     * @param applicationDate - application date
     * @param contactPerson   - contact person
     * @param contactPhone    - contact phone
     * @param relation        - participant relation
     * @param youth           - if participant is youth
     */
    public void createAnotherWiaEnrollment(String applicationDate, String contactPerson, String contactPhone, String relation, Boolean youth) {
        clickAndWait(BaseButton.CREATE_ANOTHER);
        WIAEnrollmentCreationForm creationPage = new WIAEnrollmentCreationForm();
        creationPage.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, youth);
        creationPage.clickButton(Buttons.Continue);

        creationPage.fillWIAEnrollmentProgramInformation(youth);
        validateBasicInformation(applicationDate, contactPerson, relation);
    }

    /**
     * Edit basic information
     *
     * @param applicationDate - application date
     * @param contactPerson   - contact person
     * @param contactPhone    - contact phone
     * @param relation        - relation
     */
    public void editBasicInformation(String applicationDate, String contactPerson, String contactPhone, String relation) {
        clickAndWait(BaseButton.EDIT_BASIC_INFORMATION);
        WIAEnrollmentEditForm editPage = new WIAEnrollmentEditForm();
        editPage.editBasicInfo(applicationDate, contactPerson, contactPhone, relation);
    }

    /**
     * Edit program information
     *
     * @param numberFamily - number in the family
     * @param wages        - program wages
     * @param familyIncome - annual family income
     */
    public void editProgramInformation(String numberFamily, String wages, String familyIncome) {
        clickEditProgramInfo();
        WIAEnrollmentEditForm editPage = new WIAEnrollmentEditForm();
        editPage.editProgramInfo(numberFamily, wages, familyIncome);
    }
}
