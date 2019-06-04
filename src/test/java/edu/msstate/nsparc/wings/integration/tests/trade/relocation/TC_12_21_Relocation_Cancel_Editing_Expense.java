package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditExpenseForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10785")

public class TC_12_21_Relocation_Cancel_Editing_Expense extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense defaultRelocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, defaultRelocationExpense);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Select expense and click [Edit] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.clickLastAddedExpence();
        relocationDetailsForm.editExpence();

        logStep("Edit some fields and click 'Cancel'");
        RelocationExpense newExpense = new RelocationExpense(Constants.FALSE);
        RelocationEditExpenseForm relocationEditExpenseForm = new RelocationEditExpenseForm();
        relocationEditExpenseForm.fillOutPayeeInfo(newExpense);
        relocationEditExpenseForm.clickButton(Buttons.Cancel);

        logStep("Validate that the changes are not saved");
        relocationDetailsForm.validateLastAddedExpenseData(defaultRelocationExpense);
    }
}
