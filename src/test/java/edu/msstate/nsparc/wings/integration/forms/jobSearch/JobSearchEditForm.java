package edu.msstate.nsparc.wings.integration.forms.jobSearch;

import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import framework.CommonFunctions;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Job Search -> Search for record -> Open record -> Click on Edit button
 */
public class JobSearchEditForm extends JobSearchBaseForm {

    private RadioButton rbApproved = new RadioButton("css=input[id='isApproved1']", "Approved");
    private RadioButton rbDenied = new RadioButton("css=input[id='isApproved2']", "Denied");
    private TextBox txbStatusDeterminationDate = new TextBox("css=input[id='dateStatusDetermination']", "Status Determination Date");
    private TextBox txbReasonDenied = new TextBox("id=deniedReason", "Reason Denied");
    private TextBox txbCompanyName = new TextBox("id=companyName","Company name");


    public JobSearchEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Search Edit')]"), "Job Search Edit");
    }

    public void editDetails(JobSearch jobSearch) {
        if (jobSearch.isApproved()) {
            rbApproved.click();
            txbStatusDeterminationDate.type(jobSearch.getStatusDeterminationDate());
        } else {
            rbDenied.click();
            txbReasonDenied.type(CommonFunctions.getRandomAlphanumericalCode(6));
            txbStatusDeterminationDate.type(jobSearch.getApplicationDate());
        }
    }

    public void editCompanyName(String newCompany){
        txbCompanyName.type(newCompany);
    }
}
