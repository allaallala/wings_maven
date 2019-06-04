package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import org.openqa.selenium.By;

/**
 * Class for pop-up windows
 */
public class PopUpMenu extends BaseWingsForm {

    private String baseXpath = "//table[@class='popup-menu']//img[@title='%1$s']";
    private Button btnCreate = new Button(String.format(baseXpath, "Create"), "Create");
    private Button btnSearch = new Button(String.format(baseXpath, "Search"), "Search");

    /**
     * Default constructor
     */
    public PopUpMenu() {
        super(By.xpath("//table[@class='popup-menu']"), "Pop-up Menu");
    }

    /**
     * Click [Create] button
     */
    public void clickCreate() {
        btnCreate.clickAndWait();
    }

    /**
     * Click [Search] button
     */
    public void clickSearch() {
        btnSearch.clickAndWait();
    }
}
