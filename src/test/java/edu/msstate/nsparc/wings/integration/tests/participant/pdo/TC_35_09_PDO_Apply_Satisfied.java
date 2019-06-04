package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Apply for PDO that correspond to given participant  and checked, that is successfully applied
 * Created by a.vnuchko on 11.01.2017.
 */

@TestCase(id = "WINGS-11224")
public class TC_35_09_PDO_Apply_Satisfied extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();

        Participant participant = pdoPrecondition();

        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.applyForPdo(title);

        logStep("Enter correct initials on Disclaimer page and click Continue");
        opPage.inputInitialsContinue(participant);

        logStep("Enter correct initials on Confirmation page and click Confirm");
        opPage.inputInitialsConfirm(participant);

        logResult("Participant successfully applied for PDO. PDO is visually marked as applied on PDO participantSSDetails.");
        opPage.checkApplyTextReturn();
    }
}
