package edu.msstate.nsparc.wings.integration.forms.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.NaicsSearchForm;
import webdriver.Browser;
import framework.elements.Button;
import framework.elements.TextBox;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * This is the base form for Employer forms
 */
public class EmployerBaseForm extends BaseWingsForm {
    private Button btnSearchNaicsAreaLookUp = new Button(By.xpath("//span[@id='naicsLookup']//button"), "Naics Area Lookup");
    private TextBox txbNaicsKeyword = new TextBox("css=input[id='naicsKeyword']", "NAICS Keyword");
    private final String osocPath = "//strong[contains(.,'%1$s')]";

    EmployerBaseForm(By locator, String titleLoc) {
        super(locator, titleLoc);
    }

    void inputSelectNaics(String naicsKeyword) {
        Button btnSugValue = new Button(By.xpath(String.format(osocPath, naicsKeyword)), "Suggestion value");
        txbNaicsKeyword.type(naicsKeyword);
        Browser.getDriver().getMouse().mouseMove(btnSugValue.getElement().getCoordinates());
        btnSugValue.click();
    }

    public void selectNaics(String titleNaics) {
        btnSearchNaicsAreaLookUp.clickAndWait();
        NaicsSearchForm naicsPage = new NaicsSearchForm();
        naicsPage.selectNaicsCode(titleNaics);
    }
}
