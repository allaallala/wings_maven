package edu.msstate.nsparc.wings.integration.forms.programOutcomes;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import java.util.List;

/**
 * This form is opened via Participants -> WIA -> Program Outcomes
 */
public class ProgramOutcomesDetailsForm extends ProgramOutcomesBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String firstQuarterPath = "//div[@id='firstQuarter']" + detailPath;
    private String otherXpath = "//div[@id='%1$s']//td[contains(.,'Employer')]/following-sibling::td";
    private TableCell tbcOccupationCode = new TableCell(By.xpath(String.format(detailPath, "Occupation Code")), "Occupation Code");
    private TableCell tbcRecognizedCredential = new TableCell(String.format(detailPath, "Recognized Credential"), "Recognized Credential (from Training)");

    private Button btnParticipantLookup = new Button("//td[contains(.,'Participant:')]/following-sibling::td//button", "Participant Lookup");
    private Button btnClearParticipant = new Button("//td[contains(.,'Participant:')]/following-sibling::td//a[@class='powerLookupRemoveButton']", "Cancel Participant Lookup");
    private Button btnEditProgram = new Button("id=manage", "Edit");
    private ComboBox cmbParticipationPeriod = new ComboBox("css=select#selectedPeriod", "Participation Period");

    private TableCell tbcExitDate = new TableCell(By.xpath(String.format(detailPath, "Exit Date")), "Exit Date");
    private TableCell tbcEducationStatusAtExit = new TableCell(By.xpath(String.format(detailPath, "Education Status at Exit")), "Education Status at Exit");

    private String hrefXpath = "//a[@href='%1$s']";
    //First Quarter
    private TableCell tbcEmployedFirstQuarter = new TableCell(By.xpath(String.format(firstQuarterPath, "Employed")), "Employed First Quarter");
    private TableCell tbcEmployerFirstQuarter = new TableCell(By.xpath(String.format(firstQuarterPath, "Employer")), "Employer First Quarter");
    private TableCell tbcMethodFirstQuarter = new TableCell(By.xpath(String.format(firstQuarterPath, "Method Used to Determine")), "Method Used to Determine First Quarter");
    //Second Quarter
    private TableCell tbcEmployerSecondQuarter = new TableCell(By.xpath(String.format(otherXpath, "secondQuarter")), "Employer Second Quarter");
    private Button btnSecondQuarter = new Button(By.xpath(String.format(hrefXpath, "#secondQuarter")), "Second quarter");
    //Third Quarter
    private TableCell tbcEmployerThirdQuarter = new TableCell(By.xpath(String.format(otherXpath, "thirdQuarter")), "Employer Third Quarter");
    private Button btnThirdQuarter = new Button(By.xpath(String.format(hrefXpath, "#thirdQuarter")), "Third quarter");
    //Fourth Quarter
    private TableCell tbcEmployerFourthQuarter = new TableCell(By.xpath(String.format(otherXpath, "fourthQuarter")), "Employer Fourth Quarter");
    private Button btnFourthQuarter = new Button(By.xpath(String.format(hrefXpath, "#fourthQuarter")), "Fourth quarter");

    private String occupationalCode = "Cooks, Restaurant";
    private String academicDegree = "BA or BS Diploma/Degree";

    /**
     * Default constructor
     */
    public ProgramOutcomesDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Program Outcomes Detail')]"), "Program Outcomes Detail");
    }

    /**
     * This method is used for select participant on Program Outcomes form
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantLookup.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for validate Employer names of all 4 quarters
     *
     * @param employers String array with Employer names
     */
    public void validateEmployers(List<String> employers) {
        CustomAssertion.softTrue("First Quarter Employer failed", tbcEmployerFirstQuarter.getText().contains(employers.get(0)));
        btnSecondQuarter.click();
        CustomAssertion.softTrue("Second Quarter Employer failed", tbcEmployerSecondQuarter.getText().contains(employers.get(1)));
        btnThirdQuarter.click();
        CustomAssertion.softTrue("Third Quarter Employer failed", tbcEmployerThirdQuarter.getText().contains(employers.get(2)));
        btnFourthQuarter.click();
        CustomAssertion.softTrue("Fourth Quarter Employer failed", tbcEmployerFourthQuarter.getText().contains(employers.get(3)));
    }

    /**
     * Clear data of the participant
     */
    public void clearParticipant() {
        if (btnClearParticipant.isPresent()) {
            btnClearParticipant.click();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
        }
    }

    /**
     * Edit program
     */
    public void editProgram() {
        btnEditProgram.clickAndWait();
    }

    /**
     * Select first participation period.
     */
    public void selectFirstPartipPeriod() {
        cmbParticipationPeriod.selectFirst();
    }

    /**
     * Check exit date table cell is present
     *
     * @return true, if exit date table cell is present.
     */
    public Boolean checkExitDate() {
        return tbcExitDate.isPresent();
    }

    /**
     * Check, that education status table cell at text.
     *
     * @return true, if education status table cell present.
     */
    public Boolean checkEducationStatus() {
        return tbcEducationStatusAtExit.isPresent();
    }

    /**
     * Check created program outcomes
     *
     * @param employer      - employer name to check
     * @param methodQuarter - method quarter to check.
     */
    public void checkCreatedProgramOutcomes(String employer, String methodQuarter) {
        CustomAssertion.softAssertContains(tbcEmployedFirstQuarter.getText(), Constants.YES_ANSWER, "Incorrect employed first quarter");
        CustomAssertion.softAssertContains(tbcEmployerFirstQuarter.getText(), employer, "Incorrect employed first quarter");
        CustomAssertion.softAssertContains(tbcOccupationCode.getText(), occupationalCode, "Incorrect occupation code");
        CustomAssertion.softAssertContains(tbcMethodFirstQuarter.getText(), methodQuarter, "Incorrect method first quarter");
        CustomAssertion.softAssertContains(tbcRecognizedCredential.getText(), academicDegree, "Incorrect degree");
    }

    /**
     * Get occupation code text
     *
     * @return - occupation code on the page.
     */
    public String getOccupationCode() {
        return tbcOccupationCode.getText().trim();
    }

    /**
     * Get recognized credentials
     *
     * @return - recognized credentials text.
     */
    public String getRecognizedCredentials() {
        return tbcRecognizedCredential.getText().trim();
    }

    /**
     * Get method first quarter
     *
     * @return - method first quarter
     */
    public String getMethodFirstQuarter() {
        return tbcMethodFirstQuarter.getText().trim();
    }

    /**
     * Get employer first quarter
     *
     * @return - employer name first quarter
     */
    public String getEmployerFirstQuarter() {
        return tbcEmployerFirstQuarter.getText().trim();
    }

    /**
     * Get employed first quarter
     *
     * @return - employed first quarter
     */
    public String getEmployedFirstQuarter() {
        return tbcEmployedFirstQuarter.getText().trim();
    }
}
