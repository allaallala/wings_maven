package edu.msstate.nsparc.wings.integration.forms.wiaTraining;

import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Training -> Search
 */
public class WIATrainingSearchForm extends WIATrainingBaseForm {

    private Button btnCourseLookup = new Button("css=span[id='trainingCourseLookup'] button", "Course Lookup");
    private Link lnkFirstParticipant = new Link(By.xpath("//table[@id='results-table']//tr/td[2]/a"), "Participant name");
    private ComboBox cmbTrainingType = new ComboBox("css=select#trainingType", "Training Type");
    private TextBox txbFirstDayOfTraining = new TextBox("css=input#dateTrainingStart", "First Day of Training");
    private TableCell tbcFirstDayOfTraining = new TableCell(By.xpath("//table[@id='results-table']//tr/td[5]"), "First day of training table cell");
    private TableCell tbcTrainingType = new TableCell(By.xpath("//table[@id='results-table']//tr/td[6]"), "Training type table cell");
    private TableCell tbcTrainingProvider = new TableCell(By.xpath("//table[@id='results-table']//tr/td[3]"), "Training provider table cell");
    private TableCell tbcTrainingCourse = new TableCell(By.xpath("//table[@id='results-table']//tr/td[4]"), "Training Course");
    private TableCell tbcParticipant = new TableCell(By.xpath("//table[@id='results-table']//tr/td[2]"), "Participant table cell");

    /**
     * Default constructor
     */
    public WIATrainingSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Training Search')]"), "Training Enrollment Search");
    }

    /**
     * This method is used for selecting Course from look-up
     *
     * @param course Course name
     */
    public void selectCourse(String course) {
        clickCourseLookupAndWait();
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearchAndReturn(course);
    }

    public void clickCourseLookupAndWait() {
        btnCourseLookup.clickAndWait();
    }

    /**
     * Input first day of training
     *
     * @param firstDayTraining - first day of training
     */
    public void inputFirstDayTraining(String firstDayTraining) {
        txbFirstDayOfTraining.type(firstDayTraining);
    }

    /**
     * This method is used for getting WIA Training parameters from search result
     *
     * @return String array with WIA Training parameters
     */
    public String[] getWIATrainingDetails() {
        return new String[]{
                tbcTrainingType.getText(),
                tbcFirstDayOfTraining.getText(),
                tbcParticipant.getText(),
                tbcTrainingProvider.getText()
        };
    }

    /**
     * Get training course text
     *
     * @return training course text
     */
    public String getTrainingCourseText() {
        return tbcTrainingCourse.getText();
    }

    /**
     * This method is used for choosing search parameters base on WIA Training data
     *
     * @param firstDayOfTraining - first day of training
     * @param trainingType       - training type
     */
    public void fillWIATrainingDetails(String firstDayOfTraining, String trainingType) {
        inputFirstDayTraining(firstDayOfTraining);
        cmbTrainingType.select(trainingType);
    }

    /**
     * This method is used for checking search results
     *
     * @param firstDayTraining - first day of training
     * @param trainingType     - training type
     * @param participantName  - participant name
     * @return True if expected data matches actual values
     */
    public boolean validateSearchedResults(String firstDayTraining, String trainingType, String participantName) {
        return tbcFirstDayOfTraining.getText().equals(firstDayTraining) &&
                tbcTrainingType.getText().equals(trainingType) &&
                tbcParticipant.getText().contains(participantName);
    }

    /**
     * This method is used for WIA Training searching by Participant
     *
     * @param participant Participant that will be used for search
     */
    public void performSearch(Participant participant) {
        selectParticipant(participant);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Click first participant
     */
    public void clickFirstParticipant() {
        lnkFirstParticipant.clickAndWait();
    }

    /**
     * This method is used for WIA Training searching by Participant
     *
     * @param participant Participant that will be used for search
     */
    public void performSearchAndOpen(Participant participant) {
        performSearch(participant);
        clickFirstParticipant();
    }
}
