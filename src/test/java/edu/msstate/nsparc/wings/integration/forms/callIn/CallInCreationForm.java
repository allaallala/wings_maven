package edu.msstate.nsparc.wings.integration.forms.callIn;

import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Wagner-Peyser -> Call-Ins -> Create
 */
public class CallInCreationForm extends CallInBaseForm {

    private RadioButton rbOnlyVeterans = new RadioButton("id=veteranSearch1", "Only Veterans");
    private RadioButton rbOnlyNonVeterans = new RadioButton("id=veteranSearch2", "Only Non-Veterans");
    private RadioButton rbAll = new RadioButton("id=veteranSearch3", "All (Veterans and Non-Veterans)");
    private CheckBox chkAcademicRequirements = new CheckBox("id=restrictAcademically1",
            "Participants meeting Academic Requirements ");
    private CheckBox chkDriversLicenseRequirements = new CheckBox("id=restrictByDriversLicense1",
            "Participants meeting Drivers License Requirements ");
    private ComboBox cmbCallIn = new ComboBox(By.xpath("//table[@id='results-table']//select"), "Select way to contact");
    private Button btnProcessCallIns = new Button("id=logCall", "Process Call-Ins");
    private ComboBox cmbMaxDistance = new ComboBox("css=select#maxCallInDistance", "Maximum distance to participant");
    private ComboBox cbSendOptionParticipantFirst = new ComboBox("//select[@name='selectedId1']","Send menu of the first participant");
    private ComboBox cbSendOptionParticipantSecond = new ComboBox("//select[@name='selectedId2']","Send menu of the second participant");
    private TextBox tbJobTitle = new TextBox(By.id("joLookup"), "Job title");
    private String answer = "Called,No Answer";

    /**
     * Default constructor
     */
    public CallInCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Call In Creation')]"), "Call In Creation");
    }

    /**
     * Get job order title text
     * @return job order title text
     */
    public String getJobOrderTitleText() {
        return tbJobTitle.getText();
    }
    /**
     * This method is used for open Job Order search form from look-up
     * @return Job Order search form
     */
    public JobOrderSearchForm clickJobOrderLookUpButton() {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        return new JobOrderSearchForm();
    }

    /**
     * This method is used for search for specific Job Order and select it
     * @param companyName Company name
     * @param city Company address
     * @param zipCode Company zip code
     * @param jobTitle Job Order title
     * @return Job Order participantSSDetails (0 - number, 1 - title)
     */
    public String[] selectAndReturnJobOrder(String companyName, String city, String zipCode, String jobTitle) {
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        return jobOrderSearchForm.performSearchAndReturn(companyName, city, zipCode, jobTitle);
    }

    /**
     * This method is used for search for specific Job Order and select it
     * @param jobOrder Job Order object with data for search
     */
    public void selectJobOrder(JobOrder jobOrder) {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearchAndReturn(jobOrder);
    }

    /**
     * Choose option of the participant and process call in.
     */
    public void processCallIn() {
        cbSendOptionParticipantFirst.select(answer);
        process();
    }

    /**
     * Select first two contacts in the Qualified Participants section
     * @param answer - contact type
     */
    public void selectFirstTwoContacts(String answer) {
        cbSendOptionParticipantFirst.select(answer);
        cbSendOptionParticipantSecond.select(answer);
    }

    /**
     * Process call in.
     */
    public void process() {
        btnProcessCallIns.clickAndWait();
    }

    /**
     * Select max distante for participant
     * @param distance - max distance
     */
    public void selectDistance(String distance) {
        cmbMaxDistance.select(distance);
    }

    /**
     * Select method for call in.
     * @param option - call in option
     */
    public void selectCallIn(String option) {
        cmbCallIn.select(option);
    }

    /**
     * Select veterans only or non-veterans only radio buttons
     * @param veteran - if participant veteran
     */
    public void selectVeterans(Boolean veteran) {
        if (veteran) {
            rbOnlyVeterans.click();
        } else {
            rbOnlyNonVeterans.click();
        }
    }

    /**
     * Select "all" radio buttons.
     */
    public void selectRbAll() {
        rbAll.click();
    }

    /**
     * Select academic requirements check box.
     */
    public void uncheckAcademicRequirements() {
        if (chkAcademicRequirements.isSelected()) {
            chkAcademicRequirements.click();
        }
    }

    /**
     * Check driver license requirements or uncheck it.
     */
    public void selectDriverLicenseRequirement(Boolean check) {
        if (check) {
            chkDriversLicenseRequirements.click();
        } else {
            if (chkDriversLicenseRequirements.isSelected()) {
                chkDriversLicenseRequirements.click();
            }
        }
    }
}
