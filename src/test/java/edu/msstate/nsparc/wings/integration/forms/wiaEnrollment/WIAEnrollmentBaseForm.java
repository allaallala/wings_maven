package edu.msstate.nsparc.wings.integration.forms.wiaEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.CommonFunctions;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.Span;
import framework.elements.TextBox;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This is the base form for WIA Enrollment forms
 */
public class WIAEnrollmentBaseForm extends BaseWingsForm {

    //common fields for WIA forms
    private TextBox txbApplicationDate = new TextBox(By.xpath("//input[@id='applicationDate'] | //input[@id='dateApplication'] | "
            + "//input[@id='dateATAARTAAApplication'] | //input[@id='dateTAAApplication']"), "Application Date");
    private Span spnErrors = new Span("css=span[id='id.errors']", "Errors");
    private TextBox txbContactPerson = new TextBox("css=input#contactPerson", "Contact Person");
    protected TextBox txbContactPhone = new TextBox("css=input#contactPhone", "Contact Phone");
    private TextBox txbRelationToParticipant = new TextBox("css=input#contactRelationship", "Relation to Participant");
    private ComboBox cmbParticipantType = new ComboBox("css=select#participantType", "Participant Type");
    protected ComboBox cmbWIB = new ComboBox("css=select#wibNumber", "WIB");
    private ComboBox cmbNEG = new ComboBox("css=select#nationalEmergencyGrantOne", "National Emergency Grant");
    protected TextBox txbEligibilityDate = new TextBox("css=input#eligibilityDate", "Eligibility Date");
    private Span spnApplicationDateError = new Span("css=span[id='applicationDate.errors']", "Application Date error");
    private Span spnEligibilityDateError = new Span("css=span[id='eligibilityDate.errors']", "Eligibility Date error");
    private TextBox txbNumberInFamily = new TextBox("css=input#numberInFamily", "Number In Family");
    private TextBox txbPreprogramWages = new TextBox("css=input#preprogramWage", "Preprogram Wages");
    private ComboBox cmbTxbPreprogramWagesPer = new ComboBox("css=select#preprogramWagesPer", "Preprogram Wages Per");
    private TextBox txbAnnualFamilyIncome = new TextBox("css=input#annualFamilyIncome", "Annual Family Income");
    private RadioButton rbRunawayNo = new RadioButton("css=input#isRunawayYouth2", "Runaway");
    private RadioButton rbEngLanguageLearner = new RadioButton("css=input#isEnglishLanguageLearner2", "English Language Learner - No");
    private RadioButton rbIncarcerated = new RadioButton("css=input#isIncarcerated2", "Incarcerated - No");
    private RadioButton rbReceivedSSI = new RadioButton("css=input#receivedSSI2", "Received SSI - No");
    private RadioButton rbReceivesOrEligibleFreeLunch = new RadioButton("css=input#receivesOrEligibleFreeLunch2", "Receives Or Eligible Free Lunch - No");
    private RadioButton rbDropOut = new RadioButton("css=input#isDropOut2", "Drop Out - No");
    private RadioButton rbPregnantParentingYouthNo = new RadioButton("css=input#isParentYouth2", "Pregnant/Parenting Youth");
    private RadioButton rbYouthAdditionalAssistanceNo = new RadioButton("css=input#receivedAdditionalAssistance2", "Youth Additional Assistance - No");
    private RadioButton rbUnderemployedNo = new RadioButton("css=input#isUnderemployed2", "Underemployed - No");
    private RadioButton rbFosterCareYouthNo = new RadioButton("css=input#receivedFosterCare2", "Foster Care Youth - No");
    private RadioButton rbReceivedSSIinLastSixMonthsNo = new RadioButton("css=input#receivedSSI2", "Received SSI in last six months?");
    private RadioButton rbEnglishLanguageLearner = new RadioButton("css=input#isEnglishLanguageLearner2", "English Language Learner");
    private RadioButton rbYouthAdditionalAssistanceYes = new RadioButton("css=input#receivedAdditionalAssistance1", "Youth Additional Assistance - Yes");
    private RadioButton rbUnderemployedYes = new RadioButton("css=input#isUnderemployed1", "Underemployed - Yes");
    private RadioButton rbFosterCareYouthYes = new RadioButton("css=input#receivedFosterCare1", "Foster Care Youth - Yes");
    protected RadioButton rbIncarceratedNo = new RadioButton(By.id("isIncarcerated2"), "Incarcerated program entry no");
    RadioButton rbDropoutNo = new RadioButton(By.id("isDropOut2"), "Dropout - No");
    private RadioButton rbFreeOrReducedPriceLunchYes = new RadioButton("css=input#receivesOrEligibleFreeLunch1", "Eligible for free or reduced price Lunch? - Yes");
    protected RadioButton rbBasicSkillsDeficientNo = new RadioButton("css=input#isBasicSkillsDeficient2", "Basic Skills Deficient - No");
    private RadioButton rbYouthDropout = new RadioButton("css=input#isDropOut2", "Is Youth Dropout - no");
    protected ComboBox cmbToolUsed = new ComboBox("id=bsdTool.id", "Tool Used");
    protected TextBox txbNewTool = new TextBox("id=bsdTool.newTool", "New Tool");

