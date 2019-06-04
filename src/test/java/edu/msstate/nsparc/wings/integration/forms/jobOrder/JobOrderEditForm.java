package edu.msstate.nsparc.wings.integration.forms.jobOrder;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.StaffSearchForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.elements.Button;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Wagner-Peyser -> Job Orders -> Search for record -> Open Record ->
 * Click on Edit button for Basic Information
 */
public class JobOrderEditForm extends JobOrderBaseForm {

    private TextBox txbJobTitle = new TextBox("id=jobTitle", "Job Title");
    private TextBox txbNumberOfOpenings = new TextBox("id=nbrJobOpenings", "Number of Openings");
    private Button checkedDisclaimer = new Button(By.xpath("//input[@id='showDisclaimer1']"), "Check Disclaimer");
    private TextBox tbDisclaimer = new TextBox(By.id("disclaimer"), "Disclaimer text");
    private TextBox tbOpenDate = new TextBox(By.id("dateOrderOpen"), "Open date");
    private TextBox tbCloseDate = new TextBox(By.id("dateOrderClose"), "Close date");
    private Button lbRemove = new Button(By.xpath("//img[@title='Remove']"), "Remove button");

    public JobOrderEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Edit')]"), "Job Order Edit");
    }

    public JobOrderEditForm(String jobTitle) {
        super(By.xpath(String.format("//div[@id='heading-title'][contains(.,'%1$s')]", jobTitle)), "Jod Order Edit Form for self-service");
    }

    public String[] editJobTitleAndNumberOfOpenings() {
        String[] editedData = {null, null};
        // editing job title
        if (txbJobTitle.getValue().length() < 25) {
            editedData[0] = txbJobTitle.getValue() + CommonFunctions.getRandomIntegerNumber(3);
        } else {
            editedData[0] = txbJobTitle.getValue().substring(0, 25) + CommonFunctions.getRandomIntegerNumber(2);
        }

        int temp = Integer.parseInt(txbNumberOfOpenings.getValue()) + CommonFunctions.getRandomInteger(10);
        editedData[1] = String.valueOf(temp);

        txbJobTitle.type(editedData[0]);
        txbNumberOfOpenings.type(editedData[1]);
        clickAndWait(BaseButton.SAVE_CHANGES);
        return editedData;
    }

    public void inputJobTitle(String jobTitle) {
        txbJobTitle.type(jobTitle);
    }

    public void inputOpenDate(String openDate) {
        tbOpenDate.type(openDate);
    }

    public void inputCloseDate(String closeDate) {
        tbCloseDate.type(closeDate);
    }

    public void checkDisclaimerButton(Roles role, String disclaimerValue) {
        if (role.equals(Roles.ADMIN)) {
            checkedDisclaimer.click();
            tbDisclaimer.type(disclaimerValue);
        } else {
            checkedDisclaimer.assertIsNotPresent();
        }
    }

    public void editStaffLookup(JobOrder order) {
        lbRemove.click();
        btnRestrictStaffLookup.clickAndWait();
        StaffSearchForm staffPage = new StaffSearchForm();
        staffPage.performSearchAndSelect(order.getRestrictedFirstName(), order.getRestrictedLastName());
        validateStaffInfo(order);
    }
}
