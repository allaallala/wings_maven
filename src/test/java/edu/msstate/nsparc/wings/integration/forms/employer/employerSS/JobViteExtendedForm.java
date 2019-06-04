package edu.msstate.nsparc.wings.integration.forms.employer.employerSS;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import org.openqa.selenium.By;

/**
 * Created by a.korotkin on 4/14/2017.
 */
public class JobViteExtendedForm extends BaseWingsForm {

    private Button btnReturnToSearch = new Button(By.xpath("//button[@id='returnToSearch']"), "Return to Details");

    public JobViteExtendedForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Your Job-Vite Has Been Successfully Extended')]"), "Successfully Extended");
    }

    public void clickReturnToDetails() {
        btnReturnToSearch.clickAndWait();
    }
}
