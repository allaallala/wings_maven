package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create IEP with correct creation date, check that IEP displayed correctly.
 * Created by a.vnuchko on 20.08.2015.
 */

@TestCase(id = "WINGS-10993")
public class TC_21_01_Individual_Employment_Plan_Create_With_Correct_Date extends BaseWingsTest {

    public void main() {
        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Constants.TRUE, Constants.FALSE);

        IndividualEmploymentPlanCreationForm creationPage = IEPSteps.openFillOutCreationForm(new User(Roles.STAFF), plan);

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult("The Individual Employment Plan Detail Screen is shown. A new Individual Employment Plan was created and contains the same data you have entered");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.validateInformation(plan);
    }
}
