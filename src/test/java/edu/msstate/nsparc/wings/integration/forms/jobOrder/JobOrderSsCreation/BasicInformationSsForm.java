package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class BasicInformationSsForm extends JobOrderCreationForm {

    private TextBox txbJobTitle = new TextBox("id=jobTitle", "Job Title");
    private RadioButton rbAllowOnlineApplications = new RadioButton("css= input#allowOnlineApplications2", "Allow online applications");
    private TextBox txbOpenings = new TextBox("id=nbrJobOpenings", "Number of Openings");
    private TextBox txbReferrals = new TextBox("id=nbrRefRequested", "Referrals Requested");
    private TextBox txbOpenDate = new TextBox("id=dateOrderOpen", "Open Date");
    private TextBox txbCloseDate = new TextBox("id=dateOrderClose", "Close Date");
    private TextArea txbJobDescription = new TextArea("id=tinymce", "Job Description");
    private Div dvFrame = new Div(By.xpath("//iframe[@id='jobDescription_ifr']"), "Job Description Frame");
    private TextBox txbOsocTitle = new TextBox(By.xpath("//input[@id='occTitle'] | //input[@id='osocTitle']"), "OSOC title");
    private RadioButton rbAllowDocument = new RadioButton("css=input[id='allowDoc1']", "Document Attachment");
    private TextBox txbHoursPerWeek = new TextBox("id=hoursPerWeek", "Hours per Week");
    private RadioButton rbScheduleHoursPartTime = new RadioButton("//input[@name='scheduleHours' and @value='PART_TIME']", "Schedule Hours - Part-Time");
    private RadioButton rbScheduleTermPermanent = new RadioButton("//input[@name='scheduleTerm' and @value='PERMANENT']", "Schedule Term - Permanent");
    private CheckBox chkShiftDays = new CheckBox("id=shiftDay1", "Shift by Days");
    private CheckBox chkWorkMonday = new CheckBox("id=workdayM1", "Work on Monday");

    public BasicInformationSsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Create Opening: Basic Information')]"), "Create Opening: Basic Information");
    }

    public void fillInDescriptionField(String text) {
        String handler = Browser.getDriver().getWindowHandle();
        Browser.getDriver().switchTo().frame(dvFrame.getElement());
        txbJobDescription.type(text);
        Browser.getDriver().switchTo().window(handler);
    }

    public void passWorkingHoursFields() {
        txbHoursPerWeek.type(quantity);
        rbScheduleHoursPartTime.click();
        rbScheduleTermPermanent.click();
        chkShiftDays.click();
        chkWorkMonday.click();
    }

    public void fillInTheFirstOrderPageSs(JobOrder order) {
        txbJobTitle.type(order.getJobTitle());
        Button btnSugValue = new Button(By.xpath(String.format(osocPath, COOKS)), "Suggestion value");
        txbOsocTitle.type(COOKS);

        txbOsocTitle.click();
        Browser.getDriver().getMouse().mouseMove(btnSugValue.getElement().getCoordinates());
        JavascriptExecutor jse = Browser.getDriver();
        jse.executeScript("arguments[0].click();", btnSugValue.getElement());

        txbOpenings.type(order.getQuantity());
        txbReferrals.type(order.getQuantity());
        clickJobPublicSchool(Constants.TRUE);
        rbAllowOnlineApplications.click();
        rbAllowDocument.click();
        txbOpenDate.type(CommonFunctions.getCurrentDate());
        txbCloseDate.type(CommonFunctions.getNextWeekDate());

        fillInDescriptionField(order.getDescription());
        passWorkingHoursFields();
    }

    public void fillInTheFirstOrderPageSsAndContinue(JobOrder order) {
        fillInTheFirstOrderPageSs(order);

        clickButton(Buttons.Continue);
    }
}
