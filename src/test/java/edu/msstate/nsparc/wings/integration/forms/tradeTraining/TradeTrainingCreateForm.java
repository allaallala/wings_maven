package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTrainingSemester;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import webdriver.Browser;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Trade Training -> Create
 */
public class TradeTrainingCreateForm extends TradeTrainingBaseForm {

    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private Button btnTrainingProviderLookUp = new Button("css=span[id='trainingProviderLookup'] button", "Training Provider Lookup");

    private ComboBox cmbLocation = new ComboBox("css=select[id='selectedLocationId']", "Location");
    private ComboBox cmbCourse = new ComboBox(By.id("selectedCourseId"), "Course");
    private ComboBox cmbType = new ComboBox("css=select[id='trainingType']", "Training Type");
    private Button btnDisabledEdit = new Button("//button[@id='editSemester'][@disabled='disabled']", "Disabled [Edit] button");
    private Button btnDisabledRemove = new Button("//button[@id='removeSemester'][@disabled='disabled']", "Disabled [Remove] button");
    private Button btnRemove = new Button(By.id("removeSemester"), "Remove");

    private TextBox txbFirstDayOfTraining = new TextBox("css=input[id='dateTrainingStart']", "First Day of Training");
    private TextBox txbDateAnticipatedCompletion = new TextBox("css=input[id='dateAnticipatedCompletion']", "Date Anticipated Completion");
    private TextBox txbLengthOfProgram = new TextBox("css=input[id='programLength']", "Length of Program (Weeks)");
    private RadioButton rbDistanceLearningYes = new RadioButton("css=input[id='isDistanceLearning1']", "Distance Learning - Yes");
    private RadioButton rbDistanceLearningNo = new RadioButton("css=input[id='isDistanceLearning2']", "Distance Learning - No");
    private RadioButton rbPellGrantRecipientYes = new RadioButton("css=input[id='isPellGrant1']", "Pell Grant Recipient - Yes");
    private RadioButton rbPellGrantRecipientNo = new RadioButton("css=input[id='isPellGrant2']", "Pell Grant Recipient - No");
    private RadioButton rbAssociateDegreeYes = new RadioButton("css=input[id='isAssociatesTraining1']", "Associate's Degree - Yes");
    private RadioButton rbAssociateDegreeNo = new RadioButton("css=input[id='isAssociatesTraining2']", "Associate's Degree - No");
    private Button btnAddSemester = new Button("css=button[id='addSemester']", "Add Semester");
    private Integer waitSec = 10;

    //Locators in the 'Semester' block
    private TableCell tbcBeginDateSecondRecord = new TableCell(By.xpath("//table[@id='results-table']//tr[2]//td[2]"), "Begin date of semester");

    /**
     * Default constructor
     */
    public TradeTrainingCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Training Creation')]"), "Trade Training Creation");
    }

    /**
     * Filling out the creation form
     *
     * @param training Object with Training data
     */
    public void fillOutCreationForm(TradeTraining training) {
        selectParticipant(training.getTradeEnrollment().getParticipant());
        cmbTradeEnrollment.selectFirst();
        selectTrainingProvider(training.getTrainingProvider());
        cmbLocation.select(training.getTrainingProvider().getLocations().get(0).getName());

        if (cmbCourse.isPresent()) {
            cmbCourse.selectFirst();
        }
        cmbType.select(training.getType());
        inputApplicationDate(training.getApplicationDate());
        txbFirstDayOfTraining.type(training.getFirstDayOfTraining());
        txbDateAnticipatedCompletion.type(training.getCompletionDate());
        txbLengthOfProgram.type(training.getLengthOfProgram());
        if (training.isDistanceLearning()) {
            rbDistanceLearningYes.click();
        } else {
            rbDistanceLearningNo.click();
        }
        if (training.isPellGrantRecipient()) {
            rbPellGrantRecipientYes.click();
        } else {
            rbPellGrantRecipientNo.click();
        }
        if (training.isLeadToAssociateDegree()) {
            rbAssociateDegreeYes.click();
        } else {
            rbAssociateDegreeNo.click();
        }

        for (TradeTrainingSemester semester : training.getTradeTrainingSemesters()) {
            btnAddSemester.clickAndWait();
            TradeTrainingSemesterForm semesterForm = new TradeTrainingSemesterForm();
            semesterForm.addSemester(semester);
        }

        cmbTrainingResult.select(training.getResult());
    }

    /**
     * Selecting Training Provider
     *
     * @param provider Provider participantSSDetails for search
     */
    public void selectTrainingProvider(TrainingProvider provider) {
        btnTrainingProviderLookUp.clickAndWait();
        TrainingProviderSearchForm searchForm = new TrainingProviderSearchForm();
        searchForm.performSearchAndSelect(provider);
    }

    /**
     * Remove semester
     */
    public void removeSemester() {
        btnRemove.clickAndWait();
    }

    /**
     * Select location value
     *
     * @param locationValue - location
     */
    public void selectLocation(String locationValue) {
        cmbLocation.select(locationValue);
    }

    /**
     * Check, that buttons [Disabled], [Remove] are present on the page.
     */
    public void disabledRemovePresent() {
        btnDisabledEdit.isPresent();
        btnDisabledRemove.isPresent();
    }

    /**
     * Check, that second semester is not present
     */
    public void checkSecondSemesterNotPresent() {
        tbcBeginDateSecondRecord.assertIsNotPresent();
    }
}
