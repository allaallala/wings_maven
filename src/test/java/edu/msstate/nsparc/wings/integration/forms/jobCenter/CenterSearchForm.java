package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

/**
This form is opened via Advanced -> Service Centers -> Search
 */
public class CenterSearchForm extends CenterBaseForm {

    private ComboBox cmbCenterType = new ComboBox("css=select#centerType", "Service Center Type");
    private RadioButton rbCenterName = new RadioButton("css=table#results-table input","First Center Name");
    private Link lnkRemoveLWIA = new Link("css=img[alt='Remove Lwia']", "Remove LWIA");
    private String centerType = "Any";

    /**
     * Default constructor
     */
    public CenterSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Center Search')]"), "Center Search");
    }

    /**
     * This method is used for returning first Job Center from search results
     */
    public void selectAndReturnCenter() {
        if (lnkRemoveLWIA.isPresent()) {
            lnkRemoveLWIA.click();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        clickAndWait(BaseButton.SEARCH);
        rbCenterName.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * This method is used for searching and returning requested Job Center
     * @param centerName Requested Center Name
     */
    public void selectAndReturnCenter(String centerName) {
        if (lnkRemoveLWIA.isPresent()) {
            lnkRemoveLWIA.click();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbCenterType.select(centerType);
        txbServiceCenterName.type(centerName);
        clickAndWait(BaseButton.SEARCH);
        rbCenterName.click();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * This method is user for searching for Center
     * @param title Center name
     */
    public void performSearch(String title) {
        txbServiceCenterName.type(title);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for searching and returning Center from look-up
     * @param title Center name
     */
    public void performSearchAndSelect(String title) {
        performSearch(title);
        rbCenterName.click();
        clickAndWait(BaseButton.RETURN);
    }

}
