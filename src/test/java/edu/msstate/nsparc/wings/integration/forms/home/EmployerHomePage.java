package edu.msstate.nsparc.wings.integration.forms.home;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.ResourcesAndInformationForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import framework.elements.Button;
import framework.elements.Link;
import org.openqa.selenium.By;

/**
This form is opened when after Employer log in for Self-Services
 */
public class EmployerHomePage extends BaseWingsForm {

    private Link lnkMyProfile = new Link("//a[text()[contains(.,'My Profile')]]", "My Profile");
    private Link lnkCandidateSearchTopMenu = new Link("//a[text()[contains(.,'Candidate Search')]]", "Candidate Search");
    private Link lnkViewApplicants = new Link("//a[text()[contains(.,'View Applicants')]]", "View Applicants");
    private Link lnkSearchCandidates = new Link("//a[text()[contains(.,'Search for Candidates')]]", "Search Candidates");
    private Link lnkManageJobOpeningsTopMenu = new Link("//a[text()[contains(.,'Manage Openings')]]", "Manage Openings");
    private Link lnkCreateOpening = new Link("//a[text()[contains(.,'Create Opening')]]", "Create Opening");
    private Link lnkMyOpenings = new Link("//a[text()[contains(.,'My Openings')]]", "My Openings");
    private Link lnkResourcesAndInformationTopMenu = new Link("//a[text()[contains(.,'Resources')]]", "Resources");
    private Link lnkFindJobCenter = new Link("//a[text()='Find a WIN Job Center']", "Find a WIN Job Center");
    private Link lnkResourcesAndInformation = new Link("//a[text()[contains(.,'Resources')]]/..//ul//a[text()[contains(.,'Resources & Information')]]", "Resources & Information");
    private Button btnSearch = new Button(By.xpath("//button[@id='search-button']"), "Search button");

    public EmployerHomePage() {
        super(By.xpath("//table[@id='participant-matches-table']"), "Employer home page");
    }

    public EmployerHomePage(String companyName) {
        super(By.xpath(String.format("//div[@id='user-name'][contains(.,'%1$s')]", companyName)),"Employer home page");
    }

    public ResourcesAndInformationForm openResourceInformationForm() {
        lnkResourcesAndInformationTopMenu.click();
        lnkResourcesAndInformation.clickAndWait();
        return new ResourcesAndInformationForm();
    }

    public void clickMyProfile() {
        lnkMyProfile.clickAndWait();
    }

    public void viewApplicants() {
        lnkCandidateSearchTopMenu.click();
        lnkViewApplicants.clickAndWait();
    }

    public void searchCandidates() {
        lnkCandidateSearchTopMenu.click();
        lnkSearchCandidates.clickAndWait();
    }

    public void clickSearch() {
        btnSearch.click();
    }

    public JobOrderCreationForm openJobOrderCreate() {
        lnkManageJobOpeningsTopMenu.click();
        lnkCreateOpening.clickAndWait();
        return new JobOrderCreationForm(Constants.JOB_ORDER_SS);
    }

    public JobOrderSearchForm openJobOrderSearch() {
        lnkManageJobOpeningsTopMenu.click();
        lnkMyOpenings.clickAndWait();
        return new JobOrderSearchForm(Constants.JOB_ORDER_SS);
    }

    public JobCenterSearchForm openJobCenterSearch() {
        lnkResourcesAndInformationTopMenu.click();
        lnkFindJobCenter.clickAndWait();
        return new JobCenterSearchForm(Constants.EMPLOYER);
    }
}

