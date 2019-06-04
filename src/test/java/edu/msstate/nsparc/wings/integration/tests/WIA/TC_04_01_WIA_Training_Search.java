package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10460")
public class TC_04_01_WIA_Training_Search extends BaseWingsTest {


    public void main() {
        TC_04_10_WIA_Training_Create createWIATraining = new TC_04_10_WIA_Training_Create();
        createWIATraining.main();

        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        String [] trainingDetails = wiaTrainingDetailsForm.getWIATrainingDetails();

        logStep("Open WIA training search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Open participant search form");
        new WIATrainingSearchForm().clickAndWait(BaseWingsForm.BaseButton.PARTICIPANT_LOOK_UP);

        logStep("Find participant");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(trainingDetails[2], "");

        logStep("Click [Search] button on WIA training search form");
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.fillWIATrainingDetails(trainingDetails[1], trainingDetails[0]);
        wiaTrainingSearchForm.clickButton(Buttons.Search);

        logStep("Check result found");
        wiaTrainingSearchForm = new WIATrainingSearchForm();
        assertTrue("Assert WIA Training Searched Results Failed",
                wiaTrainingSearchForm.validateSearchedResults(trainingDetails[1], trainingDetails[0], trainingDetails[2]));
    }

}
