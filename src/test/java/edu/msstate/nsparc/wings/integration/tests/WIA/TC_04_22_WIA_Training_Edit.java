package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10609")
public class TC_04_22_WIA_Training_Edit extends BaseWingsTest {

    private String lastDayTraining = CommonFunctions.getCurrentDate();


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        TrainingSteps.createWiaTrainingWithUnregisteredParticipant(participant);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);

        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();

        logStep("Search for Training and open it");
        wiaTrainingSearchForm.performSearch(participant);
        wiaTrainingSearchForm.clickFirstParticipant();

        logStep("Click on Edit button");
        WIATrainingDetailsForm detailsForm = new WIATrainingDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit WIA Training");
        WIATrainingEditForm wiaTrainingEditForm = new WIATrainingEditForm();
        wiaTrainingEditForm.editWIATrainingDetails(Constants.COMPLETED, lastDayTraining);

        wiaTrainingEditForm.clickButton(Buttons.Save);
        if (wiaTrainingEditForm.isPresent(BaseWingsForm.BaseButton.SAVE_CHANGES)) {
            wiaTrainingEditForm.clickButton(Buttons.Save);
        }

        logStep("Validate, that changes are saved");
        detailsForm.validateEditedWIATrainingDetails(Constants.COMPLETED, lastDayTraining);
    }

    /**
     * Edit WIA training
     * @param trainingResult - training result
     * @param lastDayTraining - last day of the training
     */
    public void editWIATraining (String trainingResult, String lastDayTraining) {
        logStep("Click [Edit] button");
        WIATrainingDetailsForm detailsForm = new WIATrainingDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit some data and click [Save Changes]");
        WIATrainingEditForm editForm = new WIATrainingEditForm();
        editForm.editWIATrainingDetails(trainingResult, lastDayTraining);
        editForm.clickButton(Buttons.Save);
        editForm.passParticipationRecalculationPage();

        logStep("Validate edited data");
        detailsForm = new WIATrainingDetailsForm();
        detailsForm.validateEditedWIATrainingDetails(trainingResult, lastDayTraining);
    }
}
