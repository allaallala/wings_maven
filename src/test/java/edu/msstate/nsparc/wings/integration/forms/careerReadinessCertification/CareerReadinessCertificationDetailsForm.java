package edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Career Readiness Certifications -> Search for record -> Open record
 */
public class CareerReadinessCertificationDetailsForm extends CareerReadinessCertificationBaseForm {

    public static final String WARNING_TEXT = "The score for one or more of the associated WorkKeys assessments has been changed since this Career Readiness Certification was created. "
            + "Please Edit this Career Readiness Certification in order for the record to be updated.";
    private String xpath = "//td[contains(text(),'%1$s')]/following-sibling::td";
    private TableCell tbcDateAdministered = new TableCell(String.format(xpath,"Date Administered:"), "Date Administered");
    private TableCell tbcAppliedMathematics = new TableCell(String.format(xpath,"Applied Mathematics Assessment:"), "Applied Mathematics Assessment");
    private TableCell tbcLocatingInformation = new TableCell(String.format(xpath,"Locating Information Assessment:"), "Locating Information Assessment");
    private TableCell tbcReadingForInformation = new TableCell(String.format(xpath,"Reading for Information Assessment:"), "Reading for Information Assessment");
    private TableCell tbcResult = new TableCell(By.xpath("//form[@id='careerReadinessCertificationViewForm']//td[contains(.,'Result')]/following-sibling::td"), "Result");
    private Span spnFontRed = new Span("//font[@color='red']","Warning text on the page");


    /**
     * Constructor
     */
    public CareerReadinessCertificationDetailsForm() {
           super(By.xpath("//span[@id='breadCrumb'][contains(.,'Career Readiness Certification Detail')]"), "Career Readiness Certification Detail");
    }

    public void validateCareerReadinessCertificationInformation(CareerReadinessCertification crc) {
        checkField(tbcDateAdministered, crc.getDateAdministired(), Constants.FALSE);
        checkField(tbcAppliedMathematics, crc.getAppliedMathematicsString(), Constants.FALSE);
        checkField(tbcLocatingInformation, crc.getLocatingInformationString(), Constants.FALSE);
        checkField(tbcReadingForInformation, crc.getReadingForInformationString(), Constants.FALSE);
        info(tbcResult.getText());
        checkField(tbcResult, getResult(crc), Constants.FALSE);
    }

    public String getWarningMessage() {
        return spnFontRed.getText();
    }

    public void checkWarningMessageNotPresent() {
        spnFontRed.assertIsNotPresent();
    }

    public void checkButtonsPresent(User user) {
        divideMessage("Edit CRC Button");
        ifButton(user.getCRC().getCrcViewEdit(), BaseButton.EDIT.getButton());

        divideMessage("Check audit functionality");
        ifButton(user.getCRC().getCrcViewAudit(), BaseButton.AUDIT.getButton());
        if (user.getCRC().getCrcViewAudit()) {
            audit();
        }
    }

    public void editCareerReadinessCertification(String newDate, CareerReadinessCertification crc) {
        clickButton(Buttons.Edit);
        CareerReadinessCertificationEditForm editPage = new CareerReadinessCertificationEditForm();
        editPage.inputDataAdministered(newDate);
        editPage.finishEditing();
        crc.setDateAdministired(newDate);
        validateCareerReadinessCertificationInformation(crc);
    }
}
