package edu.msstate.nsparc.wings.integration.forms.jobOrder;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.BasicInformationStaffForm;
import edu.msstate.nsparc.wings.integration.models.Address;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * This form is opened via Employers -> Wagner-Peyser -> Job Orders -> Search for record -> Open Record
 */
public class JobOrderDetailsForm extends JobOrderBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";
    private Button btnEditBasicInformation = new Button(By.id("editBasicInformation"), "Edit basic information");
    private Button btnEditJobRequirements = new Button(By.id("editJobRequirements"), "Edit job requirements");
    private Button btnEditStaffInformation = new Button(By.id("editStaffInformation"), "Edit Staff Information");
    private TableCell tbcJobTitleLabel = new TableCell(By.xpath("//td[contains(text(),'Job Title')]/following-sibling::td | //form[@id='job']//h3 | //div[@id='heading-title'] | //form[@id='referral']/div[@class='row']//h3"),
            "Job Title");
    private TableCell tbcNumberOfOpeningsLabel = new TableCell(String.format(detailPath, "Number of Openings:"), "Number of openings");
    private Button btnClone = new Button("id=clone", "Clone This Job Order");
    private TableCell tbcScreeningQuestions = new TableCell("id=customQuestions", "Screening Questions");
    private TableCell tbcCustomQuestion = new TableCell("css=tr[id='customQuestions'] div[class='longTextWrap500']", "Custom Question");
    private Span applyErrorMessage = new Span("//h2/following-sibling::p", "Job Apply - Validation message");
    private Div dvQuestion = new Div("//div[@class='question-title']", "Question");

    private Button btnExpandCustomQuestionsSection = new Button(By.xpath("//a[@class='expand'][contains(.,'Custom Questions')]"), "Custom Questions");
    @SuppressWarnings("all")
    private String locatorForAnswer = "//input[@value='%1$s']";
    private Span spCustomQuestion = new Span(By.xpath("//h4[contains(.,'Custom Questions')]"), "Custom questions title");
    private Span spAddedQuestion = new Span(By.xpath("//div[@class='col-md-12']/ul/li[1]"), "Added question");

    //Basic information labels
    private TableCell tbcEmployer = new TableCell(String.format(detailPath, "Employer:"), "Employer label");
    private TableCell tbcLocation = new TableCell(String.format(detailPath, "Location:"), "Employer label");
    private TableCell tbcJobTitle = new TableCell(String.format(detailPath, "Job Title:"), "Employer label");
    private TableCell tbcOccupationCode = new TableCell(String.format(detailPath, "Occupation Code:"), "Employer label");
    private TableCell tbcCreationDate = new TableCell(String.format(detailPath, "Creation Date:"), "Employer label");
    private TableCell tbcOpenDate = new TableCell(String.format(detailPath, "Open Date:"), "Employer label");
    private TableCell tbcCloseDate = new TableCell(String.format(detailPath, "Close Date:"), "Employer label");
    private TableCell tbcOrderLocation = new TableCell(String.format(detailPath, "Job Order Location:"), "Employer label");
    private TextBox tbDisclaimerText = new TextBox(By.xpath("//pre[@class='disclaimerText']"), "Disclaimer text");

    //Employer S-S
    private Div divJobTitle = new Div(By.xpath("//h3[@id='job-title-header']"), "Job Title");
    private Link divJobID = new Link(By.xpath("//span[contains(@class,'result-number')]"), "Job ID");
    private Div divSource = new Div(By.xpath("//div[contains(.,'Source')]/following-sibling::p"), "Source");
    private Button btnPrevious = new Button(By.xpath("//button[@id='previous']"), "Return to Search");

    private String detailXpath = "//div[contains(.,'%1$s')]/following-sibling::p";
    private Div dvDatesActive = new Div(By.xpath("//h4[contains(.,'Dates Active')]/following-sibling::p"), "Dates Active");
    private Div dvCreated = new Div(By.xpath("//em"), "Created");
    private Div dvJobDescription = new Div(By.xpath("//span[@class='embeddedHtml']"), "Job Description");
    private Div dvNumberOpenings = new Div(By.xpath(String.format(detailXpath, "Number of Openings:")), "Number of Openings");
    private Div dvReferralsRequested = new Div(By.xpath(String.format(detailXpath, "Referrals Requested:")), "Referrals Requested");
    private Div dvWorkLocation = new Div(By.xpath("//div[@class='col-md-4']"), "Job Location");
    private Div dvHoursWeek = new Div(By.xpath(String.format(detailXpath, "Hours per Week:")), "Hours per week");
    private Div dvOccupationCode = new Div(By.xpath(String.format(detailXpath, "Occupation Code:")), "Occupation code");
    private Div dvSkillsRequired = new Div(By.xpath("//div[@class='col-md-4'][contains(.,'Skills Required')]"), "Skills required");
    private Div dvEduRequirements = new Div(By.xpath(String.format(detailXpath, "Education Requirements:")), "Education requirements");
    private Div dvDriverLicense = new Div(By.xpath(String.format(detailXpath, "License Preferred:")), "Driver's license preferred");
    private String rbAnswerLocator = "//form[@id='customQuestions']//div[@class='question-title'][contains(.,'%1$s')]/following-sibling::div//input";

    public void clickPreviousS_S() {
        btnPrevious.clickAndWait();
    }

    public JobOrderDetailsForm() {
        super(By.xpath("//form[@id='referral']//p[@class='job-info'] | //span[@id='breadCrumb'][contains(.,'Job Order Detail')] | //form[@id='job']//p[@class='job-info']"),
                "Job Order Detail");
    }

    public JobOrderDetailsForm(String jobTitle) {
        super(By.xpath(String.format("//div[@id='heading-title'][contains(.,'%1$s')]|//h3[@id='job-title-header'][contains(.,'%1$s')]", jobTitle)),
                "Job Order Detail with job title");
    }

    public String getJobTitleS_S() {
        if (divSource.getText().contains("SPB")) {
            String[] array = divJobTitle.getText().split("( \\(#SPB)", 2);
            return array[0].trim();
        }
        String[] array = divJobTitle.getText().split("(\\(#\\d)", 2);
        return array[0].trim();
    }

    public void checkAddedQuestion(String question) {
        CustomAssertion.softTrue("Added question is not correct", spAddedQuestion.getText().contains(question));
    }

    public void checkCustomQuestionTitle(Boolean customPresent) {
        if (customPresent) {
            CustomAssertion.softTrue("Custom questions title is not present", spCustomQuestion.isPresent());
        } else {
            CustomAssertion.softNotTrue("Custom questions title is present", spCustomQuestion.isPresent());
        }
    }

    public void checkSearchResultOnDetailsS_S(String jobID) {
        info("Validate search result");
        checkField(divJobID, jobID, Constants.FALSE);
    }

    public void expandCustomQuestions() {
        btnExpandCustomQuestionsSection.click();
    }

    public void checkJobOrderButtons(User user) {
        divideMessage("Edit basic information");
        ifButton(user.getOrder().getViewEditBasicInformation(), btnEditBasicInformation);
        divideMessage("Edit Job Requirements");
        ifButton(user.getOrder().getViewEditJobRequirements(), btnEditJobRequirements);
        divideMessage("Edit Staff Information");
        ifButton(user.getOrder().getViewEditStaffInformation(), btnEditStaffInformation);
        divideMessage("Clone");
        ifButton(user.getOrder().getViewClone(), btnClone);
        divideMessage("Audit");
        ifButton(user.getOrder().getViewAuditButton(), BaseButton.AUDIT.getButton());
        divideMessage("Check audit functionality");
        if (user.getOrder().getViewAuditButton()) {
            audit();
        }
    }

    public void validateJobOrderSS(JobOrder jo) {
        Address address = jo.getEmployer().getAddress();
        info(dvDatesActive.getText());
        info(jo.getCreationDate() + " - " + jo.getCloseDate());
        CustomAssertion.softTrue("Incorrect Dates Active",
                dvDatesActive.getText().contains(jo.getCreationDate() + " - " + jo.getCloseDate()));
        CustomAssertion.softTrue("Incorrect Creation Date", dvCreated.getText().contains("Created " + jo.getCreationDate()));
        CustomAssertion.softAssertEquals(dvJobDescription.getText(), jo.getDescription(), "Incorrect Job Description");
        CustomAssertion.softTrue("Incorrect Number Openings", dvNumberOpenings.getText().contains(jo.getQuantity()));
        CustomAssertion.softTrue("Incorrect Number Referrals", dvReferralsRequested.getText().contains(jo.getQuantity()));
        CustomAssertion.softTrue("Incorrect Work Location", dvWorkLocation.getText().contains(
                address.getLineOne() + "\n" + address.getLineOne() + "\n" + address.getCity() + ", " + address.getState() + " "
                        + address.getZipCode() + "\n" + address.getCounty() + " County " + address.getCountry()));
        CustomAssertion.softTrue("Incorrect Hours Week", dvHoursWeek.getText().contains(jo.getQuantity()));
        CustomAssertion.softTrue("Incorrect Occupation Code", dvOccupationCode.getText().contains(jo.getOsocCode()));
    }

    public void validateSpecDataBlockers() {
        CustomAssertion.softTrue("Incorrect Driver's License", dvDriverLicense.getText().contains("Yes - Class B Required"));
        CustomAssertion.softTrue("Incorrect Skills Required", dvSkillsRequired.getText().contains("Wok ranges"));
        CustomAssertion.softTrue("Incorrect Education Requirements", dvEduRequirements.getText().contains("Bachelor's Degree"));
    }

    public void validateBasicInformation(JobOrder order) {
        String addressCity = order.getEmployer().getAddress().getCity();
        String addressState = order.getEmployer().getAddress().getState();
        String addressZipCode = order.getEmployer().getAddress().getZipCode();
        String everifyLineOne = order.getEmployer().getEverifyAddress().getLineOne();
        String everifyCity = order.getEmployer().getEverifyAddress().getCity();
        String everifyState = order.getEmployer().getEverifyAddress().getState();
        String everifyZipCode = order.getEmployer().getEverifyAddress().getZipCode();
        CustomAssertion.softAssertEquals(tbcEmployer.getText(), order.getEmployer().getCompanyName(), "Incorrect employer name");
        CustomAssertion.softAssertEquals(tbcLocation.getText(), addressCity + ", " + addressState + " " + addressZipCode, "Incorrect location");
        CustomAssertion.softAssertEquals(tbcJobTitle.getText(), order.getJobTitle(), "Incorrect job title");
        CustomAssertion.softAssertEquals(tbcOccupationCode.getText(), order.getOsocCode(), "Incorrect osoc code");
        CustomAssertion.softAssertEquals(tbcCreationDate.getText(), order.getCreationDate(), "Incorrect creation date");
        CustomAssertion.softAssertEquals(tbcOpenDate.getText(), order.getCreationDate(), "Incorrect open date");
        CustomAssertion.softAssertEquals(tbcCloseDate.getText(), order.getCloseDate(), "Incorrect close date");
        CustomAssertion.softAssertContains(tbcOrderLocation.getText().trim(), everifyLineOne + "\n" + everifyCity + ", " + everifyState + " "
                + everifyZipCode, "Incorrect job order location");
    }

    public void validateDisclaimer(JobOrder order) {
        CustomAssertion.softTrue("Incorrect disclaimer text", tbDisclaimerText.getText().contains(order.getJobTitle()));
    }

    public void answerScreeningQuestion(JobOrder jobOrder) {
        String locator = String.format(locatorForAnswer, jobOrder.getQuestion().getCorrectAnswer());
        RadioButton rbJOrderCorrectAnswer = new RadioButton(locator, "Correct answer");
        rbJOrderCorrectAnswer.click();
        clickButton(Buttons.Continue);
    }

    public String getApplyErrorMessage() {
        return applyErrorMessage.getText().trim();
    }

    public String getCustomQuestion() {
        return tbcCustomQuestion.getText().trim();
    }

    public void checkAnswerAndApply() {
        ArrayList<WebElement> questionCount = dvQuestion.getAllElements();
        for (int i = 1; i <= questionCount.size(); i++) {
            RadioButton rbAnswer = new RadioButton(By.xpath(String.format(rbAnswerLocator, String.valueOf(i))), "Answer");
            rbAnswer.click();
        }
        apply();
    }

    public void cloneOrder(JobOrder order, User user) {
        JobOrderDetailsForm detailsPage = new JobOrderDetailsForm();
        detailsPage.clickClone();

        divideMessage("Fill in all required fields on Cloning form");
        JobOrderCreationForm jobOrderCreationPage = new JobOrderCreationForm(Constants.CLONE, order.getJobTitle());
        new BasicInformationStaffForm().fillTitleDateDescription(user.getOrder().getJobTitle(), CommonFunctions.getCurrentDate(), CommonFunctions.getTomorrowDate());
        jobOrderCreationPage.clickButton(Buttons.Continue);
        jobOrderCreationPage.clickButton(Buttons.Continue);
        jobOrderCreationPage.clickButton(Buttons.Continue);

        //(!)Check, that staff look-up is present or not on the clone page
        jobOrderCreationPage.checkStaffLookupPresent(order, user.getOrder().getCloneStaffLookup());

        jobOrderCreationPage.clickButton(Buttons.Save);
    }

    public void edit(JobOrder order, Roles role) {
        editBasicInformation(order, role);
    }

    public void clickClone() {
        btnClone.clickAndWait();
    }

    public String getScreeningQuestion() {
        return tbcScreeningQuestions.getText().trim();
    }

    public Boolean checkStaffEditable() {
        return btnEditStaffInformation.isPresent();
    }

    public void editStaffInformation() {
        btnEditStaffInformation.clickAndWait();
    }

    private void editBasicInformation(JobOrder order, Roles role) {
        btnEditBasicInformation.clickAndWait();
        JobOrderEditForm editPage = new JobOrderEditForm();
        editPage.inputJobTitle(order.getJobTitle());
        editPage.inputCloseDate(order.getCloseDate());
        editPage.checkDisclaimerButton(role, order.getJobTitle());
        editPage.clickButton(Buttons.Continue);
        editPage.clickButton(Buttons.Continue);
        editPage.clickButton(Buttons.Save);
    }

    public void editOpenDate(JobOrder order, Roles role) {
        btnEditBasicInformation.clickAndWait();
        JobOrderEditForm editPage = new JobOrderEditForm();
        editPage.inputOpenDate(order.getOpenDate());
        editPage.checkDisclaimerButton(role, order.getJobTitle());
        editPage.clickButton(Buttons.Continue);
        editPage.clickButton(Buttons.Continue);
        editPage.clickButton(Buttons.Save);
    }

    public String getJobTitleText() {
        return tbcJobTitleLabel.getText().trim();
    }

    public String getNumberOfOpenings() {
        return tbcNumberOfOpeningsLabel.getText();
    }
}
