package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantBaseForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * This form is opened from Participant Details form by clicking on 'Add Employment Record' button
 */
public class ParticipantAddEmploymentForm extends ParticipantBaseForm {

    private static final String STATE = "Mississippi";
    private String osocTitle = "Cooks, Restaurant";
    private String osocSelfService = "Sales managers";
    private String reasonLeaving = "Layoff";
    private String jobTitle = "Cook";
    private String employerName = "Automation";

    private TextBox txbEmployerName = new TextBox("css=input[id='tmpPreviousJob.employer']", "Employer Name");
    private TextBox txbLineOne = new TextBox("css=input[id='tmpPreviousJob.address.lineOne']", "Address - Line One");
    private TextBox txbLineTwo = new TextBox("css=input[id='tmpPreviousJob.address.lineTwo']", "Address - Line One");
    private TextBox txbCity = new TextBox("css=input[id='tmpPreviousJob.address.city']", "Address - City");
    private ComboBox cmbState = new ComboBox("css=select[id='tmpPreviousJob.address.state']", "Address - State");
    private TextBox txbZipCode = new TextBox("css=input[id='tmpPreviousJob.address.zipcode']", "Address - Zip Code");
    private ComboBox cmbCounty = new ComboBox("css=select[id='tmpPreviousJob.address.county']", "Address - County");
    private ComboBox cmbCountry = new ComboBox(By.id("tmpPreviousJob.address.country"), "Country");

    private TextBox txbJobTitle = new TextBox("css=input[id='tmpPreviousJob.jobTitle']", "Job Title");
    private TextBox txbStartDate = new TextBox("id=tmpPreviousJob.dateStart", "Start Date");
    private Button btnAdd = new Button("id=add", "Add");
    private ComboBox cmbReasonForLeaving = new ComboBox("css=select[id='tmpPreviousJob.reasonLeave']", "Reason for Leaving");
    private TextBox txbEndDate = new TextBox("css=input[id='tmpPreviousJob.dateEnd']", "End Date");
    private RadioButton rbRreYes = new RadioButton(By.id("tmpPreviousJob.receivedRapidResponse1"), "Receive Rapid Response Event - Yes");
    private RadioButton rbRreNo = new RadioButton(By.id("tmpPreviousJob.receivedRapidResponse2"), "Receive Rapid Response Event - No");
    private TextBox txbHoursPerWeek = new TextBox("css=input[id='tmpPreviousJob.hoursPerWeek']", "Hours Per Week");
    private TextBox txbWages = new TextBox("css=input[id='tmpPreviousJob.wage']", "Wages");
    private Button btnSearchRre = new Button(By.xpath("//span[@id='rapidResponseEventLookup']/a/button[1]"), "Search rapid response event");
    private RadioButton rbRrAssistanceNo = new RadioButton(By.id("tmpPreviousJob.receivedRapidResponseAdditional2"), "Rapid Response Additional Assistance - No");

    private Div dvOSOCSelectedValues = new Div("//div[text()[contains(.,'Jobs you performed')]]/..//ul", "Selected OSOC values");

    private TextBox txbOsocLookup = new TextBox(By.xpath("//div[@id='osoclookup']//input[@class[contains(.,'power-lookup')]]"), "OSOC lookup field");
    private TextBox txbOsocTitle = new TextBox(By.xpath("//input[@id='occTitle'] | //input[@id='tmpPreviousJob.osocTitle']"), "Osoc Title");
    private Link lnkActiveRecord = new Link(By.xpath("//div//strong"), "Active record");
    private RadioButton rbCurrentlyWorkingYes = new RadioButton(By.xpath("//input[@id='tmpPreviousJob.currentEmployer1']"), "Currently Working - Yes");
    private RadioButton rbContactThisEmployer = new RadioButton(By.id("tmpPreviousJob.canContactCurrent2"), "Contact this employer - No");
    private ComboBox cmbEarnType = new ComboBox(By.id("tmpPreviousJob.wagePer"), "Earn per hour, day, month, etc.");
    private RadioButton rbSpecialLicenceYes = new RadioButton(By.id("tmpPreviousJob.hadLicense1"), "Special Licence for this job - Yes");
    private Button btnRemoveJob = new Button(By.xpath("//span[@class='glyphicon glyphicon-remove-sign']"), "Remove job performed");
    private Button btnToolsTech = new Button(By.xpath("//button[contains(.,'None selected')]"), "Tools and technologies used");
    private Button btnEclipseSoft = new Button(By.xpath("//label[contains(.,'Eclipse software')]"), "Eclipse software");
    private TextBox txbJobDuties = new TextBox(By.id("tmpPreviousJob.duties"), "Describe you job");

    public ParticipantAddEmploymentForm() {
        super(By.xpath("//h1[contains(.,'Add New Employment Record')]"), "Add New Employment Record");
    }

