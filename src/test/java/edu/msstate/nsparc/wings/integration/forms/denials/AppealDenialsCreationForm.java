package edu.msstate.nsparc.wings.integration.forms.denials;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * Denials creation form
 */
public class AppealDenialsCreationForm extends BaseWingsForm {

    /**
     * Default constructor
     */
    public AppealDenialsCreationForm() {
        super(By.xpath("//h1[contains(text(),'Denial Appeal')]"), "Appeal Denails");
    }
}
