package edu.msstate.nsparc.wings.integration.forms.assessment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import framework.CommonFunctions;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
 This form is opened via Participants -> WIA -> Youth Assessments -> Create
 */
public class AssessmentCreationForm extends AssessmentBaseForm {
    // Items on creation page
    private Span spnPageHeader = new Span("css=form[id='youthTest'] h1", "Page Header");
    private ComboBox cmbWIAEnrollment = new ComboBox("css=select[id='selectedWiaEnrollmentId']", "WIOA Enrollment");
    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private RadioButton rbPostTest = new RadioButton("css=input#isPretest2","Post-Test");
    private ComboBox cmbCategory = new ComboBox("css=select#assessmentCategory","Category");
    private ComboBox cmbEducationalFunctionalArea = new ComboBox("css=select#eduFuncLevel","Educational Functional Area");
    private Button btnYouthProviderLookup = new Button("//td[text()[contains(.,'Youth Provider')]]/following-sibling::td//button[@title='Search']", "Youth Provider Lookup");
    private Button btnRemoveYouthProvider = new Button("//td[text()[contains(.,'Youth Provider')]]/following-sibling::td//a[@class='powerLookupRemoveButton']", "Remove Youth Provider");
    private Span spnValidationErrorMessage = new Span("css=span[id='id.errors']", "Validation error message");
    private Button btnAddToEnrollment = new Button("id=addToWiaEnrollment", "Add to WIA Enrollment");
    private Span spnDateError = new Span("css=span[id='dateTest.errors']", "Date Error");
    private ComboBox cmbPreTest = new ComboBox("css=select[name='selectedPretest']", "Pre-Test");
    private Button btnAddToIndividualEmploymentPlan = new Button("id=addToIEP", "Add to IEP");
    private ComboBox cmbFirstTest = new ComboBox(By.xpath("//select[@id='selectedPretestId']/option[2]"),"Combobox with first test information.");
    private String educationalLevel = "Beginning ESL Literacy";
    private  String expectedCategory = "ABE";

    /**
     * Constructor
     */
    public AssessmentCreationForm() {
            super(By.xpath("//span[@id='breadCrumb'][contains(.,'Assessment Creation')]"), "Assessment Creation");
    }

    /**
     * This method is used for adding Not BSD Assessment (low score)
     * @param assessmentType Assessment type
     * @param assessmentScore Assessment score
     * @param assessmentFunctionalArea Assessment functional area
     */
    public void addNotBSDAssessment(String assessmentType, String assessmentScore, String assessmentFunctionalArea) {
        selectYouthProvider();
        inputDateAdministered(CommonFunctions.getYesterdayDate());
        cmbFunctionalArea.select(assessmentFunctionalArea);
        selectType(assessmentType);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        typeScore(assessmentScore);
        btnAddToEnrollment.clickAndWait();
    }

    /**
     * Get page header text
     * @return page header text
     */
    public String getSpanPageHeader() {
        return spnPageHeader.getText().trim();
    }

    /**
     * Get combobox first test
     * @return combobox first test.
     */
    public String getComboboxFirstTest() {
        return cmbFirstTest.getText();
    }

    /**
     * This method is used for selecting Youth Provider from look-up
     */
    private void selectYouthProvider() {
        if (btnRemoveYouthProvider.isPresent()) {
            btnRemoveYouthProvider.click();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
        }
        if (btnYouthProviderLookup.isPresent()) {
            btnYouthProviderLookup.clickAndWait();
            CenterSearchForm centerSearchForm = new CenterSearchForm();
            centerSearchForm.selectAndReturnCenter();
        }
    }

    /**
     * This method is used for filling all information needed for Assessment creation
     * @param assessment String array with assessment data
     */
    public void fillAssessmentInformation(User user, Assessment assessment) {
        if (!assessment.getPreTest()) {
            fillAssessmentInformationForPostTest(user, assessment);
        } else {
            selectParticipantProgram(user, assessment);
        }

        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        if (preTestPresent() && assessment.getPreTest()) {
            selectPreTest();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        if (txbDateAdministered.isPresent()) {
            inputDateAdministered(assessment.getDateAdministered());
        }
        inputWithPreTest(assessment);
        selectFunctionalArea(assessment);
    }

    /**
     * If assessment is pre test
     * @param assessment - assessment
     */
    private void inputWithPreTest(Assessment assessment) {
        if (assessment.getPreTest()) {
            cmbFunctionalArea.select(assessment.getFunctionalArea());
            selectType(assessment.getType());
            BaseOtherElement.LOADING.getElement().waitForNotVisible();

            typeScore(assessment.getScore());
            selectSpecifiedCategory(assessment);
            if (cmbCategory.isPresent()) {
                cmbCategory.selectFirst();
            }
        }
    }

    /**
     * Select functional area
     * @param assessment - assessment
     */
    private void selectFunctionalArea(Assessment assessment) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        if (cmbEducationalFunctionalArea.isPresent()) {
            if (!(educationalLevel.equals(assessment.getEducationalLevel())) && assessment.getEducationalLevel() != null) {
                cmbEducationalFunctionalArea.select(assessment.getEducationalLevel());
            } else {
                cmbEducationalFunctionalArea.selectFirst();
            }
        }
    }

    /**
     * This method is used for filling all information needed for Post Test Assessment creation
     * @param assessment Assessment data
     */
    public void fillAssessmentInformationForPostTest(User user, Assessment assessment) {
        selectParticipantProgram(user, assessment);
        rbPostTest.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        cmbPreTest.selectFirst();
        inputDateAdministered(assessment.getDateAdministered());
        typeScore(assessment.getScore());
    }

    /**
     * Select specified category depends on assessment category
     * @param assessment - assessment
     */
    private void selectSpecifiedCategory(Assessment assessment) {
        if (rbFirstCat.isPresent()) {
            if (expectedCategory.equals(assessment.getCategory())) {
                selectCategory(first);
            } else {
                selectCategory(second);
            }
        }
    }

    /**
     * Select first enrollment
     * @param type - type of enrollment
      */
    public void selectFirstEnrollment(String type) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        if (type.contains(Constants.WIOA.toUpperCase())) {
            cmbWIAEnrollment.selectFirst();
        } else {
            cmbTradeEnrollment.selectFirst();
        }
    }

    /**
     * Select participant and program
     * @param assessment - assessment object
     */
    private void selectParticipantProgram(User user, Assessment assessment) {
        if (!assessment.isParticipantPrePopulated()) {
            selectParticipantByUser(user, assessment.getParticipant());
        }

        selectProgram(assessment.getProgram());
        if (Constants.WIOA.toUpperCase().equalsIgnoreCase(assessment.getProgram())) {
            BaseOtherElement.LOADING.getElement().waitForNotVisible();

            cmbWIAEnrollment.selectFirst();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();

        }
        if (Constants.TRADE.equalsIgnoreCase(assessment.getProgram())) {
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
            cmbTradeEnrollment.selectFirst();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();

        }
    }

    /**
     * Click [Add] button to add assessment to the iep.
     */
    public void addAssessmentIEP() {
        btnAddToIndividualEmploymentPlan.clickAndWait();
    }

    /**
     * Get error data text
     * @return error text
     */
    public String getErrorDataText() {
        return spnDateError.getText().trim();
    }

    /**
     * Check, that validating error message is present and visible
     * @return present or not.
     */
    public Boolean checkValidatingMessagePresent() {
        return spnValidationErrorMessage.isPresent();
    }
}
