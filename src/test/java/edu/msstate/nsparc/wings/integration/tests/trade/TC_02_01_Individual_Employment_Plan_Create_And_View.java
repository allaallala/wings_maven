package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.PreviewAssessment;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10445")
public class TC_02_01_Individual_Employment_Plan_Create_And_View extends BaseWingsTest {

    public void main() {


        info("Creating Participant for IEP");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), Constants.FALSE);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        IndividualEmploymentPlan iep = new IndividualEmploymentPlan(participant);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        logStep("Fill out the creation form");
        IndividualEmploymentPlanCreationForm creationForm = new IndividualEmploymentPlanCreationForm();
        creationForm.fillOutCreationForm(new User(Roles.STAFF), iep);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        IndividualEmploymentPlanDetailsForm detailsForm = new IndividualEmploymentPlanDetailsForm();
        detailsForm.validateInformation(iep);

        logStep("Open Assessment pop-up window");
        detailsForm.previewAssessment();

        logStep("Make sure Assessment preview is displayed");
        PreviewAssessment previewForm = new PreviewAssessment();
        previewForm.validatePreviousJobInformation(iep.getAssessments().get(0));

        logStep("Close the preview");
        PreviewAssessment.BTN_CLOSE_PREVIEW.click();
        PreviewAssessment.BTN_CLOSE_PREVIEW.waitForNotVisible();
    }
}
