package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Search for non-existing DPO and check, that search does not return any result
 * Created by a.vnuchko on 09.01.2017.
 */
@TestCase(id = "WINGS-11220")
public class TC_35_05_PDO_Nothing_Found extends TC_35_01_PDO_Search_Title {
    String notExistingTitle = "IfYouReReadingThisSmile";
    String notExistingId = "99999999999999999";
    public void main(){

        pdoPrecondition();

        logStep("Enter nonexistent PDO title  and search");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(notExistingTitle);

        logResult("Nothing found");
        opPage.checkSearchResult(false);

        logStep("Enter nonexistent numeric PDO ID and search");
        opPage.searchId(notExistingId);

        logResult("Nothing found");
        opPage.checkSearchResult(false);
    }
}
