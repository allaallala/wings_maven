package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class EmploymentSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcEmployment = new TableCell(By.xpath("//div[contains(.,'Employment')]/following-sibling::table//td[2]"), "Employment section");

    public EmploymentSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Employment')]"), "Employment");
    }


    public void validateEmployment(Participant participant) {
        info(tbcEmployment.getText());
        CustomAssertion.softAssertContains(tbcEmployment.getText(), String.format("%1$s at %2$s %3$s to %4$s for ~ Less than a month.\n%5$s, %6$s Salary: $%7$s.00 / " + "%8$s\nOccupation Type: %9$s\n%10$s\nLeft due to: %11$s", participant.getJobTitle(), participant.getEmployerName(), participant.getEmploymentRecord().getDateStartWorking(), participant.getEmploymentRecord().getDateEndWorking(),
                participant.getAddress().getCity(), participant.getAddress().getZipCode(), participant.getEmploymentRecord().getMoneyEarn(),
                participant.getEmploymentRecord().getPeriod(), participant.getEmploymentRecord().getOsocCode(), participant.getEmploymentRecord().getJobDescription(), participant.getEmploymentRecord().getReasonForLeaving()), "Incorrect text in the employment section.\n");
    }

    public void validateEmployment(Participant participant, String[] dataCheck) {
        info(tbcEmployment.getText());
        CustomAssertion.softTrue("Incorrect text in the employment section", tbcEmployment.getText().contains(String.format("%1$s at %2$s %3$s to Present for ~ Less than a month.\n%4$s, %5$s Salary: $%6$s.00 / "
                        + "%7$s\nOccupation Type: %8$s\nTools Used: Eclipse software\n%9$s\nHad a special license.\nCannot be contacted.", participant.getJobTitle(), participant.getEmployerName(), dataCheck[0],
                participant.getAddress().getCity(), participant.getAddress().getZipCode(), dataCheck[1], dataCheck[3],
                dataCheck[4], participant.getParticipantAccount())));
    }
}
