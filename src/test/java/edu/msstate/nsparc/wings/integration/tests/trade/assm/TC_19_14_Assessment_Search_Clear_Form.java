package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Open assessment search form, fill it out, clear form and check, that all entries are cleared.
 * Created by a.vnuchko on 04.09.2015.
 */

@TestCase(id = "WINGS-10959")
public class TC_19_14_Assessment_Search_Clear_Form extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Generate some test data");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        AssessmentSearchForm searchPage = AssessmentSteps.openFillSearchForm(assessment);

        logStep("Click the [Clear Form] button");
        searchPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        CustomAssertion.softTrue("Date administred value is not cleared", Constants.EMPTY.equals(searchPage.getDateAdministeredValue()));
        CustomAssertion.softTrue("Selected option is not cleared", Constants.EMPTY.equals(searchPage.getSelectedOptionValue()));
        CustomAssertion.softNotTrue("First search result is still present", searchPage.isFirstSearchResultPresent());
    }
}
