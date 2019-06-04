package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Creating new individual employment plan: check, that buttons [Edit], [Remove] are inactive until corresponding radio button is not chosen.
 * Created by a.vnuchko on 24.08.2015.
 */

@TestCase(id = "WINGS-10998")
public class TC_21_06_IEP_Create_EditRemove_Security extends BaseWingsTest {

    public void main() {


        info("Create some data for the test");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        ParticipantCreationSteps.createParticipantDriver(plan.getParticipant(), Constants.TRUE, Constants.FALSE);

        IndividualEmploymentPlanCreationForm creationPage = IEPSteps.openFillOutCreationForm(new User(Roles.STAFF), plan);

        logStep("Unselect/Select an Assessment");
        info("Do not select assessment (radio button is inactive)");

        logResult("The buttons [Edit] and [Remove] are inactive");
        creationPage.checkEditRemoveDisabled();

        info("Select assessment (radio button is inactive)");
        creationPage.chooseFirstAssessment();

        logResult("The buttons [Edit] and [Remove] are active");
        creationPage.clickButton(Buttons.Edit);
        AssessmentEditForm editPage = new AssessmentEditForm();
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);
        creationPage.removeChosenAssessment("1");
        creationPage.checkFirstAssmNotPresent();
    }
}
