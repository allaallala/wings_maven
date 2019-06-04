package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10773")
public class TC_12_10_Relocation_Cancel_Edit extends BaseWingsTest {

    private static final String NEW_EMPLOYER_NAME = CommonFunctions.getRandomAlphanumericalCode(5);

    public void main() {

        Relocation relocation = new Relocation();
        RelocationCreationSteps.createRelocation(relocation, Roles.ADMIN);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRelocation();

        logStep("Edit any field");
        RelocationEditForm relocationEditForm = new RelocationEditForm();
        relocationEditForm.inputEmployerName(NEW_EMPLOYER_NAME);

        logStep("Click Cancel button");
        relocationEditForm.clickButton(Buttons.Cancel);
        relocationEditForm.areYouSure(Popup.Yes);
        logStep("Validate that changes were not saved");
        relocationDetailsForm.validateInformation(relocation);
    }
}
