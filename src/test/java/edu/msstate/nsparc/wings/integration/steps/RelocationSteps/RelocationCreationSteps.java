package edu.msstate.nsparc.wings.integration.steps.RelocationSteps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;

public class RelocationCreationSteps extends BaseNavigationSteps {

    public static void createRelocationWithoutLoggingOut(Relocation relocation, Roles role) {
        TradeEnrollmentSteps.createTradeEnrollment(relocation.getTradeEnrollment(), Roles.ADMIN);
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Create);
        RelocationCreateForm creationForm = new RelocationCreateForm();
        creationForm.fillOutCreationForm(relocation);
        creationForm.clickButton(Buttons.Create);
    }

    public static void createRelocation(Relocation relocation, Roles role) {
        createRelocationWithoutLoggingOut(relocation, role);
        logout();
    }

    public static void createRelocationAndEditStatus(Relocation relocation) {
        createRelocationWithoutLoggingOut(relocation, Roles.ADMIN);
        editAndSaveRelocationStatusWithoutLoggingOut(relocation);
        logout();
    }

    public static void editAndSaveRelocationStatusWithoutLoggingOut(Relocation relocation) {
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRelocation();
        RelocationEditForm relocationEditForm = new RelocationEditForm();
        relocationEditForm.editDetails(relocation);
        relocationEditForm.clickButton(Buttons.Save);
    }

    /**
     * Create relocation, edit status and add expense
     * @param relocation Relocation object
     * @param relocationExpense RelocationExpense object
     */
    public static void createRelocationWithExpense(Relocation relocation, RelocationExpense relocationExpense) {
        createRelocationWithoutLoggingOut(relocation, Roles.ADMIN);
        editAndSaveRelocationStatusWithoutLoggingOut(relocation);
        addRelocationExpenseWithoutLoggingOut(relocationExpense);
        logout();
    }

    /**
     * Create relocation, edit status and add several expenses
     * @param relocation Relocation object
     * @param expenses Array of RelocationExpense objects
     */
    public static void createRelocationWithSeveralExpenses(Relocation relocation, RelocationExpense[] expenses) {
        createRelocationWithoutLoggingOut(relocation, Roles.ADMIN);
        editAndSaveRelocationStatusWithoutLoggingOut(relocation);
        for (RelocationExpense expense : expenses) {
            addRelocationExpenseWithoutLoggingOut(expense);
        }
        logout();
    }

    /**
     * Add expense (RelocationDetailsForm should be opened)
     * @param relocationExpense RelocationExpense object
     */
    public static void addRelocationExpenseWithoutLoggingOut(RelocationExpense relocationExpense) {
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.addExpence();
        RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
        relocationAddExpenseForm.fillExpenseFormAndSave(relocationExpense);
    }
}
