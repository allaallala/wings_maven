package edu.msstate.nsparc.wings.integration.forms.participantEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This is the base form for Participant Enrollment forms
 */
public class ParticipantEnrollmentBaseForm extends BaseWingsForm {
    private Span spnError = new Span("css=span[class='error']", "Error message");
    private TableCell tbServices = new TableCell("css=table#enrollments-table","Services");

    /**
     * Constructor of the form with defined locator
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public ParticipantEnrollmentBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Get error text
     * @return error text.
     */
    public String getErrorText(){
       return spnError.getText();
    }

    public String getServicesText(){
        return tbServices.getText().trim();
    }
}
