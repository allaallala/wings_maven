package edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment;

import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

/**
This form is opened via Participants -> Trade -> ATAA/RTAA Enrollments -> Search
 */
public class AtaaRtaaEnrollmentSearchForm extends AtaaRtaaEnrollmentBaseForm {

    private TextBox TXB_PARTICIPANT = new TextBox(By.xpath("//span[@id='participantLookup']//input[2]"), "Participant");
    private TextBox TXB_PETITION = new TextBox(By.xpath("//span[@id='petitionLookup']//input[2]"), "Petition");
    private TextBox TXB_SERVICE_CENTER = new TextBox(By.xpath("//span[@id='jclookup']//input[2]"), "Service center");


    private final ComboBox cmbAtaaRtaaStatus = new ComboBox("id=status", "Status");
    private final TextBox txbSeparationDateFrom = new TextBox("id=minDateSeparation", "From");
    private final TextBox txbSeparationDateTo = new TextBox("id=maxDateSeparation", "To");
    private final Link lnkServiceCenterOfFirstSearchResult = new Link(By.xpath("//table[@id='results-table']//td[5]/a"), "First Search Result - Service Center Link");
    private final Span spFirstFoundParticipant = new Span(By.xpath("//table[@id='results-table']//span[@modelclass='Participant']"), "Participant cell in result table");
    
    private final Link lnkParticipantTableHeader = new Link(By.xpath("//th/a[contains(.,'Participant')]"), "Participant table header");
    private final Link lnkPetitionTableHeader = new Link(By.xpath("//th/a[contains(.,'Petition')]"), "Petition table header");
    private final Link lnkSeparationDateTableHeader = new Link(By.xpath("//th/a[contains(.,'Separation Date')]"), "Separation Date table header");
    private final Link lnkServiceCenterTableHeader = new Link(By.xpath("//th/a[contains(.,'Service Center')]"), "Service Center table header");
    
    private final TableCell thParticipantSortedAsc = new TableCell("//th[@class='sortable sorted order1']/a[contains(text(),'Participant')]","Sorting by Participant asc");
    private final TableCell thParticipantSortedDes = new TableCell("//th[@class='sortable sorted order2']/a[contains(text(),'Participant')]", "Sorting by Participant desc");
    private final TableCell thPetitionSortedAsc = new TableCell("//th[@class='sortable sorted order1']/a[contains(text(),'Petition')]","Sorting by Petition asc");
    private final TableCell thPetitionSortedDesc = new TableCell("//th[@class='sortable sorted order2']/a[contains(text(),'Petition')]","Sorting by Petition desc");
    private final TableCell thSeparationDateSortedAsc = new TableCell("//th[@class='sortable sorted order1']/a[contains(text(),'Separation Date')]","Sorting by Separation Date asc");
    private final TableCell thSeparationDateSortedDesc = new TableCell("//th[@class='sortable sorted order2']/a[contains(text(),'Separation Date')]","Sorting by Separation Date desc");
    private final TableCell thServiceCenterSortedAsc = new TableCell("//th[@class='sortable sorted order1']/a[contains(text(),'Service Center')]","Sorting by Service Center asc");
    private final TableCell thServiceCenterSortedDesc = new TableCell("//th[@class='sortable sorted order2']/a[contains(text(),'Service Center')]","Sorting by Service Center desc");

