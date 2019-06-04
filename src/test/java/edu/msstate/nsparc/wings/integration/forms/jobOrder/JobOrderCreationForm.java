package edu.msstate.nsparc.wings.integration.forms.jobOrder;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.StaffSearchForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation.BasicInformationSsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation.DesiredCandidateProfileSsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation.ScreeningQuestionsSsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.BasicInformationStaffForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.JobRequirementsStaffForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.ScreeningQuestionsStaffForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.StaffInformationStaffForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Wagner-Peyser -> Job Orders -> Create
 */
public class JobOrderCreationForm extends JobOrderBaseForm {
    protected static final String COOKS = "Cooks, Restaurant";
    protected static final String BACHELOR_DEGREE = "Bachelor's Degree";
    protected static final String AUTOMATION = "Automation";
    protected String questionType = "Multiple Choice";
    protected String yesAnswer = "Yes";
    protected String driverLicenseClass = "B";
    protected String correctAnswer = "Correct Answer";
    protected String quantity = "10";
    protected String openDate = "11/11/2005";
    protected String defaultState = "Mississippi";
    protected String defaultCounty = "Adams";
    protected String osocPath = "//strong[contains(.,'%1$s')]";
    protected String skillsItemLocator = "//ul[@class='dropdown-menu']//label[contains(.,'%1$s')]";

    // Page #1

    private Span spnEmployerLookUp = new Span("css=span#employerlookupspan", "Employer Lookup");
    private TextBox txbJobTitle = new TextBox("id=jobTitle", "Job Title");
    private RadioButton rbJobPublicSchoolYes = new RadioButton(By.id("isEdJob1"), "Job at Public School - Yes");
    private RadioButton rbJobPublicSchoolNo = new RadioButton(By.id("isEdJob2"), "Job at Public School - No");
    private RadioButton rbAllowOnlineApplications = new RadioButton("css= input#allowOnlineApplications2", "Allow online applications");
    private RadioButton rbAllowAttachDocuments = new RadioButton("css= input#allowDoc2", "Allow attaching documents");
    private TextBox txbOpenings = new TextBox("id=nbrJobOpenings", "Number of Openings");
    private TextBox txbReferrals = new TextBox("id=nbrRefRequested", "Referrals Requested");
    private TextBox txbOpenDate = new TextBox("id=dateOrderOpen", "Open Date");
    private TextBox txbCloseDate = new TextBox("id=dateOrderClose", "Close Date");
    private Span spnOpenDate = new Span(By.xpath("//i[contains(.,'Open Date cannot be in the past.')]"), "Open Date error message");
    private TextArea txbJobDescription = new TextArea("id=tinymce", "Job Description");
    private TableCell tbcLocation = new TableCell(By.xpath("//div[@id='basicInfoCreationForm']//tr//tr/following-sibling::tr"), "Location row");
    private Div dvQuestionTitle = new Div(By.className("question-title"), "Question title div");
    private Button btnPrevious = new Button(By.id("previous"), "Previous");
    private Div dvOpenDateErrors = new Div(By.id("dateOrderOpen.errors"), "Error in the open date field");
    private Div dvFrame = new Div(By.xpath("//iframe[@id='jobDescription_ifr']"), "Job Description Frame");