    /**
     * Constructor of the form with defined locator
     *
     * @param locator   - locator of the form
     * @param formTitle - title of the page.
     */
    public WIAEnrollmentBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Get error text
     *
     * @return get error text
     */
    public String getErrorText() {
        return spnErrors.getText();
    }

    /**
     * Check, that error message is present and visible
     *
     * @return true, if present
     */
    public Boolean checkErrorsPresent() {
        return spnErrors.isPresent();
    }

    /**
     * This method is used for filling  basic information on third page of WIA Enrollment creation for Youth participant
     *
     * @param educationalStatus Participant Educational status
     */
    public void fillBasicYouthInformation(String educationalStatus) {
        rbRunawayNo.click();
        rbPregnantParentingYouthNo.click();
        rbReceivedSSIinLastSixMonthsNo.click();
        rbEnglishLanguageLearner.click();
        rbYouthAdditionalAssistanceYes.click();
        rbUnderemployedYes.click();
        rbFosterCareYouthYes.click();
        rbIncarceratedNo.click();
        rbFreeOrReducedPriceLunchYes.click();
        rbYouthDropout.click();
        selectEducationStatus(educationalStatus);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * This method is used for checking data validation on first page of WIA Enrollment
     */
    public void checkDataValidation() {
        String longAgoDate = "01/01/1997";
        String applicationDateError = "Application Date must be on or after 01/01/1998";
        String temp = getApplicationDate();
        inputApplicationDate(longAgoDate);
        clickButton(Buttons.Continue);
        assertTrue("Application date error message wasn't displayed",
                spnApplicationDateError.getText().contains(applicationDateError));
        inputApplicationDate(temp);

        String eligibilityDate = CommonFunctions.getDaysAgoDate(2);
        String eligibilityDateError = "Eligibility Date must be on or after Application Date";
        temp = txbEligibilityDate.getValue();
        txbEligibilityDate.type(eligibilityDate);
        clickButton(Buttons.Continue);
        assertTrue("Eligibility date error message wasn't displayed",
                spnEligibilityDateError.getText().contains(eligibilityDateError));
        txbEligibilityDate.type(temp);
    }

    /**
     * Input contact person
     *
     * @param contactPerson - contact person
     */
    public void inputContactPerson(String contactPerson) {
        txbContactPerson.type(contactPerson);
    }

    /**
     * Input participant relation
     *
     * @param relation - relation.
     */
    public void inputParticipantRelation(String relation) {
        txbRelationToParticipant.type(relation);
    }

    /**
     * Select participant type
     *
     * @param participantType - value to select
     */
    public void selectParticipantType(String participantType) {
        cmbParticipantType.select(participantType);
    }

    /**
     * Select first tool used.
     */
    public void selectFirstToolUsed() {
        cmbToolUsed.selectFirst();
    }

    /**
     * Select WIB
     *
     * @param wibValue - WIB value
     */
    public void selectWIB(String wibValue) {
        cmbWIB.select(wibValue);
    }

    /**
     * Wait for combo box NEG is present and visible, check its value
     *
     * @param value - value
     */
    public void waitCheckNEG(String value) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbNEG.checkValue(value);
    }

    /**
     * Click "no" to all questions on youth page.
     */
    public void clickNoAllQuestionsYouthPage() {
        rbEngLanguageLearner.click();
        rbIncarcerated.click();
        rbReceivedSSI.click();
        rbReceivesOrEligibleFreeLunch.click();
        rbDropOut.click();
        rbRunawayNo.click();
        rbPregnantParentingYouthNo.click();
        rbYouthAdditionalAssistanceNo.click();
        rbUnderemployedNo.click();
        rbFosterCareYouthNo.click();
    }

    /**
     * Get application date
     * @return - application date.
     */
    public String getApplicationDate() {
        return txbApplicationDate.getValue();
    }

    /**
     * Input number in family
     *
     * @param numberFamily - number in family
     */
    public void inputNumberFamily(String numberFamily) {
        txbNumberInFamily.type(numberFamily);
    }

    /**
     * Input preprogram wages
     *
     * @param wages - preprogram wages
     */
    public void inputProgramWages(String wages) {
        String wagesPer = "Hour";
        txbPreprogramWages.type(wages);
        cmbTxbPreprogramWagesPer.select(wagesPer);
    }

    /**
     * Input annual family income
     *
     * @param familyIncome - family income
     */
    public void inputAnnualFamilyIncome(String familyIncome) {
        txbAnnualFamilyIncome.type(familyIncome);
    }
}
