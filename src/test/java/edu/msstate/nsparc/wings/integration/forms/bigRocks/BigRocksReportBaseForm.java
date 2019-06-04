package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.elements.Button;
import framework.elements.Embed;
import framework.elements.Span;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for all Big Rocks Reports Forms
 */
public class BigRocksReportBaseForm extends BaseWingsForm {

    private Embed embdReportFrame = new Embed("css=iframe#jasperReport", "Report frame");
    private Button btnDone = new Button(By.xpath("//a[contains(.,'Done')]"), "Done");
    private TextBox txbStartDate = new TextBox(By.id("reportDateStart"), "Report Start Date");
    private TextBox txbEndDate = new TextBox(By.id("reportDateEnd"), "Report Date End");
    private String xpathHead = "//h1[contains(.,'%1$s')]";

    /**
     * Constructor of the form with defined locator
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public BigRocksReportBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Check head title of the report
     * @param headTitle - head title to check.
     */
    public void checkHead(String headTitle) {
        new Span(By.xpath(String.format(xpathHead, headTitle)),"Head title of the report").assertIsPresent();
    }
    /**
     * Input start date
     * @param startDate - start date
     */
    public void inputStartDate(String startDate) {
        txbStartDate.type(startDate);
    }

    /**
     * Input end date
     * @param endDate - end date
     */
    public void inputEndDate(String endDate) {
        txbEndDate.type(endDate);
    }

    /**
     * This method checks if report is present on form
     * @return True if report is present on form
     */
    public boolean isReportPresent() {
         return embdReportFrame.isPresent();
    }

    /**
     * Complete creation
     */
    public void checkCompleteCreation() {
        clickButton(Buttons.Create);
        isReportPresent();
        btnDone.clickAndWait();
    }
}
