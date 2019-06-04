package edu.msstate.nsparc.wings.integration.forms.assessment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Assessments -> Search
 */
public class AssessmentSearchForm extends AssessmentBaseForm {
    private Button btnOpenContextualPane = new Button("css=span[class='contextualInfoPaneExpand']", "Open Contextual Information Pane");
    private Button btnCollapseContextualPane = new Button(By.xpath("//span[@class='contextualInfoPaneContract']"), "Close contextual information Pane");

    private Span spnSelectedOption = new Span("//option[@selected='selected']","Selected option");

    /**
     * Constructor
     */
    public AssessmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Assessment Search')]"), "Assessment Search");
    }

    /**
     * Open contextual pane
     */
    public void openContextualPane() {
        btnOpenContextualPane.isPresent();
        btnOpenContextualPane.click();
        btnOpenContextualPane.waitForNotVisible();
    }

    /**
     * Close contextual pane
     */
    public void closeContextualPane() {
        btnCollapseContextualPane.isPresent();
        btnCollapseContextualPane.click();
        btnOpenContextualPane.assertIsPresent();
    }

    /**
     * This method is used for search for Assessment record
     * @param assessment Information that will be used for search
     */
    public void performSearch(Assessment assessment) {
        fillSearchForm(assessment);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Fill out search page
     * @param assessment - assessment
     */
    public void fillSearchForm(Assessment assessment) {
        selectParticipant(assessment.getParticipant());
        inputDateAdministered(assessment.getDateAdministered());
        if (assessment.getPreTest()) {
            selectPreTest();
        }
        selectProgram(assessment.getProgram());
    }

    /**
     * This method is used for searching and opening Assessment record
     * @param assessment Information that will be used for search
     */
    public void performSearchAndOpen(Assessment assessment) {
        performSearch(assessment);
        openFirstSearchResult();
    }

    /**
     * This method is used for filling out only one criteria on the search form.
     * @param assem - assessment
     * @param type - Participant, Date Administred, Program.
     */
    public void fillOneCriteria(Assessment assem, String type) {
        switch (type) {
            case "Participant":
                selectParticipant(assem.getParticipant());
                break;
            case "DateAdministred":
                selectParticipant(assem.getParticipant());
                inputDateAdministered(assem.getDateAdministered());
                break;
            case "Program":
                selectProgram(assem.getProgram());
                break;
            default:
                break;
        }
    }

    /**
     * Validate search result by only one criteria
     * @param assem - assessment.
     */
    public void validateOneCriteria(Assessment assem) {
        int flag = 0;
        if (TablePaginationForm.isPresent()) {
            new TablePaginationForm().openLastPage();
        }

        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 1; i < Constants.RECORDS_ON_PAGE; i++) {
            String participantName = resultsForm.getRecordText(i, "1");
            String typeValue = resultsForm.getRecordText(i, "2").trim();
            String dateAdministered = resultsForm.getRecordText(i, "5").trim();
            String functionalArea = resultsForm.getRecordText(i,"4").trim();
            if (participantName.contains(assem.getParticipant().getFirstName())) {
                CustomAssertion.softAssertContains(participantName,
                        assem.getParticipant().getFirstName() + " " + assem.getParticipant().getLastName(),
                        "Incorrect assesment participant name");
                CustomAssertion.softAssertEquals(typeValue, assem.getType().trim(), "Incorrect assessment type");
                CustomAssertion.softAssertEquals(dateAdministered, assem.getDateAdministered().trim(), "Incorrect assessment date administred");
                CustomAssertion.softAssertEquals(functionalArea, assem.getFunctionalArea(), "Incorrect assessment functional area");
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            fatal("The requested record was not found");
        }
        clickButton(Buttons.Clear);
    }


    public String getSelectedOptionValue() {
        return spnSelectedOption.getValue();
    }

    /**
     * Validate contextual information pane for wagner-peyser and trade programms on the Assessment Search form.
     * @param partip - participant
     * @param trEnrl - trade enrollment
     */
    public void validateWPTradeCip(Participant partip, TradeEnrollment trEnrl) {
        selectParticipant(partip);
        validateWagnerPeyserContextualInformationPane(partip);
        selectProgram(Constants.TRADE);
        clickButton(Buttons.Search);
        openFirstSearchResult();
        validateTradeContextualInformationPane(trEnrl);
    }
}
