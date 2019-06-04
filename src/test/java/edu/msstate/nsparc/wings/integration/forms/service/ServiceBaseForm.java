package edu.msstate.nsparc.wings.integration.forms.service;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This is the base form for Service forms
 */
public class ServiceBaseForm extends BaseWingsForm {

    private TextBox txbTitle = new TextBox("css=input#serviceTitle", "Title");

    protected CheckBox chkWP = new CheckBox("css=input#isWagnerPeyser1", "Wagner-Peyser Program");
    protected CheckBox chkTrade = new CheckBox("css=input#isTrade1", "Trade Program");
    protected CheckBox chkWIAAdult = new CheckBox("css=input#isWiaAdult1", "WIOA Adult Group");
    private CheckBox chkWIAYouth = new CheckBox("css=input#isWiaYouth1", "WIOA Youth Group");
    private RadioButton rbVeteranServiceYes = new RadioButton("//input[@name='veteranService' and @value='YES']", "Veteran Service - Yes");
    private ComboBox cmbCategory = new ComboBox("css=select[id='tmpServiceCategoryId']", "Category");
    private TextArea txbDescription = new TextArea("css=textarea#serviceDesc", "Description");
    protected TextBox txbServiceEndDate = new TextBox("css=input#dateEnd", "Service End Date");

    /**
     * Constructor of the form with defined locator.
     *
     * @param locator - locator of the form
     */
    public ServiceBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Get value of the service on the page
     *
     * @return title value, service end date value, category value, description value.
     */
    public String[] getValues() {
        return new String[]{txbTitle.getValue(), txbServiceEndDate.getValue(), cmbCategory.getSelectedLabel(), txbDescription.getValue()};
    }

    /**
     * Select category
     *
     * @param categoryValue - value to select
     */
    public void selectCategory(String categoryValue) {
        cmbCategory.select(categoryValue);
    }

    /**
     * Input description.
     *
     * @param newDescription - new description.
     */
    public void inputDescription(String newDescription) {
        txbDescription.type(newDescription);
    }

    /**
     * Click veteran service yes
     */
    public void clickVeteranServiceYes() {
        rbVeteranServiceYes.click();
    }

    /**
     * Select WIA youth checkbox
     */
    public void selectWiaYouthCb() {
        chkWIAYouth.click();
    }

    /**
     * Input service name title
     *
     * @param serviceName - service name
     */
    public void inputServiceName(String serviceName) {
        txbTitle.type(serviceName);
    }

    /**
     * Wait for checkbox Wagner-Peyser is present and visible on the page.
     *
     * @param present - if true, wait for element to be present on the page.
     */
    public void waitWagnerPayserCheckBoxPresent(Boolean present) {
        if (present) {
            chkWP.waitForIsElementPresent();
        } else {
            chkWP.waitForIsElementNotPresent();
        }
    }

    /**
     * Click wagner-peyser checkbox
     */
    public void clickWPcheckbox() {
        chkWP.click();
    }
}
