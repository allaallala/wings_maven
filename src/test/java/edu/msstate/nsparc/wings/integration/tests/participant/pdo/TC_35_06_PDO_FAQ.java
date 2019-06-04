package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open PDO page, and check that FAQ is displayed below the map and contains correct data.
 * Created by a.vnuchko on 11.01.2017.
 */

@TestCase(id = "WINGS-11221")
public class TC_35_06_PDO_FAQ extends TC_35_01_PDO_Search_Title {

    public void main() {
        pdoPrecondition();

        logResult("FAQ are displayed correctly under the map");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.checkFaqText();
    }
}
