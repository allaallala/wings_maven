package edu.msstate.nsparc.wings.integration.forms.service;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Services -> Search for record -> Open record
 */
public class ServiceDetailsForm extends ServiceBaseForm {

    private String detailPath = "//form[@id='command']//tr[%1$d]/td[2]";
    private TableCell tbcTitle = new TableCell(By.xpath(String.format(detailPath, 2)), "Title text");
    private TableCell tbcServiceEndDate = new TableCell(By.xpath(String.format(detailPath, 6)), "Service end date");
    private TableCell tbcCategory = new TableCell(By.xpath(String.format(detailPath, 13)), "Category");
    private TableCell tbcDescription = new TableCell(By.xpath(String.format(detailPath, 15)), "Description");

    /**
     * Default constructor
     */
    public ServiceDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Service Detail')]"), "Service Detail");
    }

    /**
     * his method is used for comparing Enrollment participantSSDetails with provided expected values
     *
     * @param titleService   - title of the service
     * @param serviceEndDate - service end date
     * @param category       - category
     * @param description    - description
     */
    public void validateDetails(String titleService, String serviceEndDate, String category, String description) {
        CustomAssertion.softAssertEquals(tbcTitle.getText(), titleService, "Incorrect service title");
        CustomAssertion.softAssertEquals(tbcServiceEndDate.getText(), serviceEndDate, "Incorrect service end date");
        CustomAssertion.softAssertEquals(tbcCategory.getText(), category, "Incorrect category");
        CustomAssertion.softAssertContains(tbcDescription.getText(), description, "Incorrect description");
    }

    /**
     * Edit service (new value of the service title)
     *
     * @param newTitleService - new value of the title service
     */
    public void editService(String newTitleService) {
        clickButton(Buttons.Edit);
        ServiceEditForm editPage = new ServiceEditForm();
        String[] originalDetails = editPage.getValues();
        editPage.inputServiceName(newTitleService);
        editPage.clickButton(Buttons.Continue);
        editPage.clickButton(Buttons.Save);

        divideMessage("Validate service participantSSDetails");
        validateDetails(newTitleService, originalDetails[1], originalDetails[2], originalDetails[3]);
    }

    /**
     * Validate only title
     *
     * @param title - title of the service
     */
    public void validateServiceTitle(String title) {
        CustomAssertion.softAssertEquals(tbcTitle.getText(), title, "Incorrect service title");
    }
}
