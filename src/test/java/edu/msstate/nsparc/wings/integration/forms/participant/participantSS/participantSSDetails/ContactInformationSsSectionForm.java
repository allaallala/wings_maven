package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class ContactInformationSsSectionForm extends BaseParticipantSsDetailsForm {

    private String[] allContactMethods = {"Phone", "Email", "Text Message", "Mail"};
    private TableCell tbcContactMethods = new TableCell(By.xpath(String.format(parameterPath, "Contact Methods")), "Contact Methods");
    private TableCell tbcPhoneNumber = new TableCell(By.xpath(String.format(parameterPath, "Phone Number:")), "Phone Number");
    private TableCell tbcAltPhoneNumber = new TableCell(By.xpath(String.format(parameterPath, "Alternate Phone Number:")), "Alternate Phone Number");
    private TableCell tbcFaxNumber = new TableCell(By.xpath(String.format(parameterPath, "Fax Number:")), "Fax Number");
    private TableCell tbcEmailAddress = new TableCell(By.xpath(String.format(parameterPath, "Email Address:")), "Email Address");
    private TableCell tbcPreferredMessage = new TableCell(By.xpath(String.format(parameterPath, "Preferred Text Message Number")), "Preferred Text Message Number");
    private TableCell tbcResidenceAddress = new TableCell(By.xpath(String.format(parameterPath, "Residence Address")), "Residence Address");
    private TableCell tbcMailingAddress = new TableCell(By.xpath(String.format(parameterPath, "Mailing Address")), "Mailing Address");


    public ContactInformationSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Contact Information')]"), "Contact Information");
    }


    /**
     * Validate data in the contact information section
     *
     * @param participant - participant
     * @param addParam    - shows, that it's necessary to check contact text, fax number, alternate phone number, preferred text number.
     */
    public void validateContactInformationSection(Participant participant, Boolean addParam) {
        CustomAssertion.softTrue("Phone is not displayed in the contact methods", tbcContactMethods.getText().contains(allContactMethods[0]));
        CustomAssertion.softTrue("Email is not displayed in the contact methods", tbcContactMethods.getText().contains(allContactMethods[1]));
        CustomAssertion.softTrue("Mail is not displayed in the contact methods", tbcContactMethods.getText().contains(allContactMethods[3]));
        if (addParam) {
            CustomAssertion.softTrue("Text message is not displayed in the contact methods", tbcContactMethods.getText().contains(allContactMethods[2]));
            CustomAssertion.softTrue("Incorrect preferred text number", tbcPreferredMessage.getText().contains(participant.getPrimaryPhone()));
            CustomAssertion.softTrue("Incorrect alternate phone number", tbcAltPhoneNumber.getText().contains(participant.getPrimaryPhone()));
            CustomAssertion.softTrue("Incorrect fax number", tbcFaxNumber.getText().contains(participant.getPrimaryPhone()));
        }
        validateResidenceMailingAddress(participant);
        CustomAssertion.softTrue("Incorrect primary phone number", tbcPhoneNumber.getText().contains(participant.getPrimaryPhone()));
        CustomAssertion.softTrue("Incorrect email address", tbcEmailAddress.getText().contains(participant.getEmail()));
    }

    /**
     * Validate residence and mailing address
     *
     * @param participant - participant
     */
    public void validateResidenceMailingAddress(Participant participant) {
        CustomAssertion.softTrue("Incorrect residence address", tbcResidenceAddress.getText().contains(
                participant.getAddress().getLineOne() + "\n" + participant.getAddress().getLineOne() + "\n" + participant.getAddress().getCity() + ", "
                        + participant.getAddress().getState() + " " + participant.getAddress().getZipCode() + "\n" + participant.getAddress().getCounty() + " County "
                        + participant.getAddress().getCountry()));
        CustomAssertion.softTrue("Incorrect mailing address", tbcMailingAddress.getText().contains(
                participant.getAddress().getLineOne() + "\n" + participant.getAddress().getCity() + ", "
                        + participant.getAddress().getState() + " " + participant.getAddress().getZipCode() + "\n" + participant.getAddress().getCounty() + " County " + participant.getAddress().getCountry()));
    }
}
