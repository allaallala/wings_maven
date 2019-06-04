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


@TestCase(id = "WINGS-10784")
public class TC_12_20_Relocation_Edit_And_Remove_Expense_Security extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, relocationExpense);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Check 'Edit' and 'Remove' buttons are inactive while no Expenses are selected");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.checkEditRemoveDisabledPresent(Constants.TRUE);

        logStep("Check 'Edit' and 'Remove' buttons are active when an expense is selected");
        relocationDetailsForm.clickLastAddedExpence();
        relocationDetailsForm.checkEditRemoveDisabledPresent(Constants.FALSE);
    }
}
