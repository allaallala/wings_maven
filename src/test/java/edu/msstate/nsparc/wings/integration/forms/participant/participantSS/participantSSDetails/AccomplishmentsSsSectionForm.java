package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class AccomplishmentsSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcGrade = new TableCell(By.xpath(String.format(parameterPath, "Highest Grade Completed:")), "Highest grade completed");
    private TableCell tbcCertificate = new TableCell(By.xpath("//table[@id='arresults-table']//td/following-sibling::td | //a[@id='educationSection']/../..//tbody"), "Certificate");
    private TableCell tbcDriverLicense = new TableCell(By.xpath(String.format(parameterPath, "Have Driver")), "Highest grade completed");

    public AccomplishmentsSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Accomplishments')]"), "Accomplishments");
    }

    /**
     * Check status and certificate of the participant
     *
     * @param partip         - expected participant.
     * @param expectedCertif - certificate
     */
    public void checkStatusCertificateParticipant(Participant partip, String expectedCertif) {
        CustomAssertion.softAssertContains(tbcGrade.getText(), partip.getGrade(), "Incorrect participant grade");
        CustomAssertion.softAssertContains(tbcCertificate.getText(), expectedCertif, "Incorrect participant certificate");
    }

    /**
     * Validate the accomplishment section
     *
     * @param grade        - highest grade completed
     * @param licenceExist - driver's license
     */
    public void validateAccomplishmentsSection(String grade, String licenceExist) {
        CustomAssertion.softTrue("Incorrect highest grade completed", tbcGrade.getText().contains(grade));
        CustomAssertion.softTrue("Incorrect driver's license", tbcDriverLicense.getText().contains(licenceExist));
    }
}
