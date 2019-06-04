package edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Trade -> ATAA/RTAA Enrollments -> Search for record -> Open record
 */
public class AtaaRtaaEnrollmentDetailsForm extends AtaaRtaaEnrollmentBaseForm {

    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";
    // Basic information section
    private TableCell tbcParticipant = new TableCell(By.xpath("//td[contains(.,'Participant')]/following-sibling::td/span"), "Participant");
    private TableCell tbcTradeEnrollment = new TableCell(By.xpath(String.format(detailPath, "Approved Trade Enrollment")), "Approved Trade Enrollment");
    private TableCell tbcApplicationDate = new TableCell(By.xpath(String.format(detailPath, "Application Date")), "Application Date");
    private TableCell tbcDeterminationDate = new TableCell(By.xpath(String.format(detailPath, "Eligibility Determination Date")), "Eligibility Determination Date");
    private TableCell tbcStatus = new TableCell(By.xpath(String.format(detailPath, "Status")), "Status");
    private TableCell tbcFederalIncomeTax = new TableCell(By.xpath(String.format(detailPath, "Federal Income Tax Withheld")),
            "Federal Income Tax Withheld");

    private String aPath = "//a[@class='expand'][contains(.,'%1$s')]";
    // Dates & Deadlines section
    private TableCell tbcUIExhaustionDate = new TableCell(String.format(detailPath, "UI Exhaustion Date"), "UI Exhaustion Date");
    private Button btnExpandDatesSection = new Button(String.format(aPath, "Dates & Deadlines"), "Expand Dates & Deadlines section");
    // Qualifying Re-Employment section
    private Button btnExpandReEmploymentSection = new Button(String.format(aPath, "Qualifying"), "Expand Qualifying Re-Employment section");
    private Button btnPreviewReEmployment = new Button(By.xpath("//tr[@id='qualifyingReEmployment']//td[4]//img"), "Preview Re-Employment");
    //Wage Subsidies/Payments
    private Button btnExpandWageSubsidiesSection = new Button(String.format(aPath, "Wage Subsidies"), "Expand Wage Subsidies section");
    private Button btnExpandFormsSection = new Button(String.format(aPath, "Forms"), "Expand forms section");
    private Button btnExpandDenialsSection = new Button(String.format(aPath, "Denials"), "Expand denials section");

    // Denial section buttons
    private Button btnEditDenial = new Button("id=editDenial", "Edit Denial");
    private Button btnEditAppeal = new Button("id=editAppeal", "Edit Appeal");
    private Button btnAppeal = new Button("id=appealDenial", "Appeal");
    private TextBox txbDateAppeal = new TextBox("id=dateAppeal", "Appeal Date");

    //Wage subsidies section
    private Button btnManageWageSubsidies = new Button("css=button[id='manageWageSubsidies']", "Manage Wage Subsidies");
    private Button btnManageWageSubsidiesPayments = new Button("css=button[id='manageWageSubsidyPayments']", "Manage Wage Subsidies Payments");
    private TextBox txbMinWeekEndingDate = new TextBox("css=input[id='minDateWeekEnding']", "Week Ending Date - From");
    private TextBox txbMaxWeekEndingDate = new TextBox("css=input[id='maxDateWeekEnding']", "Week Ending Date - To");
    private TableCell tbcWeekEndingDate = new TableCell(String.format(detailPath, "Week Ending Date"), "Week Ending Date");
    private Button btnSaveAndGoToNextWeek = new Button("css=button[id='saveNextWeek']", "Save and Go to Next Week");
    private TableCell tbcWageSubsidies = new TableCell("css=div[id='manageWageSubsidiesForm'] table[id='result-table']", "Wage Subsidies");
    private TableCell tbcAmountAllowed = new TableCell("//td[contains(.,'Amount Allowed')]/following-sibling::td", "Amount allowed value");
    private TableCell tbcAmountTotal = new TableCell("//td[contains(.,'Total Spent')]/following-sibling::td", "Total spent");
    private TableCell tbcBalance = new TableCell("//td[contains(.,'Balance')]/following-sibling::td", "Balance");

