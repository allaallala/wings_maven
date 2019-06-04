package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.ContactInformationForm;
import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.Span;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * Created by a.vnuchko on 11.07.2016.
 */
public class CenterCreateForm extends CenterBaseForm {
    //Web elements
    private Span spnRemove = new Span(By.xpath("//img[@title='Remove']"), "Remove button");
    private TextBox txbServiceCenterNumber = new TextBox(By.id("centerNumber"), "Service center number");
    private TextBox txbDisctrictCode = new TextBox(By.id("districtCode"), "Disctrict code");
    private TextBox txbMetroArea = new TextBox(By.id("metroStatArea"), "Metro statistical area");
    private ComboBox cbCenterType = new ComboBox(By.id("centerType"), "Service center type");
    private RadioButton rbSameAddress = new RadioButton(By.id("contactInformation.addressSame1"), "Location address is the same to the mailing address");

    /**
     * Default constructor of the form with specified locator
     */
    public CenterCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'One Stop Center Creation')]"), "One Stop Center Creation");
    }

    /**
     * Fill in creation form
     *
     * @param center - service center
     */
    public void fillCreationForm(ServiceCenters center) {
        if (!spnRemove.isPresent()) {
            selectWorkforceArea(center.getWorkForceArea());
        }
        txbServiceCenterName.type(center.getCenterName());
        txbServiceCenterNumber.type(center.getCenterNumber());
        txbDisctrictCode.type(center.getDisctrictCode());
        txbMetroArea.type(center.getMetroStatisticalArea());
        cbCenterType.select(center.getCenterType());
        txbLocationAddressLineOne.type(center.getLineOne());
        txbLocationAddressCity.type(center.getCity());
        txbLocationAddressZipPostalCode.type(center.getZip());
        cmbLocationAddressCountry.select(center.getCountry());
        cmbLocationAddressState.select(center.getState());
        new ContactInformationForm().selectLocationCounty(center.getCounty());
        rbSameAddress.click();
        clickButton(Buttons.Create);
    }
}
