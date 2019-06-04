package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Div;
import org.openqa.selenium.By;

/**
To open this form you need to log in as Employer -> Click on 'View Applicants' -> Search for record ->
    Click on 'Resume' button
 */
public class ResumeForm extends BaseWingsForm {

    public static  final Div DIV_PARTICIPANT_NAME = new Div(By.xpath("//div[@id='user-name']"), "Participant Name");

    /**
     * Default constructor
     */
    public ResumeForm() {
        super(By.xpath("//div[contains(@class,'resume-view-row')]//div[@id='user-name']"), "Resume");
    }
}
