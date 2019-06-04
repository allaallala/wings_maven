package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditExpenseForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10783")

public class TC_12_19_Relocation_Expense_Lump_Amount_Security extends BaseWingsTest {

    public void main() {
        // Create Relocation with Expense
        Relocation relocation = new Relocation(Constants.TRUE);
        // For adding expenses, the Relocation should be with Approved status
        RelocationCreationSteps.createRelocationAndEditStatus(relocation);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click [Add] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.addExpence();

        // Check on the Add Expense form:
        logStep("Check that Lump Sum Amount field is not displayed");
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
        relocationAddExpenseForm.tbcLumpSumAmountPaid.assertIsNotPresent();

        logStep("Set the 'Include Lump Sum' checkbox and check that Lump Sum Amount field is appeared");
        relocationAddExpenseForm.chkIncludeLumpSum.click();
        relocationAddExpenseForm.tbcLumpSumAmountPaid.isPresent();

        logStep("Click the 'Override' checkbox and check the 'Include Lump Sum' is available to edit");
        relocationAddExpenseForm.checkIncludeLumpSumAvailableEdit();

        logStep("Uncheck 'Include Lump Sum', fill out required fields, add expense and open it for editing");
        relocationAddExpenseForm.chkIncludeLumpSum.click();
        relocationAddExpenseForm.fillExpenseFormAndSave(relocationExpense);
        relocationDetailsForm.clickLastAddedExpence();
        relocationDetailsForm.editExpence();

        // Check on the Edit Expense form:
        logStep("Check that Lump Sum Amount field is not displayed");
        RelocationEditExpenseForm relocationEditExpenseForm = new RelocationEditExpenseForm();
        relocationEditExpenseForm.tbcLumpSumAmountPaid.assertIsNotPresent();

        logStep("Set the 'Include Lump Sum' checkbox and check that Lump Sum Amount field is appeared");
        relocationEditExpenseForm.chkIncludeLumpSum.click();
        relocationEditExpenseForm.tbcLumpSumAmountPaid.isPresent();

        logStep("Click the 'Override' checkbox and check the 'Include Lump Sum' is available to edit");
        relocationEditExpenseForm.checkIncludeLumpSumAvailableEdit();
    }
}
