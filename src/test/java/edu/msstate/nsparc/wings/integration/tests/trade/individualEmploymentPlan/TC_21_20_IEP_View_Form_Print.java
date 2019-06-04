package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some individual employment plan, open its participantSSDetails page, choose [Print] button, and check, that print page is displayed.
 * Created by a.vnuchko on 31.08.2015.
 */

@TestCase(id = "WINGS-11012")
public class TC_21_20_IEP_View_Form_Print extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Press [Print] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.openPrintForm();

        logResult("Printable version of the page is displayed");
        detailsPage.validatePrintContent(plan);

    }
}
