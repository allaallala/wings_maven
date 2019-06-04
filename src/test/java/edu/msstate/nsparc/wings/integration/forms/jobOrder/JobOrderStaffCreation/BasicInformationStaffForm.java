package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;

public class BasicInformationStaffForm extends JobOrderCreationForm {
    private TextBox txbJobTitle = new TextBox("id=jobTitle", "Job Title");
    private TextBox txbOpenings = new TextBox("id=nbrJobOpenings", "Number of Openings");
    private TextBox txbReferrals = new TextBox("id=nbrRefRequested", "Referrals Requested");
    private TextBox txbOpenDate = new TextBox("id=dateOrderOpen", "Open Date");
    private TextBox txbCloseDate = new TextBox("id=dateOrderClose", "Close Date");
    private RadioButton rbDisclaimerYes = new RadioButton("css=input#showDisclaimer1", "Show Disclaimer - Yes");
    private TextArea txbJobDescription = new TextArea("id=tinymce", "Job Description");
    private TextArea txbDisclaimer = new TextArea("css=textarea#disclaimer", "Disclaimer");
    private TextBox txbInstructionsForParticipants = new TextBox("css=textarea#publicReferralInstructions",
            "Instructions for Participants");
    private Div dvFrame = new Div(By.xpath("//iframe[@id='jobDescription_ifr']"), "Job Description Frame");
    private TextBox txbLineOne = new TextBox("css=input[id='tmpAddress.lineOne']", "Address Line One");
    private TextBox txbCity = new TextBox("css=input[id='tmpAddress.city']", "Address City");
    private ComboBox cmbState = new ComboBox("css=select[id='tmpAddress.state']", "Address State");
    private TextBox txbZipCode = new TextBox("css=input[id='tmpAddress.zipcode']", "Address Zip Code");
    private ComboBox cmbCounty = new ComboBox("css=select[id='tmpAddress.county']", "Address County");
    private RadioButton rbOtherLocation = new RadioButton("css=input#useEmployerAddress2", "Use Other Location");

    public BasicInformationStaffForm() {
        super(By.xpath("//h1[contains(.,'Basic Information')]"), "Create Opening: Basic Information");
    }

    public String fillInBasicInformationWithInstructionsForParticipantAndSave(JobOrder jobOrder) {
        String instructions = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        selectEmployer(jobOrder.getEmployer());
        txbJobTitle.type(jobOrder.getJobTitle());
        selectOsocCode(COOKS);
        fillTitleDateDescription(jobOrder.getJobTitle(), CommonFunctions.getCurrentDate(), CommonFunctions.getTomorrowDate());
        txbInstructionsForParticipants.type(instructions);
        clickButton(Buttons.Continue);

        return instructions;
    }

    public void createJobOrderWithLocation(JobOrder jobOrder) {
        rbOtherLocation.click();

        txbLineOne.waitForIsElementPresent();
        txbLineOne.type(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
        txbCity.type(jobOrder.getCity());
        cmbState.select(defaultState);
        txbZipCode.type(jobOrder.getZipCode());
        cmbCounty.select(defaultCounty);
        typicalFillFirstPage(jobOrder.getEmployer(), jobOrder.getJobTitle());
    }

    public void typicalFillFirstPage(Employer employer, String jobTitle) {
        selectEmployer(employer);
        selectOsocCode(COOKS);
        fillTitleDateDescription(jobTitle, CommonFunctions.getCurrentDate(), CommonFunctions.getNextWeekDate());

        clickButton(Buttons.Continue);
    }



    public void fillOutFirstPage(String jobTitle, boolean disclaimerPresent, String disclaimer) {
        selectOsocCode(COOKS);
        fillTitleDateDescription(jobTitle, CommonFunctions.getCurrentDate(), CommonFunctions.getTomorrowDate());
        if (disclaimerPresent) {
            rbDisclaimerYes.click();
            txbDisclaimer.type(disclaimer);
        }
        clickButton(Buttons.Continue);
    }

    public void fillInDescriptionField(String text) {
        String handler = Browser.getDriver().getWindowHandle();
        Browser.getDriver().switchTo().frame(dvFrame.getElement());
        txbJobDescription.type(text);
        Browser.getDriver().switchTo().window(handler);
    }

    public void typicalFillFirstPage(JobOrder jobOrder) {
        selectEmployer(jobOrder.getEmployer());
        new BasicInformationStaffForm().selectOsocCode(COOKS);
        fillTitleDateDescription(jobOrder.getJobTitle(), CommonFunctions.getCurrentDate(), CommonFunctions.getNextWeekDate());

        clickButton(Buttons.Continue);
    }

    public void fillTitleDateDescription(String jobTitle, String openDate, String closeDate) {
        txbJobTitle.type(jobTitle);
        txbOpenings.type(quantity);
        txbReferrals.type(quantity);
        txbOpenDate.type(openDate);
        txbCloseDate.type(closeDate);
        String handler = Browser.getDriver().getWindowHandle();
        Browser.getDriver().switchTo().frame(dvFrame.getElement());
        txbJobDescription.type(AUTOMATION);
        Browser.getDriver().switchTo().window(handler);
    }

    public void fillTitleDateDescriptionAndContinue(String jobTitle, String openDate, String closeDate) {
        fillTitleDateDescription(jobTitle, openDate, closeDate);
        clickButton(Buttons.Continue);
    }
}
