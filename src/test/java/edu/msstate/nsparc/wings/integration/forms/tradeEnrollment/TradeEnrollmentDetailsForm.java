package edu.msstate.nsparc.wings.integration.forms.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

import static edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps.home;
import static edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps.logout;

/**
 * This form is opened via Participants -> Trade -> Trade Enrollments -> Search for record -> Open record
 */
public class TradeEnrollmentDetailsForm extends TradeEnrollmentBaseForm {

    private String xpathExp = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcTotalEncumbranceAmount = new TableCell(String.format(xpathExp, "Total Encumbrance:"), "Total Encumbrance");
    private TableCell tbcTotalExpenseAmount = new TableCell(String.format(xpathExp, "Total Expense:"), "Total Expense");
    private TableCell tbcTotalDeobligationAmount = new TableCell(String.format(xpathExp, "Total Deobligation:"), "Total Deobligation");
    private TableCell tbcTotalRefundAmount = new TableCell(String.format(xpathExp, "Total Refund:"), "Total Refund");
    private TableCell tbcTotalLostStolenAmount = new TableCell(String.format(xpathExp, "Total Lost/Stolen:"), "Total Lost/Stolen");
    private TableCell tbcTotalBalance = new TableCell(String.format(xpathExp, "Balance:"), "Total Balance");

    private TableCell tbcParticipant = new TableCell(By.xpath("//form[@id='tradeEnrollmentViewForm']//h1"), "Participant");
    private TableCell tbcPetition = new TableCell(By.xpath("//tr[@id='basicInfo']//tbody/tr[1]/td[2]"), "Petition");
    private TableCell tbcEligibilityStatus = new TableCell(By.xpath(String.format(xpathExp, "TAA Eligibility Status:")), "TAA Eligibility Status");
    private TableCell tbcEligibilityDeterminationDate = new TableCell(By.xpath(String.format(xpathExp, "TAA Eligibility Determination Date:")), "TAA Eligibility Determination Date");
    private TableCell tbcIneligibilityReason = new TableCell(By.xpath(String.format(xpathExp, "TAA Ineligibility Reason:")), "TAA Ineligibility reason");
    private TableCell tbcApplicationDate = new TableCell(By.xpath(String.format(xpathExp, "TAA Application Date:")), "TAA Application Date");
    private Button btnExpandExpendituresEncumbrancesSection = new Button(By.xpath("//span[contains(.,'Expenditures')]"), "Expand Expenditures and Encumbrances section");
    private Button btnManageExpendituresEncumbrances = new Button("css=button[id='manageExpendituresEncumbrances']", "Manage Expenditures Encumbrances");
    private Button btnExpandForms = new Button("//a[@title='Expand'][contains(.,'Forms')]", "Expand Forms");
    private TextBox txbDateSigned = new TextBox("css=#dateSigned1", "Date signed");
    private Button btnSaveDateSigned = new Button("css=input[name='savePrintDate']", "Save Date Signed");
    private Button btnPrint = new Button(By.xpath("//input[@value='Print']"), "Print");
    private TableCell tbcDateSignedIsSaved = new TableCell(By.xpath("//td[contains(.,'HCTC Form')]/following-sibling::td[2]"), "Date Signed");
    private Button btnExpandEmploymentSeparation = new Button("//a[@class='expand'][contains(text(),'Employment at Separation')]", "Employment at Separation");
    private Div divPreviewEmploymentSeparation = new Div("id=previewPage", "Preview page");
    private Button btnExpandDenials = new Button("//a[@class='expand'][contains(.,'Denials')]", "Expand Denials");
    private Button btnEditDenial = new Button("id=editDenial", "Edit Denial");
    private Button btnEditAppeal = new Button("id=editAppeal", " Edit Appeal");
    private TextBox txbAppealDocketNumber = new TextBox("id=appealDocketNumber", "Appeal Docket Number");
    private Button btnAppeal = new Button("id=appealDenial", "Appeal");
    private TableCell tbcEmploymentSeparation = new TableCell(By.xpath("//table[@id='empResults-table']//td[1]"), "Employment at Separation item");
    private TableCell tbcDenialEndDate = new TableCell(By.xpath(String.format(xpathExp, "Denial End Date:")), "Denial End Date");
    private TableCell tbcReasonDenialEnded = new TableCell(By.xpath(String.format(xpathExp, "Reason Denial Ended:")), "Reason Denial Ended");
    private TableCell tbcOtherDenialReason = new TableCell(By.xpath(String.format(xpathExp, "Other Denial Reason(s):")), "Other denial reason(s)");
    private TableCell tbcDenialDate = new TableCell(By.xpath(String.format(xpathExp, "Denial Date:")), "Denial Date");
    private TableCell tbcAppealDate = new TableCell(By.xpath(String.format(xpathExp, "Appeal Date:")), "Appeal Date");
    private TableCell tbcAppealDecision = new TableCell(By.xpath(String.format(xpathExp, "Decision:")), "Appeal Decision");
    private TableCell tbcAppealDecisionDate = new TableCell(By.xpath(String.format(xpathExp, "Decision Date:")), "Appeal Decision Date");
    private TableCell tbcAppealDocketNumber = new TableCell(By.xpath(String.format(xpathExp, "Appeal Docket Number:")), "Appeal Docket Number");
    private Button btnPrintLetter = new Button("//input[@value='Print Letter']", "Print Letter");
    // items for Denials Edit Form
    private TextBox txbDenialEndDate = new TextBox("id=dateDenialEnd", "Denial End Date");
    private TextBox txbReasonDenialEnded = new TextBox("id=denialEndedReason", "Reason Denial Ended");
    // items for Denials Appeal Form
    private TextBox txbAppealDate = new TextBox("id=dateAppeal", "Appeal Date");
    private RadioButton rbDecisionOvertuned = new RadioButton(By.id("appealDecision1"), "Decision overtuned");
    private TextBox txbDecisionDate = new TextBox(By.id("dateAppealDecision"), "Decision date");

