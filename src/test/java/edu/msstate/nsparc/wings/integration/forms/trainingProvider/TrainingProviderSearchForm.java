package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import framework.elements.Link;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Training Providers -> Search
 */
public class TrainingProviderSearchForm extends TrainingProviderBaseForm {

    private TableCell tbcProviderCode = new TableCell(By.xpath("//table[@id='results-table']//tbody//tr//td[3]"), "Provider Code");
    private RadioButton rbWiaNo = new RadioButton("id=isWiaApproved2", "Wia_approved_no");
    private RadioButton rbTradeYes = new RadioButton("id=isTradeApproved1", "Trade_approved_yes");
    private RadioButton rbFirstSearchResult = new RadioButton(By.xpath("//table[@id='results-table']//input"), "First search result");
    private Link lnkSortArrow = new Link("css=table#results-table > thead a", "Sort arrow");

    /**
     * Default constructor
     */
    public TrainingProviderSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Search')]"), "Training Provider Search");
    }

    /**
     * This method is used for searching and selecting Training Provider by its name
     *
     * @param providerName desired Training Provider name
     */
    public void searchAndReturnTrainingProvider(String providerName) {
        inputProviderName(providerName);
        clickAndWait(BaseButton.SEARCH);
        rbFirstSearchResult.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * This method is used for getting Provider name from search results
     *
     * @return String with Training Provider Name
     */
    public String getTrainingProviderName() {
        String text = new SearchResultsForm().getFirstSearchResultLinkText();
        return text.split(" ")[0];
    }

    /**
     * This method is used for Training Provider searching
     *
     * @param trainingProvider Data that will be used for searching
     */
    public void performSearch(TrainingProvider trainingProvider) {
        inputProviderName(trainingProvider.getName());
        if (trainingProvider.isWiaProvider()) {
            inputProviderCode(trainingProvider.getCode());
        }
        inputFein(trainingProvider.getFein());
        inputVendorNumber(trainingProvider.getDfa());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for searching only by training providers name
     *
     * @param providerName - name of the training provider
     */
    public void performSearch(String providerName) {
        inputProviderName(providerName);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for Training Provider from lookup
     *
     * @param trainingProvider Training Provider participantSSDetails for search
     */
    public void performSearchAndSelect(TrainingProvider trainingProvider) {
        performSearch(trainingProvider);
        rbFirstSearchResult.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * Searching using only one criteria.
     *
     * @param tr   - Training Provider
     * @param type - search criteria
     */
    public void performSearchCriteria(TrainingProvider tr, TrainingProvider.TrainingProviderCriteria type) {
        switch (type) {
            case TRAINING_PROVIDER_CODE:
                inputProviderName(tr.getName());
                break;
            case FEIN_LENGTH:
                inputFein(tr.getFein());
                break;
            case DFA_NUMBER:
                inputVendorNumber(tr.getDfa());
                break;
            case WIA_APPROVED_NO:
                rbWiaNo.click();
                break;
            case TRADE_APPROVED_YES:
                rbTradeYes.click();
                break;
            default:
                break;
        }
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Validate training provider data in the Search Result Form
     *
     * @param tr training provider
     */
    public void validateSearchResult(TrainingProvider tr) {
        String trainingProviderName, dfa;
        lnkSortArrow.clickAndWait();
        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 1; i < Constants.RECORDS_ON_PAGE; i++) {
            trainingProviderName = resultsForm.getRecordText(i, "2");
            dfa = resultsForm.getRecordText(i, "5");
            if (trainingProviderName.contains(tr.getName()) && dfa.contains(tr.getDfa())) {
                info("The requested record was found");
                break;
            } else {
                fatal("The requested record wasn't found");
            }
        }
    }

    /**
     * Get training provider code text
     *
     * @return provider code text
     */
    public String getProviderCodeText() {
        return tbcProviderCode.getText();
    }
}
