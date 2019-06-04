package edu.msstate.nsparc.wings.integration.tests.participant.pdo;

import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ProfessionalDevelopmentOpportunityForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Open participantSSDetails of the PDO and check, that 'Comparison Details' section is displayed correctly.
 * Created by a.vnuchko on 13.01.2017.
 */

@TestCase(id = "WINGS-11227")
public class TC_35_14_PDO_Details_Comparison extends TC_35_01_PDO_Search_Title {

    public void main() {
        String title = generatePdoReturnTitle();
        String textToCompare = "This opportunity requires a Bachelor's Degree and you indicated Some School";

        pdoPrecondition();

        logStep("Open participantSSDetails of any PDO");
        ProfessionalDevelopmentOpportunityForm opPage = new ProfessionalDevelopmentOpportunityForm();
        opPage.searchTitleProvider(title);
        opPage.clickTitle(title);

        logResult("In the left sightbar there is 'Comparison Details' that has comparison based on Education. "
                + "Comparison datails is calculated correctly");
        opPage.checkComparisonDetails();
        CustomAssertion.softTrue("Incorrect text in the Education field", opPage.getEduText().contains(
                textToCompare));
    }
}
