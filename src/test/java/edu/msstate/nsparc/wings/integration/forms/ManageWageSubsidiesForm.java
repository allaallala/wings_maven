package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * This form describes Manage Wage Subsidies Form and may be accessed from ATAA/ RTAA Enrollment Details form.
 * Created by a.vnuchko on 29.10.2015.
 */
public class ManageWageSubsidiesForm extends BaseWingsForm {

    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private Button btnDone = new Button("id=cancel", "Done");
    private Button btnSumbitPayment = new Button(By.xpath("//input[@type='submit']"), "Submit payment");
    private String input = SearchResultsForm.SEARCH_RESULT_XPATH_ROW + "//input";
    private String img = SearchResultsForm.SEARCH_RESULT_XPATH_CELL + "/img";
    private String defaultStatus = "Status";

    private TableCell tbcFirstRecord = new TableCell(By.xpath(String.format(input, 1, "1")), "First wage subsidie record");
    private TableCell tbcSecondRecord = new TableCell(By.xpath(String.format(input, 2, "1")), "Second wage subsidie record");
    private TableCell tbcThirdRecord = new TableCell(By.xpath(String.format(input, 3, "1")), "Second wage subsidie record");
    private TableCell tbcFirstPreview = new TableCell(By.xpath(String.format(img, 1, "8")), "Preview button");
    private ComboBox cmbEmployer = new ComboBox("id=tmpEmployer", "Employer combobox");
    private TextBox txbPaymentNumber = new TextBox("id=tmpPaymentNumber", "Payment number");
    private ComboBox cmbSubsidyStatus = new ComboBox("id=subsidyStatus", "Subsidy status combobox");
    private TextBox txbWeekEndingFrom = new TextBox("id=minDateWeekEnding", "Week Ending date from");
    private TextBox txbWeekEndingTo = new TextBox("id=maxDateWeekEnding", "Week Ending date to");
    private TextBox txbDataKeyedFrom = new TextBox("id=minDateKeyed", "Data keyed from");
    private TextBox txbDataKeyedTo = new TextBox("id=maxDateKeyed", "Data keyed to");
    private ComboBox cmbWorkStatus = new ComboBox(By.xpath("//tr[@class='fixPowerLookupBottoms']/td[2]/select"), "Work status");
    private Button btnSaveNextWeek = new Button("id=saveNextWeek", "Save and move to next week");
    private Button btnSaveFinish = new Button("id=saveFinish", "Save and finish");
    private Button rbPayable = new Button("//input[@value='true']", "This is payable");
    private Button rbNotPayable = new Button("//input[@value='false']", "This is NOT payable");

    private TableCell tbcWeekSubsidies = new TableCell(String.format(detailPath, "Subsidy this week"), "Subsidy this week");
    private TableCell tbcTotalNextPayment = new TableCell(String.format(detailPath, "Total of next Payment"), "Total of next Payment");

    //Preview part
    private String previewXpath = "//table[@id='wageStatements-table']/tbody//td[%1$d]";
    private Button btnClosePreview = new Button("id=closePreview", "Close preview");
    private TableCell tbcEmployerPrw = new TableCell(By.xpath(String.format(previewXpath, 1)), "Employer preview");
    private TableCell tbcHoursWeekPrw = new TableCell(By.xpath(String.format(previewXpath, 2)), "Hours week preview");
    private TableCell tbcWorkStatusPrw = new TableCell(By.xpath(String.format(previewXpath, 3)), "Work status preview");
    private TableCell tbcHourlyRatePrw = new TableCell(By.xpath(String.format(previewXpath, 4)), "Hourly rate preview");

    //Wage subsidies payments
    private TextBox tbPaymentNumber = new TextBox("id=paymentNumber", "Payment number");
    private ComboBox cmbPayStatus = new ComboBox("id=paymentStatus", "Payment status");
    private ComboBox cmbPayType = new ComboBox("id=paymentType", "Payment type");
    private TextBox txbPayDateFrom = new TextBox("id=minDatePayment", "Date from");
    private TextBox txbPayDateTo = new TextBox("id=maxDatePayment", "Date to");
    private String[] cellNumber = {"2", "3", "4", "5", "6", "7"};

