package edu.msstate.nsparc.wings.integration.forms.jobSearch;

import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import framework.elements.ComboBox;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Job Search -> Create
 */
public class JobSearchCreateForm extends JobSearchBaseForm {

    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private TextBox txbContactName = new TextBox("css=input[id='contactName']", "Contact Name");
    private TextBox txbPhoneNumber = new TextBox("css=input[id='contactInformation.primaryPhone']", "Phone Number");
    private TextBox txbEmail = new TextBox("css=input[id='contactInformation.emailAddress']", "Email");
    private TextBox txbMileage = new TextBox("css=input[id='mileage']", "Mileage");
    private TextBox txbInterviewDate = new TextBox("css=input[id='dateInterview']", "Interview Date");
    private TableCell tbcInterviewDateValidationMessage = new TableCell("id=dateInterview.errors", "Validation message");

    /**
     * Default constructor
     */
    public JobSearchCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Search Creation')]"), "Job Search Creation");
    }

    /**
     * Filling out the creation form
     *
     * @param jobSearch Object with Job Search data
     */
    public void fillOutCreationForm(JobSearch jobSearch) {
        selectParticipant(jobSearch.getTradeEnrollment().getParticipant());
        cmbTradeEnrollment.selectFirst();
        inputCompanyName(jobSearch.getCompanyName());
        inputLocationCityZip(jobSearch.getLineOne(), jobSearch.getCity(), jobSearch.getZipCode());
        selectLocationState(jobSearch.getState());
        selectLocation(jobSearch.getCounty());
        txbContactName.type(jobSearch.getContactName());
        txbPhoneNumber.type(jobSearch.getPhoneNumber());
        txbEmail.type(jobSearch.getEmail());
        txbMileage.type(jobSearch.getMileage());
        inputApplicationDate(jobSearch.getApplicationDate());
        txbInterviewDate.type(jobSearch.getInterviewDate());
    }

    /**
     * Check that validation message is displayed
     *
     * @param message Expected validation message
     */
    public void validateErrorMessage(String message) {
        checkField(tbcInterviewDateValidationMessage, message, false);
    }


    /**
     * Input interview date
     *
     * @param interviewDate - interview date
     */
    public void inputInterviewDate(String interviewDate) {
        txbInterviewDate.type(interviewDate);
    }
}