    /**
     * Default constructor
     */
    public AtaaRtaaEnrollmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'ATAA/RTAA Enrollment Search')]"), "Trade Enrollment Search");
    }

    /**
     * Searching for the record
     * @param enrollment Enrollment participantSSDetails
     */
    public void performSearch(AtaaRtaaEnrollment enrollment) {
        selectParticipant(enrollment.getParticipant());
        selectInactivePetition(enrollment.getPetition());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record and opening it
     * @param enrollment Enrollment participantSSDetails
     */
    public void performSearchAndOpen(AtaaRtaaEnrollment enrollment) {
        performSearch(enrollment);
        openFirstSearchResult();
    }

    public void fillOutSearchCriteriaFields(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        selectParticipant(ataaRtaaEnrollment.getParticipant());
        selectInactivePetition(ataaRtaaEnrollment.getPetition());
        selectAnyAvailableServiceCenter();
        cmbAtaaRtaaStatus.select(ataaRtaaEnrollment.getEligibilityString());
        txbSeparationDateFrom.type(ataaRtaaEnrollment.getEligibilityDeterminationDate());
        txbSeparationDateTo.type(ataaRtaaEnrollment.getEligibilityDeterminationDate());
    }

    /**
     * Click service center link for first search result.
     */
    public void clickServiceCenter() {
        lnkServiceCenterOfFirstSearchResult.clickAndWait();
    }

    /**
     * Get name of the participant found
     * @return name of the participant.
     */
    public String getFoundParticipantName() {
        return spFirstFoundParticipant.getText();
    }

    /**
     * Input status date
     * @param enrollment - enrollment object
     */
    public void inputStatusDate(AtaaRtaaEnrollment enrollment) {
        cmbAtaaRtaaStatus.select(enrollment.getEligibilityString());
        txbSeparationDateFrom.type(enrollment.getEligibilityDeterminationDate());
        txbSeparationDateTo.type(enrollment.getEligibilityDeterminationDate());
    }

    /**
     * Validate search criteria
     * @param emptyField - empty field
     */
    public void validateSearchCriteria(String emptyField) {
        CustomAssertion.softAssertContains(TXB_PARTICIPANT.getText(), emptyField, "Participant was not cleared!");
        CustomAssertion.softAssertContains(TXB_PETITION.getText(), emptyField, "Petition was not cleared!");
        CustomAssertion.softAssertContains(TXB_SERVICE_CENTER.getText(), emptyField, "Service Center was not cleared!");
        CustomAssertion.softAssertContains(txbSeparationDateFrom.getText(), emptyField, "Separation Date From was not cleared!");
        CustomAssertion.softAssertContains(txbSeparationDateTo.getText(), emptyField, "Separation Date To was not cleared!");
        CustomAssertion.softAssertContains(cmbAtaaRtaaStatus.getSelectedLabel(), emptyField, "Status was not set to default");
    }

    /**
     * Sort by participant and check sorting
     */
    public void sortByParticipantAndCheckSorting() {
        assertTrue("Participants are not sorted ascending by default!", thParticipantSortedAsc.isPresent());
        lnkParticipantTableHeader.clickAndWait();
        assertTrue("Participants are not sorted descending!", thParticipantSortedDes.isPresent());
    }

    /**
     * Sort by petition number and check sorting
     */
    public void sortByPetitionAndCheckSorting() {
        lnkPetitionTableHeader.clickAndWait();
        assertTrue("Petitions are not sorted ascending!", thPetitionSortedAsc.isPresent());
        lnkPetitionTableHeader.clickAndWait();
        assertTrue("Petitions are not sorted descending!", thPetitionSortedDesc.isPresent());
    }

    /**
     * Sort by separation date and check sorting
     */
    public void sortBySeparationDateAndCheckSorting() {
        lnkSeparationDateTableHeader.clickAndWait();
        assertTrue("Separation Dates are not sorted ascending!", thSeparationDateSortedAsc.isPresent());
        lnkSeparationDateTableHeader.clickAndWait();
        assertTrue("Separation Dates are not sorted descending!", thSeparationDateSortedDesc.isPresent());
    }

    /**
     * Sort by service center and check sorting
     */
    public void sortByServiceCenterAndCheckSorting() {
        lnkServiceCenterTableHeader.clickAndWait();
        assertTrue("Service Centers are not sorted ascending!", thServiceCenterSortedAsc.isPresent());
        lnkServiceCenterTableHeader.clickAndWait();
        assertTrue("Service Centers are not sorted descending!", thServiceCenterSortedDesc.isPresent());
    }
}