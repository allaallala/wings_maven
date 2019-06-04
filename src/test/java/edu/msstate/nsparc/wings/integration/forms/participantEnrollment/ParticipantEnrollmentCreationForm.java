package edu.msstate.nsparc.wings.integration.forms.participantEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import framework.elements.Button;
import framework.elements.Span;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Participant Service Enrollment -> Create
 */
public class ParticipantEnrollmentCreationForm extends ParticipantEnrollmentBaseForm {

    private Button btnServiceLookup = new Button(By.xpath("//button[@title='Search']"), "Services Lookup");
    private TextBox txbCreationDate = new TextBox("css=input[id='datePosted']", "Creation Date");
    private Span spnServiceError = new Span("css=span[id='services.errors']", "Services Errors");
    private TextBox txbScheduledDate = new TextBox("css=input#dateSchedule", "Scheduled Date");
    private TextBox txbScheduledTime = new TextBox("css=input#timeScheduleId", "Scheduled Time");
    private TextBox txbAnswer = new TextBox("css=input[id='serviceEnrollments0.serviceAnswers0.answer']", "Question Answer");
    private Button btnYouthProviderLookup = new Button(By.xpath("//div[@id='youthProviderSection0']//button"), "Youth Provider Lookup");


    /**
     * Default constructor
     */
    public ParticipantEnrollmentCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Creation')]"), "Enrollment Creation");
    }

    /**
     * This method is used for searching and selecting Service in look-up
     *
     * @param serviceName Name of the service that will be selected
     */
    public void selectService(String serviceName) {
        btnServiceLookup.clickAndWait();
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.selectAndReturnParticipantServiceCheckbox(serviceName);
        if (btnYouthProviderLookup.isPresent()) {
            selectYouthProvider();
        }
    }

    /**
     * This method is used for selecting Youth Provider
     */
    public void selectYouthProvider() {
        if (btnYouthProviderLookup.isPresent()) {
            btnYouthProviderLookup.clickAndWait();
            CenterSearchForm activityManagerSearch = new CenterSearchForm();
            activityManagerSearch.selectAndReturnCenter();
        }
    }

    /**
     * Input creation date
     *
     * @param creationDate - creation date
     */
    public void inputCreationDate(String creationDate) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbCreationDate.type(creationDate);
    }

    /**
     * Input scheduled date and time
     *
     * @param scheduledDate - scheduled date
     * @param scheduledTime - scheduled time
     */
    public void inputScheduledDateTime(String scheduledDate, String scheduledTime) {
        txbScheduledDate.type(scheduledDate);
        txbScheduledTime.type(scheduledTime);
    }

    /**
     * Input answer
     *
     * @param answer - answer
     */
    public void inputAnswer(String answer) {
        txbAnswer.type(answer);
    }

    /**
     * Complete participant enrollment creation.
     */
    public void completeCreation() {
        clickButton(Buttons.Create);
        clickButton(Buttons.Done);
    }

    /**
     * Get service error text
     *
     * @return service error text.
     */
    public String getServiceErrorText() {
        return spnServiceError.getText();
    }

    /**
     * Find created service and check that is present in the results
     *
     * @param participant  - participant
     * @param serviceName  - service name
     * @param creationDate - creation Date.
     */
    public void findCreatedServiceCheckDate(Participant participant, String serviceName, String creationDate) {
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.performSearch(participant, serviceName);

        divideMessage("Check search results");
        searchForm.checkSomeFields(participant.getNameForSearchPages(), serviceName, creationDate);
    }
}
