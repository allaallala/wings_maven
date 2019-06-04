package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open the PDO participantSSDetails page, click [Return to Search] and check, that on the Search page correct data is being displayed.
 * Created by a.vnuchko on 13.01.2017.
 */

@TestCase(id = "WINGS-11229")
public class TC_35_16_PDO_Details_Return_Search extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();

        pdoPrecondition();

        logStep("Open participantSSDetails of any PDO");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.clickTitle(title);

        logStep("Click [Return to Search] button");
        opPage.clickReturnSearch();

        logResult("PDO Search screen with previously entered criteria and shown results is displayed");
        opPage.validateDataTitleField(title);
        String[] pdoData = opPage.getDataFromDB(title);
        opPage.validatePdoData(title, pdoData, rowNumber);
    }
}
