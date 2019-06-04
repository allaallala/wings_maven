package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form can be opened from any NAICS lookup on employer creation/edit and other forms
 */
public class NaicsSearchForm extends BaseWingsForm {

    private TextBox txbNaics = new TextBox("css=input[id='naicsTitle']", "NAICS Title");
    private Button btnSearchNaics = new Button("id=naicsSearch", "Search");
    private RadioButton rbSelectedCode = new RadioButton("css=table[id='results-table'] input[name='selectedId']", "Selected NAICS");

    /**
     * Default constructor
     */
    public NaicsSearchForm() {
        super(By.xpath("//h1[contains(.,'NAICS Search')]"), "NAICS Search");
    }

    /**
     * This method is used for selecting NAICS code
     * @param title Title of the requested code
     */
    public void selectNaicsCode(String title) {
        txbNaics.type(title);
        btnSearchNaics.clickAndWait();
        rbSelectedCode.click();
        clickAndWait(BaseButton.RETURN);
    }
}