    /**
     * Default constructor
     */
    public ManageWageSubsidiesForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Manage Wage')]"), "Manage Wage Subsidies");
    }

    /**
     * Validates wage records on form
     *
     * @param dateStart  - start date of reemployment
     * @param count      - count recours of reemployment
     * @param wageStatus - expected wage status
     * @param atrt       - ATAA / RTAA Enrollment
     * @param weekAmount - week amount
     */
    public void validateWageRecords(String dateStart, Integer count, String wageStatus, AtaaRtaaEnrollment atrt, String weekAmount) {
        String weekEndingDate, subsidy, status;
        String startDate = dateStart;
        SearchResultsForm resultsForm = new SearchResultsForm();

        for (int i = 1; i <= count; i++) {
            weekEndingDate = resultsForm.getRecordText(i, cellNumber[0]);
            subsidy = resultsForm.getRecordText(i, cellNumber[4]);
            status = resultsForm.getRecordText(i, cellNumber[5]);
            CustomAssertion.softAssertEquals(weekEndingDate, startDate, "Incorrect week date");
            if (!wageStatus.contains("Not Yet Entered")) {
                String amountRegexMatch = CommonFunctions.regexGetMatch(subsidy, Constants.AMOUNT_REGEX);
                info(amountRegexMatch);
                validateSpecificRecordFields(i, CommonFunctions.getCurrentDate(), atrt);
                info(subsidy);
                CustomAssertion.softAssertEquals(amountRegexMatch, weekAmount, "Incorrect subsidy amount");
            }
            CustomAssertion.softAssertEquals(status, wageStatus, "Incorrect status");
            startDate = CommonFunctions.getNextWageWeek(startDate);
        }
    }

    private void validateSpecificRecordFields(Integer recordNumber, String expectedDataKeyed, AtaaRtaaEnrollment atrt) {
        String dataKeyed, employer, combined;
        SearchResultsForm resultsForm = new SearchResultsForm();
        dataKeyed = resultsForm.getRecordText(recordNumber, cellNumber[1]);
        employer = resultsForm.getRecordText(recordNumber, cellNumber[2]);
        combined = resultsForm.getRecordText(recordNumber, cellNumber[3]);
        CustomAssertion.softAssertContains(dataKeyed, expectedDataKeyed, "Incorrect date keyed");
        CustomAssertion.softAssertContains(employer, atrt.getReemployment().getEmployer(), "Incorrect employer");
        CustomAssertion.softAssertContains(combined, atrt.getReemployment().getWages() + ".0", "Incorrect combined hours");

    }

    /**
     * Validate specific row
     *
     * @param recordNumber  - number of the record to check
     * @param dataKeyed     - data keyed
     * @param atrt          - ataa enrollment
     * @param weekAmount    - week amount of the subsidy
     * @param subsidyStatus - status of the subsidy
     */
    public void validateSpecificRecordNumber(Integer recordNumber, String dataKeyed, AtaaRtaaEnrollment atrt, String weekAmount, String subsidyStatus) {
        String subsidy, status;
        SearchResultsForm resultsForm = new SearchResultsForm();
        subsidy = resultsForm.getRecordText(recordNumber, cellNumber[4]);
        status = resultsForm.getRecordText(recordNumber, cellNumber[5]);
        validateSpecificRecordFields(recordNumber, dataKeyed, atrt);
        CustomAssertion.softAssertContains(subsidy, "$" + weekAmount, "Incorrect subsidy week amount");
        CustomAssertion.softAssertContains(status, subsidyStatus, "Incorrect subsidy status");
    }

    /**
     * Validate search result table for payments
     *
     * @param weekAmount   - week amount
     * @param paymentState - payment state
     * @param count        - count of the records
     */
    public void validatePaymentsRecords(String weekAmount, String paymentState, Integer count) {
        String curPaymentWeekAmount, curPaymentState;
        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 1; i <= count; i++) {
            curPaymentWeekAmount = resultsForm.getRecordText(i, "2");
            curPaymentState = resultsForm.getRecordText(i, "4");
            CustomAssertion.softAssertContains(curPaymentWeekAmount, "$" + weekAmount, "Incorrect week wage amount");
            CustomAssertion.softAssertContains(curPaymentState, paymentState, "Incorrect payment state");
        }
    }

    /**
     * Check, that all values in the elements of Manage subsidies form are cleared on clicking [Clear] button.
     */
    public void checkFieldsCleared() {
        CustomAssertion.softAssertContains(cmbEmployer.getSelectedLabel(), Constants.ANY, "Employer combobox is not correct");
        CustomAssertion.softAssertEquals(txbPaymentNumber.getValue(), Constants.EMPTY, "Payment number is not empty");
        CustomAssertion.softAssertContains(cmbSubsidyStatus.getSelectedLabel(), Constants.EMPTY, "Subsidy status combobox is not correct");
        CustomAssertion.softAssertEquals(txbWeekEndingFrom.getValue(), Constants.EMPTY, "Week ending 'from' is not empty");
        CustomAssertion.softAssertEquals(txbWeekEndingTo.getValue(), Constants.EMPTY, "Week ending 'to' is not empty");
        CustomAssertion.softAssertEquals(txbDataKeyedFrom.getValue(), Constants.EMPTY, "Data keyed 'from' is not empty");
        CustomAssertion.softAssertEquals(txbDataKeyedTo.getValue(), Constants.EMPTY, "Data keyed 'to' is not empty");
    }

    /**
     * Searches week by only one criteria
     *
     * @param type          - search type
     * @param atrt          - ataa/rtaa enrollment
     * @param subsidyStatus - status of subsidy
     * @param dateStart     - start date of wage week.
     */
    public void searchOneCriteriaWeek(String type, AtaaRtaaEnrollment atrt, String subsidyStatus, String dateStart) {
        switch (type) {
            case "Employer":
                cmbEmployer.select(atrt.getReemployment().getEmployer());
                cmbSubsidyStatus.select(Constants.ANY);
                break;
            case "Status":
                cmbSubsidyStatus.select(subsidyStatus);
                break;
            case "WeekDate":
                txbWeekEndingFrom.type(dateStart);
                txbWeekEndingTo.type(CommonFunctions.getDaysNextDate(dateStart, 6));
                break;
            case "DataKeyedFrom":
                txbDataKeyedFrom.type(CommonFunctions.getCurrentDate());
                break;
            case "DataKeyedTo":
                txbDataKeyedTo.type(CommonFunctions.getCurrentDate());
                break;
            default:
                info("Try to input correct data for searching");
                break;
        }
    }

    /**
     * Search payments only by one criteria
     *
     * @param type   - search type
     * @param status - search status
     */
    public void searchOneCriteriaPayment(String type, String status) {
        if (defaultStatus.equals(type)) {
            cmbPayStatus.select(status);
        } else {
            info("Try to input correct date");
        }
        clickButton(Buttons.Search);
    }

    /**
     * Cancel action
     *
     * @return Ataa/Rtaa Enrollment participantSSDetails form.
     */
    public AtaaRtaaEnrollmentDetailsForm cancel() {
        btnDone.clickAndWait();
        return new AtaaRtaaEnrollmentDetailsForm();
    }

    /**
     * Choose selected status
     *
     * @param status - status of the wage subsidie
     */
    public void selectStatus(String status) {
        cmbSubsidyStatus.select(status);
    }

    /**
     * Select employer on the Manage Wage Subsidies Form
     *
     * @param employer - employer name
     */
    public void selectEmployer(String employer) {
        cmbEmployer.select(employer);
    }

    /**
     * Choose first record in the search table
     */
    public void chooseFirstRecord() {
        tbcFirstRecord.click();
    }

    /**
     * Choose record with defined number in the wage subsidies table.
     *
     * @param recordNumber - record number.
     */
    public void chooseDefinedRecord(Integer recordNumber) {
        TableCell tbcRecord = new TableCell(By.xpath(String.format(input, recordNumber, "1")), "Table cell record");
        tbcRecord.click();
    }

    /**
     * Open first record preview in the search table.
     */
    public void openFirstPreview() {
        tbcFirstPreview.click();
        tbcEmployerPrw.waitForIsElementPresent();
    }

    /**
     * Edit work status and save changes
     *
     * @param changedState - changed working state
     */
    public void editStatus(String changedState) {
        cmbWorkStatus.select(changedState);
    }

    /**
     * Validate first record status in the search table
     *
     * @param expectedState - expected state.
     */
    public void validateFirstStatus(String expectedState) {
        String statusColumn = "7";
        String firstStatus = new SearchResultsForm().getFirstRowRecordText(statusColumn);
        Assert.assertEquals("Incorrect status of the first record", expectedState, firstStatus);
    }

    /**
     * Choose work status on the form and move to next wage week
     *
     * @param workedState - chosen work status
     * @param isPayable   - if week is payable (true/ false)
     * @param finish      - check, if is required to click 'Save and Finish' or 'Save and go to the next week'.
     */
    public String choosePayableSave(String workedState, Boolean isPayable, Boolean finish) {
        cmbWorkStatus.select(workedState);
        String weekAmount = getWeekSubsidiesAmount();
        if (isPayable) {
            rbPayable.click();
        } else {
            rbNotPayable.click();
        }
        if (!finish) {
            btnSaveNextWeek.clickAndWait();
        } else {
            btnSaveFinish.clickAndWait();
        }
        return weekAmount;
    }

    /**
     * Choose wage week and make payment
     *
     * @param weekNumber - number of the wage week
     * @param workState  - state of the wage week.
     */
    public String selectWeekMakePayment(Integer weekNumber, String workState) {
        switch (weekNumber) {
            case 1:
                tbcFirstRecord.click();
                break;
            case 2:
                tbcSecondRecord.click();
                break;
            case 3:
                tbcThirdRecord.click();
                break;
            default:
                break;
        }
        clickButton(Buttons.Edit);
        String paymentAmount = getPayment();
        cmbWorkStatus.select(workState);
        saveFinish();
        return paymentAmount;
    }

    /**
     * Save and finish editing of the subsidy.
     */
    private void saveFinish() {
        btnSaveFinish.clickAndWait();
    }

    /**
     * Returns amount of week subsidie
     *
     * @return wage week amount
     */
    public String getWeekSubsidiesAmount() {
        return CommonFunctions.regexGetMatch(tbcWeekSubsidies.getText(), Constants.AMOUNT_REGEX);
    }

    /**
     * Get amount of next wage payment
     *
     * @return wage payment amount
     */
    public String getPayment() {
        return CommonFunctions.regexGetMatch(tbcTotalNextPayment.getText(), Constants.AMOUNT_REGEX);
    }

    /**
     * Validate wage week data on preview
     *
     * @param atrt       - ataa/rtaa enrollment
     * @param workStatus - work status
     */
    public void validatePreview(AtaaRtaaEnrollment atrt, String workStatus) {

        CustomAssertion.softAssertContains(tbcEmployerPrw.getText(), atrt.getReemployment().getEmployer(), "Incorrect employer");
        CustomAssertion.softAssertContains(tbcHoursWeekPrw.getText(), atrt.getReemployment().getHoursPerWeek() + ".0", "Incorrect hours per week");
        CustomAssertion.softAssertContains(tbcWorkStatusPrw.getText(), workStatus, "Incorrect work status");
        CustomAssertion.softAssertContains(tbcHourlyRatePrw.getText(), "$" + atrt.getReemployment().getWages() + ".0", "Incorrect hourly rate");
    }

    /**
     * Submit some payments
     */
    public void submitPayment() {
        btnSumbitPayment.click();
        areYouSure(Popup.Yes);
    }

    /**
     * Close preview form.
     */
    public void closePreview() {
        btnClosePreview.click();
    }

    //Manage wage subsidies payments

    /**
     * Validates, that after clicking button [Clear] all of the fields are cleared.
     */
    public void checkPaymentFormClear() {
        CustomAssertion.softAssertEquals(tbPaymentNumber.getText(), Constants.EMPTY, "Payment number is not empty");
        CustomAssertion.softAssertContains(cmbPayStatus.getText(), Constants.ANY, "Pay status combobox does not have 'Any' value");
        CustomAssertion.softAssertContains(cmbPayType.getText(), Constants.ANY, "Pay type combobox does not have 'Any' value");
        CustomAssertion.softAssertContains(txbPayDateFrom.getText(), Constants.EMPTY, "Pay data 'from' is not empty");
        CustomAssertion.softAssertContains(txbPayDateTo.getText(), Constants.EMPTY, "Pay data 'to' is not empty");
    }

    /**
     * Fill out manage subsidies payments form with any data.
     *
     * @param paymentNumber - number of payment
     * @param payStatus     - status of payment
     * @param payType       - type of payment
     * @param date          - date of the payment
     */
    public void fillOutPaymentForm(String paymentNumber, String payStatus, String payType, String date) {
        tbPaymentNumber.type(paymentNumber);
        cmbPayStatus.select(payStatus);
        cmbPayType.select(payType);
        txbPayDateFrom.type(date);
        txbPayDateTo.type(date);
    }

}
