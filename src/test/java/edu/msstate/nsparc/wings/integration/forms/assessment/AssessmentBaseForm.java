package edu.msstate.nsparc.wings.integration.forms.assessment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This is the base form for Youth Assessment forms
 */
public class AssessmentBaseForm extends BaseWingsForm {

    // common elements for each assessment view
    private ComboBox cmbProgram = new ComboBox("css=select[id='program']", "Program");
    private RadioButton rbPreTest = new RadioButton("id=isPretest1", "Pre-Test");
    protected TextBox txbDateAdministered = new TextBox("id=dateTest", "Date Administered");
    private ComboBox cmbType = new ComboBox("id=assessmentType", "Type");
    private TextBox txbScore = new TextBox("id=score","Score");
    protected ComboBox cmbFunctionalArea = new ComboBox("id=functionalArea", "Functional Area");
    private TableCell tbcType = new TableCell(By.xpath("//td[contains(.,'Type')]/following-sibling::td"), "Type");
    protected ComboBox cmbEducationalFunctionalArea = new ComboBox(By.xpath("//select[@id='eduFuncLevel']"), "Educational Functional Area");
    private TableCell tbcFunctionalArea = new TableCell(By.xpath("//td[contains(.,'Functional Area')]/following-sibling::td"), "Functional Area");
    protected RadioButton rbFirstCat = new RadioButton("id=assessmentCategory0", "Click the first Category");
    private RadioButton rbSecondCat = new RadioButton("id=assessmentCategory1", "Click the second Category");
    private Button btnSaveChanges = new Button(By.xpath("//button[@id='saveChangesForIEP']"), "Edit assessment");
    private Button btnSaveChangesPreTest = new Button(By.xpath("//button[@id='saveChangesPreTest']"), "Edit assessment");
    protected String first = "first";
    protected String second = "second";
    private TextBox TXB_PARTICIPANT = new TextBox(By.xpath("//span[@id='participantLookup']//input[2]"), "Participant");

    /**
     * Constructor
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public AssessmentBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Save changes
     */
    public void saveChanges() {
        if (btnSaveChanges.isPresent()) {
        btnSaveChanges.clickAndWait();
        } else {
            btnSaveChangesPreTest.clickAndWait();
        }
    }

    /**
     * Select functional area
     * @param area - functional area
     */
    public void selectFunctionalArea(String area) {
        cmbFunctionalArea.select(area);
    }
    /**
     * Select assessment type
     * @param type - assessment type
     */
    public void selectType(String type) {
        cmbType.select(type);
    }

    /**
     * Check, that comboboxes "assessment type", "functional area" and radio button "first category"
     * are not present on the page.
     */
    public void elementsNotPresent() {
        cmbType.softIsNotPresent();
        cmbFunctionalArea.softIsNotPresent();
        rbFirstCat.softIsNotPresent();
    }

    /**
     * Type new score
     * @param scoreValue - score value
     */
    public void typeScore(String scoreValue) {
        txbScore.type(scoreValue);
    }

    /**
     * Select required category
     * @param categoryNumber - first or second category
     */
    public void selectCategory(String categoryNumber) {
        if (categoryNumber.contains(first)) {
            rbFirstCat.click();
        } else {
            rbSecondCat.click();
        }
    }

    /**
     * Select pre test: soft
     */
    public void selectPreTest() {
        if (rbPreTest.isPresent()) {
            clickPreTest();
        }
    }

    /**
     * Click pre test: strict.
     */
    public void clickPreTest() {
        rbPreTest.click();
    }

    /**
     * Return true, if radio button pre test is present and visible.
     * @return true/false
     */
    public Boolean preTestPresent() {
       return rbPreTest.isPresent();
    }

    /**
     * This method is used for selecting appropriate program for Assessment
     * @param program The name of the desired Program
     */
    public void selectProgram(String program) {
        cmbProgram.select(program);
    }
    /**
     * Check fields that can be edited on trying to edit created assessment
     */
    public void validateResult() {
        cmbFunctionalArea.softIsNotPresent();
        cmbType.softIsNotPresent();
        TXB_PARTICIPANT.softIsNotPresent();
        cmbProgram.softIsNotPresent();
        txbScore.type(CommonFunctions.getRandomIntegerNumber(2));
        inputDateAdministered(CommonFunctions.getCurrentDate());
    }

    /**
     * Check presented value in combobox
     * @param value - value to check
     * @return true/false
     */
    public Boolean checkValueType(String value) {
        return cmbType.checkValue(value);
    }

    /**
     * Check presented value in combobox
     * @param value - value to check.
     * @return true/false
     */
    public Boolean checkFunctionalType(String value) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        return cmbFunctionalArea.getText().contains(value);
    }


    public String getFunctionalSelectedLabel() {
       return cmbFunctionalArea.getSelectedLabel();
    }

    /**
     * Get title of the type on the page
     * @return title of type
     */
    public String getTypeText() {
        return tbcType.getText().trim();
    }

    /**
     * Get title of the functional area on the page
     * @return title of the functional area.
     */
    public String getFunctionalText() {
        return tbcFunctionalArea.getText().trim();
    }


    public void inputDateAdministered(String dateAdministered) {
        txbDateAdministered.type(dateAdministered);
    }


    public String getDateAdministeredValue() {
        return txbDateAdministered.getValue();
    }

    public Boolean checkFunctionalAreaCbPresent() {
       return cmbFunctionalArea.isPresent();
    }
}
