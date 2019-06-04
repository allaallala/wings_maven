package edu.msstate.nsparc.wings.integration.forms.everify;

import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;

/**
This form is opened via Participants -> Wagner-Peyser -> E-Verify -> Search
 */
public class EverifySearchForm extends EverifyBaseForm {

    /**
     * Default constructor
     */
    public EverifySearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'E Verify Search')]"), "E-Verify Search");
    }

    /**
     * This method is used for checking search result
     * @param caseNumber Case number that should be presented in results
     */
    private void checkSearchResult(String caseNumber) {
        info("Checking, that E-Verify was found");
        assertEquals("E-Verify Case Number assert fail", caseNumber, new SearchResultsForm().getFirstSearchResultLinkText());
        info("Search result: OK");
    }

    /**
     * This method is used for searching E-Verify records for participant
     * @param participant Participant with E-Verify
     */
    public void performSearch(Participant participant) {
        selectParticipant(participant);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for searching E-Verify records for participant and verifying search results
     * @param participant Participant with E-Verify
     * @param caseNumber Case number that should be found
     */
    public void performSearchAndCheckResults(Participant participant, String caseNumber) {
        performSearch(participant);
        checkSearchResult(caseNumber);
    }

    /**
     * Searching for the record
     * @param participant Participant with E-Verify
     */
    public void performSearchAndOpen(Participant participant) {
        performSearch(participant);
        openFirstSearchResult();
    }
}
