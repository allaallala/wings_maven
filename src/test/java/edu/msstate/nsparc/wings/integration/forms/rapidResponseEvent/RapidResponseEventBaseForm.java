package edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Rapid Response Events forms
 */
public class RapidResponseEventBaseForm extends BaseWingsForm {

    protected TextBox txbNotificationDate = new TextBox("css=input[id='dateNotification']", "Notification Date");
    protected TextBox txbImpactDate = new TextBox("css=input[id='dateImpact']", "Impact Date");
    protected TextBox txbAffectedEmployees = new TextBox("css=input[id='numPotentialAffectedEmployees']", "Affected Employees");
    protected TextBox txbServedEmployees = new TextBox("css=input[id='numEmployeesServed']", "Served Employees");
    protected RadioButton rbTradeAffectesYes = new RadioButton("css=input[id='isTradeAffected1']", "Trade Affected - Yes");
    protected RadioButton rbTradeAffectesNo = new RadioButton("css=input[id='isTradeAffected2']", "Trade Affected - No");
    protected TextBox txbDescription = new TextBox("css=input[id='eventDescription']", "Event Description");
    protected TextBox txbEventDate = new TextBox(By.id("dateEvent"), "Event Date");

    /**
     * Constructor of the form with specified locator
     * @param locator - locator of the form.
     * @param formTitle - title of the form
     */
    public RapidResponseEventBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
