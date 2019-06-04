package edu.msstate.nsparc.wings.integration.forms.wiaEnrollment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Enrollments -> Create
 */
public class WIAEnrollmentCreationForm extends WIAEnrollmentBaseForm {

    private static final String youthParticipant = "Youth";
    private static final String adultParticipant = "Adult";
    private String participantDislocated = "Dislocated Worker";
    private RadioButton rbSingleParentNo = new RadioButton("css=input#isSingleParent2", "Single Parent - No");
    private RadioButton rbOffenderNo = new RadioButton(By.id("isExOffender2"), "Offender - No");
    private Button btnParticipantWIALookUp = new Button(By.xpath("//input[@value='Participant']/following-sibling::button"), "Participant Lookup");
    private RadioButton rbDisplacedHomemakerNo = new RadioButton("css=input#isDisplacedHomemaker2", "Displaced Homemaker - No");
    private TextBox txbDislocationDate = new TextBox("css=input#dateDislocation", "Dislocation Date");
    private TableCell tbcOffenderLabel = new TableCell(By.xpath("//div[@id='basicInfoForm']//tbody[3]//tr[6]//td"), "Offender Label");
    //Page 2
    private RadioButton rbTANFParticipantNo = new RadioButton("css=input#receivedTANF2", "TANF Participant - No");
    private RadioButton rbFosterChildPaymentsNo = new RadioButton("css=input#receivedFosterChildPayments2", "Foster Child Payments - No");
    private RadioButton rbFoodStampsNo = new RadioButton("css=input#receivedFoodStamps2", "Food Stamps - No");
    private RadioButton rbOtherPublicAssistanceRecepientNo = new RadioButton("css=input#receivedOtherPublicAssistance2", "Other Public Assistance Recipient - No");
    private RadioButton rbEmploymentAndTrainingServicesRelatedToFoodStampsNo = new RadioButton("css=input#receivedFoodStampRelated2", "Employment and Training Services related to Food Stamps - No");
    private RadioButton rbVocationalEducationNo = new RadioButton(By.id("receivedVocationalEducation2"), "Vocational Education no");
    private RadioButton rbAdultEducationNo = new RadioButton(By.id("receivedAdultEducation2"), "Adult Education no");
    private ComboBox cmbVocationalReab = new ComboBox(By.id("receivedVocationalRehab"), "VocationalReabilitation no");
    private RadioButton rbLongTermUnemployedNo = new RadioButton(By.id("isLongTermUI2"), "Long term unemployed no");
    private RadioButton rbTanfNo = new RadioButton(By.id("exhaustingTANFInTwoYears2"), "Exhausting TANF Within 2 Years no");
    private RadioButton rbLowLevelLiteracyNo = new RadioButton(By.id("isLowLiteracyLevel2"), "Low Level Literacy no");
    private RadioButton rbCulturalBarriersNo = new RadioButton(By.id("hasCulturalBarriers2"), "Cultural barriers no");
    private ComboBox cbRecepientTraining = new ComboBox(By.id("recvIncumbentTraining"), "Recipient of Incumbent Worker Training");


    //Page 3
    private H1 h1YouthInformation = new H1(By.xpath("//h1[contains(.,'WIA Enrollment - Youth Information')]"), "Youth Information header");
    private RadioButton rbHomelessNo = new RadioButton("css=input#isHomelessYouth2", "Homeless");
    private RadioButton rbBasicSkillsDeficientYes = new RadioButton("css=input#isBasicSkillsDeficient1", "Basic Skills Deficient - Yes");

    private String programWages = CommonFunctions.getRandomIntegerNumber(Constants.FAMILY_LENGTH);
    private String annualFamilyIncome = CommonFunctions.getRandomIntegerNumber(Constants.FAMILY_LENGTH);
    private Button btnAddYouthAssessment = new Button("id=addAssessment", "Add Youth Assessment");

