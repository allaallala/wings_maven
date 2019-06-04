package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * Contextual information pane search form.
 */
public class CIPSearch extends BaseWingsForm {

    private RadioButton rbFirstAvailableMajorTitle = new RadioButton("//input[@class='radio']", "The very first Major title found");
    private Button btnCIPSearch = new Button("id=cipSearch", "Search");
    private TextBox txbMajorTitle = new TextBox("id=majorTitle", "Major, or Keyword");

    /**
     * Constructor
     */
    public CIPSearch() {
        super(By.xpath("//h1[contains(.,'Cip Search')]"), "Cip Search");
    }

    /**
     * Select first major title
     */
    public void selectFirstMajorTitle() {
        btnCIPSearch.clickAndWait();
        rbFirstAvailableMajorTitle.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * Select specific major
     * @param majorTitle - major title
     */
    public void selectSpecificMajor(String majorTitle) {
        txbMajorTitle.type(majorTitle);
        selectFirstMajorTitle();
    }
}