    //Qualifying re-employment
    private TableCell tbcReemployment = new TableCell(By.xpath("//tr[@id='qualifyingReEmployment']//table[@id='results-table']//td[1]"), "Reemployment information");

    // Forms section
    private TextBox txbAtaaSelectionDateSigned = new TextBox("id=dateSigned1", "Date Signed");
    private TextBox txbAtaaApprovalDateSigned = new TextBox("id=dateSigned2", "Date Signed");
    private Button btnSaveAtaaSelectionDateSigned = new Button("//input[@name='dateSaver']", "Save");
    private Button btnSaveAtaaApprovalDateSigned = new Button(By.xpath("//table[@id='formsResults-table']//tr[@class='dt-row-even']//input[@name='dateSaver']"), "Save");
    private TableCell tbcSavedAtaaSelectionDateSigned = new TableCell("//td[contains(text(),'Date Signed:')]", "Date Signed");
    private TableCell tbcSavedAtaaApprovalDateSigned = new TableCell(By.xpath("//table[@id='formsResults-table']//tr[@class='dt-row-even']//td[contains(text(),'Date Signed:')]"), "Date Signed");

    private Button btnPrint = new Button("//input[@name='formPrinter']", "Print");


    /**
     * Default constructor
     */
    public AtaaRtaaEnrollmentDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'ATAA/RTAA Enrollment Detail')]"), "ATAA/RTAA Enrollment Detail");
    }


    private void expandWageSubsidy() {
        if (btnExpandWageSubsidiesSection.isPresent()) {
            btnExpandWageSubsidiesSection.click();
        }
    }

    private void expandFormsSection() {
        if (btnExpandFormsSection.isPresent()) {
            btnExpandFormsSection.click();
        }
    }

    private void expandDenialsSection() {
        if (btnExpandDenialsSection.isPresent()) {
            btnExpandDenialsSection.click();
        }
    }

    private void expandReemployment() {
        if (btnExpandReEmploymentSection.isPresent()) {
            btnExpandReEmploymentSection.click();
        }
    }

    public void checkExhaustionDate(AtaaRtaaEnrollment atrta) {
        btnExpandDatesSection.click();
        CustomAssertion.softTrue("Incorrect UI Exhaustion Date", tbcUIExhaustionDate.getText().trim().contains(atrta.getUiExhaustionDate()));
    }

    public void validateInformation(AtaaRtaaEnrollment enrollment) {
        Participant part = enrollment.getParticipant();
        Petition petit = enrollment.getTradeEnrollment().getPetition();
        CustomAssertion.softAssertContains(tbcParticipant.getText().trim(), part.getFirstName() + " " + part.getLastName(), "Incorrect participant");
        CustomAssertion.softAssertContains(tbcTradeEnrollment.getText().trim(), petit.getNumber() + " - " + petit.getEmployer().getCompanyName()
                + " - " + petit.getStatus() + " " + petit.getFileDate(),"Incorrect trade enrollment");
        CustomAssertion.softAssertContains(tbcApplicationDate.getText().trim(), enrollment.getApplicationDate(), "Incorrect application date");
    }

    public void validateDeterminationDate(String determinationDate) {
        CustomAssertion.softTrue("Incorrect determination date", tbcDeterminationDate.getText().trim().equals(determinationDate));
    }

    public void validateFullInformation(AtaaRtaaEnrollment enrollment) {
        validateInformation(enrollment);

        validateDeterminationDate(enrollment.getEligibilityDeterminationDate());
        CustomAssertion.softAssertContains(getStatusPage(), enrollment.getEligibilityString(), "Incorrect enrollment status");
    }

    public void validateQualifyingReemployment(User user, AtaaRtaaEnrollment atrt) {
        expandReemployment();
        if (user.getAtrt().getAtaaQualifyingReemploymentView()) {
            PreviousJob jb = atrt.getReemployment();
            CustomAssertion.softTrue("Incorrect re-employment job title, employer", tbcReemployment.getText().contains(
                    jb.getJobTitle() + " at " + jb.getEmployer()));
            CustomAssertion.softTrue("Incorrect re-employment date", tbcReemployment.getText().contains(
                    jb.getStartDate()));
            CustomAssertion.softTrue("Incorrect employment separation address, salary", tbcReemployment.getText().contains(
                    jb.getCity() + ", " + jb.getZipCode() + " Salary: $" + jb.getHoursPerWeek()));
        }
    }

    public void performWageSubsidySearch(String date) {
        String expectedSearchString = "One item found";
        txbMinWeekEndingDate.type(date);
        txbMaxWeekEndingDate.type(date);
        clickAndWait(BaseButton.SEARCH);

        checkField(tbcWageSubsidies, expectedSearchString, Constants.FALSE);
    }

    public void enterAtaaSelectionDateAndSave(String date) {
        txbAtaaSelectionDateSigned.type(date);
        btnSaveAtaaSelectionDateSigned.click();
        areYouSure(Popup.Yes);
        BaseButton.ARE_YOU_SURE_YES.getButton().waitForNotVisible();
    }

    public void enterAtaaApprovalDateAndSave(String date) {
        txbAtaaApprovalDateSigned.type(date);
        btnSaveAtaaApprovalDateSigned.click();
        areYouSure(Popup.Yes);
        BaseButton.ARE_YOU_SURE_YES.getButton().waitForNotVisible();
    }

    public void validateAtaaSelectionDateInfo(String date) {
        CustomAssertion.softTrue("'Date Signed' for 'TAA Selection Form' is incorrect!", tbcSavedAtaaSelectionDateSigned.getText().contains(date));
    }

    public void validateAtaaApprovalDateInfo(String date) {
        CustomAssertion.softTrue("'Date Signed' for 'TAA Approval Form' is incorrect!", tbcSavedAtaaApprovalDateSigned.getText().contains(date));
    }

    public void validatePrintButtonsAvailable() {
        btnPrint.waitForIsElementPresent();
    }

    public void validateFederalIncomeTax(AtaaRtaaEnrollment enrollment) {
        CustomAssertion.softAssertEquals(tbcFederalIncomeTax.getText(), enrollment.getWithholdTaxString(), "Incorrect 'Federal Income Tax'!");
    }

    public void validateWageSubsidies(String amountAllowed, String total, String balance) {
        expandWageSubsidy();
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(tbcAmountAllowed.getText(), Constants.AMOUNT_REGEX),amountAllowed,"Incorrect value for allowed amount");
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(tbcAmountTotal.getText(), Constants.AMOUNT_REGEX), total,"Incorrect value for total spent");
        CustomAssertion.softAssertEquals(CommonFunctions.regexGetMatch(tbcBalance.getText(), Constants.AMOUNT_REGEX), balance,"Incorrect value for balance");
    }


    public void openWagePaymentsForm() {
        expandWageSubsidy();
        btnManageWageSubsidiesPayments.clickAndWait();
    }

    public void openWageManage() {
        expandWageSubsidy();
        btnManageWageSubsidies.clickAndWait();
    }

    public void subsidyManipulation(User user, AtaaRtaaEnrollment atrt, String workedState) {
        String initialState = "Pending";
        String submitStatus = "Sent";
        String weekAmount = null;
        //Manage wage subsidy
        expandWageSubsidy();
        ifButton(user.getAtrt().getAtaaManageWageSubsidies(), btnManageWageSubsidies);
        if (user.getAtrt().getAtaaManageWageSubsidies()) {
            divideMessage("Open manage wage subsidies form");
            openWageManage();
        }
        if (user.getAtrt().getAtaaAddWageSubsidy()) {
            divideMessage("Add new wage subsidy");
            Integer countWageSubsidies = ParticipantSqlFunctions.getCountWageSubsidies(atrt.getParticipant().getFirstName());
            String dataKeyed = CommonFunctions.getCurrentDate();
            ManageWageSubsidiesForm wageSubsidiesForm = new ManageWageSubsidiesForm();
            wageSubsidiesForm.chooseDefinedRecord(countWageSubsidies);
            wageSubsidiesForm.clickButton(Buttons.Edit);
            weekAmount = wageSubsidiesForm.getWeekSubsidiesAmount();
            wageSubsidiesForm.choosePayableSave(workedState, Boolean.TRUE, Boolean.TRUE);
            wageSubsidiesForm.chooseDefinedRecord(countWageSubsidies);
            wageSubsidiesForm.validateSpecificRecordNumber(countWageSubsidies, dataKeyed, atrt, weekAmount, initialState);
            wageSubsidiesForm.submitPayment();
            wageSubsidiesForm.validateSpecificRecordNumber(countWageSubsidies, dataKeyed, atrt, weekAmount, submitStatus);
            wageSubsidiesForm.clickButton(Buttons.Cancel); //Done button
        }
        subsidyPayments(user, weekAmount, initialState);

    }

    public void subsidyPayments(User user, String weekAmount, String statusPayment) {
        expandWageSubsidy();
        ifButton(user.getAtrt().getAtaaManageWagePayments(), btnManageWageSubsidiesPayments);
        if (user.getAtrt().getAtaaManageWagePayments()) {
            divideMessage("Manage wage payments");
            openWagePaymentsForm();
            ManageWageSubsidiesForm wageSubsidiesForm = new ManageWageSubsidiesForm();
            wageSubsidiesForm.clickButton(Buttons.Search);
            wageSubsidiesForm.validatePaymentsRecords(weekAmount, statusPayment, Constants.RANDOM_ONE);
            wageSubsidiesForm.clickButton(Buttons.Cancel);
        }
    }

    public String getStatusPage() {
        return tbcStatus.getText().trim();
    }

    public String getWageSubsidiesText() {
        return tbcWageSubsidies.getText().trim();
    }

    public void saveNextWeek() {
        btnSaveAndGoToNextWeek.clickAndWait();
    }

    public String getWeekEndingDate() {
        return tbcWeekEndingDate.getText().trim();
    }

    public void openPreview() {
        expandReemployment();
        btnPreviewReEmployment.click();
    }

    public void checkAtaaButtons(User user) {
        expandWageSubsidy();
        expandFormsSection();
        expandDenialsSection();
        divideMessage("Check [Manage Wage Subsidies]");
        ifButton(user.getAtrt().getAtaaViewManageWageSubsidiesButton(), btnManageWageSubsidies);
        divideMessage("Check [Manage Wage Subsidies Payments]");
        ifButton(user.getAtrt().getAtaaViewManageWagePaymentsButton(), btnManageWageSubsidiesPayments);
        divideMessage("Check [Edit Denial]");
        ifButton(user.getAtrt().getAtaaViewEditDenial(), btnEditDenial);
        divideMessage("Check [Appeal Denial]");
        ifButton(user.getAtrt().getAtaaViewAppealDenial(), btnAppeal);
        //If appeal button is present, we should make appeal to check [Edit Appeal]
        if (user.getAtrt().getAtaaViewEditAppeal()) {
            btnAppeal.clickAndWait();
            txbDateAppeal.type(CommonFunctions.getCurrentDate());
            clickButton(Buttons.Save);
            expandDenialsSection();
        }
        divideMessage("Check [Edit Appeal]");
        ifButton(user.getAtrt().getAtaaViewEditAppeal(), btnEditAppeal);

        checkButtonsPresent(user.getAtrt().getAtaaViewEditEnrollmentButton(), user.getAtrt().getAtaaViewAuditButton());
    }

    public void checkFormsPrint() {
        btnPrint.assertIsPresent();
    }

    public void editQualifyingReemployment(User user, AtaaRtaaEnrollment atrt) {
        if (user.getAtrt().getAtaaQualifyingReemploymentEdit()) {
            clickButton(Buttons.Edit);
            AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
            editPage.removeAddReemployment();
        }
        validateQualifyingReemployment(user, atrt);
    }
}
