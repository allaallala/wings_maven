package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Relocation forms
 */
public class RelocationBaseForm extends BaseWingsForm {

    private final Button btnAdd = new Button("id=add", "Add");
    protected final TextBox txbExpenseRequestedAmount = new TextBox(By.id("tmpRelocationExpense.amountRequested"), "Requested Amount");
    private TextBox txbEmployerName = new TextBox("css=input[id='employerName']", "Employer Name");


    /**
     * Constructor of the form with defined locator
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public RelocationBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Click [Add] button.
     */
    public void clickAdd(){
        btnAdd.clickAndWait();
    }
    /**
     * Input employer name
     * @param employerName - employer name
     */
    public void inputEmployerName(String employerName){
        txbEmployerName.type(employerName);
    }

    /**
     * Get employer text name
     * @return employer text
     */
    public String getEmployerName(){
       return txbEmployerName.getText();
    }
}