    /**
     * Default constructor
     */
    public TradeEnrollmentDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Detail')]"), "Trade Enrollment Detail");
    }

    /**
     * Click [Appeal]
     */
    public void clickAddAppeal() {
        btnAppeal.clickAndWait();
    }

    private void setAppealDate(String appealDate) {
        txbAppealDate.type(appealDate);
    }

    /**
     * Add appeal to the trade enrollment
     *
     * @param trd - trade enrollment
     */
    public void addAppeal(TradeEnrollment trd) {
        setAppealDate(trd.getAppealDate());
        if (trd.isOvertuned()) {
            rbDecisionOvertuned.click();
        }
        txbDecisionDate.type(trd.getAppealDate());
        txbAppealDocketNumber.type(trd.getAppealNumber());
        clickButton(Buttons.Save);
    }

    /**
     * Input appeal date
     *
     * @param appealDate - appeal date
     */
    public void inputAppealDate(String appealDate) {
        clickAddAppeal();
        setAppealDate(appealDate);
    }

    /**
     * Click [Edit Appeal]
     */
    public void clickEditAppeal() {
        btnEditAppeal.clickAndWait();
    }

    /**
     * Input appeal docket number
     *
     * @param appealDocketNumber - appeal docket number
     */
    public void inputAppealDocketNumber(String appealDocketNumber) {
        clickEditAppeal();
        txbAppealDocketNumber.type(appealDocketNumber);
    }

    /**
     * Edit denial
     *
     * @param denialDate - denial date to type.
     */
    public void editDenial(String denialDate) {
        btnEditDenial.clickAndWait();
        txbDenialEndDate.type(denialDate);
        txbReasonDenialEnded.type(denialDate);
    }

    /**
     * Click [Manage expenditures] button.
     */
    public void clickManageExpEncumbrances() {
        btnManageExpendituresEncumbrances.clickAndWait();
    }

    /**
     * Validating displayed amount of Expenditure Encumbrances for each type
     *
     * @param expenditureEncumbrance Object with Expenditure Encumbrance info
     */
    private void checkBalanceInExpenditureRow(ExpenditureEncumbrance expenditureEncumbrance) {
        switch (expenditureEncumbrance.getType()) {
            case ENCUMBRANCE:
                checkField(tbcTotalEncumbranceAmount, expenditureEncumbrance.getAmount(), false);
                break;
            case EXPENSE:
                checkField(tbcTotalExpenseAmount, expenditureEncumbrance.getAmount(), false);
                break;
            case DEOBLIGATION:
                checkField(tbcTotalDeobligationAmount, expenditureEncumbrance.getAmount(), false);
                break;
            case REFUND:
                checkField(tbcTotalRefundAmount, expenditureEncumbrance.getAmount(), false);
                break;
            case LOST_STOLEN:
                checkField(tbcTotalLostStolenAmount, expenditureEncumbrance.getAmount(), false);
                break;
            default:
                break;
        }
    }

    /**
     * Calculate total balance of all expenditures
     *
     * @param encumbrances Object array with Expenditure Encumbrances
     */
    private float calculateTotalBalanceOfExpenditures(ExpenditureEncumbrance[] encumbrances) {
        ManageExpenditureEncumbrancesForm encumbrancesForm = new ManageExpenditureEncumbrancesForm();
        float balance = 0;
        for (ExpenditureEncumbrance expenditureEncumbrance : encumbrances) {
            balance = encumbrancesForm.calculateBalance(expenditureEncumbrance);
        }
        return balance;
    }

    /**
     * Reformat float and negative balances to positive string number
     *
     * @param balance Float number
     */
    private String getFormattedBalance(float balance) {
        float newBalance;
        if (balance < 0) {
            newBalance = balance * (-1);
        } else {
            newBalance = balance;
        }
        return Float.toString(newBalance);
    }

    /**
     * Validating displayed balance of all Expenditure Encumbrances
     *
     * @param encumbrances Object array with Expenditure Encumbrances
     */
    private void checkTotalBalanceOfExpenditures(ExpenditureEncumbrance[] encumbrances) {
        checkField(tbcTotalBalance, getFormattedBalance(calculateTotalBalanceOfExpenditures(encumbrances)), false);
    }

    /**
     * Appeal denial
     *
     * @param tradeEnrollment - trade enrollment
     */
    public void appealDenial(TradeEnrollment tradeEnrollment) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        expandDenials();
        inputAppealDate(CommonFunctions.getCurrentDate());
        clickAndWait(BaseButton.SAVE_CHANGES);
        clickAndWait(BaseButton.DONE);
        logout();
    }

    /**
     * Add expenditure encumbrance
     *
     * @param tradeEnrollment        - trade erollment
     * @param expenditureEncumbrance - single expenditure encumbrance
     */
    public void addExpenditureEncumbrance(TradeEnrollment tradeEnrollment, ExpenditureEncumbrance expenditureEncumbrance) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        expandExpendituresSection();
        clickManageExpEncumbrances();
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbrance);
        home();
        logout();
    }

    /**
     * Add new expenditure/encumbrance depends on user permissions
     *
     * @param user - current user
     * @param exp  - expenditure/encumbrance
     */
    public void addExpenditureEncumbrance(User user, ExpenditureEncumbrance exp) {
        expandExpendituresSection();
        if (user.getTradeEnrollment().getTeAddNewExpenditure()) {
            divideMessage("Add expenditure encumbrance");
            clickManageExpEncumbrances();
            ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
            manageExpenditureEncumbrancesForm.addExpenditure(exp);
            manageExpenditureEncumbrancesForm.validateExpenditureDetails(exp);
            manageExpenditureEncumbrancesForm.validateProcessDate(exp);
        } else {
            btnManageExpendituresEncumbrances.assertIsNotPresent();
        }
    }

    public void editExpenditureEncumbrance(User user, ExpenditureEncumbrance exp) {
        if (user.getTradeEnrollment().getTeEditExpenditur()) {
            divideMessage("Edit existing expenditure encumbrance");
            ManageExpenditureEncumbrancesForm manPage = new ManageExpenditureEncumbrancesForm();
            manPage.selectExpenditureAndOpenEditForm();
            manPage.fillExpenditureDetails(exp);
            manPage.clickButton(Buttons.Save);
            manPage.validateExpenditureDetails(exp);
            manPage.validateProcessDate(exp);
        }
    }

    /**
     * Adds some expenditure encumbrances (massive)
     *
     * @param tradeEnrollment - trade enrollment
     * @param encumbrances    - expenditure encumbrances to add
     */
    public void addSeveralExpenditureEncumbrance(TradeEnrollment tradeEnrollment, ExpenditureEncumbrance[] encumbrances) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        expandExpendituresSection();
        clickManageExpEncumbrances();
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        for (ExpenditureEncumbrance expenditureEncumbrance : encumbrances) {
            manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbrance);
        }
        home();
        logout();
    }

    /**
     * Validating displayed Trade Enrollment information
     *
     * @param tradeEnrollment Object with Trade Enrollment information
     */
    public void validateInformation(TradeEnrollment tradeEnrollment) {
        CustomAssertion.softAssertContains(tbcParticipant.getText(), tradeEnrollment.getParticipant().getNameForSearchPages(), "Incorrect participant name");
        CustomAssertion.softAssertContains(tbcPetition.getText(), tradeEnrollment.getPetition().getNumber(), "Incorrect petition title");
        CustomAssertion.softAssertContains(tbcApplicationDate.getText(), tradeEnrollment.getApplicationDate(), "Incorrect application date");
    }

    /**
     * Validate eligibility fields
     *
     * @param tradeEnrollment - trade enrollment
     */
    public void validateEligibility(TradeEnrollment tradeEnrollment) {
        String eligibleStatus;
        if (tradeEnrollment.isEligible()) {
            eligibleStatus = Constants.ELIGIBLE;
        } else {
            eligibleStatus = Constants.INELIGIBLE;
        }
        CustomAssertion.softAssertContains(tbcEligibilityStatus.getText(), eligibleStatus, "Incorrect eligibility status");
        CustomAssertion.softAssertContains(tbcEligibilityDeterminationDate.getText(), tradeEnrollment.getEligibilityDeterminationDate(), "Incorrect eligibility determination date");
        if (!tradeEnrollment.isEligible()) {
            CustomAssertion.softAssertContains(tbcIneligibilityReason.getText(), tradeEnrollment.getDenialReason(), "Incorrect eligibility reason");
        }
    }

    /**
     * Validate employment separation data
     */
    public void validateEmploymentSeparation(User user, TradeEnrollment trd) {
        expandEmploymentSeparation();
        if (user.getTradeEnrollment().getTeEmploymentSeparationView()) {
            PreviousJob jb = trd.getSeparationJob();
            CustomAssertion.softAssertContains(tbcEmploymentSeparation.getText(),
                    jb.getJobTitle() + " at " + jb.getEmployer(), "Incorrect employment job title, employer");
            CustomAssertion.softAssertContains(tbcEmploymentSeparation.getText(),
                    jb.getStartDate() + " to " + jb.getEndDate(), "Incorrect employment separation date");
            CustomAssertion.softAssertContains(tbcEmploymentSeparation.getText(),
                    jb.getCity() + ", " + jb.getZipCode() + " Salary: $" + jb.getHoursPerWeek(), "Incorrect employment separation address, salary");
        }
    }

    /**
     * Validate encumbrance and expense after editing
     *
     * @param editedExpense - expense edited.
     */
    public void validateEncumbranceExpense(String editedExpense) {
        CustomAssertion.softAssertEquals(tbcTotalEncumbranceAmount.getText(), editedExpense, "Total Encumbrance is incorrect!");
        CustomAssertion.softAssertEquals(tbcTotalExpenseAmount.getText(), editedExpense, "Total Expense is incorrect!");
    }

    /**
     * Validate denials or that there are no denials present
     *
     * @param present - denials
     */
    public void validateDenials(Boolean present) {
        if (present) {
            CustomAssertion.softAssertContains(tbcDenialEndDate.getText(), CommonFunctions.getCurrentDate(), "Assert Denial End Date failed!");
            CustomAssertion.softAssertContains(tbcReasonDenialEnded.getText(), CommonFunctions.getCurrentDate(), "Assert Reason Denial Ended failed!");
        } else {
            tbcDenialEndDate.assertIsNotPresent();
            tbcReasonDenialEnded.assertIsNotPresent();
        }
    }

    /**
     * Check information in the denials section, after changing status of the trade enrollment to ineligible.
     *
     * @param trd - trade enrollment
     */
    public void validateDenialsFirst(TradeEnrollment trd) {
        expandDenials();
        CustomAssertion.softTrue("Incorrect denial date", tbcDenialDate.getText().contains(trd.getDenialDate()));
        CustomAssertion.softTrue("Incorrect denial reason", tbcOtherDenialReason.getText().contains(trd.getDenialReason()));
    }

    /**
     * Check information in the denials section afted adding appeal
     *
     * @param trd - trade enrollment
     */
    public void validateAppeal(TradeEnrollment trd) {
        expandDenials();
        CustomAssertion.softTrue("Incorrect appeal date", tbcAppealDate.getText().contains(trd.getAppealDate()));
        CustomAssertion.softTrue("Incorrect appeal decision", tbcAppealDecision.getText().contains(trd.getAppealDecision()));
        CustomAssertion.softTrue("Incorrect appeal decision date", tbcAppealDecisionDate.getText().contains(trd.getAppealDate()));
        CustomAssertion.softTrue("Incorrect appeal docket number", tbcAppealDocketNumber.getText().contains(trd.getAppealNumber()));
    }

    /**
     * Validating displayed total balance of all Expenditure Encumbrances and each amount of Expenditure Encumbrances
     *
     * @param expenditureEncumbrances Object array with Expenditure Encumbrances
     */
    public void validateExpendituresEncumbranceBalances(ExpenditureEncumbrance[] expenditureEncumbrances) {
        for (ExpenditureEncumbrance expenditureEncumbrance : expenditureEncumbrances) {
            checkBalanceInExpenditureRow(expenditureEncumbrance);
        }
        checkTotalBalanceOfExpenditures(expenditureEncumbrances);
    }

    /**
     * Expand denials section and check, that [Print] button is present and visible.
     */
    public void expandDenialsCheckPrintButton() {
        expandDenials();
        btnPrintLetter.assertIsPresentAndVisible();
    }

    /**
     * Expand employment separation
     */
    public void expandEmploymentSeparation() {
        if (btnExpandEmploymentSeparation.isPresent()) {
            btnExpandEmploymentSeparation.click();
        }
    }

    /**
     * Expand denials section
     */
    public void expandDenials() {
        if (btnExpandDenials.isPresent()) {
            btnExpandDenials.click();
        }
    }

    /**
     * Expand form section.
     */
    public void expandFormSection() {
        if (btnExpandForms.isPresent()) {
            btnExpandForms.waitClickAndWait();
        }
    }

    /**
     * Expand expenditures encumbrances section
     */
    public void expandExpendituresSection() {
        if (btnExpandExpendituresEncumbrancesSection.isPresent()) {
            btnExpandExpendituresEncumbrancesSection.click();
        }
    }

    /**
     * Input data signed
     *
     * @param dataSigned - data signed
     */
    public void inputDataSigned(String dataSigned) {
        txbDateSigned.type(dataSigned);
        btnSaveDateSigned.clickAndWait();
    }

    /**
     * Get signed save text
     *
     * @return signed save text
     */
    public String getSignedSaveText() {
        return tbcDateSignedIsSaved.getText();
    }

    /**
     * Check employment separation.
     */
    public void checkEmploymentSeparation() {
        tbcEmploymentSeparation.isPresent();
    }

    /**
     * Check appeal date is not present
     */
    public void checkAppealDateNotPresent() {
        tbcAppealDate.assertIsNotPresent();
    }

    /**
     * Get appeal date text
     *
     * @return appeal date text
     */
    public String getAppealDateText() {
        return tbcAppealDate.getText();
    }

    /**
     * Get appeal docket number text
     *
     * @return appeal docket number
     */
    public String getAppealDocketNumberText() {
        return tbcAppealDocketNumber.getText();
    }

    /**
     * Check preview employment separation
     *
     * @return true, if present
     */
    public Boolean checkPreviewEmploymentSeparation() {
        return divPreviewEmploymentSeparation.isPresent();
    }

    /**
     * Check some trade enrollment buttons, that should be present on the page.
     *
     * @param user - current user.
     */
    public void checkTrdEnrlButtons(User user) {
        expandEmploymentSeparation();
        expandExpendituresSection();
        expandFormSection();
        expandDenials();
        divideMessage("Manage expenditures/encumbrance");
        ifButton(user.getTradeEnrollment().getTeViewManageEncumbrances(), btnManageExpendituresEncumbrances);
        divideMessage("Edit Denial Button");
        ifButton(user.getTradeEnrollment().getTeViewEditDenialButton(), btnEditDenial);
        divideMessage("Appeal Denial Button");
        ifButton(user.getTradeEnrollment().getTeViewAppealDenialButton(), btnAppeal);
        //If appeal button is present, we should make appeal to check [Edit Appeal]
        if (user.getTradeEnrollment().getTeViewAppealDenialButton()) {
            btnAppeal.clickAndWait();
            txbAppealDate.type(CommonFunctions.getCurrentDate());
            clickButton(Buttons.Save);
            expandDenials();
        }
        divideMessage("Edit Appeal Button");
        ifButton(user.getTradeEnrollment().getTeViewEditAppealButton(), btnEditAppeal);
        divideMessage("Edit trade enrollment button");
        ifButton(user.getTradeEnrollment().getTeViewEditTradeEnrollmentButton(), BaseButton.EDIT.getButton());
        divideMessage("Audit button");
        ifButton(user.getTradeEnrollment().getTeViewAuditButton(), BaseButton.AUDIT.getButton());
    }

    /**
     * Check forms, print, sign
     *
     * @param user - current user.
     */
    public void checkFormsPrintSign(User user, TradeEnrollment trd) {
        expandFormSection();
        if (user.getTradeEnrollment().getTeFormsPrintSign()) {
            btnPrint.assertIsPresent(); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-8611
            inputDataSigned(trd.getApplicationDate()); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-8579
            areYouSure(Popup.Yes);
            Browser.getInstance().waitForPageToLoad();
            expandFormSection();
            CustomAssertion.softAssertContains(getSignedSaveText(), trd.getApplicationDate(), "Assert Date Signed failed!");
        } else {
            txbDateSigned.assertIsNotPresent();
            btnSaveDateSigned.assertIsNotPresent();
            btnPrint.assertIsNotPresent();
        }
    }

    /**
     * Edit employment at separation (first remove job, then add it again)
     *
     * @param user - current user
     * @param trd  - trade enrollment
     */
    public void editEmploymentSeparation(User user, TradeEnrollment trd) {
        if (user.getTradeEnrollment().getTeEmploymentSeparationEdit()) {
            divideMessage("Remove previous job");
            clickButton(Buttons.Edit);
            TradeEnrollmentEditForm editPage = new TradeEnrollmentEditForm();
            editPage.selectFirstJobEdit();
            editPage.removeSeparationJob();

            divideMessage("Check, that job is successfully removed from trade enrollment");
            noSearchResults();

            divideMessage("Add previous job to the trade enrollment again");
            editPage.addPreviousJob();
            editPage.selectFirstJobEdit();
            editPage.markQualifyingSeparation();
            clickButton(Buttons.Save);
            validateEmploymentSeparation(user, trd);
        }
    }
}
