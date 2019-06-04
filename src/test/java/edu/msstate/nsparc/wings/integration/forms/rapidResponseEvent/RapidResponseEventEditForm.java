package edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent;

import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Rapid Response Events -> Search for record -> Open record -> Edit
 */
public class RapidResponseEventEditForm extends RapidResponseEventBaseForm {

    /**
     * Default constructor
     */
    public RapidResponseEventEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Rapid Response Event Edit')]"), "Rapid Response Event Edit");
    }

    /**
     * Filling out form fields with provided data
     *
     * @param event Object with Event data
     */
    public void fillOutEditForm(RapidResponseEvent event) {
        txbImpactDate.type(event.getImpactDate());
        txbAffectedEmployees.type(event.getNumberOfPotentiallyAffectedEmployees());
        txbServedEmployees.type(event.getNumberOfEmployeesServed());
        if (event.isTradeAffected()) {
            rbTradeAffectesYes.click();
        } else {
            rbTradeAffectesNo.click();
        }
        txbDescription.type(event.getDescription());
    }

    /**
     * Input notification date
     *
     * @param notDate - notification date
     */
    public void inputNotificationDate(String notDate) {
        txbNotificationDate.type(notDate);
    }
}
