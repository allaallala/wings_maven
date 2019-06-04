package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * This form is opened via Participants -> Trade -> Training Waivers -> Search for record -> Open record
 */
public class TrainingWaiverDetailsForm extends TrainingWaiverBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private Button btnEditWaiver = new Button("css=button[id='editWaiverInformationButton']", "Edit");
    private Button btnAudit = new Button("id=audit", "Audit");
    private TableCell tbcParticipant = new TableCell(By.xpath("//td[contains(.,'Participant')]/following-sibling::td/span"), "Participant");
    private TableCell tbcTradeEnrollment = new TableCell(By.xpath(String.format(detailPath, "Approved Trade Enrollment")), "Approved Trade Enrollment");
    private TableCell tbcReasonForWaiver = new TableCell(By.xpath(String.format(detailPath, "Reason for Waiver")), "Reason for Waiver");
    private TableCell tbcIssueDate = new TableCell(By.xpath(String.format(detailPath, "Issue Date")), "Issue Date");
    private TableCell tbcExpirationDate = new TableCell(By.xpath(String.format(detailPath, "Waiver Expiration Date")), "Waiver Expiration Date");
    private TableCell tbcWaiverStatus = new TableCell(By.xpath(String.format(detailPath, "Waiver Status")), "Waiver Status");
    private TableCell tbcTradeEnrollmentDeadline = new TableCell(By.xpath(String.format(detailPath, "Trade Enrollment Deadline")), "Trade Enrollment Deadline");

    // Waiver Revocation
    private Button btnExpandWaiverRevocationsSection = new Button("//a[@class='expand'][contains(.,'Waiver Revocations')]", "Expand Waiver Revocations section");
    private Button btnAddRevocation = new Button("css=button[id='addRevocation']", "Add Revocation");
    private TextBox txbRevocationDate = new TextBox("css=input[id='tmpRevocation.dateRevocation']", "Revocation Date");
    private ComboBox cmbRevocationReason = new ComboBox("css=select[id='tmpRevocation.revocationReason']", "Revocation Reason");
    private Button btnRevoke = new Button("//button[text()='Revoke']", "Revoke");
    private TableCell tbcRevocations = new TableCell("css=table[id='revocation-table']", "Revocations Information");
    private RadioButton rbRevocation = new RadioButton("css=input[id='rvkselectedRadio1']", "Revocation");
    private TextBox txbDateSignedRevocation = new TextBox("id=dateRvkSigned1", "Date Signed revocation");
    private Button btnSaveRevocationDateSigned = new Button(By.xpath("//table[@id='revocation-table']//input[@name='dateSaver']"), "Save");
    private TableCell tbcSavedRevocationDateSigned = new TableCell(By.xpath("//table[@id='revocation-table']//td[contains(text(),'Date Signed:')]"), "Saved revocation data signed");
    private TableCell tbcRevocationDate = new TableCell(By.xpath("//table[@id='revocation-table']//td[2]"), "Revocation date");
    private TableCell tbcRevocationReason = new TableCell(By.xpath("//table[@id='revocation-table']//td[3]"), "Revocation reason");
    private Button btnEditRevocation = new Button("css=button[id='editRevocation']", "Edit Revocation");
    private Button btnEditRevocationDisabled = new Button(By.xpath("//button[@id='editRevocation' and @disabled='disabled']"), "Edit Revocation (disabled)");
    private Button btnRemoveRevocationDisabled = new Button(By.xpath("//button[@id='removeRevocation' and @disabled='disabled']"), "Remove Revocation (disabled)");
    private Button btnRemoveRevocation = new Button(By.xpath("//div[@id='revocationButtons']/input[3]"), "Remove Revocation");
    private Button btnRemoveRevocationYes = new Button(By.xpath("//*[@id='removeRevocationMenu']//img"), "Remove this record? Yes");

    // Waiver Renewal
    private Button btnExpandWaiverRenewalsSection = new Button("//a[@class='expand'][contains(text(),'Waiver Renewals')]", "Expand Waiver Renewals section");
    private Button btnAddRenewal = new Button("css=button[id='addRenewal']", "Add Renewal");
    private Button btnAddRenewalDisabled = new Button(By.xpath("//button[@id='addRenewal' and @disabled='disabled']"), "Add Renewal (disabled)");
    private RadioButton rbCompletedWorkSearchFormsYes = new RadioButton("css=input[id='tmpRenewal.isJobSearchFormCompleted1']", "Completed TAA Work Search Form - Yes");
    private TextBox txbRenewalDate = new TextBox("css=input[id='tmpRenewal.dateRenewal']", "Renewal Date");
    private ComboBox cmbRenewalReason = new ComboBox("css=select[id='tmpRenewal.renewalReason']", "Renewal Reason");
    private TableCell tbcRenewals = new TableCell("css=table[id='renewals-table']", "Renewals Information");
    private RadioButton rbRenewal = new RadioButton("css=input[id='rnwselectedRadio1']", "Renewal");
    private Button btnEditRenewal = new Button("css=button[id='editRenewal']", "Edit Renewal");
    private Button btnEditRenewalDisabled = new Button(By.xpath("//button[@id='editRenewal' and @disabled='disabled']"), "Edit Renewal (disabled)");
    private Button btnRemoveRenewal = new Button(By.xpath("//div[@id='renewalButtons']/input[3]"), "Remove Renewal");
    private Button btnRemoveRenewalDisabled = new Button(By.xpath("//button[@id='removeRenewal' and @disabled='disabled']"), "Remove Renewal (disabled)");
    private TableCell tbcRenewalDate = new TableCell(By.xpath("//table[@id='renewals-table']//td[3]"), "Renewal date");
    private TableCell tbcRenewalReason = new TableCell(By.xpath("//table[@id='renewals-table']//td[4]"), "Renewal reason");
    private TextBox txbDateSignedRenewal = new TextBox("css=#dateSigned1", "Date Signed renewal");
    private Button btnSaveRenewalDateSigned = new Button("//input[@name='dateSaver']", "Save");
    private TableCell tbcSavedRenewalDateSigned = new TableCell("//td[contains(text(),'Date Signed:')]", "Saved renewal data signed");
    private Button btnRemoveRenewalYes = new Button(By.xpath("//*[@id='removeRenewalMenu']//img"), "Remove this record? Yes");
    private TableCell tbcNumberOfAllowedRenewalsRemaining = new TableCell("//td[contains(text(),'Number of Allowed Renewals Remaining:')]/following-sibling::td",
            "Number of Allowed Renewals Remaining");

    // Forms
    String formPath = "//td[contains(text(),'%1$s')]/following-sibling::td/input";
    private Button btnExpandForms = new Button("//a[@class='expand'][contains(text(),'Forms')]", "Expand Forms");
    private TextBox txbDateSignedWaiverForm = new TextBox("id=dateSigned1", "Date Signed Waiver Form");
    private Button btnSaveDateSignedWaiverForm = new Button(By.xpath("//table[@id='formsResults-table']//input[@name='dateSaver'][1]"), "Save");
    private Button btnPrintWaiverForm = new Button(String.format(formPath, "Waiver Form"), "Print waiver");
    private Button btnPrintRevocationForm = new Button(String.format(formPath, "Revocation Form"), "Print revocation");
    private Button btnPrintTAAWorkSearchForm = new Button(String.format(formPath, "TAA Work Search Form"), "Print TAA work");

    // Denial
    private TableCell tbcDenial = new TableCell("css=tr[id='denial']", "Denial");
    String renewed = "Renewed";

    /**
     * Default constructor
     */
    public TrainingWaiverDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Waiver Detail')]"), "Training Waiver Detail");
    }

    /**
     * Validating displayed Waiver information
     *
     * @param waiver Object with Waiver information
     */
    public void validateInformation(TrainingWaiver waiver) {
        checkField(tbcParticipant, waiver.getTradeEnrollment().getParticipant().getFirstName(), Constants.FALSE);
        checkField(tbcTradeEnrollment, waiver.getTradeEnrollment().getPetition().getNumber(), Constants.FALSE);
        checkField(tbcIssueDate, waiver.getIssueDate(), Constants.FALSE);
        checkField(tbcWaiverStatus, waiver.getWaiverStatus(), Constants.FALSE);
        if (!waiver.isEligible()) {
            checkField(tbcDenial, waiver.getDenialReason(), Constants.FALSE);
        } else {
            checkField(tbcReasonForWaiver, waiver.getWaiverReason(), Constants.FALSE);
        }
    }

    /**
     * Validate full information about training waiver
     *
     * @param waiver         - training waiver
     * @param expirationDate - expiration date (As a single parameter, because this date can be changed by adding renewals)
     */
    public void validateFullInformation(TrainingWaiver waiver, String expirationDate) {
        Participant part = waiver.getTradeEnrollment().getParticipant();
        Petition petit = waiver.getTradeEnrollment().getPetition();
        CustomAssertion.softAssertContains(tbcWaiverStatus.getText(), waiver.getWaiverStatus(), "Incorrect training waiver status");
        CustomAssertion.softAssertContains(tbcParticipant.getText().trim(), part.getFirstName() + " " + part.getLastName(), "Incorrect participant");
        CustomAssertion.softAssertContains(tbcTradeEnrollment.getText().trim(), petit.getNumber() + " - " + petit.getEmployer().getCompanyName()
                + " - " + petit.getStatus() + " " + petit.getFileDate(), "Incorrect trade enrollment");
        if (waiver.isEligible()) {
            CustomAssertion.softAssertContains(tbcReasonForWaiver.getText(), waiver.getWaiverReason(), "Incorrect training waiver reason");
        }
        CustomAssertion.softAssertContains(tbcIssueDate.getText(), waiver.getIssueDate(), "Incorrect training waiver issue date");
        CustomAssertion.softAssertContains(tbcExpirationDate.getText(), expirationDate, "Incorrect training waiver expiration date");

    }

    /**
     * Get reason waiver text
     *
     * @return reason waiver text.
     */
    public String getReasonWaiverText() {
        return tbcReasonForWaiver.getText();
    }

    /**
     * Get issue date text
     *
     * @return issue date text
     */
    public String getIssueDateText() {
        return tbcIssueDate.getText();
    }

    /**
     * Get waiver status text
     *
     * @return training waiver status text
     */
    public String getWaiverStatusText() {
        return tbcWaiverStatus.getText();
    }

    /**
     * Get trade enrollment deadline
     *
     * @return trade enrollment deadline
     */
    public String getTradeEnrlDeadline() {
        return tbcTradeEnrollmentDeadline.getText();
    }

    /**
     * Get revocations text on search page
     *
     * @return revocations text.
     */
    public String getRevocationsText() {
        return tbcRevocations.getText().trim();
    }

    /**
     * Get renewals
     *
     * @return renewals text
     */
    public String getRenewals() {
        return tbcRenewals.getText().trim();
    }

    /**
     * Get denial reason text
     *
     * @return denial reason text on the page
     */
    public String getDenialReasonText() {
        return tbcDenial.getText().trim();
    }

    /**
     * Get max number of renewals on the page
     *
     * @return renewals number
     */
    public String getNumberRenewalsRemaining() {
        return tbcNumberOfAllowedRenewalsRemaining.getText();
    }

    /**
     * Check, that print buttons are available
     */
    public void checkPrintPresent() {
        btnPrintWaiverForm.assertIsPresentAndVisible();
        btnPrintRevocationForm.assertIsPresentAndVisible();
        btnPrintTAAWorkSearchForm.assertIsPresentAndVisible();
    }

    /**
     * Check, that save buttons are available
     */
    public void checkSaveAvailable() {
        txbDateSignedWaiverForm.assertIsPresentAndVisible();
        btnSaveDateSignedWaiverForm.assertIsPresentAndVisible();
    }

    /**
     * Expand waiver revocation section.
     */
    public void expandWaiverRevocationSection() {
        if (btnExpandWaiverRevocationsSection.isPresent()) {
            btnExpandWaiverRevocationsSection.click();
        }
    }

    /**
     * Expand waiver renewals section.
     */
    public void expandWaiverRenewalsSection() {
        if (btnExpandWaiverRenewalsSection.isPresent()) {
            btnExpandWaiverRenewalsSection.click();
        }
    }

    /**
     * Expand forms section.
     */
    public void expandForms() {
        if (btnExpandForms.isPresent()) {
            btnExpandForms.click();
        }
    }

    /**
     * Save revocation date and check, that it successfully saved.
     *
     * @param date - revocation date
     */
    public void saveRevocationDateCheckIt(String date) {
        txbDateSignedRevocation.type(date);
        btnSaveRevocationDateSigned.click();
        areYouSure(Popup.Yes);
        btnExpandWaiverRevocationsSection.click();
        info("Check date was saved");
        CustomAssertion.softAssertContains("Date Signed: " + date,
                tbcSavedRevocationDateSigned.getText(), "Date wasn't saved!");
    }

    /**
     * Save renewal date and check, that is successfully saved
     *
     * @param date - renewal date.
     */
    public void saveRenewalDateCheckIt(String date) {
        txbDateSignedRenewal.type(date);
        btnSaveRenewalDateSigned.click();
        areYouSure(Popup.Yes);
        info("Check date was saved");
        CustomAssertion.softAssertContains(tbcSavedRenewalDateSigned.getText(), "Date Signed: " + date, "Date wasn't saved!");
    }

    /**
     * Click [Audit] button.
     */
    public void clickAudit() {
        btnAudit.clickAndWait();
    }

    /**
     * Click [Add Revocation] button
     */
    public void clickAddRevocation() {
        btnAddRevocation.clickAndWait();
    }

    /**
     * Click [Add Renewal] button
     */
    public void clickAddRenewal() {
        btnAddRenewal.clickAndWait();
    }

    /**
     * Click revocation radio button.
     */
    public void clickRevocation() {
        rbRevocation.click();
    }

    /**
     * Click renewal
     */
    public void clickRenewal() {
        rbRenewal.click();
    }

    /**
     * Edit waiver
     */
    public void editWaiver() {
        btnEditWaiver.clickAndWait();
    }

    /**
     * Edit revocation and select value
     *
     * @param value - revocation value
     */
    public void editRevocation(String value) {
        btnEditRevocation.clickAndWait();
        cmbRevocationReason.select(value);
    }

    /**
     * Input renewal date and renewal reason
     *
     * @param renewalDate   - renewal date
     * @param renewalReason - renewal reason
     */
    public void inputRenewalReasonDate(String renewalDate, String renewalReason) {
        rbCompletedWorkSearchFormsYes.click();
        txbRenewalDate.type(renewalDate);
        cmbRenewalReason.select(renewalReason);
    }

    /**
     * Check, that [Edit waiver] button is present and visible.
     */
    public void checkEditWaiverPresent() {
        btnEditWaiver.assertIsPresentAndVisible();
    }

    /**
     * Remove revocation
     */
    public void removeRevocation() {
        btnRemoveRevocation.click();
        btnRemoveRevocationYes.clickAndWait();
    }

    /**
     * Remove renewal and check, that it is deleted
     */
    public void removeRenewal() {
        btnRemoveRenewal.click();
        btnRemoveRenewalYes.clickAndWait();
        info("Check renewal was deleted");
        expandWaiverRenewalsSection();
        rbRenewal.assertIsNotPresent();
    }

    /**
     * Check, that [Edit revocation] and [Remove revocation] buttons are present or not
     *
     * @param present - if true, elements should present on page.
     */
    public void checkEditRemoveRevocationDisabledPresent(Boolean present) {
        waitForNotVisible(BaseOtherElement.LOADING);
        if (present) {
            btnEditRevocationDisabled.assertIsPresentAndVisible();
            btnRemoveRevocationDisabled.assertIsPresentAndVisible();
        } else {
            btnEditRevocationDisabled.assertIsNotPresent();
            btnRemoveRevocationDisabled.assertIsNotPresent();
        }
    }

    /**
     * Check, that [Edit renewal] and [Remove renewal] buttons are present or not
     *
     * @param present - if true, elements should present on the page.
     */
    public void checkEditRemoveRenewalsDisabledPresent(Boolean present) {
        if (present) {
            btnEditRenewalDisabled.assertIsPresentAndVisible();
            btnRemoveRenewalDisabled.assertIsPresentAndVisible();
        } else {
            btnEditRenewalDisabled.assertIsNotPresent();
            btnRemoveRenewalDisabled.assertIsNotPresent();
        }
    }

    /**
     * Adding Revocation to a Waiver
     *
     * @param waiver Object with Revocation data
     */
    public void addRevocation(TrainingWaiver waiver) {
        clickAddRevocation();
        fillOutRevokeTrainingWaiverForm(waiver);
        btnRevoke.clickAndWait();
        if (isPresent(BaseButton.SAVE_CHANGES)) {
            clickAndWait(BaseButton.SAVE_CHANGES);
        }

        waiver.setWaiverStatus(Constants.REVOKED);
    }

    /**
     * Fill out Revoke Training Waiver form
     *
     * @param waiver Object with Revocation data
     */
    public void fillOutRevokeTrainingWaiverForm(TrainingWaiver waiver) {
        txbRevocationDate.type(waiver.getRevocation().getRevocationDate());
        cmbRevocationReason.select(waiver.getRevocation().getRevocationReason());
    }

    /**
     * Editing existing Revocation in Training Waiver
     *
     * @param waiver Object with Revocation data
     */
    public void editRevocation(TrainingWaiver waiver) {
        clickRevocation();
        btnEditRevocation.clickAndWait();
        cmbRevocationReason.select(waiver.getRevocation().getRevocationReason());
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Adding Renewal to a Waiver
     *
     * @param waiver Object with Renewal data
     */
    public void addRenewal(TrainingWaiver waiver) {
        clickAddRenewal();
        rbCompletedWorkSearchFormsYes.click();
        txbRenewalDate.type(waiver.getRenewal().getRenewalDate());
        cmbRenewalReason.select(waiver.getRenewal().getRenewalReason());
        clickAndWait(BaseButton.CREATE);

        if (isPresent(BaseButton.SAVE_CHANGES)) {
            clickAndWait(BaseButton.SAVE_CHANGES);
        }

        waiver.setWaiverStatus(renewed);
    }

    /**
     * Editing existing Renewal in Training Waiver
     *
     * @param waiver Object with Renewal data
     */
    public void editRenewal(TrainingWaiver waiver) {
        clickRenewal();
        btnEditRenewal.clickAndWait();
        cmbRenewalReason.select(waiver.getRenewal().getRenewalReason());
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Open edit form for training waiver
     */
    public void openEditForm() {
        btnEditWaiver.clickAndWait();
    }

    /**
     * Validate data in renewals/revocation sections of the training waiver
     *
     * @param waiver - training waiver to check.
     */
    public void validateRenewalsRevocations(TrainingWaiver waiver) {
        validateRevocationsBlock(waiver);
        validateRenewalsBlock(waiver);
    }

    /**
     * Validate data in the revocations section
     *
     * @param waiver - training waiver
     */
    public void validateRevocationsBlock(TrainingWaiver waiver) {
        expandWaiverRevocationSection();
        CustomAssertion.softAssertEquals(tbcRevocationDate.getText(), waiver.getRevocation().getRevocationDate(), "Incorrect revocation date");
        CustomAssertion.softAssertEquals(tbcRevocationReason.getText(), waiver.getRevocation().getRevocationReason(), "Incorrect revocation reason");
    }

    /**
     * Validate data in the renewals section
     *
     * @param waiver - training waiver
     */
    public void validateRenewalsBlock(TrainingWaiver waiver) {
        expandWaiverRenewalsSection();
        CustomAssertion.softAssertEquals(tbcRenewalDate.getText(), waiver.getRenewal().getRenewalDate(), "Incorrect renewal date");
        CustomAssertion.softAssertEquals(tbcRenewalReason.getText(), waiver.getRenewal().getRenewalReason(), "Incorrect renewal reason");
    }

    /**
     * Validate allowed renewals
     *
     * @param expectedRenewals - expected renewals
     */
    public void validateAllowedRenewals(String expectedRenewals) {
        expandWaiverRenewalsSection();
        Assert.assertEquals("Incorrect number of allowed renewals on page", expectedRenewals, getNumberRenewalsRemaining());
    }

    /**
     * Validate, that is impossible to add renewals and revocations
     */
    public void validateImpossibleRenewRevoc() {
        validateImpossibleRenewals();
        validateImpossibleRevocations();
    }

    /**
     * Validate, that is impossible to add renewals
     */
    public void validateImpossibleRenewals() {
        expandWaiverRenewalsSection();
        btnAddRenewal.assertIsNotPresent();
        btnEditRenewal.assertIsNotPresent();
        btnRemoveRenewal.assertIsNotPresent();
    }

    /**
     * Validate, that is impossible to add revocations
     */
    public void validateImpossibleRevocations() {
        expandWaiverRevocationSection();
        btnAddRevocation.assertIsNotPresent();
        btnEditRevocation.assertIsNotPresent();
        btnRemoveRevocation.assertIsNotPresent();
    }

    /**
     * Validate, that renewals buttons are disabled
     */
    public void validateDisabledRenewals() {
        expandWaiverRevocationSection();
        btnAddRenewalDisabled.assertIsPresentAndVisible();
        btnEditRenewalDisabled.assertIsPresentAndVisible();
        btnRemoveRenewalDisabled.assertIsPresentAndVisible();
    }

    /**
     * Check buttons present on the Training Waiver Details form.
     *
     * @param user - current user
     */
    public void checkWaiverButtons(User user) {
        expandWaiverRenewalsSection();
        expandWaiverRevocationSection();
        expandForms();
        divideMessage("Check [Edit]");
        ifButton(user.getTrainingWaiver().getTwEdit(), btnEditWaiver);
        divideMessage("Check [Add Renewal]");
        ifButton(user.getTrainingWaiver().getTwAddRenewal(), btnAddRenewal);
        divideMessage("Check [Edit Renewal]");
        ifButton(user.getTrainingWaiver().getTwEditRenewal(), btnEditRenewal);
        divideMessage("Check [Add Revocation]");
        ifButton(user.getTrainingWaiver().getTwAddRevocation(), btnAddRevocation);
        divideMessage("Check [Edit Revocation]");
        ifButton(user.getTrainingWaiver().getTwEditRevocation(), btnEditRevocation);
        divideMessage("Check [Print], [Data Signed]");
        ifButton(user.getTrainingWaiver().getTwPrintSign(), btnPrintWaiverForm);
        ifButton(user.getTrainingWaiver().getTwPrintSign(), btnPrintRevocationForm);
        ifButton(user.getTrainingWaiver().getTwPrintSign(), btnPrintTAAWorkSearchForm);
    }
}
