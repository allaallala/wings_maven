package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class EducationSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcEducation = new TableCell(By.xpath("//div[contains(.,'Education')]/following-sibling::table//td[2]"), "Education section");
    private String educationWarn = "One or more of your Education records cannot be edited because it has been verified as accurate.\n"
            + "Verified records are indicated below with a check-mark. Please contact your local WIN Service Center if the information is not correct.";
    private Div dvAlertText = new Div(By.xpath("//div[@class='alert alert-info']"), "Alert text");
    private Div dvEducationText = new Div(By.xpath(String.format(nonRecordsXpath, "Education")), "Provided education records");
    private String educationText = "No Education History provided.";

    public EducationSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Education')]"), "Education");
    }

    public void validateEducation(Participant participant) {
        info(tbcEducation.getText());
        info(String.format("%1$s Incomplete, left school on %2$s\nAttended %3$s - %4$s ,%5$s\nGPA: %6$s",
                participant.getDegree(), CommonFunctions.getCurrentDate(), participant.getCertification(),
                participant.getAddress().getCity(), participant.getAddress().getZipCode(), participant.getGpa()));
        CustomAssertion.softTrue("Incorrect text in the education section", tbcEducation.getText().contains(
                String.format("%1$s Incomplete, left school on %2$s\nAttended %3$s - %4$s , %5$s\nGPA: %6$s",
                        participant.getDegree(), CommonFunctions.getCurrentDate(), participant.getCertification(),
                        participant.getAddress().getCity(), participant.getAddress().getZipCode(), participant.getGpa())
        ));
    }

    public void removeEducationRecord() {
        Button btnRemoveButton = new Button(ParticipantDetailsButtons.REMOVE_EDUCATION.getLocator(), ParticipantDetailsButtons.REMOVE_EDUCATION.getName());
        if (btnRemoveButton.isPresent()) {
            btnRemoveButton.click();
            clickAndWait(BaseButton.ARE_YOU_SURE_YES);
        }

        CustomAssertion.softTrue("Incorrect text, that education record is not present", dvEducationText.getText().contains(educationText));
    }

    public void validateEducationCannotChanged() {
        info(educationWarn);
        info(dvAlertText.getText());
        CustomAssertion.softTrue("Incorrect alert text", dvAlertText.getText().contains(educationWarn));
    }
}
