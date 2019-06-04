package edu.msstate.nsparc.wings.integration.forms.home;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import framework.elements.Button;
import framework.elements.H1;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
To open this form you need to log in as Staff and select location
 */
public class StaffHomeForm extends BaseWingsForm {

    private TableCell tbcLocation = new TableCell(By.xpath("//table[@id='contentContainer']//strong[2]"), "Location");
    private H1 h1CurrentLocationLabel = new H1("css=table.side-bar-inner h1", "Current location");
    private TableCell tbcUnresultedReferralsTable = new TableCell("css=table#unresulted-referrals > tbody", "Unresulted Referral");
    private TableCell tbcUnfinishedParticipants = new TableCell("css=table#unfinished-participants > tbody", "Unfinished participant");
    private Link lnkIncompleteParticipantsPagesQuantity = new Link("css=span.tablePageLinks","Incomplete Participants Pages Quantity");
    private Link lnkIncompleteParticipantsNextPage = new Link("//span[@class='tablePageLinks']//a[contains(.,'Next')]","Incomplete Participants - Next Page");
    private Link lnkUnresultedReferralDetails = new Link(By.xpath("//table[@id='unresulted-referrals']//tbody/tr[1]/td[1]/a"), "Details");
    private Link lnkServiceCenterUnresulted = new Link(By.xpath("//h2[contains(.,'Your Service Center')]"), "Service Center Unresulted Referrals");
    private Link lnkStaffUnresulted = new Link(By.xpath("//h2[contains(.,'Your Unresulted Referrals')]"), "Staff Unresulted Referrals");

    private boolean incompleteParticipantFound = false;
    private String incompleteParticipantsRows = "//table[@id='unfinished-participants']/tbody/tr";
    private String incompleteName = "table#unfinished-participants a:contains('%1$s')";
    private String specifiedDataRow = incompleteParticipantsRows + "[%1$d]/td[1]";
    private String specifiedInput = incompleteParticipantsRows + "[%1$d]/td[3]/input";
    private String specifiedLink = incompleteParticipantsRows + "[%1$d]/td[1]/a";

    /**
     * Default constructor
     */
    public StaffHomeForm() {
        super(By.xpath("//span[@id='breadCrumb']"), "Staff Home");
    }

    /**
     * Get location value text
     * @return location value text.
     */
    public String getLocationValueText() {
        return tbcLocation.getText().trim();
    }
    /**
     * This method is used for Abandoning partial participant records from home page
     * @param participant Participant that will be abandoned
     */
    private void abandonParticipant(Participant participant) {
        // getting number of records on home page
        int rowsCount = getNumberOfElementsPresent(By.xpath(incompleteParticipantsRows));
        // for each record comparing with Participant FIRST_NAME LAST_NAME SSN
        for (Integer j = 1; j <= rowsCount; j++) {
            String locator = String.format(specifiedDataRow, j);
            Link tmpLink = new Link(By.xpath(locator), "Participant Name");
            // getting text from participant link
            String text = tmpLink.getText();
            // if text matches or participant
            if (text.contains(participant.getFirstName())) {
                locator = String.format(specifiedInput, j);
                Button abandonButton = new Button(By.xpath(locator), "Abandon Button");
                abandonButton.click();
                // marking that we've found record
                incompleteParticipantFound = true;
            }
        }
    }

    /**
     * This method is used for Abandoning partial participant records from home page
     * @param participant Participant that will be abandoned
     */
    public void abandonIncompleteParticipant(Participant participant) {
        // reset search indicator
        incompleteParticipantFound = false;
        // if there is more than 1 page of search results
        if (lnkIncompleteParticipantsPagesQuantity.isPresent()) {
            int count = 0;
            int total = BaseNavigationSteps.getLastPage(lnkIncompleteParticipantsPagesQuantity);
            while (count <= total) {
                // for each page of search results perform checks
                abandonParticipant(participant);
                // if participant found - stop
                if (incompleteParticipantFound) {
                    break;
                }
                // else - open next page
                lnkIncompleteParticipantsNextPage.clickAndWait();
                count++;
                total = BaseNavigationSteps.getLastPage(lnkIncompleteParticipantsPagesQuantity);
            }
        } else {
            abandonParticipant(participant);
        }
        assertTrue("Incomplete participant isn't found", incompleteParticipantFound);
    }

