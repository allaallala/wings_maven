package edu.msstate.nsparc.wings.integration.forms.mailer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * This is the base form for Mailer report forms
 */
public class MailerBaseForm extends BaseWingsForm {

    public MailerBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
