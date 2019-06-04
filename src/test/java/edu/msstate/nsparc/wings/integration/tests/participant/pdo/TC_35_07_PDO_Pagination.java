package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check pagination in the search results
 * Created by a.vnuchko on 11.01.2017.
 */

@TestCase(id = "WINGS-11222")
public class TC_35_07_PDO_Pagination extends TC_35_01_PDO_Search_Title {

    public void main(){
        String title = generatePdoReturnTitle();
        pdoPrecondition();

        info("Check pagination on this page");
        logResult("Proper PDOs are shown on the page");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.checkPagination(title);
    }
}
