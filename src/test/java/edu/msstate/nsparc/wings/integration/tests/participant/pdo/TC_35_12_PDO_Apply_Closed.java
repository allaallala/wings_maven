package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create PDO with Application DeadLine in the past. Check, that it's not possible to apply for it.
 * Created by a.vnuchko on 13.01.2017.
 */

@TestCase(id = "WINGS-11225")
public class TC_35_12_PDO_Apply_Closed extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = DEFAULT_TITLE + CommonFunctions.getTimestamp();
        AdvancedSqlFunctions.insertPdo(title, CommonFunctions.getCurrentDateDbFormat(), CommonFunctions.getCurrentDateDbFormat());
        pdoPrecondition();

        logStep("Click on name of any PDO");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.clickTitle(title);
        opPage.unableApply();
    }
}
