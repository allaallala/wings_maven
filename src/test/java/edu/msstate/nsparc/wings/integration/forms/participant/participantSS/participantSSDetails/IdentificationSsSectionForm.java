package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class IdentificationSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcName = new TableCell(By.xpath(String.format(parameterPath, "Name:")), "Name:");
    private TableCell tbcGender = new TableCell(By.xpath(String.format(parameterPath, "Gender:")), "Gender:");
    private TableCell tbcEthnicity = new TableCell(By.xpath(String.format(parameterPath, "Ethnicity/Race:")), "Ethnicity/Race:");

    public IdentificationSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Identification Information')]"), "Identification Information");
    }


    /**
     * Validate data in the identification block
     *
     * @param participant - participant to check
     * @param base        - if it's default validation
     */
    public void validateIdentificationSection(Participant participant, Boolean base) {
        Div dvTitle = new Div(By.xpath(String.format(titlePath, participant.getFirstName())), "Title participant");
        if (base) {
            CustomAssertion.softTrue("Incorrect participant title", dvTitle.getText().contains(participant.getFirstName() + " " + participant.getLastName()));
            CustomAssertion.softTrue("Incorrect participant name, surname", tbcName.getText().contains(participant.getFirstName() + " " + participant.getLastName()));
            CustomAssertion.softTrue("Incorrect participant gender", tbcGender.getText().contains(participant.getParticipantBioInfo().getGender()));
        } else {
            CustomAssertion.softTrue("Incorrect participant title", dvTitle.getText().contains(participant.getParticipantBioInfo().getPrefix() + " "
                    + participant.getFirstName() + " " + participant.getLastName() + " " + participant.getParticipantBioInfo().getSuffix() + " (" + participant.getParticipantBioInfo().getPreferredName() + ")"));
            CustomAssertion.softTrue("Incorrect participant names", tbcName.getText().contains(participant.getParticipantBioInfo().getPrefix() + " "
                    + participant.getFirstName() + " " + participant.getLastName() + " " + participant.getParticipantBioInfo().getSuffix() + " (" + participant.getParticipantBioInfo().getPreferredName() + ")"));
            CustomAssertion.softTrue("Incorrect participant gender", tbcGender.getText().contains(participant.getParticipantBioInfo().getGender()));
            CustomAssertion.softTrue("Incorrect ethnicity hispanic", tbcEthnicity.getText().contains(participant.getParticipantNations().getHispanic()));
            CustomAssertion.softTrue("Incorrect ethnicity alaskan", tbcEthnicity.getText().contains(participant.getParticipantNations().getAlaskan()));
            CustomAssertion.softTrue("Incorrect ethnicity asian", tbcEthnicity.getText().contains(participant.getParticipantNations().getAsian()));
            CustomAssertion.softTrue("Incorrect ethnicity black", tbcEthnicity.getText().contains(participant.getParticipantNations().getBlack()));
            CustomAssertion.softTrue("Incorrect ethnicity hawaiian", tbcEthnicity.getText().contains(participant.getParticipantNations().getHawaiian()));
            CustomAssertion.softTrue("Incorrect ethnicity white", tbcEthnicity.getText().contains(participant.getParticipantNations().getWhite()));
        }
    }
}
