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

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10786")

public class TC_12_22_Relocation_Remove_Expense extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense relocationExpenseDenied = new RelocationExpense(Constants.FALSE);
        RelocationExpense relocationExpenseApproved = new RelocationExpense(Constants.TRUE);
        RelocationExpense[] expensesArray = {relocationExpenseDenied, relocationExpenseApproved};
        RelocationCreationSteps.createRelocationWithSeveralExpenses(relocation, expensesArray);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Select expense and delete it");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        // store deleted expense amount (will compare it with another expense):
        String deletedExpenseAmount = relocationDetailsForm.getLastAddedExpenseAmountText();
        relocationDetailsForm.clickLastAddedExpence();
        relocationDetailsForm.removeExpence();

        logStep("Validate that expense was deleted");
        assertFalse("Expense wasn't deleted!", relocationDetailsForm.getLastAddedExpenseAmountText().contains(deletedExpenseAmount));
    }
}
