package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10778")
public class TC_12_15_Relocation_Edit_Expense extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, relocationExpense);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Select expense and click [Edit] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.clickLastAddedExpence();

        logStep("Edit some fields and save expense");
        RelocationExpense newExpense = new RelocationExpense(Constants.FALSE);
        relocationDetailsForm.editExistedExpense(newExpense);

        logStep("Validate changes were saved");
        relocationDetailsForm.validateLastAddedExpenseData(newExpense);
    }
}
