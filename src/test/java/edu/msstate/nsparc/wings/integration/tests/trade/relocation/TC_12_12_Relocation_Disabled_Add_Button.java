package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10775")
public class TC_12_12_Relocation_Disabled_Add_Button extends BaseWingsTest {

    private static final String EMPTY_RELOCATION_DATE = "";
    private static final String NEW_RELOCATION_DATE = CommonFunctions.getCurrentDate();
    private static final String DENIED_REASON = CommonFunctions.getRandomAlphanumericalCode(5);
    private static final String STATUS_DETERMINATION_DATE = CommonFunctions.getCurrentDate();

    /**
     * Common steps for this test
     * @param relocation Relocation participantSSDetails
     */
    private void editRelocationData(final Relocation relocation) {
        relocation.setReasonDenied(DENIED_REASON);
        relocation.setStatusDeterminationDate(STATUS_DETERMINATION_DATE);

        //common steps of editing the relocation
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRelocation();
        RelocationEditForm relocationEditForm = new RelocationEditForm();
        relocationEditForm.editRelocationDateAndStatusAndSave(relocation);
    }


    public void main() {
        Relocation relocation = new Relocation();
        relocation.setRelocationDate(EMPTY_RELOCATION_DATE);
        RelocationCreationSteps.createRelocation(relocation, Roles.ADMIN);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        checkAddDisabled(relocationDetailsForm);

        logStep("Edit Relocation: add Relocation date and change status to Approve");
        relocation.setRelocationDate(NEW_RELOCATION_DATE);
        relocation.setApproved(Constants.TRUE);
        editRelocationData(relocation);

        logStep("Check that Add button is enabled");
        relocationDetailsForm.checkAddButtonDisabled(Constants.FALSE);

        logStep("Edit Relocation: remove Relocation date");
        relocation.setRelocationDate(EMPTY_RELOCATION_DATE);
        editRelocationData(relocation);

        checkAddDisabled(relocationDetailsForm);

        logStep("Edit Relocation: add Relocation date and change status to Denied");
        relocation.setRelocationDate(NEW_RELOCATION_DATE);
        relocation.setApproved(Constants.FALSE);
        relocation.setReasonDenied(DENIED_REASON);
        editRelocationData(relocation);

        checkAddDisabled(relocationDetailsForm);
    }

    /**
     * Check, that add button is inactive.
     * @param relocationDetailsForm - relocation participantSSDetails form.
     */
    private void checkAddDisabled(RelocationDetailsForm relocationDetailsForm){
        logStep("Check that Add button is inactive");
        relocationDetailsForm.checkAddButtonDisabled(Constants.TRUE);
    }
}
