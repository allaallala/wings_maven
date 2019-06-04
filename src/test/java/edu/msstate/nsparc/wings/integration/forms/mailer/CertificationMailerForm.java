package edu.msstate.nsparc.wings.integration.forms.mailer;

import framework.elements.Link;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This form is opened via Advanced -> E-Verify Mailers -> Certification Mailers
 */
public class CertificationMailerForm extends MailerBaseForm {

    public CertificationMailerForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'E-Verify Certifications')]"), "E-Verify Certifications");
    }

    public void checkFileEndPresent(String fileEnd) {
        Link lnk = new Link(By.cssSelector("css=a:contains('" + fileEnd + "')"), "End of the file");
        assertTrue("Mailer isn't created", lnk.isPresent());
    }
}
