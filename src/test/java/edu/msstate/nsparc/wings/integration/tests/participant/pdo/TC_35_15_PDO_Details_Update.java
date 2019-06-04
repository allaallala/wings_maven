package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participantSSDetails page of the PDO (that satisfied to a chose participant), click [Update Education Profile] and check that correct page is opened.
 * Created by a.vnuchko on 13.01.2017.
 */

@TestCase(id = "WINGS-11228")
public class TC_35_15_PDO_Details_Update extends TC_35_01_PDO_Search_Title {

    public void main(){
        String title = generatePdoReturnTitle();

        Participant participant = pdoPrecondition();

        logStep("Open participantSSDetails of any PDO");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.clickTitle(title);

        logStep("Click the Update Education Profile link in the");
        opPage.clickUpdateProfileLink();

        logResult("Profile page is opened");
        new ParticipantHomePage(participant);
    }
}
