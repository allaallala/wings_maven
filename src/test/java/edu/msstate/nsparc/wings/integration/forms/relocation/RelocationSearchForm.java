package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import framework.customassertions.CustomAssertion;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Relocation -> Search
 */
public class RelocationSearchForm extends RelocationBaseForm {

    private TextBox txbApplicationDateFrom = new TextBox("id=mindateApplication", "Application Date From");
    private TextBox txbApplicationDateTo = new TextBox("id=maxdateApplication", "Application Date To");
    private TextBox txbRelocationDateFrom = new TextBox("id=mindateRelocation", "Relocation Date From");
    private TextBox txbRelocationDateTo = new TextBox("id=maxdateRelocation", "Relocation Date To");

    /**
     * Default constructor
     */
    public RelocationSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Relocation Search')]"), "Relocation Search");
    }

    /**
     * Search for Relocation record
     *
     * @param relocation Object with data for search
     */
    public void performSearch(Relocation relocation) {
        selectParticipant(relocation.getTradeEnrollment().getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Search for Relocation record and open it
     *
     * @param relocation Object with data for search
     */
    public void performSearchAndOpen(Relocation relocation) {
        performSearch(relocation);
        openFirstSearchResult();
    }

    /**
     * Search be employer name and open first available relocation
     *
     * @param employerName Employer name
     */
    public void performSearchByEmployerName(String employerName) {
        inputEmployerName(employerName);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Choose any available participant
     */
    public void selectAnyAvailableParticipant() {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        searchSelectResultAndReturn();
    }

    /**
     * Input application and relocation date
     *
     * @param relocation - relocation object
     */
    public void inputDate(Relocation relocation) {
        txbApplicationDateFrom.type(relocation.getApplicationDate());
        txbApplicationDateTo.type(relocation.getApplicationDate());
        txbRelocationDateFrom.type(relocation.getRelocationDate());
        txbRelocationDateTo.type(relocation.getRelocationDate());
    }

    /**
     * Check parameters set to default
     *
     * @param expectedValue - expected value
     */
    public void checkDate(String expectedValue) {
        CustomAssertion.softAssertContains(txbApplicationDateFrom.getText(), expectedValue, "Application Date From was not cleared!");
        CustomAssertion.softAssertContains(txbApplicationDateTo.getText(), expectedValue, "Application Date To was not cleared!");
        CustomAssertion.softAssertContains(txbRelocationDateFrom.getText(), expectedValue, "Relocation Date From was not cleared!");
        CustomAssertion.softAssertContains(txbRelocationDateTo.getText(), expectedValue, "Relocation Date To was not cleared!");
    }

    /**
     * Choose any available petition
     */
    public void selectAnyAvailablePetition() {
        clickAndWait(BaseButton.INACTIVE_PETITION);
        searchSelectResultAndReturn();
    }
}
