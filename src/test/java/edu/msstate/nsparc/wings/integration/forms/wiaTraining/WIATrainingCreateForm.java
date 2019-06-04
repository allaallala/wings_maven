package edu.msstate.nsparc.wings.integration.forms.wiaTraining;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.ProjectCodeSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Training -> Create
 */
public class WIATrainingCreateForm extends WIATrainingBaseForm {

    private String lookupXpath = "//input[@value='%1$s']/following-sibling::button";
    private Button btnParticipantLookup = new Button(By.xpath(String.format(lookupXpath, "Participant")), "Participant Lookup");
    private Button btnProjectCodeLookup = new Button(By.xpath(String.format(lookupXpath, "ActiveProjectCode")), "Project Code Lookup");
    private Button btnYouthProviderLookup = new Button(By.xpath(String.format(lookupXpath, "ActiveYouthProvider")), "Youth Provider Lookup");

    private TextBox txbFirstDayOfTraining = new TextBox("css=input#dateTrainingStart", "First Day of Training");
    private TextBox txbDateAnticipatedCompletion = new TextBox("css=input#dateAnticipatedCompletion", "Date Anticipated Completion");
    private RadioButton rbITAEstablishedYes = new RadioButton("css=input#isITAEstablished1", "ITA Established - Yes");
    private RadioButton rbPayPerformance = new RadioButton(By.id("isTrainingPayForPerformance2"), "Pay-performance strategies - No");
    private RadioButton rbLeadToEmployment = new RadioButton(By.id("trainingLeadsToEmployment2"), "Training lead to employment - No");

    private RadioButton rbPellGrantRecipientYes = new RadioButton("css=input#isPellGrant1", "Pell Grant Recipient - Yes");
    private ComboBox cmbTrainingType = new ComboBox("css=select[name='trainingType']", "Training Type");
    private ComboBox cmbTrainingLeadsTo = new ComboBox(By.id("trainingExpectedOutcome"), "Training Leads To");
    private ComboBox cmbEnrollment = new ComboBox("css=select[id='selectedWiaEnrollmentId']", "WIA Enrollment");

    private Span spnFirstDayOfTrainingError = new Span("css=span[id='dateTrainingStart.errors']", "First Day of Training error");
    private Span spnDateAnticipatedCompletionError = new Span("css=span[id='dateAnticipatedCompletion.errors']", "Date Anticipated Completion error");

    private TableCell tbcProjectCodeText = new TableCell(By.xpath("//td[contains(.,'Project Code:')]/following-sibling::td"), "Project Code Value");

    /**
     * Default constructor
     */
    public WIATrainingCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Training Creation')]"), "Training Enrollment Creation");
    }

    /**
     * This method is used for fill WIA Training form with participantSSDetails from parameter
     *
     * @param trainingType   - training type
     * @param dayTraining    - first day of training and application date
     * @param dateCompletion - date anticipated completion
     */
    public void fillTrainingInformation(String trainingType, String dayTraining, String dateCompletion) {
        String youth = "Youth";
        String trainingLead = "Associate degree";
        String result = Constants.IN_PROGRESS;
        cmbEnrollment.selectFirst();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (cmbEnrollment.getSelectedLabel().contains(youth)) {
            selectYouthProvider();
        }
        rbITAEstablishedYes.click();
        rbPayPerformance.click();
        cmbTrainingLeadsTo.select(trainingLead);
        rbLeadToEmployment.click();
        rbPellGrantRecipientYes.click();
        inputApplicationDate(dayTraining);
        txbFirstDayOfTraining.type(dayTraining);
        txbDateAnticipatedCompletion.type(dateCompletion);
        selectResultTraining(result);
        selectTrainingType(trainingType);
    }

    /**
     * Select training type
     *
     * @param typeValue - value to select
     */
    public void selectTrainingType(String typeValue) {
        cmbTrainingType.select(typeValue);
    }

    /**
     * Select first enrollment
     */
    public void selectFirstEnrollment() {
        cmbEnrollment.selectFirst();
    }

    /**
     * This method is used for select participant on form
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantLookup.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for select first Project Code from search results
     *
     * @param providerName - provider name
     * @return course - course name
     */
    public String selectProjectCode(String providerName) {
        String course = null;
        btnProjectCodeLookup.clickAndWait();
        ProjectCodeSearchForm projectCodeSearchForm = new ProjectCodeSearchForm();
        if ("".equals(providerName)) {
            projectCodeSearchForm.performSearchAndSelectFirst();
        } else {
            course = projectCodeSearchForm.performSearchAndSelect(providerName);
        }
        return course;
    }

    /**
     * This method is used for select first Service Center from Youth Provider search results
     */
    public void selectYouthProvider() {
        btnYouthProviderLookup.waitForIsElementPresent();
        btnYouthProviderLookup.clickAndWait();
        CenterSearchForm centerForm = new CenterSearchForm();
        centerForm.selectAndReturnCenter();
    }

    /**
     * This method is used for check dates validation on WIA Training form
     */
    public void checkDataValidation() {
        String temp = txbFirstDayOfTraining.getValue();
        String dayTraining = CommonFunctions.getDaysAgoDate(2);
        String firstDayOfTrainingError = "First Day of Training must be on or after Application Date";
        txbFirstDayOfTraining.type(dayTraining);
        clickAndWait(BaseButton.CREATE);
        checkField(spnFirstDayOfTrainingError, firstDayOfTrainingError, Constants.FALSE);
        txbFirstDayOfTraining.type(temp);

        temp = txbDateAnticipatedCompletion.getValue();
        String dateAnticipatedCompletionError = "Anticipated Date of Completion must be on or after First Day of Training";
        txbDateAnticipatedCompletion.type(CommonFunctions.getYesterdayDate());
        clickAndWait(BaseButton.CREATE);
        checkField(spnDateAnticipatedCompletionError, dateAnticipatedCompletionError, Constants.FALSE);
        txbDateAnticipatedCompletion.type(temp);
    }

    /**
     * Click participant look up
     */
    public void clickParticipantLookup() {
        btnParticipantLookup.clickAndWait();
    }

    /**
     * Validate project code information
     *
     * @param provider - training provider
     */
    public void validateProjectCodeInformation(TrainingProvider provider) {
        CustomAssertion.softTrue("Incorrect text in the project code field", tbcProjectCodeText.getText().trim().contains(
                provider.getCourseName() + " at " + provider.getLocations().get(0).getName() + " by " + provider.getName()));
    }
}
