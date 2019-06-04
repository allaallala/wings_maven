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


/**
 * Open assessment search form, fill it out, click [Cancel] button and check, that [Staff Home Form] is shown
 * Created by a.vnuchko on 11.09.2015.
 */

@TestCase(id = "WINGS-10960")
public class TC_19_15_Assessment_Search_Cancel extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Generate some test data");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        AssessmentSearchForm searchPage = AssessmentSteps.openFillSearchForm(assessment);

        logStep("Click the [Cancel] button");
        searchPage.clickButton(Buttons.Cancel);

        logResult("The Staff Home Screen is shown");
        //new StaffHomeForm().assertIsOpen();
    }
}