    public ParticipantAddEmploymentForm(String participantSS) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Employment Record')]"), "Add Employment Record for " + participantSS);
    }

    public String addRecord(String date, boolean ended) {
        String employer = CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH);
        addRecord(employer, date, ended);
        clickAddRecord();
        return employer;
    }

    public String addRecord(String employer, String date, boolean ended) {
        selectOsocCode();
        txbEmployerName.type(employer);
        txbCity.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
        cmbState.select(STATE);
        txbZipCode.type(CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH));
        txbJobTitle.type(jobTitle);
        txbStartDate.type(date);
        if (ended) {
            cmbReasonForLeaving.select(reasonLeaving);
            txbEndDate.type(date);
        }
        return employer;
    }

    public void addRecordWithRre(Employer employer, String date, RapidResponseEvent event) {
        selectOsocCode();
        txbEmployerName.type(employer.getEmployerAccount());
        txbCity.type(employer.getAddress().getCity());
        cmbState.select(STATE);
        txbZipCode.type(employer.getAddress().getZipCode());
        txbJobTitle.type(jobTitle);
        txbStartDate.type(date);
        addRre(event);
        rbRrAssistanceNo.click();
        clickAddRecord();
    }

    public void addRecord(Participant participant) {
        txbEmployerName.type(participant.getEmployerName());
        txbCity.type(participant.getAddress().getCity());
        cmbState.select(participant.getAddress().getState());
        txbJobTitle.type(participant.getJobTitle());
        selectOsocTemp(participant.getOsoc());
        txbStartDate.type(CommonFunctions.getYesterdayDate());
        clickAddRecord();
    }

    public void addRecordSelfService() {
        Link lnkActive = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]", osocSelfService)),
                "Active record");
        rbCurrentlyWorkingYes.click();
        txbJobTitle.type(jobTitle);
        txbOsocTitle.scrollTo();
        txbOsocTitle.type(osocSelfService);

        Browser.getDriver().getMouse().mouseMove(lnkActive.getElement().getCoordinates());
        JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
        jse.executeScript("arguments[0].click();", lnkActive.getElement());


        txbEmployerName.type(employerName);

        txbCity.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
        cmbState.select(STATE);
        txbZipCode.type(CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH));

        txbStartDate.type(CommonFunctions.getYesterdayDate());
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    public void addRecordStaff() {
        txbEmployerName.type(employerName);
        txbCity.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
        cmbState.select(STATE);
        txbZipCode.type(CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH));
        txbJobTitle.type(jobTitle);
        selectOsocCode();
        txbStartDate.type(CommonFunctions.getCurrentDate());
        clickAddRecord();
    }

    public void addRecordStaffAndContinue() {
        addRecordStaff();
        clickButton(Buttons.Continue);

    }

    private void selectOsocCode() {
        selectOsocTemp(osocTitle);
    }

    private void clickOsoc(String osoc) {
        Link lnkActive = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]", osocSelfService)),
                "Active record");
        txbOsocTitle.type(osoc);

        Browser.getDriver().getMouse().mouseMove(lnkActive.getElement().getCoordinates());
        JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
        jse.executeScript("arguments[0].click();", lnkActive.getElement());
    }

    private void selectOsocTemp(String osoc) {
        Link lnkOsocRecord = new Link(By.xpath(String.format("//li[@role='menuitem']/a[contains(.,'%1$s')]", osoc)),
                "Suggested osoc record");
        txbOsocLookup.type(osoc);
        lnkOsocRecord.clickAndWait();
    }

    public void addRecord(PreviousJob job) {
        info("adding Employment Record....");
        txbEmployerName.type(job.getEmployer());
        txbLineOne.type(job.getLineOne());
        txbCity.type(job.getCity());
        cmbState.select(job.getState());
        txbZipCode.type(job.getZipCode());
        cmbCounty.select(job.getCounty());
        txbJobTitle.type(job.getJobTitle());

        selectOsocTemp(job.getOsocTitle());
        txbStartDate.type(job.getStartDate());
        if (job.isEnded()) {
            cmbReasonForLeaving.select(job.getReasonForLeaving());
            txbEndDate.type(job.getEndDate());
        }
        txbHoursPerWeek.type(job.getHoursPerWeek());
        txbWages.type(job.getWages());

        clickAddRecord();
    }

    public void selectOsocSelfServices(String title) {
        txbEmployerName.type(title);
        txbOsocTitle.type(title);

        Browser.getDriver().getMouse().mouseMove(lnkActiveRecord.getElement().getCoordinates());
        JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
        jse.executeScript("arguments[0].click();", lnkActiveRecord.getElement());

    }

    private void addRre(RapidResponseEvent event) {
        rbRreYes.click();
        btnSearchRre.clickAndWait();
        RapidResponseEventSearchForm searchRrePage = new RapidResponseEventSearchForm();
        searchRrePage.performSearch(event);
        searchRrePage.selectReturn();
    }

    public void clickAddRecord() {
        btnAdd.clickAndWait();
    }

    public void inputJobTitle(String jobTitle) {
        txbJobTitle.type(jobTitle);
    }

    public String getOSOCValues() {
        return dvOSOCSelectedValues.getText().trim();
    }

    public void addEmployRecord(Participant participant, String[] params) {
        txbEmployerName.type(participant.getEmployerName());
        txbJobTitle.type(participant.getJobTitle());
        rbCurrentlyWorkingYes.click();
        txbStartDate.type(params[0]);
        txbHoursPerWeek.type(params[1]);
        rbContactThisEmployer.click();
        txbWages.type(params[2]);
        cmbEarnType.select(params[3]);
        rbSpecialLicenceYes.click();
        txbLineOne.type(participant.getAddress().getLineOne());
        txbLineTwo.type(participant.getAddress().getLineOne());
        txbCity.type(participant.getAddress().getCity());
        cmbState.select(participant.getAddress().getState());
        txbZipCode.type(participant.getAddress().getZipCode());
        cmbCounty.select(participant.getAddress().getCounty());
        cmbCountry.select(participant.getAddress().getCountry());
        rbRreNo.click(); //TODO Left 'No' until https://jira.nsparc.msstate.edu/browse/WINGS-9351 is fixed.
        btnRemoveJob.click();
        clickOsoc(params[4]);
        btnToolsTech.click();
        btnEclipseSoft.click();
        txbJobDuties.click();
        txbJobDuties.type(participant.getParticipantAccount());
    }
}
