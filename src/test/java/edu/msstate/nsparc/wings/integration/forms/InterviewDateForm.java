package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Employer S-S -> View Applicants -> Search for record -> Open it -> Click on 'Set Date' link
 */
public class InterviewDateForm extends BaseWingsForm {

    private TextBox txbDate = new TextBox("id=dateCheckIn", "Interview Date");
    private TextBox txbTime = new TextBox("id=timeCheckIn", "Interview Time");

    /**
     * Constructor
     */
    public InterviewDateForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Update Interview Date & Time')]"), "Update Interview Date/Time");
    }

    /**
     * Input date for interview
     *
     * @param date - date to set
     */
    public void inputDate(String date) {
        txbDate.type(date);
    }

    /**
     * INput time for interview
     *
     * @param time - time
     */
    public void inputTime(String time) {
        txbTime.type(time);
    }
}
