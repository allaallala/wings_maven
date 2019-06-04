package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;
import webdriver.Browser;

/**
 * This form is displayed for user login.
 * Link: http://wingsqa.nsparc.msstate.edu/wings/welcome.jsp
 */
public class LoginForm extends BaseWingsForm {

    private TextBox txbUsername = new TextBox(By.xpath("//input[@id='IDToken1']"), "Username");
    private Button btnMyAccount = new Button(By.xpath("//a[contains(@href,'/welcome/login')]"), "My Account");
    private Div divBackdoorLoginInfo = new Div(By.id("instruction-section"), "Backdoor Login Info");
    private Div divCandidate = new Div(By.id("public-home-candidate-descrip"), "Candidate Pane");
    private TextBox txbSearchCandidate = new TextBox("css=input[id='keyword']", "Search for Candidates");
    private Div divTitle = new Div("css=div[id=heading-title]", "Title of the page");
    private Link lnkFindAJob = new Link(By.xpath("//a[@href='/wings/spring/self-service/job-order/job-order-lookup']"), "Find A Job");
    private Link lnkPDO = new Link(By.xpath("//a[@href='/wings/spring/self-service/professional-development-opportunity/professional-development-opportunity-lookup']"), "See PDO");
    private Link lnkNewUser = new Link(By.xpath("//a[@href='/wings/register.jsp']"), "New User");
    private Link lnkJobSeeker = new Link(By.xpath("//a/strong[.='Job Seeker']"), "Job seeker: register now");
    private String selectedRecord = "//li[@role='menuitem']/a[contains(.,'%1$s')]";
    /**
     * Default constructor
     */
    public LoginForm() {
        super(properties.getProperty("urlLoginPage"), By.xpath("//a[text()='My Account']"), "Login");
    }

    /**
     * Constructor of the form with defined title and locator of the form
     *
     * @param locator - locator of the form
     */
    public LoginForm(String locator) {
        super(By.xpath("//div[@id='instruction-section']"), "Login Form with specified " + locator);
    }

    /**
     * This method is used for login as Employer
     */
    public void loginEmployer() {
        login(AccountUtils.getEmployerAccount(), AccountUtils.getDefaultPassword());
    }

    /**
     * This method is used for login as Admin
     */
    public void loginAdmin() {
        login(AccountUtils.getAdminAccount(), AccountUtils.getAdminAccount());
    }

    /**
     * This method is used for login as Admin1
     */
    public void loginAdmin1() {
        login(AccountUtils.getAdminAccount1(), AccountUtils.getAdminAccount1());
        StaffLocationForm staffPage = new StaffLocationForm();
        staffPage.clickButton(Buttons.Continue);
    }

    /**
     * This method is used for login as area director.
     */
    public void loginAreaDirector() {
        login(AccountUtils.getAreaDirectorAccount(), AccountUtils.getAreaDirectorAccount());
    }

    /**
     * This method is used for login as Project Code administrator
     */
    public void loginProjectCodeAdmin() {
        login(AccountUtils.getProjectCodeAdminAccount(), AccountUtils.getProjectCodeAdminAccount());
    }

    /**
     * This method is used for login as Staff (main account)
     */
    public void loginStaff() {
        login(AccountUtils.getStaffAccount(), AccountUtils.getStaffAccount());
    }

    /**
     * This method is used for login as Staff (additional account)
     */
    public void loginStaff02() {
        login(AccountUtils.getStaff02Account(), AccountUtils.getStaff02Account());
    }


    /**
     * Login to the system as rapid response director
     */
    public void loginRapidResponseDirector() {
        login(AccountUtils.getRapidResponseDirectorAccount(), AccountUtils.getRapidResponseDirectorAccount());
    }

    /**
     * This method is used for login as Trade Director
     */
    public void loginTradeDirector() {
        login(AccountUtils.getTradeDirectorAccount(), AccountUtils.getTradeDirectorAccount());
    }

    /**
     * This method is used for login as Executive
     */
    public void loginExecutive() {
        login(AccountUtils.getExecutiveAccount(), AccountUtils.getExecutiveAccount());
    }

    /**
     * This method is used for login as Manager
     */
    public void loginManager() {
        login(AccountUtils.getManagerAccount(), AccountUtils.getManagerAccount());
    }

    /**
     * This method is used for login as Youth Provider
     */
    public void loginYouthProvider() {
        login(AccountUtils.getYouthProviderAccount(), AccountUtils.getYouthProviderAccount());
    }

    /**
     * This method is used for login as DVOP
     */
    public void loginDvop() {
        login(AccountUtils.getDVOPAccount(), AccountUtils.getDVOPAccount());
    }

    /**
     * This method is used for login as LVER
     */
    public void loginLver() {
        login(AccountUtils.getLVERAccount(), AccountUtils.getLVERAccount());
    }

    /**
     * This method is used for login as Intake Staff
     */
    public void loginIntakeStaff() {
        login(AccountUtils.getIntakeStaffAccount(), AccountUtils.getIntakeStaffAccount());
    }

    /**
     * This method is used for login as EVerify Admin
     */
    public void loginEVerifyAdmin() {
        login(AccountUtils.getEVerifyAdminAccount(), AccountUtils.getEVerifyAdminAccount());
    }

    /**
     * This method is used for login as WIOA Admin
     */
    public void loginWIOAAdmin() {
        login(AccountUtils.getWIOAAdminAccount(), AccountUtils.getWIOAAdminAccount());
    }

    public void loginWIOAAdminPLUS() {
        login(AccountUtils.getWIOAAdminPLUSAccount(), AccountUtils.getWIOAAdminPLUSAccount());
    }

    /**
     * This method is used for login as LWDA Staff
     */
    public void loginLWDAStaff() {
        login(AccountUtils.getLWDAStaffAccount(), AccountUtils.getLWDAStaffAccount());
    }

    /**
     * This method is used for login as WIOA Provider
     */
    public void loginWIOAProv() {
        login(AccountUtils.getWIOAProvAccount(), AccountUtils.getWIOAProvAccount());
    }

    /**
     * This method is used for login as Participant
     */
    public void loginParticipant() {
        info("Log in WINGS as Participant");
        login(AccountUtils.getParticipantAccount(), AccountUtils.getDefaultPassword());
    }

    /**
     * This method is used for login with provided credentials
     *
     * @param username Username
     * @param password Password
     */
    public synchronized void login(final String username, final String password) {
        //backdoor login
        String url = "urlBlackdoorLoginPage";
        String pattern = "User %1$s successfully authenticated";
        Browser.getInstance().navigate(String.format(global.getProperty(url), username));

        if (!txbUsername.isPresent()) {
            CustomAssertion.softTrue("User is not authenticated", divBackdoorLoginInfo.getText().contains(String.format(pattern, username)));
            btnMyAccount.clickAndWait();
        }
    }

    public void clickSeePDO() {
        if (!txbSearchCandidate.isPresent()) {
            divCandidate.clickLogin();
        }
        lnkPDO.clickLogin();
    }

    public String getNameOfPage() {
        return divTitle.getText();
    }

    public void openCandidateSearchPage() {
        lnkFindAJob.clickLogin();
    }

    public void clickNewUser() {
        lnkNewUser.clickLogin();
    }

    public Boolean jobSeekerIsPresent() {
        return lnkJobSeeker.isPresent();
    }

    public void clickJobSeeker() {
        lnkJobSeeker.clickAndWait();
    }

}
