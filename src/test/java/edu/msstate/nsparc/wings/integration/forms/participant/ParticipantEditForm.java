package edu.msstate.nsparc.wings.integration.forms.participant;

import edu.msstate.nsparc.wings.integration.models.Address;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via
 * Participants -> Participant Profiles -> Search for Participant -> Open record -> Click on Edit button
 */
public class ParticipantEditForm extends ParticipantBaseForm {

    private Span spnSSNError = new Span("css=span[id='ssn.errors']", "SSN error");

    // Personal Information
    private TextBox txbFirstName = new TextBox("id=firstName", "First Name");
    private TextBox txbLastName = new TextBox("id=lastName", "Last Name");
    private RadioButton rbNoDriversLicense = new RadioButton("css=input#haveDriversLicense2",
            "Doesn't Have Driver's License");
    private TextBox txbDateBirth = new TextBox(By.id("dateOfBirth"), "Date of birth");

    //Employer address
    private TextBox txbCity = new TextBox(By.id("tmpPreviousJob.address.city"), "City");

    //National Guard
    private ComboBox cmbMilitaryBranch = new ComboBox(By.id("tmpMilitaryRecord.militaryBranch"),
            "Military branch");
    private TextBox txbServiceBegin = new TextBox(By.id("tmpMilitaryRecord.dateMilitaryBegin"),
            "Military Service Begin Date");
    private TextBox txbServiceEnd = new TextBox(By.id("tmpMilitaryRecord.dateMilitaryEnd"),
            "Military Service End Date");
    private Button btnAdd = new Button(By.id("add"), "Add");

    //Contact Information Participant S-S
    private Button btnSave = new Button(By.id("update"), "Save Changes");

    private Button btnOsocLookup = new Button("css=div[id='osoclookup'] button", "OSOC Lookup");
    private TextBox txbOsocTitle = new TextBox(By.xpath("//input[@id='occTitle'] | //input[@id='tmpPreviousJob.osocTitle']"),
            "Osoc Title");
    private Button btnSearchOsoc = new Button("id=osocSearch", "Osoc Search");
    private RadioButton rbSelectOsoc = new RadioButton("css=table#results-table input", "Select Osoc");
    private Button btnReturnMultiple = new Button("id=returnMultiple", "Return");

    /**
     * Default constructor
     */
    public ParticipantEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Edit')]"), "Participant Edit");
    }

    /**
     * Participant edit form for SS
     *
     * @param formTitle - form title.
     */
    public ParticipantEditForm(String formTitle) {
        super(By.xpath(String.format("//div[@id='heading-title'][contains(.,'%1$s')]", formTitle)), "Edit form for "
                + formTitle);
    }

    /**
     * Input first name
     *
     * @param firstName - first name
     */
    public void inputFirstName(String firstName) {
        txbFirstName.type(firstName);
    }

    /**
     * Check error is present and visible
     *
     * @return true if error is present.
     */
    public Boolean checkErrorPresent() {
        return spnSSNError.isPresent();
    }

    /**
     * Input ssn value on the edit form
     *
     * @param ssn - ssn
     */
    public void inputSsn(String ssn) {
        txbSSN.type(ssn);
    }

    /**
     * Input birthday date.
     *
     * @param date - birthday date.
     */
    public void inputDateBirth(String date) {
        txbDateBirth.type(date);
    }

    /**
     * Input city
     *
     * @param city - city
     */
    public void inputCity(String city) {
        txbCity.type(city);
    }

    /**
     * Check radio button, that participant has no driver licence
     */
    public void checkNoDriverLicense() {
        rbNoDriversLicense.click();
    }

    /**
     * Select military Branch only
     *
     * @param milBranch - military branch
     */
    public void selectMilitaryBranch(String milBranch) {
        cmbMilitaryBranch.select(milBranch);
    }

    /**
     * Convert to the member of national guard
     *
     * @param militaryBranch - military branch of the national guard.
     * @param beginDate      - begin date
     * @param endDate        - end date
     */
    public void convertNationalGuard(String militaryBranch, String beginDate, String endDate) {
        cmbMilitaryBranch.select(militaryBranch);
        txbServiceBegin.type(beginDate);
        txbServiceEnd.type(endDate);
        btnAdd.clickAndWait();
    }

    public void fillLocations(Participant participant) {
        Integer newZipCode = 54367;
        participant.setNewAddress(new Address(newZipCode));
        inputLocationCityZip(participant.getAddress().getLineOne(), participant.getAddress().getCity(),
                participant.getAddress().getZipCode());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectLocationState(participant.getAddress().getState());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        btnSave.clickAndWait();
    }

    /**
     * This method is used for selecting OSOC code
     *
     * @param title - name of the osoc code
     */
    public void selectOsocCode(String title) {
        btnOsocLookup.clickAndWait();
        txbOsocTitle.type(title);
        btnSearchOsoc.clickAndWait();
        rbSelectOsoc.click();
        btnReturnMultiple.clickAndWait();
    }
}
