package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10774")
public class TC_12_11_Relocation_Add_Expenses extends BaseWingsTest {

    public void main() {

        Relocation relocation = new Relocation(true);
        // For adding expenses, the Relocation should be with Approved status
        RelocationCreationSteps.createRelocationAndEditStatus(relocation);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click [Add] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.addExpence();

        logStep("Fill out the required fields with valid data");
        RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        relocationAddExpenseForm.fillExpenseFormAndSave(relocationExpense);

        logStep("Validate displayed Expense data");
        relocationDetailsForm.validateLastAddedExpenseData(relocationExpense);
    }
}
