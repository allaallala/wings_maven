package edu.msstate.nsparc.wings.integration.forms.employer.employerSS;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * Job Candidate Search Form
 * Created by User on 26.02.2017.
 */
public class JobCandidateSearchForm extends BaseWingsForm {
    private TableCell tbcFirstCandidate = new TableCell(By.xpath("//tr[@class='job-results-list-row']/td/a"), "First Candidate Search");
    public JobCandidateSearchForm() {
       super(By.xpath("//div[@id='heading-title'][contains(.,'Job Candidate Search')]"), "Job Candidate Search");
    }

    public void clickFirstCandidate() {
        tbcFirstCandidate.clickAndWait();
    }
}
