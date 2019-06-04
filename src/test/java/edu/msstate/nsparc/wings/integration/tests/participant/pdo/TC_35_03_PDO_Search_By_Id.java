package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Search for PDO by id and check, that search result is correct.
 * Created by a.vnuchko on 06.01.2017.
 */

@TestCase(id = "WINGS-11218")
public class TC_35_03_PDO_Search_By_Id extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();
        String providerId = AdvancedSqlFunctions.getPdoProviderCreatedDate(title)[2];

        pdoPrecondition();

        logStep("Enter correct value into PDO ID field and Search");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchId(providerId);

        logResult("All open PDOs, matched the search criteria, are shown");
        String[] pdoData = opPage.getDataFromDB(title);
        opPage.validatePdoData(title, pdoData, rowNumber);
    }
}
