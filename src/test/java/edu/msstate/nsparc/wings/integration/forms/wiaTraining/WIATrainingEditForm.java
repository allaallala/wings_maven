package edu.msstate.nsparc.wings.integration.forms.wiaTraining;

import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This form is opened via Participants -> WIA -> WIA Training -> Search for record -> Open record -> Click on Edit button
 */
public class WIATrainingEditForm extends WIATrainingBaseForm {

    private Button btnRemoveParticipant = new Button("css=span[id='participantLookup'] a", "Remove Participant");
    private Button btnParticipantLookup = new Button("css=span[id='participantLookup'] button", "Participant Lookup");
    private Button btnRemoveServiceCenter = new Button(By.xpath("//td[contains(.,'Youth Provider')]/following-sibling::td/a"), "Remove Youth Provider");
    private Button btnServiceCenterLookup = new Button(By.xpath("//td[contains(.,'Youth Provider')]/following-sibling::td//button[1]"), "Youth Provider Lookup");
    private ComboBox cmbWIAEnrollment = new ComboBox("css=select#selectedWiaEnrollmentId", "WIA Enrollment");
    private TextBox txbApplicationDate = new TextBox("css=input#dateApplication", "Application date");
    private TextBox txbFirstDayOfTraining = new TextBox("css=input#dateTrainingStart", "First Day of Training");
    private TextBox txbDateAnticipatedCompletion = new TextBox("css=input#dateAnticipatedCompletion", "Date Anticipated Completion");
    private TextBox txbLastDayOfTraining = new TextBox("css=input#dateTrainingEnd", "Last Day of Training");
    private ComboBox cmbTrainingType = new ComboBox("css=select#trainingType", "Training Type");
    private RadioButton rbOutcomePassed = new RadioButton("css=input[id='outcome1']", "Outcome - Passed");

    private Span spnFirstDayOfTrainingError = new Span("css=span[id='dateTrainingStart.errors']", "First Day of Training error");
    private Span spnDateAnticipatedCompletionError = new Span("css=span[id='dateAnticipatedCompletion.errors']", "Date Anticipated Completion error");
    private Span spnLastDayError = new Span("css=span[id='dateCompleted.errors']", "Last Day of Training error");
    private Span spnTrainingErrors = new Span("css=span[id='id.errors']", "WIA Training errors");
    private Span spnPleaseBeSure = new Span(By.xpath("//div[contains(text(), 'Please be sure to add')]"), "Please be sure");

    /**
     * Default constructor
     */
    public WIATrainingEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Training Edit')]"), "Training Enrollment Edit");
    }

    /**
     * This method is used for editing WIA Training participantSSDetails
     *
     * @param result          - training result
     * @param lastDayTraining - last day of training
     */
    public void editWIATrainingDetails(String result, String lastDayTraining) {
        selectResultTraining(result);
        inputLastDayOfTraining(lastDayTraining);
        rbOutcomePassed.click();
    }

    public void waitPleaseBeSure() {
        spnPleaseBeSure.waitForIsElementPresent();
    }

    /**
     * Edit WIA training.
     *
     * @param trainingType         - training type
     * @param firstDayTrain        - first day of training
     * @param dateCompletionString - date anticipated completion
     */
    public void editWIATraining(String trainingType, String firstDayTrain, String dateCompletionString) {
        selectTrainingType(trainingType);
        txbFirstDayOfTraining.type(firstDayTrain);
        txbApplicationDate.type(firstDayTrain);
        txbDateAnticipatedCompletion.type(dateCompletionString);
    }

    /**
     * This method is used for selecting Participant from look-up and selecting first WIA Enrollment from combo box
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantLookup.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbWIAEnrollment.selectFirst();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * This method is used for clearing existing Service Center value and selecting new
     *
     * @param newServiceCenter Service Center name that will be selected
     */
    public void changeServiceCenter(String newServiceCenter) {
        btnRemoveServiceCenter.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        btnServiceCenterLookup.waitForIsElementPresent();
        btnServiceCenterLookup.clickAndWait();
        CenterSearchForm centerSearchForm = new CenterSearchForm();
        centerSearchForm.selectAndReturnCenter(newServiceCenter);
    }

    /**
     * This method is used for checking dates validation for First Day of Training,
     * Date Anticipated Completion  and Last Day of Training fields
     */
    public void checkDataValidation() {
        String temp = txbFirstDayOfTraining.getValue();
        String firstDayTraining = CommonFunctions.getDaysAgoDate(2);
        String firstDayError = "First Day of Training must be on or after Eligibility Date";
        txbFirstDayOfTraining.type(firstDayTraining);
        clickAndWait(BaseButton.SAVE_CHANGES);
        assertTrue("First Day of Training error message wasn't displayed", spnFirstDayOfTrainingError.getText().contains(firstDayError));
        txbFirstDayOfTraining.type(temp);

        temp = txbDateAnticipatedCompletion.getValue();
        String dateAnticipatedCompletionError = "Anticipated Date of Completion must be on or after First Day of Training";
        txbDateAnticipatedCompletion.type(CommonFunctions.getYesterdayDate());
        clickAndWait(BaseButton.SAVE_CHANGES);
        assertTrue("Date Anticipated Completion error message wasn't displayed", spnDateAnticipatedCompletionError.getText().contains(dateAnticipatedCompletionError));
        txbDateAnticipatedCompletion.type(temp);

        temp = txbLastDayOfTraining.getValue();
        String lastDayError = "Last Day of Training must be on or after First Day of Training";
        inputLastDayOfTraining(CommonFunctions.getYesterdayDate());
        clickAndWait(BaseButton.SAVE_CHANGES);
        assertTrue("Last Day of Training error message wasn't displayed", spnLastDayError.getText().contains(lastDayError));
        inputLastDayOfTraining(temp);
    }

    /**
     * Remove participant
     */
    public void removeParticipant() {
        btnRemoveParticipant.click();
    }

    /**
     * Input last day of training
     *
     * @param lastDayTraining - last day of training
     */
    public void inputLastDayOfTraining(String lastDayTraining) {
        txbLastDayOfTraining.type(lastDayTraining);
    }

    /**
     * Get training errors text on page
     *
     * @return - error text
     */
    public String getTrainingErrorText() {
        return spnTrainingErrors.getText();
    }

    /**
     * Select training type
     *
     * @param trainingType - training type.
     */
    public void selectTrainingType(String trainingType) {
        cmbTrainingType.select(trainingType);
    }
}
