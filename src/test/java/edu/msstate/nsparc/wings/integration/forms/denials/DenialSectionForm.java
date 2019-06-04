package edu.msstate.nsparc.wings.integration.forms.denials;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * Denial Section Form
 */
public class DenialSectionForm extends BaseWingsForm {

    // Expand denial section
    private Button btnExpandDenials = new Button(By.xpath("//a[@title='Expand'][contains(.,'Denials')]"), "Expand Denials");

    // Denial section buttons
    private Button btnEditDenial = new Button("id=editDenial", "Edit Denial");
    private Button btnEditAppeal = new Button("id=editAppeal", "Edit Appeal");
    private Button btnAppeal = new Button("id=appealDenial", "Appeal");
    private Button btnPrintLetter = new Button("//*[@name='denialSaver']", "Print Letter");

    // Denial fields
    private TextBox txbDateAppeal = new TextBox("id=dateAppeal", "Appeal Date");
    private TextBox txbDenialEndDate = new TextBox("id=dateDenialEnd", "Denial End Date");
    private TextBox txbReasonDenialEnded = new TextBox("id=denialEndedReason", "Reason Denial Ended");
    private TableCell tbcDateAppeal = new TableCell("//td[contains(text(),'Appeal Date:')]/following-sibling::td", "Appeal Date");
    private TableCell tbcImgVerified = new TableCell(By.xpath("//tr[@id='denial']//img"), "Verified Print Letter");
    private TableCell tbcOtherDenialReason = new TableCell("//td[contains(text(),'Other Denial Reason(s):')]/following-sibling::td", "Other Denial Reason(s)");
    private TableCell tbcDenialDate = new TableCell("//td[contains(text(),'Denial Date:')]/following-sibling::td[1]", "Denial Date");
    private TableCell tbcDenialEndDate = new TableCell("//td[contains(text(),'Denial End Date:')]/following-sibling::td", "Denial End Date");
    private TableCell tbcReasonDenialEnded = new TableCell("//td[contains(text(),'Reason Denial Ended:')]/following-sibling::td", "Reason Denial Ended");

    public DenialSectionForm() {
        super(By.xpath("//a[contains(.,'Denials')]"), "Denials");
    }

    public DenialSectionForm(String ataa) {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'ATAA/RTAA Enrollment Detail')]"),"Denial Section for " + ataa);
    }

    public void validateDenialsBaseInfo(String denialDate, String otherDenialReason) {
        CustomAssertion.softAssertContains(tbcDenialDate.getText().trim(), denialDate, "Incorrect denial date");
        CustomAssertion.softAssertContains(tbcOtherDenialReason.getText().trim(), otherDenialReason, "Incorrect denial reason");
    }

    public void print() {
        btnPrintLetter.click();
    }

    public void editDenial() {
        btnEditDenial.clickAndWait();
    }

    public void editAppeal() {
        btnEditAppeal.clickAndWait();
    }

    public void clickAppeal() {
        btnAppeal.clickAndWait();
    }

    public void inputDateAppeal(String dateAppeal) {
        txbDateAppeal.type(dateAppeal);
    }


    public void validateAppealDate(String dateAppeal) {
        checkField(tbcDateAppeal, dateAppeal, true);
    }

    public void validateAppealingIsNotPerformed() {
        btnAppeal.assertIsPresentAndVisible();
        tbcDateAppeal.softIsNotPresent();
    }

    public void validateDenialEndDateAndReasonEnded(String denialEndDate, String reasonDenialEnded) {
        checkField(tbcDenialEndDate, denialEndDate, Constants.TRUE);
        checkField(tbcReasonDenialEnded, reasonDenialEnded, Constants.TRUE);
    }

    public void openDenialAppealCreationForm() {
        btnAppeal.clickAndWait();
    }

    public void expandDenials() {
        btnExpandDenials.click();
    }

    public void inputDenialsDateReason(String denialEndDate, String reason) {
        txbDenialEndDate.type(denialEndDate);
        txbReasonDenialEnded.type(reason);
    }

    public void checkImageVerified() {
        tbcImgVerified.isPresent();
    }

    public void checkDenialsNotPresent() {
        tbcDenialEndDate.softIsNotPresent();
        tbcReasonDenialEnded.softIsNotPresent();
    }
}
