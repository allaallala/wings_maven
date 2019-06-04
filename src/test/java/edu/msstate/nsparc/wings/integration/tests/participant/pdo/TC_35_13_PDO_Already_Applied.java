package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Some PDO are already applied by the user, try to apply on already applied PDO.
 * Created by a.vnuchko on 13.01.2017.
 */

@TestCase(id = "WINGS-11226")
public class TC_35_13_PDO_Already_Applied extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();
        String title2 = generatePdoReturnTitle();
        Participant participant = pdoPrecondition();

        info("Apply for several PDO's");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.applyForPdo(title);
        opPage.inputInitialsContinue(participant);
        opPage.inputInitialsConfirm(participant);
        opPage.checkApplyTextReturn();

        opPage.searchTitleProvider(title2);
        opPage.applyForPdo(title2);
        opPage.inputInitialsContinue(participant);
        opPage.inputInitialsConfirm(participant);
        opPage.checkApplyTextReturn();

        opPage.searchTitleProvider(title2);
        opPage.clickTitle(title2);
        opPage.applyNotPresent();
    }
}