    // Page #2
    private TextBox txbHoursPerWeek = new TextBox("id=hoursPerWeek", "Hours per Week");
    private RadioButton rbScheduleHoursPartTime = new RadioButton("//input[@name='scheduleHours' and @value='PART_TIME']", "Schedule Hours - Part-Time");
    private RadioButton rbScheduleTermPermanent = new RadioButton("//input[@name='scheduleTerm' and @value='PERMANENT']", "Schedule Term - Permanent");
    private CheckBox chkShiftDays = new CheckBox("id=shiftDay1", "Shift by Days");
    private CheckBox chkWorkMonday = new CheckBox("id=workdayM1", "Work on Monday");
    private RadioButton rbDriversLicenseRequired = new RadioButton("css=input#needDriversLicense1", "Driver's License Required");
    private ComboBox cmbDriversLicenseClass = new ComboBox("id=driversLicense.dlClass", "Drivers License Required Class");
    private RadioButton rbDriversLicenseClassB = new RadioButton("id=driversLicense.dlClass3", "Drivers License - Class B");
    private ComboBox cmbEducationLevelRequired = new ComboBox("css=select[id='reqsEduLevel']", "Education Level Required");
    private Button btnSkills = new Button(By.xpath("//button[contains(@class,'multiselect')]"), "Skills button");
    private CheckBox chbEduOnline = new CheckBox(By.id("blockerEdLevel1"), "Education apply online");
    private CheckBox chbSkillsOnline = new CheckBox(By.id("blockerSkills1"), "Skills apply online");
    private CheckBox chbDrivOnline = new CheckBox(By.id("blockerDl1"), "Driver's license apply online");
    private CheckBox chbLicenseOnline = new CheckBox(By.id("blockerDlClass1"), "License category apply online");
    private Button btnDriverApply = new Button(By.xpath("//input[@value='Apply'][@field='needDriversLicense']"), "Apply");

    // Page #3
    private Div dvNoItemsFound = new Div(By.xpath("//span[@class='pagebanner'][contains(.,'No items found')]"), "No items found");
    private ComboBox cmbQuestionType = new ComboBox("css=select[id='tempScreeningQuestion.questionType']", "Question Type");
    private RadioButton rbQuestionGradedYes = new RadioButton("css=input[id='tempScreeningQuestion.isGraded1']", "Question Graded - Yes");
    private TextBox txbQuestion = new TextBox("id=tempScreeningQuestion.question", "Question");
    private TextBox txbCorrectAnswer = new TextBox("id=tempScreeningQuestion.correctAnswer", "Correct Answer");
    private TextBox txbIncorrectAnswer1 = new TextBox("id=tempScreeningQuestion.incorrectAnswerOne", "Incorrect Answer 1");
    private TextBox txbIncorrectAnswer2 = new TextBox("id=tempScreeningQuestion.incorrectAnswerTwo", "Incorrect Answer 2");
    private TextBox txbIncorrectAnswer3 = new TextBox("id=tempScreeningQuestion.incorrectAnswerThree", "Incorrect Answer 3");
    private Button btnAdd = new Button(By.xpath("//input[@id='addQuestion'] | //button[@id='addQuestion']"), "Add");
    private TableCell tbcQuestionDetails = new TableCell(By.xpath("//table[@id='job-matches-table']//tbody"), "Answers");

    // Page #
    private Button btnRestrictStaffLookup = new Button(By.xpath("//input[@value='Staff']/following-sibling::button[1]"), "Restrict staff member");

    private RadioButton rbAllowDocument = new RadioButton("css=input[id='allowDoc1']", "Document Attachment");

    private Button btnCloneThisJobOrder = new Button("id=clone", "Clone This Job Order");

    private TableCell tbcJobID = new TableCell("//td[contains(text(),'Job Order Number:')]/following-sibling::td", "Job ID table cell");

    private Button btnOsocLookup = new Button("css=div[id='osoclookup'] button", "Search Osoc");
    private TextBox txbOsocTitle = new TextBox(By.xpath("//input[@id='occTitle'] | //input[@id='osocTitle']"), "OSOC title");
    private Button btnSearchOsoc = new Button("id=osocSearch", "Osoc Search");
    private RadioButton rbSelectOsoc = new RadioButton("css=table#results-table  input", "Select Osoc");

    private Link lnkPermalink = new Link("//div[text()[contains(., 'Permalink:')]]//..//a", "Permalink");
    private Button btnCreateAnother = new Button(By.id("createAnother"), "Create Another");

