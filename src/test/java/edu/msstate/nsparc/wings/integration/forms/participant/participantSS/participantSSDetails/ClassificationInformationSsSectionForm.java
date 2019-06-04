package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class ClassificationInformationSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcVeteran = new TableCell(By.xpath(String.format(parameterPath, "Veteran:")), "Veteran:");
    private TableCell tbcGuard = new TableCell(By.xpath(String.format(parameterPath, "Mississippi National Guard:")), "Mississippi National Guard:");
    private TableCell tbcDisabled = new TableCell(By.xpath(String.format(parameterPath, "Disabled:")), "Disabled:");
    private TableCell tbcEmploymentStatus = new TableCell(By.xpath(String.format(parameterPath, "Employment Status:")), "Employment Status:");
    private TableCell tbcDisabilityType = new TableCell(By.xpath(String.format(parameterPath, "Category of your Disability:")), "Category of your Disability:");

    public ClassificationInformationSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Classification Information')]"), "Classification Information");
    }

    /**
     * Validate the classification section of the participant
     *
     * @param disabled         - disabled text
     * @param employmentStatus -employment state
     * @param typeDisability   - disability type
     */
    public void validateClassificationSection(String disabled, String employmentStatus, String typeDisability) {
        CustomAssertion.softTrue("Incorrect veteran sign", tbcVeteran.getText().contains(Constants.YES_ANSWER));
        CustomAssertion.softTrue("Incorrect national guard sign", tbcGuard.getText().contains(Constants.NO_ANSWER));
        CustomAssertion.softTrue("Incorrect disabled state", tbcDisabled.getText().contains(disabled));
        CustomAssertion.softTrue("Incorrect employment status", tbcEmploymentStatus.getText().contains(employmentStatus));
        if (!typeDisability.equalsIgnoreCase(Constants.EMPTY)) {
            CustomAssertion.softTrue("Incorrect participant gender", tbcDisabilityType.getText().contains(typeDisability));
        }
    }

}
