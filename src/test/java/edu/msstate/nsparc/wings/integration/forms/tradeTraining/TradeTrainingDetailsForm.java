package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTrainingSemester;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Trade Training -> Search for record -> Open record
 */
public class TradeTrainingDetailsForm extends TradeTrainingBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String semesterPath = "//table[@id='results-table']//tbody/tr[%1$d]//input";
    private TableCell tbcParticipant = new TableCell(String.format(detailPath, "Participant:"), "Participant");
    private TableCell tbcTradeEnrollment = new TableCell(String.format(detailPath, "Trade Enrollment:"), "Trade Enrollment");
    private TableCell tbcTrainingProvider = new TableCell(String.format(detailPath, "Training Provider:"), "Training Provider");
    private TableCell tbcLocation = new TableCell(String.format(detailPath, "Location:"), "Location");
    private TableCell tbcTrainingType = new TableCell(String.format(detailPath, "Training Type:"), "Training Type");
    private TableCell tbcApplicationDate = new TableCell(String.format(detailPath, "Application Date:"), "Application Date");
    private TableCell tbcFirstDayOfTraining = new TableCell(String.format(detailPath, "First Day of Training:"), "First Day of Training");
    private TableCell tbcDateAnticipatedCompletion = new TableCell(String.format(detailPath, "Date Anticipated Completion:"), "Date Anticipated Completion");
    private TableCell tbcLengthOfProgram = new TableCell(String.format(detailPath, "Length of Program (Weeks):"), "Length of Program (Weeks)");
    private TableCell tbcResult = new TableCell(String.format(detailPath, "Result:"), "Result");
    private Button btnNotes = new Button("//img[@title='Notes']", "Notes");
    private Button btnPrint = new Button("//input[@name='formPrinter']", "Print");
    private Button btnDelete = new Button("id=deleteEnrollment", "Delete");
    private Button btnDeleteConfirm = new Button("//input[@value='Delete']", "Confirm deletion");
    private Span spnPopNoteText = new Span(By.xpath("//div[@id='Notes']//table//tr/td"), "Text in the pop-up 'Notes'");

    /**
     * Default constructor
     */
    public TradeTrainingDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Training Detail')]"), "Trade Training Detail");
    }

    /**
     * Validating displayed Training information
     *
     * @param training Object with Training information
     */
    public void validateInformation(TradeTraining training) {
        CustomAssertion.softAssertContains(tbcParticipant.getText(), training.getTradeEnrollment().getParticipant().getFirstName(), "Incorrect participant first name");
        CustomAssertion.softAssertContains(tbcTradeEnrollment.getText(), training.getTradeEnrollment().getPetition().getNumber(), "Incorrect petition number");
        CustomAssertion.softAssertContains(tbcTrainingProvider.getText(), training.getTrainingProvider().getName(), "Incorrect provider name");
        CustomAssertion.softAssertContains(tbcLocation.getText(), training.getTrainingProvider().getLocations().get(0).getName(), "Incorrect Locations");
        CustomAssertion.softAssertContains(tbcTrainingType.getText(), training.getType(), "Incorrect training type");
        CustomAssertion.softAssertContains(tbcApplicationDate.getText(), training.getApplicationDate(), "Incorrect application date");
        CustomAssertion.softAssertContains(tbcFirstDayOfTraining.getText(), training.getFirstDayOfTraining(), "Incorrect first day of training");
        CustomAssertion.softAssertContains(tbcDateAnticipatedCompletion.getText(), training.getCompletionDate(), "Incorrect date anticipated completion");
        CustomAssertion.softAssertContains(tbcLengthOfProgram.getText(), training.getLengthOfProgram(), "Incorrect length of program");
        CustomAssertion.softAssertContains(tbcResult.getText(), training.getResult(), "Incorrect training result");
    }

    /**
     * Validate information about the semester
     *
     * @param train - trade training
     */
    public void validateSemestersData(TradeTraining train) {
        String contentSemesterBegin, contentSemesterEnd, contentSemesterCredit;
        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 0; i < train.getTradeTrainingSemesters().size(); i++) {
            TradeTrainingSemester semester = train.getTradeTrainingSemesters().get(i);
            contentSemesterBegin = resultsForm.getRecordText(i + 1, "1");
            contentSemesterEnd = resultsForm.getRecordText(i + 1, "2");
            contentSemesterCredit = resultsForm.getRecordText(i + 1, "3");
            CustomAssertion.softAssertContains(contentSemesterBegin, semester.getBeginDate(), "Incorrect begin date of the semester");
            CustomAssertion.softAssertContains(contentSemesterEnd, semester.getEndDate(), "Incorrect end date of the semester");
            CustomAssertion.softAssertContains(contentSemesterCredit, semester.getCreditHours(), "Incorrect hours of the semester");
        }
    }

    /**
     * Open notes block
     */
    public void openNotesBlock() {
        btnNotes.click();
    }

    /**
     * Delete record
     */
    public void deleteRecord() {
        btnDelete.clickAndWait();
    }

    /**
     * Confirm deletion
     */
    public void confirmDeletion() {
        btnDeleteConfirm.click();
    }

    /**
     * Check, that print button is present.
     */
    public void checkPrintPresent() {
        btnPrint.assertIsPresentAndVisible();
    }

    /**
     * Get pop note text
     *
     * @return pop note text.
     */
    public String getPopNoteText() {
        return spnPopNoteText.getText();
    }

    /**
     * Check [Edit] button present or not on the page
     *
     * @param user - current user.
     */
    public void checkTradeTrainingButtonsPresent(User user) {
        divideMessage("Edit");
        ifButton(user.getTradeTraining().getTradeTrainingEdit(), BaseButton.EDIT.getButton());
    }

    /**
     * Edit completion date and first day of training
     *
     * @param trTraining - trade training.
     */
    public void editSomeParam(TradeTraining trTraining) {
        clickButton(Buttons.Edit);
        TradeTrainingEditForm editPage = new TradeTrainingEditForm();
        editPage.inputDateCompletion(trTraining.getCompletionDate());
        editPage.clickButton(Buttons.Save);
    }

    /**
     * Edit semester
     *
     * @param training       - trade training
     * @param startDate      - semester start date
     * @param endDate        - semester end date
     * @param hours          - semester hours
     * @param semesterNumber - quantity of the semesters
     */
    public void editSemester(TradeTraining training, String startDate, String endDate, String hours, Integer semesterNumber) {
        RadioButton rbSemester = new RadioButton(By.xpath(String.format(semesterPath, semesterNumber)), "Semester number");
        rbSemester.click();
        clickButton(Buttons.Edit);
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.editSemester(startDate, endDate, hours);
        clickButton(Buttons.Save);
        validateSemestersData(training);
    }

    /**
     * Add semester to the trade training
     *
     * @param training - trade training
     * @param number   - number in the semester's list.
     */
    public void inputNewSemester(TradeTraining training, Integer number) {
        addSemester();
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.addSemester(training.getTradeTrainingSemesters().get(number));
        clickButton(Buttons.Save);
        validateSemestersData(training);
    }
}
