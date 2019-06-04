package edu.msstate.nsparc.wings.integration.forms.employer.employerSS;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
 * Created by User on 26.02.2017.
 */
public class JobCandidateDetailsForm extends BaseWingsForm {
    private Button btnApply = new Button(By.id("apply"), "Apply");
    private Button btnLogin = new Button(By.xpath("//a[@href[contains(.,'eventId=login')]]"), "Login button");
    private Button btnVisit = new Button(By.xpath("//a[@href[contains(.,'http://www.mdes.ms.gov/win-job-centers/')]]"), "Visit button");
    private Link lnkNoAccount = new Link(By.xpath("//a[@href[contains(.,'/wings/employerInstructions.jsp')]]"), "No Account? link");
    private Link lnkCanvas = new Link(By.id("piechart-canvas"), "Employment canvas");
    private Span spDgaaWinner = new Span(By.xpath("//img[@src='/wings/images/dgaa-badge.gif']"), "DGAA winner");
    private Span spMissWorks = new Span(By.xpath("//img[@src='/wings/images/partnerLogo.gif']"), "Mississippi works");
    /**
     * Default constructor
     */
    public JobCandidateDetailsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Job Candidate Details')]"), "Job Candidate Details");
    }

    /**
     * Click [I'm interested]
     */
    public void clickInterested() {
        btnApply.clickAndWait();
    }

    /**
     * Check default buttons, links on the candidate participantSSDetails form.
     */
    public void checkDefaultButtonsLinks() {
        btnLogin.isPresent();
        btnVisit.isPresent();
        lnkNoAccount.isPresent();
        lnkCanvas.isPresent();
        spDgaaWinner.isPresent();
        spMissWorks.isPresent();
    }
}
