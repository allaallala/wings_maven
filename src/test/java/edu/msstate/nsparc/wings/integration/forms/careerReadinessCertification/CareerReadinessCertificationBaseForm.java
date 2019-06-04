package edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This is the base form for Career Readiness Certification forms
 */
public class CareerReadinessCertificationBaseForm extends BaseWingsForm {

    private TextBox txbDateAdministered = new TextBox(By.id("dateAdministered"), "Date Administered");
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    //Choosing Assessments
    private ComboBox cmbAppliedMathematics = new ComboBox(By.id("selectedMathematicsAssessmentId"), "Applied Mathematics Assessment");
    private ComboBox cmbLocatingInformation = new ComboBox(By.id("selectedInformationAssessmentId"), "Locating Information Assessment");
    private ComboBox cmbReadingForInformation = new ComboBox(By.id("selectedReadingAssessmentId"), "Reading for Information Assessment");

    //Help block
    private String helpPath = detailPath + "/strong";
    private Button btnHelp = new Button("//a[contains(.,'Help')]","Help");
    private TableCell tbcHeadDescription = new TableCell(By.xpath("//tr[@id='help']//tr[1]//u"),"Description head");
    private TableCell tbcDescription = new TableCell(By.xpath("//tr[@id='help']//tr[2]"),"Description text");
    private TableCell tbcHeadScoringTables = new TableCell(By.xpath("//tr[@id='help']//tr[3]//u"),"Scoring tables head");
    private Span spnApplMathLevel = new Span(String.format(helpPath,"Mathematics"), "Applied Mathematics Assessment level");
    private Span spnLocating = new Span(String.format(helpPath,"Locating"), "Locating Information Assessment Level");
    private Span spnReading = new Span(String.format(helpPath,"Reading"), "Reading for Information Assessment level");
    private Span spnResult = new Span("//form[@method='post']//td[contains(.,'Result')]/following-sibling::td","Certification result for Assessments");
    //Data
    private String qualifiedXpath = "//tr[@id='help']//tr[%1$s]//tbody/tr[%2$s]/td[%3$s]";
    private String headXpath = "//tr[@id='help']//tr[%1$s]//thead//th";
    private String regExp = "\\d{1,}";
    private static final String DESCRIPTION_TEXT = "A Career Readiness Certification is comprised of 3 WorkKeys assessments, one in Applied Mathematics, one in Reading for Information and one in Locating Information. "
            + "The result is determined by the lowest level of the 3 chosen assessments.";
    private static final String DESCRIPTION_HEAD = "Description";
    private static final String SCORING_TABLES_HEAD = "Scoring Tables";

    enum MassiveTypes { MATH_TYPE, LOC_TYPE, READ_TYPE, RESULT }

    private String[] possibleResults = {"Below Certification Level", "Bronze", "Silver", "Gold", "Platinum", "Platinum"};
    private Integer[] possibleNumberLevel = {0, 3, 4, 5, 6, 7};
    private String[] appliedMathematics = {"Applied Mathematics", "Minimum Score", "Maximum Score", "Level", "0", "70", "0", "71", "74", "3", "75", "77", "4", "78", "81", "5", "82","86","6", "87", "90", "7"};
    private String[] locatingInformation = {"Locating Information", "Minimum Score", "Maximum Score", "Level", "0", "71", "0", "72", "74", "3", "75", "79", "4", "80", "86", "5", "87", "90", "6"};
    private String[] readingInformation = {"Reading for Information", "Minimum Score", "Maximum Score", "Level", "0", "72", "0", "73", "74", "3", "75", "78", "4", "79", "81", "5", "82", "84", "6", "85", "90", "7"};
    private String[] resultDetermination = {"Result Determination", "Level", "Result", "0", "Below Certification Level", "3", "Bronze", "4", "Silver", "5", "Gold", "6", "Platinum", "7", "Platinum"};
    private static final Integer[] mathematicsValues = {0, 0, 70, 3, 71, 3, 74, 4, 75, 4, 77, 5, 78, 5, 81, 6, 82, 6, 86, 7, 87, 7, 90};
    private static final Integer[] locationValues =    {0, 0, 71, 3, 72, 3, 74, 4, 75, 4, 79, 5, 80, 5, 86, 6, 87, 6, 90};
    private static final Integer[] readingValues =     {0, 0, 72, 3, 73, 3, 74, 4, 75, 4, 78, 5, 79, 5, 81, 6, 82, 6, 84, 7, 85, 7, 90};
    private static final Integer MATH_ROW = 7;
    private static final Integer MATH_COLUMN = 3;
    private static final Integer LOC_ROW = 6;
    private static final Integer LOC_COLUMN = 3;
    private static final Integer READ_ROW = 7;
    private static final Integer READ_COLUMN = 3;
    private static final Integer RESULT_ROW = 7;
    private static final Integer RESULT_COLUMN = 2;

