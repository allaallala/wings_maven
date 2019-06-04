package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**Create some individual employment plan, open its detailed page, edit IEP creation Date and save changes. Check, that IEP Edit screen is shown and prefilled
 * with correct data.
 * Created by a.vnuchko on 31.08.2015.
 */

@TestCase(id = "WINGS-11013")
public class TC_21_21_IEP_Edit_IEP_Creation_Date extends BaseWingsTest {

    public void main() {
        String days = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);

        info("Precondition: Create some Individual Employment Plan");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Click the [Edit] button");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit IEP Creation date: Use date no more than 90 days before it associated service enrollment ");
        IndividualEmploymentPlanEditForm editPage = new IndividualEmploymentPlanEditForm();
        editPage.editSomeData(days);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("The Individual Employment Plan Edit Screen is shown. All the fields are pre-filled with the actual correct data");
        plan.setCreationDate(days);
        detailsPage.validateInformation(plan);

    }
}
