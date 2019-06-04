package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Search for PDO by provider name and check, that search result is correct.
 * Created by a.vnuchko on 06.01.2017.
 */
@TestCase(id = "WINGS-11217")
public class TC_35_02_PDO_Search_Provider_Name extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();
        String providerName = AdvancedSqlFunctions.getPdoProviderCreatedDate(title)[0];

        pdoPrecondition();

        logStep("Enter the PDO provider name and Search");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(providerName);

        logResult("All open PDOs, matched the search criteria, are shown");
        String[] pdoData = opPage.getDataFromDB(title);
        opPage.validateRecordsData(title, pdoData);
    }
}
