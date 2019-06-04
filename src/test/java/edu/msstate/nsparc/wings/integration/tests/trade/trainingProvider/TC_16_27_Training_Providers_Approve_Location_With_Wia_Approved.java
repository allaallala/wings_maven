package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this case, create some training_provider, add location for it, approve it wia and check, that is displayed in the appropriate drop-down
 * Created by a.vnuchko on 30.06.2015.
 */

@TestCase(id = "WINGS-10901")
public class TC_16_27_Training_Providers_Approve_Location_With_Wia_Approved extends BaseWingsTest {

    public void main() {

        info("Precondition: Create some Training Providers.");
        Participant partip = new Participant();
        TrainingProvider provider = new TrainingProvider();
        TrainingSteps.createTraining(provider, Roles.PCADMIN);
        ParticipantCreationSteps.createParticipantDriver(partip, Constants.TRUE, Constants.FALSE);

        logStep("Go to Participants -> WIA -> WIA Training");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);

        logStep("Select valid participant");
        WIATrainingCreateForm wiaCreationPage = new WIATrainingCreateForm();
        wiaCreationPage.selectParticipant(partip);

        logStep("Select Project Code, appropriate to the created above Training Provider");
        wiaCreationPage.selectProjectCode(provider.getName());

        logResult("Approved location is present on the page");
        wiaCreationPage.validateProjectCodeInformation(provider);
    }

}