    /**
     * Default constructor
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    CareerReadinessCertificationBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Validate information in [Help] section.
     */
    public void validateHelpInformation() {
        String[] blockNumbers = {"4", "5", "6", "7"};
        CustomAssertion.softAssertEquals(tbcHeadDescription.getText(), DESCRIPTION_HEAD,"Incorrect head in Description block");
        CustomAssertion.softAssertEquals(tbcDescription.getText(), DESCRIPTION_TEXT,"Incorrect text in Description block");
        CustomAssertion.softAssertEquals(tbcHeadScoringTables.getText(), SCORING_TABLES_HEAD,"Incorrect head in Scoring Tables block");
        validateBlockInfo(blockNumbers[0], MATH_ROW, MATH_COLUMN, MassiveTypes.MATH_TYPE);
        validateBlockInfo(blockNumbers[1], LOC_ROW, LOC_COLUMN, MassiveTypes.LOC_TYPE);
        validateBlockInfo(blockNumbers[2], READ_ROW, READ_COLUMN, MassiveTypes.READ_TYPE);
        validateBlockInfo(blockNumbers[3], RESULT_ROW, RESULT_COLUMN, MassiveTypes.RESULT);
    }
    /**
     * This method is used for validating information in some block (Applied mathematices, Locating Information, Reading Information).
     * @param blockNumber - nubmer of block
     * @param rows - quantity of the records in block matrix
     * @param columns - quantity of columns in block matrix
     * @param type - type of the massive to check.
     */
    private void validateBlockInfo(String blockNumber, Integer rows, Integer columns, MassiveTypes type) {
        String[] massive;
        switch (type) {
            case MATH_TYPE:
                massive = appliedMathematics;
                break;
            case LOC_TYPE:
                massive = locatingInformation;
                break;
            case READ_TYPE:
                massive = readingInformation;
                break;
            case RESULT:
                massive = resultDetermination;
                break;
            default:
                throw new InvalidParameterException(type + " is incorrect. Try to input correct type value");
        }

        int k = 1;
        TableCell tbcHead = new TableCell(By.xpath(String.format(headXpath, blockNumber)),"Head of the block");
        Assert.assertEquals("Incorrect value in the head of block",massive[0],tbcHead.getText());
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                TableCell tbcCell = new TableCell(By.xpath(String.format(qualifiedXpath,blockNumber,i,j)),"Element in chosen block");
                Assert.assertEquals("Incorrect value in matrix",massive[k],tbcCell.getText());
                k++;
            }
        }
    }

    private Integer countLevel(Integer value, MassiveTypes type) {
        Integer[] massive = {1,2,3};
        switch (type) {
            case MATH_TYPE:
                massive = mathematicsValues;
                break;
            case LOC_TYPE:
                massive = locationValues;
                break;
            case READ_TYPE:
                massive = readingValues;
                break;
            default:
                throw new InvalidParameterException(type + " is incorrect. Try to input correct type value");
        }


        Integer level = 0;
        int i = 0;
        while (value > massive[i] && i < massive.length - 1) {
            level = massive [i + 1];
            i = i + 2;
        }
        if (Objects.equals(value, massive[massive.length - 1])) {
            level = massive[massive.length - 2];
        }
        return level;
    }

    public String getResult(CareerReadinessCertification crc) {
        Integer[] levels = getLevels(crc);
        Arrays.sort(levels);
        Integer min = levels[0];

        String result = null;
        //Get ordinal number in the massive and text result
        for (int i = 0; i < possibleNumberLevel.length; i++) {
            if (min.equals(possibleNumberLevel[i])) {
                result = possibleResults[i];
            }
        }
        return result;
    }

    private Integer[] getLevels(CareerReadinessCertification crc) {
        Integer lvlMath = countLevel(Integer.valueOf(crc.getAppliedMathematics().getScore()), MassiveTypes.MATH_TYPE);
        Integer lvlLocating = countLevel(Integer.valueOf(crc.getLocatingInformation().getScore()), MassiveTypes.LOC_TYPE);
        Integer lvlReading = countLevel(Integer.valueOf(crc.getReadingForInformation().getScore()), MassiveTypes.READ_TYPE);
        return new Integer[] {lvlMath, lvlLocating, lvlReading};
    }

    public void validateLevel(CareerReadinessCertification crc) {
        Integer[] levels = getLevels(crc);
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(spnApplMathLevel.getText(), regExp), levels[0].toString(),"Incorrect level for certificate math");
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(spnLocating.getText(), regExp), levels[1].toString(), "Incorrect level for certificate loc");
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(spnReading.getText(), regExp), levels[2].toString(), "Incorrect level for certificate read");
        CustomAssertion.softAssertEquals(spnResult.getText(), getResult(crc), "Incorrect result for certifications");
    }

    public void inputDataAdministered(String date) {
        txbDateAdministered.type(date);
    }

    public void openHelpBlock() {
        btnHelp.click();
    }

    public enum AssessmentTypes { MATH, LOC, READ }


    public void selectAssessments(String option, AssessmentTypes type) {
        switch (type) {
            case MATH:
                cmbAppliedMathematics.select(option);
                break;
            case LOC:
                cmbLocatingInformation.select(option);
                break;
            case READ:
                cmbReadingForInformation.select(option);
                break;
            default:
                info("Try to enter some correct data");
                break;
        }
    }

    public void assessmentsIsNotAvailable() {
        cmbAppliedMathematics.softIsNotPresent();
        cmbLocatingInformation.softIsNotPresent();
        cmbReadingForInformation.softIsNotPresent();
    }

    public String getDateAdminValue() {
        return txbDateAdministered.getValue();
    }
}
