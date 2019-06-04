package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.elements.H1;
import org.openqa.selenium.By;

public class CertificationsForm extends ParticipantCreationForm {

    private String h1Xpath = "//h1[contains(.,'%1$s')]";
    private H1 h1Certifications = new H1(By.xpath(String.format(h1Xpath, "Participant Creation: Certifications")), "Certifications title");

    public CertificationsForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Certifications')]"), "Participant Creation: Certifications");
    }

    private boolean isCertificationFormDisplayed() {
        return h1Certifications.isPresent();
    }

    public void skipCertificationsSection() {
        if (isCertificationFormDisplayed()) {
            clickButton(Buttons.Continue);
        }
    }
}
