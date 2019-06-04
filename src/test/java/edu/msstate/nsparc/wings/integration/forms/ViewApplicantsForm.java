package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.elements.Link;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * To open this form you need to log in as Employer -> Click on 'View Applicants' button
 */
public class ViewApplicantsForm extends BaseWingsForm {

    private TextBox txbJobTitle = new TextBox("id=jobOrder.jobTitle", "Job Title");
    private String matchesTablePath = "//table[@id='job-matches-table']/%1$s";
    private Link lnkJobDetails = new Link(By.xpath(String.format(matchesTablePath, "/td[2]//a")), "Job Details link");
    private Link lnkResume = new Link(By.xpath(String.format(matchesTablePath, "/td[1]//a")), "Resume");
    private Link lnkSetDate = new Link(By.xpath(String.format(matchesTablePath, "/td[3]//a")), "Set Date");
    private TableCell tbcDate = new TableCell(By.xpath(String.format(matchesTablePath, "/td[3]")), "Date");
    private Link lnkUpdateStatus = new Link(By.xpath(String.format(matchesTablePath, "/td[4]//a")), "Update Status");
    private TableCell tbcStatus = new TableCell(By.xpath(String.format(matchesTablePath, "/td[4]")), "Status");
    private TableCell tbcScreeningScore = new TableCell(By.xpath(String.format(matchesTablePath, "/td[6]")), "Screening Score");

    /**
     * Default Constructor
     */
    public ViewApplicantsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'View Applicants')]"), "View Applicants");
    }

    /**
     * Searching for Application for specified Job Order
     *
     * @param jobOrder Job Order
     */
    public void performSearch(JobOrder jobOrder) {
        txbJobTitle.type(jobOrder.getJobTitle());
        clickButton(Buttons.Search);
    }

    /**
     * Input job title
     *
     * @param value - title of the job
     */
    public void inputJobTitle(String value) {
        txbJobTitle.type(value);
    }

    /**
     * Open job order participantSSDetails form.
     */
    public void openJobOrderDetails() {
        lnkJobDetails.clickAndWait();
    }

    /**
     * Open resume form.
     *
     * @return new resume form.
     */
    public ResumeForm openResumeForm() {
        lnkResume.clickAndWait();
        return new ResumeForm();
    }

    /**
     * Open interview data form.
     */
    public void openInterviewDataForm() {
        lnkSetDate.clickAndWait();
    }

    /**
     * Get data for selected applicants.
     *
     * @return date, status, screening score.
     */
    public String[] getDataText() {
        return new String[]{tbcDate.getText().trim(), tbcStatus.getText().trim(), tbcScreeningScore.getText().trim()};
    }

    /**
     * Open Update Status Form
     */
    public void openUpdateStatusForm() {
        lnkUpdateStatus.clickAndWait();
    }
}
