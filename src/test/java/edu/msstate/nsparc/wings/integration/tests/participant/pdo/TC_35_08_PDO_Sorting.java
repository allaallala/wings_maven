package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check sorting by title, posted on the PDO form.
 * Created by a.vnuchko on 11.01.2017.
 */

@TestCase(id = "WINGS-11223")
public class TC_35_08_PDO_Sorting extends TC_35_01_PDO_Search_Title {

    public void main(){
        String title = generatePdoReturnTitle();
        pdoPrecondition();

        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        logResult("Sort Title. Search results are sorted properly");
        opPage.sortTitle(title);

        logResult("Sort Posted. Search results are sorted properly");
        opPage.sortPosted(title);
    }
}
