package edu.msstate.nsparc.wings.integration.forms.service;

import framework.elements.Button;
import framework.elements.CheckBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Services -> Search
 */
public class ServiceSearchForm extends ServiceBaseForm {

    private Button btnReturnMultiple = new Button("id=returnMultiple", "Return");

    private RadioButton rbEmployerServices = new RadioButton("id=employerOrParticipant2", "Employer Services");
    private RadioButton rbParticipantServices = new RadioButton("id=employerOrParticipant1", "Participant Services");
    private CheckBox chkServiceResult = new CheckBox(By.xpath("//table[@id='results-table']//input[@class='checkbox']"), "Service Checkbox");
    private RadioButton rbServiceResult = new RadioButton(By.xpath("//table[@id='results-table']//input"), "Service Radiobutton");
    private RadioButton rbEmployerServiceRadiobutton = new RadioButton(By.xpath("//table[@id='results-table']//input"), "Employer Service");
    private TextBox tbServiceCenterName = new TextBox(By.xpath("//input[@id='centerName']"), "Service Center Name");

    /**
     * Default constructor
     */
    public ServiceSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Service Search')]"), "Service Search");
    }

    /**
     * This constructor is used for finding participant service
     */
    public ServiceSearchForm(String participant) {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'One Stop Center Search')]"), "Service Center Search for " + participant);
    }

    /**
     * Input service center name
     *
     * @param serviceName -service name
     */
    public void inputServiceCenterName(String serviceName) {
        tbServiceCenterName.type(serviceName);
    }

    /**
     * Input service center name and return result
     */
    public void inputServiceCenterResult(String serviceName) {
        inputServiceCenterName(serviceName);
        clickAndWait(BaseButton.SEARCH);
        selectEmployerService();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * Click employer service radiobutton.
     */
    public void selectEmployerService() {
        rbEmployerServiceRadiobutton.click();
    }

    /**
     * This method is used for search for Service by its Title
     *
     * @param title Service name
     */
    public void performSearch(String title) {
        inputServiceName(title);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for search for Service by its Title and then open it
     *
     * @param title Service name
     */
    public void performSearchAndOpen(String title) {
        performSearch(title);
        openFirstSearchResult();
    }

    /**
     * This method is used for Employer service searching with specified Name and returning from look-up
     *
     * @param serviceTitle Service title for search
     */
    public void selectAndReturnEmployerService(String serviceTitle) {
        rbEmployerServices.click();
        inputServiceName(serviceTitle);
        clickAndWait(BaseButton.SEARCH);
        selectEmployerService();
        clickAndWait(BaseButton.RETURN);
    }

    /**
     * This method is used for Employer service searching with specified Name and returning from look-up
     *
     * @param serviceTitle Service title for search
     */
    public void selectAndReturnEmployerServiceCheckbox(String serviceTitle) {
        rbEmployerServices.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        inputServiceName(serviceTitle);
        clickAndWait(BaseButton.SEARCH);
        chkServiceResult.click();
        btnReturnMultiple.clickAndWait();
    }

    /**
     * This method is used for Participant service searching with specified Name and returning from look-up
     *
     * @param serviceTitle Service title for search
     */
    public void selectAndReturnParticipantServiceCheckbox(String serviceTitle) {
        rbParticipantServices.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        inputServiceName(serviceTitle);
        clickAndWait(BaseButton.SEARCH);
        chkServiceResult.click();
        btnReturnMultiple.clickAndWait();
    }

    /**
     * This method is used for Participant service searching with specified Name and returning from look-up
     *
     * @param serviceTitle Service title for search
     */
    public void selectAndReturnParticipantServiceRadioButton(String serviceTitle) {
        rbParticipantServices.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        inputServiceName(serviceTitle);
        clickAndWait(BaseButton.SEARCH);
        rbServiceResult.click();
        clickAndWait(BaseButton.RETURN);
    }
}
