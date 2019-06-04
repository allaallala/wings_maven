package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class EligibilityInformationSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcDateOfBirth = new TableCell(By.xpath(String.format(parameterPath, "Date of Birth:")), "Date of Birth");
    private TableCell tbcCitizenship = new TableCell(By.xpath(String.format(parameterPath, "U.S. Citizen/Legally Able to Work:")), "U.S. Citizen/Legally Able to Work");
    private TableCell tbcSSN = new TableCell(By.xpath(String.format(parameterPath, "Social Security Number:")), "Social Security Number:");

    public EligibilityInformationSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Eligibility Information')]"), "Eligibility Information");
    }

    /**
     * Validate data in the eligibility block
     *
     * @param participant - participant
     */
    public void validateEligibilitySection(Participant participant) {
        CustomAssertion.softTrue("Incorrect participant's citizenship", tbcCitizenship.getText().contains(participant.getCitizenship()));
        CustomAssertion.softTrue("Incorrect date of birth", tbcDateOfBirth.getText().contains(participant.getParticipantBioInfo().getDateOfBirth()));
        CustomAssertion.softTrue("Incorrect social security number", tbcSSN.getText().contains(participant.getSsn().substring(5, 9)));
    }
}
