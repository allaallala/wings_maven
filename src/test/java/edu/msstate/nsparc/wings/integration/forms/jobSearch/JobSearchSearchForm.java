package edu.msstate.nsparc.wings.integration.forms.jobSearch;

import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Job Search -> Search
 */
public class JobSearchSearchForm extends JobSearchBaseForm {

    private ComboBox cmbStatus = new ComboBox("id=status", "Status");

    public JobSearchSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Search Search')]"), "Job Search Search");
    }

    public void performSearch(JobSearch jobSearch) {
        selectParticipant(jobSearch.getTradeEnrollment().getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    public void performSearch(User user, JobSearch jobSearch) {
        selectParticipantByUser(user, jobSearch.getTradeEnrollment().getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    public void performSearchAndOpen(JobSearch jobSearch) {
        performSearch(jobSearch);
        openFirstSearchResult();
    }

    public void searchByCompanyAndOpen(JobSearch jobSearch) {
        inputCompanyName(jobSearch.getCompanyName());
        clickAndWait(BaseButton.SEARCH);
        openFirstSearchResult();
    }

    public void selectStatus(String value) {
        cmbStatus.select(value);
    }
}
