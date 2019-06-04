package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form can be opened during Job Order creation to search for OSOC code
 */
public class OsocSearchForm extends BaseWingsForm {

    private TextBox txbKeyword = new TextBox("css=input#occTitle", "Job Title, Occupation, or Keyword: ");
    private Button btnSearchOsoc = new Button("css=button#osocSearch", "Search");
    private RadioButton rbFirstOsoc = new RadioButton("css=table#results-table input", "First Osoc in results table");

    /**
     * Default constructor
     */
    public OsocSearchForm() {
        super(By.xpath("//h1[contains(.,'Osoc Search')]"), "Osoc Search");
    }

    /**
     * This method is used for searching OSOC code by title and returning it
     *
     * @param osocTitle Title for search
     */
    public void selectAndReturnOsoc(String osocTitle) {
        txbKeyword.type(osocTitle);
        btnSearchOsoc.clickAndWait();
        rbFirstOsoc.click();
       clickAndWait(BaseButton.RETURN);
    }
}
