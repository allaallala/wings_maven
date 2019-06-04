package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Search for PDO by all available fields
 * Created by a.vnuchko on 11.01.2017.
 */

@TestCase(id = "WINGS-11219")
public class TC_35_04_PDO_Search_All_Fields extends TC_35_01_PDO_Search_Title {

    public void main() {
        int rowNumber = 1;
        String title = generatePdoReturnTitle();
        String providerId = AdvancedSqlFunctions.getPdoProviderCreatedDate(title)[2];

        pdoPrecondition();

        logStep("Search by provider name and id");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        String[] pdoData = opPage.getDataFromDB(title);
        opPage.searchIdTitleProvider(providerId, pdoData[0]);

        logResult("All open PDOs, matched the search criteria, are shown");
        opPage.validatePdoData(title, pdoData, rowNumber);

        logStep("Search by pdo title and id");
        opPage.searchIdTitleProvider(providerId, title);

        logResult("All open PDOs, matched the search criteria, are shown");
        opPage.validatePdoData(title, pdoData, rowNumber);
    }
}
