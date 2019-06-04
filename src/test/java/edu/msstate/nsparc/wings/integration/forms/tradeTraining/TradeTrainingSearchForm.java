package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;
import org.testng.Assert;

/**
 * This form is opened via Participants -> Trade -> Trade Training -> Search
 */
public class TradeTrainingSearchForm extends TradeTrainingBaseForm {
    private TextBox tbFirstDayFrom = new TextBox("id=minDateTrainingStart", "First Day From");
    private TextBox tbFirstDayTo = new TextBox("id=maxDateTrainingStart", "First Day To");
    private TextBox tbLastDayFrom = new TextBox("id=minDateAnticipatedCompletion", "Last Day From");
    private TextBox tbLastDayTo = new TextBox("id=maxDateAnticipatedCompletion", "Last Day To");
    private TableCell tbcParticipant = new TableCell("//span[@modelclass='Participant']", "Value of the participant name, surname in the search result table");
    private String particialXpath = "//table[@id='results-table']//tbody//td[%1$d]";
    private TableCell tbcPetition = new TableCell(By.xpath("//table[@id='results-table']//tbody//td[3]/a"), "Value of the petition name in the search result table");
    private TableCell tbcFirstDay = new TableCell(By.xpath(String.format(particialXpath, 4)), "FirstDay in the result table");
    private TableCell tbcCompleteDay = new TableCell(By.xpath(String.format(particialXpath, 5)), "CompleteDay in the result table");
    private TableCell tbcTrainingType = new TableCell(By.xpath(String.format(particialXpath, 7)), "TrainingType in the result table");
    private TableCell tbcTrainingStatus = new TableCell(By.xpath(String.format(particialXpath, 8)), "TrainingStatus in the result table");
    private String expectedCourse = "Course28102015";
    private String service = "Golden";

    /**
     * Default constructor
     */
    public TradeTrainingSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Training Search')]"), "Trade Training Search");
    }

    /**
     * Search for Trade Training record
     *
     * @param training Object with data for search
     */
    public void performSearch(TradeTraining training) {
        selectParticipant(training.getTradeEnrollment().getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Search for Trade Training record and open it
     *
     * @param training Object with data for search
     */
    public void performSearchAndOpen(TradeTraining training) {
        performSearch(training);
        openFirstSearchResult();
    }

    /**
     * check results in the results table.
     *
     * @param training - trade training
     */
    public void validateSearchResult(TradeTraining training) {
        Assert.assertEquals("Incorrect petition information", training.getTradeEnrollment().getPetition().getNumber() + " - " + training.getTradeEnrollment().getPetition().getEmployer().getCompanyName()
                + " - " + training.getTradeEnrollment().getPetition().getStatus() + " " + training.getTradeEnrollment().getPetition().getFileDate(), tbcPetition.getText());
        Assert.assertEquals("Incorrect participant information", training.getTradeEnrollment().getParticipant().getFirstName() + " " + training.getTradeEnrollment().getParticipant().getLastName(), tbcParticipant.getText().substring(0, 31));
    }

    /**
     * Fill out one criteria depending on its type
     *
     * @param training - trade training data
     * @param type     - training type
     */
    public void performSearchCriteria(TradeTraining training, TRADE_TRAINING_CRITERIA type) {
        performSearchBasic(training, type);
        performSearchSecondary(training, type);
        info("Click the [Search] button");
        clickAndWait(BaseButton.SEARCH);
    }

    public enum TRADE_TRAINING_CRITERIA {
        PARTICIPANT, PETITION, COURSE, SERVICE_CENTER, TRAINING_STATUS, TRAINING_TYPE,
        FIRSTDAY_FROM, FIRSTDAY_TO, COMPLETIONDAY_FROM, COMPLETIONDAY_TO
    }

    /**
     * Perform search for trade training using main fields
     *
     * @param training - trade training data
     * @param type     - search type
     */
    private void performSearchBasic(TradeTraining training, TRADE_TRAINING_CRITERIA type) {
        switch (type) {
            case PARTICIPANT:
                selectParticipant(training.getTradeEnrollment().getParticipant());
                break;
            case PETITION:
                selectInactivePetition(training.getTradeEnrollment().getPetition());
                break;
            case COURSE:
                selectCourse(expectedCourse);
                break;
            case SERVICE_CENTER:
                clickAndWait(BaseButton.SERVICE_CENTER_LOOK_UP);
                ServiceSearchForm serviceSearchForm = new ServiceSearchForm(Constants.PARTICIPANT_SS);
                serviceSearchForm.inputServiceCenterName(service);
                clickAndWait(BaseButton.SEARCH);
                serviceSearchForm.selectEmployerService();
                clickAndWait(BaseButton.RETURN);
                break;
            case TRAINING_STATUS:
                cmbTrainingResult.select(training.getResult());
                break;
            case TRAINING_TYPE:
                cmbTrainingType.select(training.getType());
                break;
            default:
                break;
        }
    }

    /**
     * Perform search for trade training using additional fields
     *
     * @param training - trade training
     * @param type     - search type
     */
    private void performSearchSecondary(TradeTraining training, TRADE_TRAINING_CRITERIA type) {
        switch (type) {
            case FIRSTDAY_FROM:
                tbFirstDayFrom.type(training.getFirstDayOfTraining());
                break;
            case FIRSTDAY_TO:
                tbFirstDayTo.type(training.getFirstDayOfTraining());
                break;
            case COMPLETIONDAY_FROM:
                tbLastDayFrom.type(training.getCompletionDate());
                break;
            case COMPLETIONDAY_TO:
                tbLastDayTo.type(training.getCompletionDate());
                break;
            default:
                break;
        }
    }

    /**
     * Method for validating all data in the displayed record on the Search Result Page.
     *
     * @param training - trade training
     */
    public void validateEntireRecord(TradeTraining training) {
        validateSearchResult(training);
        CustomAssertion.softAssertEquals(tbcFirstDay.getText(), training.getFirstDayOfTraining(), "Incorrect first day of training");
        CustomAssertion.softAssertEquals(tbcCompleteDay.getText(), training.getCompletionDate(), "Incorrect complete day of training");
        CustomAssertion.softAssertEquals(tbcTrainingType.getText(), training.getType(), "Incorrect training type");
        CustomAssertion.softAssertEquals(tbcTrainingStatus.getText(), training.getResult(), "Incorrect training status");
    }
}
