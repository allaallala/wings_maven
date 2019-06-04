package edu.msstate.nsparc.wings.integration.forms.assessment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Assessments -> Search for record -> Open record
 */
public class AssessmentDetailsForm extends AssessmentBaseForm {

    private String xpathExp = "//form[@id='youthTest']//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcAdministeredDate = new TableCell(By.xpath(String.format(xpathExp, "Date Administered")), "Date Administered");
    private TableCell tbcScore = new TableCell(By.xpath(String.format(xpathExp, "Score")),"Score");
    private TableCell tbcProgram = new TableCell(By.xpath(String.format(xpathExp, "Program")),"Program");
    private Link lnkAddNote = new Link("css=span#LinkNotesDiv > a", "Add note");
    private TableCell tbcCategory = new TableCell(By.xpath(String.format(xpathExp, "Category")), "Category");
    private TableCell tbcEducational = new TableCell(By.xpath(String.format(xpathExp, "Educational Functional Level")), "Educational Functional Level");
    private Button btnDelete = new Button(By.xpath("//input[@title='Delete']"), "Delete button");

    /**
     * Constructor
     */
    public AssessmentDetailsForm() {
           super(By.xpath("//span[@id='breadCrumb'][contains(.,'Assessment Detail')]"), "Assessment Detail");
    }

    /**
     * This method is used for comparing Assessment data with expected values
     * @param assessment Expected values
     */
    public void validateAssessmentInformation(Assessment assessment) {
        CustomAssertion.softAssertEquals(getTypeText(), assessment.getType(), "Incorrect assesment type");
        CustomAssertion.softAssertEquals(getFunctionalText(), assessment.getFunctionalArea(), "Incorrect assesment functional area");
        CustomAssertion.softAssertEquals(tbcAdministeredDate.getText().trim(), assessment.getDateAdministered(), "Incorrect date administred.");
        CustomAssertion.softAssertEquals(tbcScore.getText().trim(), assessment.getScore(), "Incorrect assessment score");
        CustomAssertion.softAssertEquals(tbcProgram.getText().trim(), assessment.getProgram(), "Incorrect assessment program");
    }

    /**
     * This method is used for validatin assessment data with Category and Educational level fields also.
     * @param assessment - assessment
     */
    public void validateAssessmentInformationWithCategoryAndEducational(Assessment assessment) {
        validateAssessmentInformation(assessment);
        CustomAssertion.softAssertEquals(tbcCategory.getText().trim(), assessment.getCategory(), "Incorrect assessment category");
        CustomAssertion.softAssertEquals(tbcEducational.getText().trim(), assessment.getEducationalLevel(), "Incorrect assessment educational level");
    }

    /**
     * Validate infromation on the assessment participantSSDetails page using defined data
     * @param program - program to validate
     * @param functionalArea - functional area to validate
     * @param type - type to validate
     * @param date - date to validate
     * @param score - score to validate
     */
    public void validateData(String program, String functionalArea, String type, String date, String score) {
        CustomAssertion.softAssertEquals(tbcProgram.getText().trim(), program, "Incorrect assessment program");
        CustomAssertion.softAssertEquals(getFunctionalText(), functionalArea, "Incorrect assesment functional area");
        CustomAssertion.softAssertEquals(getTypeText(), type, "Incorrect assesment type");
        CustomAssertion.softAssertEquals(tbcAdministeredDate.getText().trim(), date, "Incorrect date administred.");
        CustomAssertion.softAssertEquals(tbcScore.getText().trim(), score, "Incorrect assessment score");

    }

    /**
     * Get data administred text
     * @return date administred text
     */
    public String getDateAdministered() {
        return tbcAdministeredDate.getText();
    }

    /**
     * Add note for assessment
     */
    public void addNote() {
        lnkAddNote.click();
    }

    /**
     * Delete assessment
     */
    public void deleteAssessment() {
        btnDelete.clickAndWait();
        areYouSure(Popup.Yes);
        clickButton(Buttons.Search);
        noSearchResults();
    }
}
