package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import org.openqa.selenium.By;

public class DesiredCandidateProfileSsForm extends JobOrderCreationForm {

    public DesiredCandidateProfileSsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Create Opening: Desired Candidate Profile')]"),
                "Create Opening: Desired Candidate Profile");
    }

    public void skipDesiredCandidateProfilePage() {
        clickButton(Buttons.Continue);
    }
}
