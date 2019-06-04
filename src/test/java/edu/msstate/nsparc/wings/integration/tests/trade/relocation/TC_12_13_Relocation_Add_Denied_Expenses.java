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

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10776")
public class TC_12_13_Relocation_Add_Denied_Expenses extends BaseWingsTest {

    private static final String DEFAULT_TOTAL_AMOUNT = "$0.00";

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
        RelocationExpense relocationExpense = new RelocationExpense(Constants.FALSE);
        relocationAddExpenseForm.fillExpenseFormAndSave(relocationExpense);

        logStep("Check that Denied expense doesn't effect for payment");
        assertEquals("Denied Expense wasn't added!", relocationDetailsForm.getLastAddedExpenseStatusText(), relocationExpense.getStatusName());
        relocationDetailsForm.checkAmounts(DEFAULT_TOTAL_AMOUNT);
    }
}
