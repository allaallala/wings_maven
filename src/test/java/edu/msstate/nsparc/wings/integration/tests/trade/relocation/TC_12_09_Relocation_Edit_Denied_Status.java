package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10772")
public class TC_12_09_Relocation_Edit_Denied_Status extends BaseWingsTest {

    private static final String DENIED_REASON = CommonFunctions.getRandomAlphanumericalCode(5);
    String status;
    String expectedDenied = "Denied";
    String expectedApproved = "Approved";

    public void main() {
        createRelocationEditStatus(false);
    }

    /**
     * Create relocation without status and change it to denied
     * @param approved - if relocation is approved for trade/wia
     */
    protected void createRelocationEditStatus(Boolean approved){
        Relocation relocation = new Relocation();
        RelocationCreationSteps.createRelocation(relocation, Roles.ADMIN);
        relocation.setStatusDeterminationDate(CommonFunctions.getCurrentDate());
        if(!approved){
            relocation.setApproved(Constants.FALSE);
            relocation.setReasonDenied(DENIED_REASON);
            status = expectedDenied;
        }else{
            relocation.setApproved(Constants.TRUE);
            status = expectedApproved;
        }



        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRelocation();

        logStep("Select "+ status+" status'");
        RelocationEditForm relocationEditForm = new RelocationEditForm();
        relocationEditForm.editDetails(relocation);

        logStep("Save Changes");
        relocationEditForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        //relocationDetailsForm.assertIsOpen();
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        if(!approved){
            denialSectionForm.validateDenialsBaseInfo(relocation.getStatusDeterminationDate(), relocation.getReasonDenied());
        }
    }
}
