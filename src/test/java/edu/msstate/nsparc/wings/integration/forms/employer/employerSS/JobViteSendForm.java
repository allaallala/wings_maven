package edu.msstate.nsparc.wings.integration.forms.employer.employerSS;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import org.openqa.selenium.By;

/**
 * Created by a.korotkin on 4/14/2017.
 */
public class JobViteSendForm extends BaseWingsForm {

    private Button btnChoose = new Button(By.xpath("//tbody//a[text()[contains(.,'Choose')]]"), "Choose");
    private Button btnConfirm = new Button(By.xpath("//button[@id='confirm']"), "Confirm");

    public JobViteSendForm() {
        super(By.xpath("//form[@id='invitation']"), "Job-Vite Send Form");
    }

    public void sendJobVite() {
        btnChoose.clickAndWait();
        btnConfirm.clickAndWait();
    }
}
