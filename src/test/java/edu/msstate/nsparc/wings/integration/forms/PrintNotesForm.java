package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * This class describes [Print Notes] form, which can be accessed by clicking 'Printable version' link in the notes.
 * Created by a.vnuchko on 09.09.2015.
 */
public class PrintNotesForm extends BaseWingsForm {
    /**
     * Default constructor
     */
    public PrintNotesForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Print Notes')]"), "Print Notes");
    }

}
