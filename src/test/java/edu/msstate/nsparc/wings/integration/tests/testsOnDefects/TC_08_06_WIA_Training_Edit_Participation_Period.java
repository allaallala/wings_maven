package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10707")
public class TC_08_06_WIA_Training_Edit_Participation_Period extends BaseWingsTest {

    private String [] trainingDetails = {
            Constants.COMPLETED,
            CommonFunctions.getCurrentDate(),
    };

    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        TrainingSteps.createWiaTrainingWithUnregisteredParticipant(participant);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();

        logStep("Search for Training and open it");
        wiaTrainingSearchForm.performSearchAndOpen(participant);

        logStep("Click on Edit button");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.clickButton(Buttons.Edit);

        logStep("Select Completed status ");
        WIATrainingEditForm wiaTrainingEditForm = new WIATrainingEditForm();
        wiaTrainingEditForm.editWIATrainingDetails(trainingDetails[0], trainingDetails[1]);

        logStep("Save Changes");
        wiaTrainingEditForm.clickButton(Buttons.Save);
        if (wiaTrainingEditForm.isPresent(BaseWingsForm.BaseButton.SAVE_CHANGES)) {
            wiaTrainingEditForm.clickButton(Buttons.Save);
        }

        logStep("Open Participant profile search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search and open participant record");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Check that Participation period is Tentative");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.expandParticipationPeriods();
        Assert.assertTrue(participantDetailsForm.getParticipationPeriodsText().contains("Tentative"));

        logStep("Navigate back to the WIA Training search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Training and open it");
        wiaTrainingSearchForm.performSearchAndOpen(participant);

        logStep("Click on Edit button");
        wiaTrainingDetailsForm.clickButton(Buttons.Edit);

        logStep("Change status to Please Select");
        wiaTrainingEditForm.selectResultTraining(Constants.IN_PROGRESS);

        logStep("Save Changes");
        wiaTrainingEditForm.clickButton(Buttons.Save);

        logStep("Open Participant profile search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search and open participant record");
        participantSearchForm.performSearchAndOpen(participant);

        logStep("Check that Participation period is not Tentative");
        participantDetailsForm.expandParticipationPeriods();
        Assert.assertTrue(participantDetailsForm.getParticipationPeriodsText().contains("Open Ended"));
    }
}
