package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10777")
public class TC_12_14_Relocation_Cancel_Adding_Expense extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        // For adding expenses, the Relocation should be with Approved status
        RelocationCreationSteps.createRelocationAndEditStatus(relocation);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click [Add] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.addExpence();

        logStep("Fill out the required fields with any data");
        RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        relocationAddExpenseForm.fillOutStatusRelatedFields(relocationExpense);
        relocationAddExpenseForm.fillOutPayeeInfo(relocationExpense);

        logStep("Click the [Cancel] button");
        relocationAddExpenseForm.clickButton(Buttons.Cancel);

        logStep("Check that Job Relocation wasn't added");
        relocationDetailsForm.noSearchResults();
    }
}