    /**
     * This method is used for Incomplete participant record searching on home page
     * @param participant Participant that will be used for search
     */
    private void findIncompleteParticipantOnPage(Participant participant) {
        // if there is no records on home page fail test
        assertFalse("Incomplete Participant wasn't found", tbcUnfinishedParticipants.getText().contains("Nothing found to display."));
        String participantString = String.format("%1$s %2$s", participant.getFirstName(), participant.getLastName());
        // getting number of result pages
        int pageResultsCount = getNumberOfElementsPresent(By.xpath(incompleteParticipantsRows));
        
        for (int j = 1; j <= pageResultsCount; j++) {
            String locator = String.format(specifiedLink, j);
            Link lnkDetails = new Link(By.xpath(locator), "Incomplete Participant Details");
            String text = lnkDetails.getText();
            // if participant link contains our name and last name
            if (text.contains(participantString)) {
                // marking that we've found participant
                incompleteParticipantFound = true;
                // opening found record
                lnkDetails.clickAndWait();
                return;
            }
        }
    }

    public void openIncompleteParticipant(Participant participant) {
        // reset search indicator
        incompleteParticipantFound = false;
        // if there is more than 1 page of search results
        if (lnkIncompleteParticipantsPagesQuantity.isPresent()) {
            int count = 0;
            int total = BaseNavigationSteps.getLastPage(lnkIncompleteParticipantsPagesQuantity);
            while (count <= total) {
                // for each page of search results perform check
                findIncompleteParticipantOnPage(participant);
                // if participant was found - stop
                if (incompleteParticipantFound) {
                    break;
                }
                // else open next page
                lnkIncompleteParticipantsNextPage.clickAndWait();
                count++;
                total = BaseNavigationSteps.getLastPage(lnkIncompleteParticipantsPagesQuantity);
            }
        } else {
            findIncompleteParticipantOnPage(participant);
        }
        assertTrue("Incomplete participant was not found", incompleteParticipantFound);
    }

    public boolean checkIncompleteRecord(Participant participant) {
        TableCell tbcUnfinishedParticipnatName = new TableCell(By.cssSelector(String.format(incompleteName, participant.getFirstName())),
                "Unfinished participant name on the form");
        return tbcUnfinishedParticipnatName.isPresent();
    }

    public void selectUnresultedReferrals() {
        lnkUnresultedReferralDetails.clickAndWait();
    }

    public Boolean checkUnresultedReferralsPresent() {
        return lnkUnresultedReferralDetails.isPresent();
    }

    public Boolean checkUnresReferTablePresent() {
        return tbcUnresultedReferralsTable.isPresent();
    }

    public Boolean checkUnfinishedPartipPresent() {
        return tbcUnfinishedParticipants.isPresent();
    }

    public Boolean checkLocationHeadPresent() {
        return h1CurrentLocationLabel.isPresent();
    }

    public String getLocationHeadText() {
        return h1CurrentLocationLabel.getText().trim();
    }

    public void checkServiceUnresultedReferrals(Boolean state) {
        if (state) {
            lnkServiceCenterUnresulted.assertIsPresentAndVisible();
        } else {
            lnkServiceCenterUnresulted.assertIsNotPresent();
        }
    }

    public void checkStaffUnresultedReferrals(Boolean state) {
        if (state) {
            lnkStaffUnresulted.assertIsPresentAndVisible();
        } else {
            lnkStaffUnresulted.assertIsNotPresent();
        }
    }
}
