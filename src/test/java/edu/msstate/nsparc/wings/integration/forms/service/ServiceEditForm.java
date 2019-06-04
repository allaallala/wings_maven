package edu.msstate.nsparc.wings.integration.forms.service;

import framework.elements.RadioButton;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Services -> Search for record -> Open record -> Click on Edit
 */
public class ServiceEditForm extends ServiceBaseForm {

    private RadioButton rbServiceFeeNo = new RadioButton("css=input#serviceFee2","Is there a fee for this service - No");

    /**
     * Default constructor
     */
    public ServiceEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Service Edit')]"), "Service Edit");
    }

    /**
     * This method is used for setting Service participantSSDetails to provided values
     * @param title - service title
     * @param endDate - service end date
     * @param category - category
     * @param description - description
     */
    public void editServiceDetails(String title, String endDate, String category, String description) {
        inputServiceName(title);
        txbServiceEndDate.type(endDate);
        rbServiceFeeNo.click();
        if (chkWP.isPresent()) {
            chkWP.click();
        }
        chkWIAAdult.click();
        chkTrade.click();

        selectCategory(category);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        inputDescription(description);
    }
}
