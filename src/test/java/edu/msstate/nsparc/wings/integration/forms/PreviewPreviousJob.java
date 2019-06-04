package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This page is displayed while previewing Employment History record
 */
public class PreviewPreviousJob extends BaseWingsForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String xpathPreview = "//div[@id='previewPage']" + detailPath;
    private TableCell tbcEmployerName = new TableCell(By.xpath(String.format(xpathPreview, "Employer Name:")), "Employer Name");
    private TableCell tbcJobTitle = new TableCell(By.xpath(String.format(xpathPreview, "Job Title:")), "Job Title");
    private TableCell tbcStartDate = new TableCell(By.xpath(String.format(xpathPreview, "Start Date:")), "Start Date");
    private TableCell tbcHoursPerWeek = new TableCell(By.xpath(String.format(xpathPreview, "Hours per Week:")), "Hours per Week");
    private TableCell tbcWages = new TableCell(By.xpath(String.format(xpathPreview, "Wages")), "Wages");
    public static final Button BTN_CLOSE_PREVIEW = new Button(By.id("closePreview"), "Close Preview");

    /**
     * Default constructor
     */
    public PreviewPreviousJob() {
        super(By.xpath("//td[contains(.,'Employer Name')]"), "Preview Previous Job");
    }

    /**
     * Validating displayed Previous Job information
     *
     * @param previousJob Object with expected data
     */
    public void validatePreviousJobInformation(PreviousJob previousJob) {
        CustomAssertion.softAssertEquals(tbcEmployerName.getText(), previousJob.getEmployer(), "Incorrect employer name");
        CustomAssertion.softAssertEquals(tbcJobTitle.getText(), previousJob.getJobTitle(), "Incorrect job title");
        CustomAssertion.softAssertContains(tbcStartDate.getText(), previousJob.getStartDate(), "Incorrect start date");
        CustomAssertion.softAssertContains(tbcHoursPerWeek.getText(), previousJob.getHoursPerWeek(), "Incorrect hours per week");
        CustomAssertion.softAssertContains(tbcWages.getText(), previousJob.getWages(), "Incorrect wages");
    }
}
