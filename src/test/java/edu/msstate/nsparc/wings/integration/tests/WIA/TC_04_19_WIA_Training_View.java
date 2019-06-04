package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10608")
public class TC_04_19_WIA_Training_View extends BaseWingsTest {


    public void main () {
        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);

        logStep("Click search button");
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.clickButton(Buttons.Search);

        logStep("Open participant participantSSDetails");

        String [] trainingDetails = wiaTrainingSearchForm.getWIATrainingDetails();
        wiaTrainingSearchForm.clickFirstParticipant();

        logStep("Check training participantSSDetails");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        assertTrue("Assert WIA Training Details Failed",
                wiaTrainingDetailsForm.validateWIATrainingDetails(trainingDetails[0], trainingDetails[1], trainingDetails[2], trainingDetails[3]));
    }
}
