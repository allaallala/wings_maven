package edu.msstate.nsparc.wings.integration.forms.home;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.*;
import org.openqa.selenium.By;

/**
This form is displayed after Participant S-S login
 */
public class ParticipantHomePage extends BaseWingsForm {

    private Link lnkMyProfile = new Link("//a[text()[contains(.,'My Profile')]]", "My Profile");
    private Link lnkJobSearchTopMenu = new Link(By.xpath("//div[@id='navbar']//a[contains(.,'Job Search')]"), "Job Search");
    private Link lnkArrowJobSearch = new Link(By.xpath("//a[contains(.,'Job Search')]/b[2]"), "Open Job Search DropDown menu");
    private Link lnkPdo = new Link(By.xpath("//ul[@class='dropdown-menu']/li[contains(.,'View Professional Development')]"), "PDO");
    private Link lnkSearchForJobs = new Link("//ul[@class='dropdown-menu']//a[contains(.,'Search for Jobs')]", "Search for Jobs");
    private Button btnFindJobCenter = new Button("//a[text()[contains(.,'Find a WIN Job Center')]]", "Find a WIN Job Center");
    private Link lnkJobTips = new Link("//*[@id='more-triage']/a[1]", "See all Job-Tips");
    private TableCell tbcHelpInformation = new TableCell("//h2[text()='Have questions for MDES?']/..", "Help Information");
    private TableCell tbcTradeBenefitsMessage = new TableCell(By.xpath("//div/h2[contains(.,'Unemployment Insurance Benefits')]"),
            "Trade Benefits Message");
    private Button btnPrintWageForm = new Button("id=printWageForm", "Print Weekly Wage Form");

    private Link lnkViewedToday = new Link(By.xpath("//a[@href='/wings/spring/self-service/job-view/job-view-search']"), "Jobs Viewed Today");
    private Link lnkPendingApp = new Link(By.xpath("//a[@href='/wings/spring/self-service/referral/referral-search?searchResult=PENDING']"),
            "Pending Applications");
    private Link lnkWinJobCenter = new Link(By.xpath("//a[@href='/wings/spring/self-service/job-center/job-center-search']"), "WIN Job Center");
    private Link lnkJobSearchMenu = new Link(By.xpath("//a[@href='/wings/spring/self-service/job-order/job-order-lookup']"), "Job Search Menu");
    private Link lnkApplications = new Link(By.xpath("//a[@href='/wings/spring/self-service/referral/referral-search']"), "View Applications");

    private Div divLocation = new Div(By.id("user-location"));
    private Div divFullName = new Div(By.id("user-name"), "Full Name");
    private Link lnkEditLocation = new Link(By.xpath("//div[@id='user-location']/a"), "Edit Location");
    private Link lnkAdvancedSearch = new Link(By.xpath("//a[text()[contains(.,'Advanced Search')]]"), "Advanced Search");
    private TextBox txbJobSearch = new TextBox(By.id("search-field"), "Job Search");
    protected Button btnSearch = new Button(By.xpath("//button[@type='submit']"), "Search");
    private Link lnkJobMatch = new Link(By.xpath("//span[@class='result-title']"), "Job from Job Matching list");
    private Button btnWhyTheseJobs = new Button(By.xpath("//a[@href='/wings/spring/self-service/participant/participant-notifications']"),
            "Why These Jobs?");
    private Button btnSeeAllMatches = new Button(By.xpath("//h4[@id='homeOccupationTitle']/a[2]"), "See All Matches");
    private Link lnkViewJobVites = new Link(By.xpath("//ul[@class='dropdown-menu']/li[contains(.,'View Job-Vites')]"), "Job-Vites");
    private Link lnkViewApplications = new Link(By.xpath("//ul[@class='dropdown-menu']/li[contains(.,'View Applications')]"), "Applications");

    public ParticipantHomePage(String participantSs) {
        super(By.xpath("//a[text()[contains(.,'My Profile')]]"), String.format("Participant Home Page for %1$s",participantSs));
    }

    public ParticipantHomePage(Participant participant) {
        super(By.xpath(String.format("//div[@id='user-name'][contains(.,'%1$s')]",participant.getFirstName())), "Partcipant profile");
    }

    public ParticipantHomePage(By xpath, String s) {
        super(xpath, s);
    }

    public void printWageIsPresent(Boolean present) {
        if (present) {
            btnPrintWageForm.assertIsPresentAndVisible();
        } else {
            btnPrintWageForm.assertIsNotPresent();
        }
    }

    public void checkTradeBenefitsPresent(Boolean present) {
        if (present) {
            tbcTradeBenefitsMessage.assertIsPresentAndVisible();
        } else {
            tbcTradeBenefitsMessage.softIsNotPresent();
        }
    }

    public void goWinJobCenterS_S() {
        lnkWinJobCenter.clickAndWait();
    }

    public void goApplicationsS_S() {
        lnkJobSearchMenu.click();
        lnkApplications.clickAndWait();
    }

    public void openMyProfile() {
        lnkMyProfile.clickAndWait();
    }

    public void checkProfilePresent() {
        lnkMyProfile.assertIsPresentAndVisible();
    }

    public void openPdo() {
        lnkArrowJobSearch.click();
        lnkPdo.clickAndWait();
    }

    public void searchForJobs() {
        lnkJobSearchTopMenu.click();
        searchJob();
    }

    public void viewJobVites() {
        lnkArrowJobSearch.click();
        lnkViewJobVites.clickAndWait();
    }

    public void viewApplications() {
        lnkArrowJobSearch.click();
        lnkViewApplications.clickAndWait();
    }

    private void searchJob() {
        lnkSearchForJobs.clickAndWait();
    }

    public void findJobCenter() {
        btnFindJobCenter.clickAndWait();
    }

    public String getHelpInformation() {
        return tbcHelpInformation.getText();
    }

    public void clickJobTips() {
        if (lnkJobTips.isPresent()) {
            lnkJobTips.clickAndWait();
        }
    }

    public String getTextOfViewed() {
        return lnkViewedToday.getText();
    }

    public String getTextOfPending() {
        return lnkPendingApp.getText();
    }

    public String getTextOfLocation() {
        return divLocation.getText();
    }

    public String getParticipantName() {
        return divFullName.getText();
    }

    public void clickEditLocation() {
        lnkEditLocation.clickAndWait();
    }

    public void clickAdvancedSearch() {
        lnkAdvancedSearch.clickAndWait();
    }

    public String getJobTitle() {
        return lnkJobMatch.getText();
    }

    public void clickFirstJob() {
        lnkJobMatch.clickAndWait();
    }

    public void clickWhyTheseJobs() {
        btnWhyTheseJobs.clickAndWait();
    }

    public String getNumberOfMatches() {
        String[] array = btnSeeAllMatches.getText().split("(?=\\d)",2);
        String[] array2 = array[1].trim().split("( Matches)",2);
        return  array2[0].trim();
    }

    public void clickSeeAllMatches() {
        btnSeeAllMatches.clickAndWait();
    }
}
