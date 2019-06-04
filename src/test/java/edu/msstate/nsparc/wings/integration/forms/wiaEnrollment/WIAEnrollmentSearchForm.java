package edu.msstate.nsparc.wings.integration.forms.wiaEnrollment;

import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Enrollments -> Search
 */
public class WIAEnrollmentSearchForm extends WIAEnrollmentBaseForm {

    private Link lnkParticipant = new Link(By.xpath("//table[@id='results-table']//tbody//td[3]//a"), "Participant");
    private RadioButton rbWIAStartedYes = new RadioButton(By.xpath("//table[@id='search-fields']//input[@id='isStarted1']"), "WIA Started - Yes");
    private RadioButton rbWIAStartedNo = new RadioButton(By.xpath("//table[@id='search-fields']//input[@id='isStarted2']"), "WIA Started - No");
    private RadioButton rbWIAExitedYes = new RadioButton(By.xpath("//table[@id='search-fields']//input[@id='isExited1']"), "WIA Exited - Yes");
    private RadioButton rbWIAExitedNo = new RadioButton(By.xpath("//table[@id='search-fields']//input[@id='isExited2']"), "WIA Exited - No");
    private TableCell tbcParticipant = new TableCell(By.xpath("//table[@id='results-table']/tbody/tr/td[3]"), "Participant table cell");
    private ComboBox spnParticipantTypeExited = new ComboBox(By.xpath("//select[@id='participantType']"), "Participant table cell");

    /**
     * Default constructor
     */
    public WIAEnrollmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIA Enrollment Search')]"), "Wia Enrollment Search");
    }

    /**
     * Click participant name.
     */
    public void clickParticipant() {
        lnkParticipant.clickAndWait();
    }

    /**
     * Click WIA started radio buttons (yes or no)
     *
     * @param yes - if true - yes.
     */
    public void clickWiaStarted(Boolean yes) {
        if (yes) {
            rbWIAStartedYes.click();
        } else {
            rbWIAStartedNo.click();
        }
    }

    /**
     * Click WIA started radio buttons (yes or no)
     *
     * @param yes - if true - yes.
     */
    public void clickWiaExited(Boolean yes) {
        if (yes) {
            rbWIAExitedYes.click();
        } else {
            rbWIAExitedNo.click();
        }
    }

    /**
     * This method is used for selecting Participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }



    /**
     * This method is used for WIA Enrollment searching by Participant
     *
     * @param participant Participant that will be used in search
     */
    public void performSearch(Participant participant) {
        selectParticipant(participant);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record
     *
     * @param participant Participant that will be used in search
     */
    public void performSearchAndOpen(Participant participant) {
        performSearch(participant);
        openFirstSearchResult();
    }

    /**
     * Get participant text on the page
     *
     * @return participant text on page.
     */
    public String getParticipantTextPage() {
        return tbcParticipant.getText().trim();
    }

    /**
     * Get participant type exited
     *
     * @return participant type exited text.
     */
    public String getParticipantTypeExited() {
        final String youth = "Youth";
        spnParticipantTypeExited.select(youth);
        return spnParticipantTypeExited.getText();
    }
}
