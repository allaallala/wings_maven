package edu.msstate.nsparc.wings.integration.forms.participantEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Participant Service Enrollment -> Search for record -> Open record
 */
public class ParticipantEnrollmentDetailsForm extends ParticipantEnrollmentBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcServiceResult = new TableCell(By.xpath("//form[@id='serviceEnrollment']//tr[contains(.,'Service Result')]/following-sibling::tr/td"), "Service Result");
    private TableCell tbcServiceName = new TableCell(By.xpath(String.format(detailPath + "/span", "Service")), "Service");

    private TableCell tbcServiceBeginDate = new TableCell(By.xpath(String.format(detailPath, "Service began on")), "Begin service date");
    private TableCell tbcServiceEndDate = new TableCell(By.xpath(String.format(detailPath, "Service ended on")), "Begin service date");
    private Button deleteButton = new Button("//input[@value='Delete']", "Delete button");

    /**
     * Default constructor
     */
    public ParticipantEnrollmentDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Detail')]"), "Enrollment Detail");
    }


    /**
     * Get services result
     *
     * @return services result
     */
    public String getServiceResult() {
        return tbcServiceResult.getText().trim();
    }

    /**
     * Get service name
     *
     * @return service name
     */
    public String getServiceName() {
        return tbcServiceName.getText();
    }

    /**
     * Get service begin date
     *
     * @return - service begin date.
     */
    public String getServiceBeginDate() {
        return tbcServiceBeginDate.getText();
    }

    /**
     * Get service end date
     *
     * @return - service end date.
     */
    public String getServiceEndDate() {
        return tbcServiceEndDate.getText();
    }

    /**
     * Check, that specified buttons are present on the page or not
     *
     * @param user        - current user
     * @param part        - participant to search after deletion.
     * @param serviceName - service name
     */
    public void checkButtons(User user, Participant part, String serviceName) {
        Boolean btnEdit = user.getServiceEnrollment().getPseViewEditButton();
        Boolean btnAudit = user.getServiceEnrollment().getPseViewAuditButton();
        Boolean btnDelete = user.getServiceEnrollment().getPseViewDeleteButton();
        checkButtonsPresent(btnEdit, btnAudit);
        divideMessage("Delete");
        ifButton(btnDelete, deleteButton);

        //Check, that is possible to open Audit button, if this button is present
        divideMessage("Check audit functionality");
        if (btnAudit) {
            clickAndWait(BaseButton.AUDIT);
            //new AuditForm().assertIsOpen();
            clickAndWait(BaseButton.DONE);
        }
        //Check, that is possible to edit record, if button [Edit] is present
        divideMessage("Check edit functionality");
        if (btnEdit) {
            clickButton(Buttons.Edit);
            ParticipantEnrollmentEditForm editPage = new ParticipantEnrollmentEditForm();
            editPage.inputServiceSave(CommonFunctions.getCurrentDate(), CommonFunctions.getCurrentDate());
            editPage.passParticipationRecalculationPage();
            CustomAssertion.softTrue("Incorrect service begin date", CommonFunctions.getCurrentDate().equals(getServiceBeginDate()));
            CustomAssertion.softTrue("Incorrect service end date", CommonFunctions.getCurrentDate().equals(getServiceEndDate()));
        }
        //Check, that is possible to delete record, if button [Delete] is present
        divideMessage("Check delete functionality");
        if (btnDelete) {
            deleteButton.click();
            areYouSure(Popup.Yes);
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
            BaseWingsSteps.popClick(Popup.Search);
            ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
            searchPage.performSearch(part, serviceName);
            searchPage.noSearchResults();
        }
    }
}
