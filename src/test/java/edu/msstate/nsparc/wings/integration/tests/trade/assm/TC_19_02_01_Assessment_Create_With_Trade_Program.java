package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 17.06.2016.
 */

@TestCase(id = "WINGS-10945")
public class TC_19_02_01_Assessment_Create_With_Trade_Program extends TC_19_01_Assessment_Create_Cancel {

    public void main(){
        info("Precondition: create new Trade Enrollment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        AssessmentSteps.openFillCreationForm(assessment);

        logStep("Click the [Create] button");
        AssessmentCreationForm creationPage = new AssessmentCreationForm();
        creationPage.clickButton(Buttons.Create);

        logResult("The Assessment Detail Screen is shown. A new Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