    public JobOrderCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Creation')]"), "Job Order Creation");
    }

    public JobOrderCreationForm(By xpath, String s) {
        super(xpath, s);
    }

    public JobOrderCreationForm(String jobOrderSs) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Create Opening')]"), "Form for " + jobOrderSs + " is opened");
    }

    public JobOrderCreationForm(String clone, String jobOrder) {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Clone')]"), clone + " form for the job order " + jobOrder);
    }

    public void selectEmployer(String companyName, String zipCode, String city) {
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.searchAndReturnEmployer(companyName, zipCode, city);
    }

    public String createJobOrder(JobOrder jobOrder, Boolean allowOnline, boolean question) {
        new BasicInformationStaffForm().typicalFillFirstPage(jobOrder.getEmployer(), jobOrder.getJobTitle());
        new JobRequirementsStaffForm().passWorkingHoursFieldsAndSave(jobOrder);
        new ScreeningQuestionsStaffForm().passQuestionBlockAndCreate(jobOrder, question);

        String jobID = new StaffInformationStaffForm().fillInStaffInfoAngGetJobOrderId(allowOnline);

        return jobID;
    }

    public void selectOsocCode(String text) {
        btnOsocLookup.clickAndWait();
        waitDivLoading();
        txbOsocTitle.type(text);
        btnSearchOsoc.clickAndWait();
        rbSelectOsoc.click();
        clickAndWait(BaseButton.RETURN);
    }

    public void createJobOrder(String companyName, String zipCode, String city, String jobTitle) {
        BasicInformationStaffForm basicInformationStaffForm = new BasicInformationStaffForm();
        selectEmployer(companyName, zipCode, city);
        selectOsocCode(COOKS);
        basicInformationStaffForm.fillTitleDateDescriptionAndContinue(jobTitle, CommonFunctions.getCurrentDate(),
                CommonFunctions.getNextWeekDate());
        new JobRequirementsStaffForm().fillHoursDriver();
        new StaffInformationStaffForm().fillInStaffInformationAndSave();
    }

    public void createJobOrderWithUser(User user, JobOrder order) {
        selectEmployer(order.getEmployer().getCompanyName(), order.getEmployer().getAddress().getZipCode(),
                order.getEmployer().getAddress().getCity());

        BasicInformationStaffForm basicInformationStaffForm = new BasicInformationStaffForm();

        basicInformationStaffForm.selectOsocCode(order.getOsocCode());
        basicInformationStaffForm.fillTitleDateDescriptionAndContinue(order.getJobTitle(),
                CommonFunctions.getCurrentDate(), CommonFunctions.getNextWeekDate());

        new JobRequirementsStaffForm().fillHoursDriver();

        new StaffInformationStaffForm().fillInStaffInformation();

        //(!) Chek Staff lookup
        if (user.getOrder().getCloneStaffLookup()) {
            btnRestrictStaffLookup.clickAndWait();
            StaffSearchForm staffPage = new StaffSearchForm();
            staffPage.performSearchAndSelect(order.getRestrictedFirstName(), order.getRestrictedLastName());
            validateStaffInfo(order);
        } else {
            btnRestrictStaffLookup.assertIsNotPresent();
        }

        clickButton(Buttons.Create);
        clickButton(Buttons.Done);
    }

    public void createJobOrderWithLocation(JobOrder jobOrder) {
        new BasicInformationStaffForm().createJobOrderWithLocation(jobOrder);
        new JobRequirementsStaffForm().fillHoursDriver();

        clickButton(Buttons.Create);
        clickButton(Buttons.Done);
    }

    private void fillThirdPage() {
        clickAllowOnline(Constants.TRUE);
        rbAllowDocument.click();
        clickJobDevelopmentNo();
    }

    public void checkNoQuestionsPresent() {
        CustomAssertion.softTrue("Some job orders were found", dvNoItemsFound.isPresent());
    }

    public void checkErrorOpenDate(String errorText) {
        CustomAssertion.softTrue("Incorrect error text in the open date field", dvOpenDateErrors.getText().equals(errorText));
    }

    public String createJobOrderWithInstructionsForParticipant(JobOrder jobOrder) {
        String instructions = new BasicInformationStaffForm().fillInBasicInformationWithInstructionsForParticipantAndSave(jobOrder);

        new JobRequirementsStaffForm().fillSecondPage();
        clickButton(Buttons.Continue);
        clickAllowOnline(Constants.TRUE);
        rbAllowDocument.click();
        clickJobDevelopmentNo();
        clickAndWait(BaseButton.CREATE);
        clickAndWait(BaseButton.DONE);

        return instructions;
    }

    public String createJobOrderWithDisclaimer(JobOrder jobOrder) {
        String disclaimer = CommonFunctions.getRandomLiteralCode(20);

        selectEmployer(jobOrder.getEmployer());

        new BasicInformationStaffForm().fillOutFirstPage(jobOrder.getJobTitle(), Constants.TRUE, disclaimer);

        new JobRequirementsStaffForm().fillSecondPage();
        clickButton(Buttons.Continue);

        fillThirdPage();
        clickAndWait(BaseButton.CREATE);
        clickAndWait(BaseButton.DONE);

        return disclaimer;
    }

    public String createJobOrder(JobOrder jobOrder, Boolean driver, Boolean education, Boolean question) {
        new BasicInformationStaffForm().typicalFillFirstPage(jobOrder);

        new JobRequirementsStaffForm().passWorkingHoursFields();
        if (driver) {
            rbDriversLicenseRequired.click();
            cmbDriversLicenseClass.select(driverLicenseClass);
        }
        if (education) {
            cmbEducationLevelRequired.select(BACHELOR_DEGREE);
        }
        clickButton(Buttons.Continue);

        new ScreeningQuestionsStaffForm().passQuestionBlock(jobOrder, question);

        clickButton(Buttons.Continue);
        clickAllowOnline(Constants.TRUE);
        clickJobDevelopmentNo();
        rbAllowDocument.click();
        clickAndWait(BaseButton.CREATE);
        clickAndWait(BaseButton.DONE);

        return jobOrder.getQuestion().getText();
    }

    public String createJobOrderSelfServices(JobOrder order, boolean questionNeeded, boolean cloneJobOrder) {
        String question = CommonFunctions.getRandomLiteralCode(20);
        new BasicInformationSsForm().fillInTheFirstOrderPageSsAndContinue(order);

        fillInOrderPagesUpToCreate(questionNeeded, question);
        return finishOrderCreation(cloneJobOrder);
    }

    public void fillInOrderPagesUpToCreate(boolean questionNeeded, String question) {

        new DesiredCandidateProfileSsForm().skipDesiredCandidateProfilePage();

        if (questionNeeded) {
            new ScreeningQuestionsSsForm().addGradedQuestions(question);
        }
    }

    public void fillSecondPageWithBlockers() {
        final String wokRanges = "Wok ranges";
        Button btnItem = new Button(By.xpath(String.format(skillsItemLocator, wokRanges)), "Skill item");
        clickButton(Buttons.Continue);
        cmbEducationLevelRequired.select(BACHELOR_DEGREE);
        rbDriversLicenseRequired.click();
        if (btnDriverApply.isPresent()) {
            btnDriverApply.click();
        }
        rbDriversLicenseClassB.click();
        btnSkills.click();
        btnItem.waitForIsElementPresent();
        btnItem.click();
        chbEduOnline.click();
        chbSkillsOnline.click();
        chbDrivOnline.click();
        chbLicenseOnline.click();
        clickButton(Buttons.Continue);
    }

    public String finishOrderCreation(Boolean cloneJobOrder) {
        String permalink = null;
        clickAndWait(BaseButton.CREATE);
        if (lnkPermalink.isPresent()) {
            permalink = lnkPermalink.getText();
        }
        if (cloneJobOrder) {
            cloneJobOrderSelfServices();
        }
        return permalink;
    }

    public void returnToTheFirstPage() {
        btnPrevious.clickAndWait();
        btnPrevious.clickAndWait();
    }

    public void clickJobPublicSchool(Boolean inSchool) {
        if (inSchool) {
            rbJobPublicSchoolYes.click();
        } else {
            rbJobPublicSchoolNo.click();
        }
    }

    private void addGradedQuestion(String question) {
        cmbQuestionType.select(questionType);
        txbQuestion.type(question);
        btnAdd.clickAndWait();
        txbCorrectAnswer.type(correctAnswer);
        rbQuestionGradedYes.click();
        String incorrectAnswer1 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer1.type(incorrectAnswer1);
        String incorrectAnswer2 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer2.type(incorrectAnswer2);
        String incorrectAnswer3 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer3.type(incorrectAnswer3);
        btnAdd.clickAndWait();

        checkField(tbcQuestionDetails, question, Constants.FALSE);
    }

    //todo MOVE TO STEPS
    public void createJobOrderSelfServicesWithIncorrectOpenDate(String jobTitle, boolean questionNeeded) {
        String expectedErrorText = "Open Date cannot be in the past.";
        String question = CommonFunctions.getRandomLiteralCode(20);
        txbJobTitle.type(jobTitle);
        Button btnSugValue = new Button(By.xpath(String.format(osocPath, COOKS)), "Suggestion value");
        txbOsocTitle.type(COOKS);
        if (btnSugValue.isPresent()) {
            btnSugValue.click();
        }
        dvQuestionTitle.click();

        txbOpenings.type(quantity);
        txbReferrals.type(quantity);
        rbAllowOnlineApplications.click();
        rbAllowAttachDocuments.click();

        // incorrect date
        txbOpenDate.type(openDate);
        txbCloseDate.type(CommonFunctions.getTomorrowDate());
        fillInDescriptionField(AUTOMATION);

        clickButton(Buttons.Continue);
        checkField(spnOpenDate, expectedErrorText, Constants.TRUE);

        txbOpenDate.type(CommonFunctions.getCurrentDate());
        clickButton(Buttons.Continue);

        passWorkingHoursFields();

        // The following fields are now (July, 2014) moved to the "Create Opening: Desired Candidate Profile" page.
        // So we need to click Continue to reach that fields.
        clickButton(Buttons.Continue);
        rbDriversLicenseRequired.click();
        rbDriversLicenseClassB.click();
        cmbEducationLevelRequired.select(BACHELOR_DEGREE);

        clickButton(Buttons.Continue);

        if (questionNeeded) {
            addGradedQuestion(question);
        }
        clickAndWait(BaseButton.CREATE);
    }

    public void passWorkingHoursFields() {
        txbHoursPerWeek.type(quantity);
        rbScheduleHoursPartTime.click();
        rbScheduleTermPermanent.click();
        chkShiftDays.click();
        chkWorkMonday.click();
    }

    public void fillInDescriptionField(String text) {
        String handler = Browser.getDriver().getWindowHandle();
        Browser.getDriver().switchTo().frame(dvFrame.getElement());
        txbJobDescription.type(text);
        Browser.getDriver().switchTo().window(handler);
    }

    private void cloneJobOrderSelfServices() {
        btnCloneThisJobOrder.clickAndWait();
        txbOpenDate.type(CommonFunctions.getCurrentDate());
        txbCloseDate.type(CommonFunctions.getTomorrowDate());
        fillInDescriptionField(AUTOMATION);
        clickButton(Buttons.Continue);
        clickButton(Buttons.Continue);
        clickButton(Buttons.Save);
        clickAndWait(BaseButton.DONE);
    }

    public void cloneJobOrder() {
        txbOpenDate.type(CommonFunctions.getCurrentDate());
        txbCloseDate.type(CommonFunctions.getTomorrowDate());
        fillInDescriptionField(AUTOMATION);
        clickButton(Buttons.Continue);
        clickButton(Buttons.Continue);
        clickButton(Buttons.Continue);
        rbAllowDocument.click();
        clickButton(Buttons.Save);
        clickAndWait(BaseButton.DONE);
    }

    public String getEmployerText() {
        return spnEmployerLookUp.getText().trim();
    }

    public String getEmployerLocation() {
        return tbcLocation.getText().trim();
    }

    public void checkStaffLookupPresent(JobOrder order, Boolean isDisplayed) {
        if (isDisplayed) {
            divideMessage("Check, that staff look-up is present or not on the creation page");
            btnRestrictStaffLookup.clickAndWait();
            StaffSearchForm staffPage = new StaffSearchForm();
            staffPage.performSearchAndSelect(order.getRestrictedFirstName(), order.getRestrictedLastName());
            validateStaffInfo(order);
        }
    }
}


