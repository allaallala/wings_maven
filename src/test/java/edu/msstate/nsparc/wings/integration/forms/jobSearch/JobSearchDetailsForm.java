package edu.msstate.nsparc.wings.integration.forms.jobSearch;

import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Job Search -> Search for record -> Open record
 */
public class JobSearchDetailsForm extends JobSearchBaseForm {

    private String detailPath = "//td[contains(text(),'%1$s')]/following-sibling::td";
    private TableCell tbcParticipant = new TableCell(String.format(detailPath, "Participant:"), "Participant");
    private TableCell tbcTradeEnrollment = new TableCell(String.format(detailPath, "Trade Enrollment:"), "Trade Enrollment");
    private TableCell tbcCompanyName = new TableCell(String.format(detailPath, "Company Name:"), "Company Name");
    private TableCell tbcMileage = new TableCell(String.format(detailPath, "Mileage:"), "Mileage");
    private TableCell tbcApplicationDate = new TableCell(String.format(detailPath, "Application Date:"), "Application Date");
    private TableCell tbcInterviewDate = new TableCell(String.format(detailPath, "Interview Date:"), "Interview Date");
    private TableCell tbcStatusDeterminationDate = new TableCell(String.format(detailPath, "Status Determination Date:"), "Status Determination Date");
    private TableCell tbcStatus = new TableCell(String.format(detailPath, "Status:"), "Status");

    public JobSearchDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Search Detail')]"), "Job Search Detail");
    }

    public void validateInformation(JobSearch jobSearch) {
        CustomAssertion.softAssertContains(tbcParticipant.getText(), jobSearch.getTradeEnrollment().getParticipant().getFirstName(),
                "Incorrect participant first name");
        CustomAssertion.softAssertContains(tbcTradeEnrollment.getText(), jobSearch.getTradeEnrollment().getPetition().getNumber(),
                "Incorrect trade enrollment number");
        CustomAssertion.softAssertContains(tbcCompanyName.getText(), jobSearch.getCompanyName(),
                "Incorrect company name");
        CustomAssertion.softAssertContains(tbcMileage.getText(), jobSearch.getMileage(),
                "Incorrect mileage");
        CustomAssertion.softAssertContains(tbcApplicationDate.getText(), jobSearch.getApplicationDate(),
                "Incorrect job application date");
        CustomAssertion.softAssertContains(tbcInterviewDate.getText(), jobSearch.getInterviewDate(),
                "Incorrect interview date");
        if (jobSearch.getStatusDeterminationDate() != null) {
            CustomAssertion.softAssertContains(tbcStatusDeterminationDate.getText(), jobSearch.getStatusDeterminationDate(),
                    "Incorrect status determination date");
        }
    }

    public String getStatusTextPage() {
        return tbcStatus.getText();
    }

    public void checkEditButton(Boolean present) {
        if (present) {
            BaseButton.EDIT.getButton().assertIsPresentAndVisible();
        } else {
            BaseButton.EDIT.getButton().assertIsNotPresent();
        }
    }
}
