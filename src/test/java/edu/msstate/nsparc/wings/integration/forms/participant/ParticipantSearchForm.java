package edu.msstate.nsparc.wings.integration.forms.participant;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.ContactInformationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Participant Profiles -> Search
 */
public class ParticipantSearchForm extends ParticipantBaseForm {

    private TextBox txbMSAccess = new TextBox("css=input[name='tmpUsername']",
            "Unemployment Services System Username");
    private TextBox txbSSN = new TextBox("css=input#ssn", "SSN");
    private TextBox txbDob = new TextBox(By.xpath("//input[@id='dateOfBirth']"), "Date Of Birth");
    private TextBox txbPhone = new TextBox("css=input[id='contactInformation.primaryPhone']", "Phone");
    private Button btnSubmit = new Button(By.xpath("//button[@alt='Submit']"), "Submit");
    private String participantName = "css=table#results-table > tbody a";
    private Link lnkParticipantName = new Link(participantName, "Participant Name");
    private String openParticipantXpath = "//a[contains(.,'%1$s')]";
    private String msAccess = "test";
    private String testPhone = "123456789";

    /**
     * Default constructor
     */
    public ParticipantSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Search')]"), "Participant Search");
    }

    /**
     * This method is used for search for Participant and check search results
     *
     * @param firstName Participant first name
     * @param lastName  Participant last name
     */
    public void performSearch(String firstName, String lastName) {
        txbFirstName.type(firstName);
        txbLastName.type(lastName);
        search();

        info("Checking, that Participant was found");
        CustomAssertion.softTrue("Participant wasn't found", lnkParticipantName.isPresent());
        info("Search result: OK");
    }

    public void performSearchExternal(Participant participant) {
        txbFirstName.type(participant.getFirstName());
        txbLastName.type(participant.getLastName());
        txbSSN.type(participant.getTruncatedSsn());
        search();


        if (txbDob.isPresent()) {
            txbLastName.type(participant.getLastName());
            txbSSN.type(participant.getTruncatedSsn());
            txbDob.type(participant.getParticipantBioInfo().getDateOfBirth());
            btnSubmit.clickAndWait();
        }

    }

    /**
     * This method is used for search for Participant without checking search results
     *
     * @param participant Participant for search
     */
    public void performSearchWithoutCheck(Participant participant) {
        txbFirstName.type(participant.getFirstName());
        txbLastName.type(participant.getLastName());
        search();
    }

    /**
     * This method is used for search for Participant and check search results
     *
     * @param participant Participant for search
     */
    public void performSearch(Participant participant) {

        performSearch(participant.getFirstName(), participant.getLastName());
    }

    /**
     * This method is used for search for participant record and open it
     *
     * @param participant Participant for search
     */
    public void performSearchAndOpen(Participant participant) {
        performSearch(participant);
        CustomAssertion.softTrue("Participant wasn't found", lnkParticipantName.isPresent());
        lnkParticipantName.clickAndWait();
    }

    public void performSearchAndOpenByUser(User user, Participant participant) {
        performSearchByUser(user, participant);

        selectResultAndReturn();
    }

    public void performSearchByUser(User user, Participant participant) {
        if (user.getRole().equals(Roles.LWDASTAFF) || user.getRole().equals(Roles.WIOAPROVIDER)) {
            performSearchExternal(participant);
        } else {
            performSearch(participant);
            CustomAssertion.softTrue("Participant wasn't found", lnkParticipantName.isPresent());
            lnkParticipantName.clickAndWait();
        }

        if (isButtonPresent(BaseButton.DONE)) {
            clickButton(Buttons.Done);
        }
    }

    /**
     * This method is used for search for Participant from look-ups
     *
     * @param firstName First name of Participant
     * @param lastName  Last name of Participant
     */
    public void performSearchAndSelect(String firstName, String lastName) {
        performSearch(firstName, lastName);
        selectResultAndReturn();
    }

    /**
     * This method is used for search for Participant from look-ups
     *
     * @param participant Participant for search
     */
    public void performSearchAndSelect(Participant participant) {
        performSearchAndSelect(participant.getFirstName(), participant.getLastName());
    }

    /**
     * This method gets participant first name from string "FIRST_NAME LAST_NAME SSN" and types it in textbox
     *
     * @param participantName String "FIRST_NAME LAST_NAME SSN"
     */
    public void fillFirstName(String participantName) {
        String firstName = CommonFunctions.regexGetMatchGroup(participantName, "(\\w+)\\s");
        txbFirstName.type(firstName);
    }

    /**
     * This method gets participant last name from string "FIRST_NAME LAST_NAME SSN" and types it in textbox
     *
     * @param participantName String "FIRST_NAME LAST_NAME SSN"
     */
    public void fillLastName(String participantName) {
        String lastName = CommonFunctions.regexGetMatchGroup(participantName, "\\s(\\w+)");
        txbLastName.type(lastName);
    }

    /**
     * This method is used for open Participant participantSSDetails form from search result
     *
     * @param participant Participant that will be opened
     */
    public void clickOnSearchResult(Participant participant) {
        Link lnkOpenParticipant = new Link(By.xpath(String.format(openParticipantXpath, participant.getFirstName())),
                "Open Participant profile");
        lnkOpenParticipant.clickAndWait();
    }

    /**
     * Fill out search criteria fields
     *
     * @param participant Participant object which data will be used for search
     */
    public void fillFields(Participant participant) {
        txbFirstName.type(participant.getFirstName());
        txbLastName.type(participant.getLastName());
        txbSSN.type(participant.getSsn());
        txbMSAccess.type(msAccess);
        ContactInformationForm infoForm = new ContactInformationForm();
        infoForm.inputLocationCity(participant.getAddress().getCity());
        infoForm.inputZipCode(participant.getAddress().getZipCode());
        txbPhone.type(testPhone);
    }

    /**
     * Check if search criteria fields were cleared (should be empty)
     */
    public void checkFields() {
        CustomAssertion.softAssertEquals(txbFirstName.getValue(), Constants.EMPTY, "First name isn't empty");
        CustomAssertion.softAssertEquals(txbLastName.getValue(), Constants.EMPTY, "Last name isn't empty");
        CustomAssertion.softAssertEquals(txbSSN.getValue(), Constants.EMPTY, "SSN isn't empty");
        CustomAssertion.softAssertEquals(txbMSAccess.getValue(), Constants.EMPTY,
                "Unemployment Services System isn't empty");
        ContactInformationForm infoForm = new ContactInformationForm();
        CustomAssertion.softAssertEquals(infoForm.getLocationCity(), Constants.EMPTY, "City isn't empty");
        CustomAssertion.softAssertEquals(infoForm.getZipCode(), Constants.EMPTY, "Zip Code isn't empty");
        CustomAssertion.softAssertEquals(txbPhone.getValue(), Constants.EMPTY, "Phone isn't empty");
    }

    /**
     * Check, that participant is present on the page.
     *
     * @return if element present or not.
     */
    public Boolean checkParticipantFound() {
        return lnkParticipantName.isPresent();
    }

    /**
     * Input ssn
     *
     * @param ssn - ssn to type.
     */
    public void inputSSN(String ssn) {
        txbSSN.type(ssn);
    }
}

