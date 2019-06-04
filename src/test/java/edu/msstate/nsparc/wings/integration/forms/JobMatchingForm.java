package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import webdriver.Browser;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * This form is opened via Participants -> Wagner-Peyser -> Job Matching
 */
public class JobMatchingForm extends BaseWingsForm {

    private CheckBox chkAcademic = new CheckBox("css=input#restrictAcademically1", "Academic Requirements checkbox");
    private CheckBox chkDriversLicense = new CheckBox("css=input#restrictByDriversLicense1", "Drivers License Requirements  checkbox");

    public static final TextBox TXB_JOB_TITLE = new TextBox("id=jobTitle", "Job Title");
    public static final TextBox TXB_KEYWORDS = new TextBox("id=searchString", "Keywords");
    public static final TableCell TBC_JOB_TITLE = new TableCell("css=table#results-table td", "Job Title");
    public static final TableCell TBC_EMPLOYER = new TableCell("css=table#results-table td + td", "Employer");
    private RadioButton rbParticipantLocation = new RadioButton(By.xpath("//input[@id='participantLocation']"), "Use participant's residence address");
    private RadioButton rbSpecificLocation = new RadioButton("css=input#specificLocation", "Use a specific location");

    //Job-Match Setting on Participant S-S
    private Div divFirstQuestion = new Div(By.xpath("//div[contains(@class,'contact-prefs-panel')]/div[1]/div"), "The first question on the Contact panel");
    private TextBox txbOSOC = new TextBox(By.id("jobInterest.jobTitle"), "Selecting new OSOC");
    private Button btnAddOSOC = new Button(By.id("addOsoc"), "Add OSOC");
    private Div divAddedOSOC = new Div(By.xpath("//div[@class='whymatch-bubble matching-on']/div[2]"), "Selected OSOC");
    private String labelOsocAdded = "//div[contains(@class,'whymatch-label')][contains(.,'%1$s')]";

    /**
     * Getting added OSOC
     *
     * @param numberCount - osoc records on the page
     * @param osocCheck   - osoc title to check.
     */
    public void getOSOCS_S(Integer numberCount, String osocCheck) {
        boolean osocFound = false;
        for (int i = 1; i <= numberCount; i++) {
            divAddedOSOC = new Div(By.xpath(String.format("//div[@class='whymatch-bubble matching-on'][%1$d]/div[2]", i))
                    , "Selected OSOC");
            if (divAddedOSOC.getText().contains(osocCheck)) {
                osocFound = true;
            }
        }
        CustomAssertion.softTrue("Incorrect osoc on the page", osocFound);
    }

    /**
     * Getting the first question on the right pane
     *
     * @return
     */
    public String getFirstQuestionS_S() {
        return divFirstQuestion.getText();
    }

    /**
     * Typing and selecting OSOC
     *
     * @param osoc - osoc to add.
     */
    public void typeAndSelectOSOCS_S(String osoc) {
        txbOSOC.type(osoc);
        Link lnkStrongOsoc = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]", osoc)), "Osoc link");
        Div addedOsoc = new Div(By.xpath(String.format(labelOsocAdded, osoc)), "Added osoc");
        Browser.getDriver().getMouse().mouseMove(lnkStrongOsoc.getElement().getCoordinates());
        JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();
        jse.executeScript("arguments[0].click();", lnkStrongOsoc.getElement());

        scrollUp();
        CustomAssertion.softTrue("Added osoc is not present on the page", addedOsoc.isPresent());
    }

    /**
     * Adding OSOC
     */
    public void clickAddOSOCS_S() {
        btnAddOSOC.clickAndWait();
    }

    /**
     * Default constructor
     */
    public JobMatchingForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Matching')]"), "Job Matching");
    }

    /**
     * Default constructor
     *
     * @param participantSS - participant S-S
     */
    public JobMatchingForm(String participantSS) {
        super(By.xpath("//h3[contains(.,'Showing You These Jobs Right Now')]"), "Job Matching for " + participantSS);
    }

    /**
     * Uncheck checkbox Jobs in which the Participant meet Academic Requirements->Search
     */
    public void chooseAcademicCheckBox() {
        chkAcademic.click();
    }

    /**
     * Choose participant driver license
     */
    public void chooseDriverLicense() {
        chkDriversLicense.click();
    }

    /**
     * Choose specific location rb
     */
    public void chooseLocation() {
        rbSpecificLocation.click();
    }

    /**
     * Choose location for participant rb
     */
    public void choosePartipLoc() {
        rbParticipantLocation.click();
    }

    /**
     * Check, that participant location rb is editable
     * return true/false
     */
    public Boolean checkParticipantEditable() {
        return rbParticipantLocation.isEditable();
    }
}
