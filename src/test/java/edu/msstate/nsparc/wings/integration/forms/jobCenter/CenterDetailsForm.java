package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Service Centers -> Search -> Find record and open it -> Click on Edit button
 */
public class CenterDetailsForm extends CenterBaseForm {

    private String xpathExpression = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcTitle = new TableCell(By.xpath("//span[@id='notesLink']/span"), "Center title");
    private TableCell tbcServiceCenterNumber = new TableCell(By.xpath(String.format(xpathExpression, "Service Center Number:")), "Service Center Number");
    private TableCell tbcDistrictCode = new TableCell(By.xpath(String.format(xpathExpression, "District Code:")), "District Code");
    private TableCell tbcMetroStatisticalArea = new TableCell(By.xpath(String.format(xpathExpression, "Metro Statistical Area:")), "Metro Statistical Area");
    private TableCell tbcServiceCenterType = new TableCell(By.xpath(String.format(xpathExpression, "Service Center Type:")), "Service Center Type");
    private TableCell tbcWorkforce = new TableCell(By.xpath("//strong[contains(.,'Workforce Area')]/../following-sibling::td"), "Workforce Area");
    private TableCell tbcLocationAddress = new TableCell(By.xpath("//form[@id='command']//tr[11]/td[1]"), "Location address");
    private TableCell tbcMailingAddress = new TableCell(By.xpath("//form[@id='command']//tr[11]/td[2]"), "Mailing address");
    private TableCell tbcPhoneNumber = new TableCell(By.xpath(String.format(xpathExpression, "Phone Number")), "Phone Number");
    private TableCell tbcEmail = new TableCell(By.xpath(String.format(xpathExpression, "Email Address")), "Email Address");

    private Button btnCreateAnother = new Button(By.id("createAnother"), "Create Another");
    private Div divText = new Div(By.xpath("//div[@class='blue-border']"), "Enable/Disable text");
    private Button btnDisable = new Button(By.id("disableJobCenter"), "Disable job center");
    private Button btnEnable = new Button(By.id("enableJobCenter"), "Enable job center");

    /**
     * Default constructor
     */
    public CenterDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'One Stop Center Detail')]"), "One Stop Center Detail");
    }

    /**
     * This method is used for comparison Center participantSSDetails form
     *
     * @param centers - service centers parameters.
     */
    public void validateDetails(ServiceCenters centers) {
        CustomAssertion.softAssertContains(tbcPhoneNumber.getText(), centers.getPhoneNumber(), "Incorrect phone number");
        CustomAssertion.softAssertContains(tbcEmail.getText(), centers.getEmailAddress(), "Incorrect email");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), centers.getLineOne(), "Incorrect loc line one");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), centers.getLineTwo(), "Incorrect loc line two");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), centers.getCity(), "Incorrect loc city");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), centers.getZip(), "Incorrect loc zip code");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), centers.getCountry(), "Incorrect loc country");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), centers.getLineOne(), "Incorrect mail line one");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), centers.getLineTwo(), "Incorrect mail line two");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), centers.getCity(), "Incorrect mail city");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), centers.getZip(), "Incorrect mail zip code");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), centers.getCountry(), "Incorrect mail country");
    }

    /**
     * Validate service center data on the participantSSDetails form.
     *
     * @param center - service center
     */
    public void validateData(ServiceCenters center) {
        //validate common information
        CustomAssertion.softAssertContains(tbcTitle.getText(), center.getCenterName(), "Incorrect service center name");
        CustomAssertion.softAssertContains(tbcServiceCenterNumber.getText(), center.getCenterNumber(), "Incorrect service center number");
        CustomAssertion.softAssertContains(tbcDistrictCode.getText(), center.getDisctrictCode(), "Incorrect district code");
        CustomAssertion.softAssertContains(tbcMetroStatisticalArea.getText(), center.getMetroStatisticalArea(), "Incorrect metro statistical area");
        CustomAssertion.softAssertContains(tbcServiceCenterType.getText(), center.getCenterType(), "Incorrect service center type");

        //validate workforce information
        CustomAssertion.softAssertContains(tbcWorkforce.getText(), center.getWorkForceArea(), "Incorrect workforce area");

        //validate location address
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getLineOne(), "Incorrect mail line one");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getCity(), "Incorrect mail city");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getZip(), "Incorrect mail zip");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getState(), "Incorrect mail state");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getCounty() + " County", "Incorrect mail county");
        CustomAssertion.softAssertContains(tbcLocationAddress.getText(), center.getCountry(), "Incorrect mail country");

        //validate mailing address
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getLineOne(), "Incorrect mail line one");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getCity(), "Incorrect mail city");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getZip(), "Incorrect mail zip");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getState(), "Incorrect mail state");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getCounty() + " County", "Incorrect mail county");
        CustomAssertion.softAssertContains(tbcMailingAddress.getText(), center.getCountry(), "Incorrect mail country");
    }

    /**
     * Click [Create Another]
     */
    public void clickCreateAnother() {
        btnCreateAnother.clickAndWait();
    }

    /**
     * Check enable/disable buttons
     *
     * @param user - current user.
     */
    public void checkEnableDisable(User user) {
        if (user.getServiceCenters().getScViewDisableJobCenter()) {
            CustomAssertion.softTrue("Incorrect disable text",
                    divText.getText().contains("If you would like to disable this Service Center, click the button below"));
            btnDisable.clickAndWait();
        }
        if (user.getServiceCenters().getScViewEnableJobCenter()) {
            CustomAssertion.softTrue("Incorrect enable text",
                    divText.getText().contains("If you would like to enable this Service Center, click the button below"));
            btnEnable.clickAndWait();
        }
    }
}
