package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import framework.customassertions.CustomAssertion;
import framework.elements.Div;
import org.openqa.selenium.By;

public class MilitarySsSectionForm extends BaseParticipantSsDetailsForm {

    public MilitarySsSectionForm() {
        super(By.xpath("//h3[contains(.,'Military')]"), "Military");
    }

    public void validateMilitary(String militaryType, String discharge, String serviceStart, String serviceEnd) {
        Div dvMilitaryDescription = new Div(By.xpath(String.format(militaryDescriptionXpath, militaryType)), "Military description");
        info(dvMilitaryDescription.getText());
        info(militaryType + " " + serviceStart + " to " + serviceEnd
                + " for ~ Less than a month.\n" + discharge);
        CustomAssertion.softTrue("Incorrect military record", dvMilitaryDescription.getText().contains(militaryType
                + " " + serviceStart + " to " + serviceEnd + " for ~ Less than a month.\n" + discharge));
    }
}
