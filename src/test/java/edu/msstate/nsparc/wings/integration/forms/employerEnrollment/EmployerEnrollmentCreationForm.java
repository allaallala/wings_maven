package edu.msstate.nsparc.wings.integration.forms.employerEnrollment;

import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Employer Service Enrollment -> Create
 */
public class EmployerEnrollmentCreationForm extends EmployerEnrollmentBaseForm {

    private TextBox txbScheduledDate = new TextBox("css=input#dateSchedule", "Scheduled Date");
    private TextBox txbScheduledTime = new TextBox("css=input#timeScheduleId", "Scheduled Time");

    /**
     * Default constructor
     */
    public EmployerEnrollmentCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Creation')]"), "Enrollment Creation");
    }
    /**
     * This method is used for selecting Employer service
     * @param serviceTitle Service that will be selected
     */
    public void selectService(String serviceTitle) {
        info("Selecting Service");
        clickAndWait(BaseButton.SERVICE_LOOK_UP);
        ServiceSearchForm searchForm = new ServiceSearchForm();
        searchForm.selectAndReturnEmployerServiceCheckbox(serviceTitle);
    }

    /**
     * Input scheduled date and time
     * @param scheduledDate - scheduled date
     * @param scheduledTime - scheduled time
     */
    public void inputScheduledDateTime(String scheduledDate, String scheduledTime) {
        txbScheduledDate.type(scheduledDate);
        txbScheduledTime.type(scheduledTime);
    }

    /**
     * Input creation date.
     * @param creationDate - creation date
     */
    public void inputCreationDate(String creationDate) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbCreationDate.type(creationDate);
    }
}
