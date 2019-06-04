package edu.msstate.nsparc.wings.integration.forms.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Trade Enrollments -> Search
 */
public class TradeEnrollmentSearchForm extends TradeEnrollmentBaseForm {

    private TextBox txbParticipant = new TextBox("//span[@id='participantLookup']/a/input[2]", "Participant");
    private TextBox txbPetition = new TextBox("//span[@id='petitionLookup']/a/input[2]", "Petition");
    private TextBox txbCreatorStaff = new TextBox("//span[@id='stafflookup']/a/input[2]", "Creator Staff");
    private String firstName = "Auto";
    private String lastName = "Admin";

    /**
     * Default constructor
     */
    public TradeEnrollmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Search')]"), "Trade Enrollment Search");
    }

    /**
     * Searching for the record
     *
     * @param tradeEnrollment Trade Enrollment participantSSDetails
     */
    public void performSearch(TradeEnrollment tradeEnrollment) {
        selectParticipant(tradeEnrollment.getParticipant());
        selectInactivePetition(tradeEnrollment.getPetition());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record and opening it
     *
     * @param tradeEnrollment Trade Enrollment participantSSDetails
     */
    public void performSearchAndOpen(TradeEnrollment tradeEnrollment) {
        performSearch(tradeEnrollment);
        openFirstSearchResult();
    }

    /**
     * Fills out search criteria fields
     *
     * @param tradeEnrollment Trade Enrollment participantSSDetails
     */
    public void fillSearchCriteria(TradeEnrollment tradeEnrollment) {
        selectParticipant(tradeEnrollment.getParticipant());
        selectInactivePetition(tradeEnrollment.getPetition());
        Staff autoAdmin = new Staff();
        autoAdmin.setFirstName(firstName);
        autoAdmin.setLastName(lastName);
        selectStaff(autoAdmin);
    }

    /**
     * Validate if lookups fields are empty
     */
    public void validateEmptyLookup() {
        txbParticipant.isPresent();
        txbPetition.isPresent();
        txbCreatorStaff.isPresent();
    }
}
