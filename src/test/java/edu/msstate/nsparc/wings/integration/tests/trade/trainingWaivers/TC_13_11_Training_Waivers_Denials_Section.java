package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.denials.AppealDenialsCreationForm;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.forms.denials.EditDenialForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10816")
public class TC_13_11_Training_Waivers_Denials_Section extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedIneligibleTrainingWaiver();

        logStep("Log in as Admin and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.ADMIN);

        logStep("Click 'Appeal' and check appeal form is opened");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.clickAppeal();
        AppealDenialsCreationForm appealDenialsCreationForm = new AppealDenialsCreationForm();
        //appealDenialsCreationForm.assertIsOpen();
        appealDenialsCreationForm.clickButton(Buttons.Cancel);
        appealDenialsCreationForm.areYouSure(Popup.Yes);

        logStep("Click 'Edit Denial' and check edit denial form is opened");
        denialSectionForm.editDenial();
        EditDenialForm editDenialForm = new EditDenialForm();
        //editDenialForm.assertIsOpen();
        editDenialForm.clickButton(Buttons.Cancel);
        editDenialForm.areYouSure(Popup.Yes);

        logStep("Check 'Print Letter' button is available");
        denialSectionForm.print();
    }
}
