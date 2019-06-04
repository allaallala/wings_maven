package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.tests.participant.editProfile.TC_31_01_EP_General_Standard_View;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Search for PDO by title and check, that search result is correct.
 * Created by a.vnuchko on 03.01.2017.
 */

@TestCase(id = "WINGS-11216")
public class TC_35_01_PDO_Search_Title extends TC_31_01_EP_General_Standard_View {
    protected static final String DEFAULT_TITLE = "Cantante";
    int rowNumber = 1;

    public void main() {
        String title = generatePdoReturnTitle();

        pdoPrecondition();

        logStep("Enter the PDO title and Search");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);

        logResult("All open PDOs, matched the search criteria, are shown");
        String[] pdoData = opPage.getDataFromDB(title);
        opPage.validatePdoData(title, pdoData, rowNumber);
    }

    /**
     * Create new participant and open the PDO form
     */
    protected Participant pdoPrecondition() {
        Participant participant = precondition();

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.openPdo();

        return participant;
    }

    /**
     * Insert PDO into the database and return its title
     * @return PDO title
     */
    protected String generatePdoReturnTitle() {
        String title = DEFAULT_TITLE + CommonFunctions.getTimestamp();
        AdvancedSqlFunctions.insertPdo(title, CommonFunctions.getCurrentDateDbFormat(), CommonFunctions.getFutureDateDbFormat(Constants.DAYS_MONTH));
        return title;
    }
}
