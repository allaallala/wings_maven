package edu.msstate.nsparc.wings.integration.forms.participant.participantSS;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderBaseForm;
import org.openqa.selenium.By;

public class ParticipantJobOrderDetailsForm  extends JobOrderBaseForm {
    public ParticipantJobOrderDetailsForm(String jobTitle) {
        super(By.xpath(String.format("//h3[@id='job-title-header'][contains(.,'%1$s')]",jobTitle)), "Job Order Detail with job title");
    }
}
