package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import org.openqa.selenium.By;

/**
 * Describes relocation edit expense form
 */
public class RelocationEditExpenseForm extends RelocationAddExpenseForm {

    /**
     * Default constructor
     */
    public RelocationEditExpenseForm() {
        super(By.xpath("//h1[contains(.,'Edit Relocation Expense')]"), "Edit Relocation Expense");
    }

    /**
     * Fill out required fields
     * @param relocationExpense RelocationExpense object
     */
    @Override
    public void fillExpenseFormAndSave(RelocationExpense relocationExpense) {
        fillOutStatusRelatedFields(relocationExpense);
        fillOutPayeeInfo(relocationExpense);
        clickAndWait(BaseButton.SAVE_CHANGES);
    }
}
