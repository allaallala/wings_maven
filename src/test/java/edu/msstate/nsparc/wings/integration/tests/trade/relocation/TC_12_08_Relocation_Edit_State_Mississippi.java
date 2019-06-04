package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10770")
public class TC_12_08_Relocation_Edit_State_Mississippi extends BaseWingsTest {

    private static final String ALABAMA = "Alabama";
    private static final String MISSISSIPPI = "Mississippi";

    public void main() {

        Relocation relocation = new Relocation();
        relocation.getNewAddress().setState(ALABAMA);
        relocation.getOldAddress().setState(ALABAMA);
        relocation.getLocationAddress().setState(ALABAMA);
        RelocationCreationSteps.createRelocation(relocation, Roles.ADMIN);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRelocation();

        logStep("Select a state = Mississippi for Employer Information, Old Address, New Address");
        RelocationEditForm relocationEditForm = new RelocationEditForm();
        relocationEditForm.selectStateCounty(MISSISSIPPI);
    }
}