    /**
     * Default constructor
     */
    public WIAEnrollmentCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Enrollment Creation')]"), "Wioa Enrollment Creation");
    }


    /**
     * Get offender text
     *
     * @return offender text
     */
    public String getOffenderText() {
        return tbcOffenderLabel.getText();
    }

    /**
     * Check youth information page is displayed
     *
     * @return - true, if displayed
     */
    public Boolean checkYouthInfoPageNotDisplayed() {
        return h1YouthInformation.isPresent();
    }

    /**
     * Click basic skills deficient yes radio button
     */
    public void clickBasicSkillsYes() {
        rbBasicSkillsDeficientYes.click();
    }

    /**
     * Add youth assessment
     */
    public void addYouthAssessment() {
        btnAddYouthAssessment.clickAndWait();
    }

    /**
     * This method is used for selecting Participant from look-up
     *
     * @param participant Participant that should be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantWIALookUp.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    public void selectParticipantByUser(User user, Participant participant) {
        btnParticipantWIALookUp.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpenByUser(user, participant);
    }

    /**
     * This method is used for filling basic information on first page
     *
     * @param date          - eligibility date or application date
     * @param contactPerson - contact person
     * @param phone         - contact phone
     * @param relation      - participant relation
     */
    public void fillBasicInformation(String date, String contactPerson, String phone, String relation) {
        inputApplicationDate(date);
        txbEligibilityDate.type(date);
        rbOffenderNo.clickAndWait();
        if (rbOffenderNo.isEditable()) {
            rbOffenderNo.click();
        }
        rbSingleParentNo.click();
        inputContactPerson(contactPerson);
        txbContactPhone.type(phone);
        inputParticipantRelation(relation);
        rbHomelessNo.click();
    }

    /**
     * This method is used for filling first page of the WIA Enrollment creation process
     *
     * @param date          - eligibility date or application date
     * @param contactPerson - contact person
     * @param phone         - contact phone
     * @param relation      - participant relation
     * @param youth         - is youth
     */
    public void fillWIAEnrollmentBasicInformation(String date, String contactPerson, String phone, String relation, boolean youth) {
        fillBasicInformation(date, contactPerson, phone, relation);

        if (youth) {
            selectParticipantType(youthParticipant);
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
        } else {
            selectParticipantType(adultParticipant);
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
            cmbWIB.waitForIsElementPresent();
        }
    }

    /**
     * This method is used for filling first page of WIA Enrollment creation process
     *
     * @param date            - eligibility date or application date
     * @param contact         - contact person
     * @param phone           - contact phone
     * @param relation        - participant relation
     * @param participantType Participant Type that will be selected
     * @param wIB             WIB that will be selected
     */
    public void fillWIAEnrollmentBasicInformation(String date, String contact, String phone, String relation, String participantType, String wIB) {
        fillBasicInformation(date, contact, phone, relation);

        selectParticipantType(participantType);
        cmbWIB.select(wIB);
    }

    /**
     * This method is used for filling information in Dislocated Worker section on first page
     */
    public void fillBasicInformationDislocatedWorker() {
        selectParticipantType(participantDislocated);
        rbDisplacedHomemakerNo.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbDislocationDate.type(CommonFunctions.getCurrentDate());
        clickButton(Buttons.Continue);
    }

    /**
     * This method is used for filling second page of WIA Enrollment creation process
     *
     * @param youth Indicates if participant is youth
     */
    public void fillWIAEnrollmentProgramInformation(boolean youth) {
        // Fix for new validation
        String familyNumber = "5";
        inputNumberFamily(familyNumber);
        inputProgramWages(programWages);
        inputAnnualFamilyIncome(annualFamilyIncome);
        rbTANFParticipantNo.click();
        rbFosterChildPaymentsNo.click();
        rbFoodStampsNo.click();
        rbOtherPublicAssistanceRecepientNo.click();
        rbEmploymentAndTrainingServicesRelatedToFoodStampsNo.click();
        rbVocationalEducationNo.click();
        rbAdultEducationNo.click();
        cmbVocationalReab.select(Constants.NO_ANSWER);
        rbLongTermUnemployedNo.click();
        rbTanfNo.click();
        rbLowLevelLiteracyNo.click();
        rbCulturalBarriersNo.click();
        cbRecepientTraining.select(Constants.NO_ANSWER);
        if (youth) {
            clickButton(Buttons.Continue);
        } else {
           clickAndWait(BaseButton.CREATE);
        }
    }

    /**
     * This method is used for filling information on third page of WIA Enrollment creation for Youth participant
     *
     * @param educationalStatus Participant Educational status
     * @param toolUsed          Tool used for determine Basic Skills
     * @param youth             Indicates if participant is youth
     */
    public void fillWIAEnrollmentYouthInformation(String educationalStatus, String toolUsed, boolean youth) {
        if (youth) {
            fillBasicYouthInformation(educationalStatus);

            selectBasicSkillsDeficientNo(toolUsed);
            rbIncarceratedNo.click();
            rbDropoutNo.click();
            clickButton(Buttons.Continue);
            clickAndWait(BaseButton.CREATE);
        }
    }

    /**
     * This method is used for setting Basic Skills Deficient radiobutton to No
     *
     * @param toolUsed Tool that will be selected from combobox
     */
    public void selectBasicSkillsDeficientNo(String toolUsed) {
        rbBasicSkillsDeficientNo.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbToolUsed.waitForIsElementPresent();
        cmbToolUsed.select(toolUsed);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbNewTool.waitForIsElementPresent();
        txbNewTool.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
    }
}
