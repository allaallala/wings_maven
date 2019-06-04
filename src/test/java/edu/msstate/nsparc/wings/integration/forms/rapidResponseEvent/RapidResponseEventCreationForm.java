package edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent;

import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Rapid Response Events -> Create
 */
public class RapidResponseEventCreationForm extends RapidResponseEventBaseForm {

    /**
     * Default constructor
     */
    public RapidResponseEventCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Rapid Response Event Creation')]"), "Rapid Response Event Creation");
    }

    /**
     * Filling out form fields with provided data
     *
     * @param event Object with Event data
     */
    public void fillOutCreationForm(RapidResponseEvent event) {
        selectEmployer(event.getEmployer());
        selectWorkforceArea(event.getWorkforceArea());
        txbNotificationDate.type(event.getNotificationDate());
        txbImpactDate.type(event.getImpactDate());
        txbAffectedEmployees.type(event.getNumberOfPotentiallyAffectedEmployees());
        txbServedEmployees.type(event.getNumberOfEmployeesServed());
        if (event.isTradeAffected()) {
            rbTradeAffectesYes.click();
        } else {
            rbTradeAffectesNo.click();
        }
        txbDescription.type(event.getDescription());
        if (txbEventDate.isPresent()) {
            txbEventDate.type(event.getEventDate());
        }
    }
}
