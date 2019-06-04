package edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Employers -> Rapid Response Events -> Search
 */
public class RapidResponseEventSearchForm extends RapidResponseEventBaseForm {

    private Link lnkEventNumber = new Link("css=table#results-table > tbody a", "Event Number");
    private Link lnkSortArrow = new Link("css=table#results-table > thead a", "Sort arrow");
    private TextBox tbNotificationDateFrom = new TextBox("id=minDateNotification", "Notification Date From");
    private TextBox tbNotificationDateTo = new TextBox("id=maxDateNotification", "Notification Date To");
    private TextBox tbImpactDateFrom = new TextBox("id=minDateImpact", "Impact Date From");
    private TextBox tbImpactDateTo = new TextBox("id=maxDateImpact", "Impact Date To");
    private TextBox tbEventNumber = new TextBox("id=eventNumber", "Event Number");
    private RadioButton rbSelected = new RadioButton(By.xpath("//input[@name='selectedId']"), "Selected Id");
    private Button btnReturn = new Button(By.xpath("//button[@id='return']"), "Return button");
    private String xpathExp = "//table[@id='results-table']/tbody/tr[%1$d]/td[%2$d]";

    /**
     * Default constructor
     */
    public RapidResponseEventSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Rapid Response Event Search')]"), "Rapid Response Event Search");
    }

    /**
     * Searching for the record
     *
     * @param event Event participantSSDetails
     */
    public void performSearch(RapidResponseEvent event) {
        selectEmployer(event.getEmployer());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record
     *
     * @param event Event participantSSDetails
     */
    public void performSearchAndOpen(RapidResponseEvent event) {
        performSearch(event);
        lnkEventNumber.clickAndWait();
    }

    public enum RRE_CRITERIA { EMPLOYEER, WORKFORCE, NOTIFICATION_FROM, NOTIFICATION_TO, IMPACT_FROM, IMPACT_TO, NAICS }

    /**
     * Searching for the record by one defined field.
     *
     * @param event       Event participantSSDetails
     * @param searchField search criteria
     */
    public void performSearchCriteria(RapidResponseEvent event, RRE_CRITERIA searchField) {
        switch (searchField) {
            case EMPLOYEER:
                performSearch(event);
                break;
            case WORKFORCE:
                selectWorkforceArea(event.getWorkforceArea());
                clickAndWait(BaseButton.SEARCH);
                break;
            case NOTIFICATION_FROM:
                tbNotificationDateFrom.type(event.getNotificationDate());
                clickAndWait(BaseButton.SEARCH);
                break;
            case NOTIFICATION_TO:
                tbNotificationDateTo.type(event.getNotificationDate());
                clickAndWait(BaseButton.SEARCH);
                break;
            case IMPACT_FROM:
                tbImpactDateFrom.type(event.getImpactDate());
                clickAndWait(BaseButton.SEARCH);
                break;
            case IMPACT_TO:
                tbImpactDateTo.type(event.getImpactDate());
                clickAndWait(BaseButton.SEARCH);
                break;
            case NAICS:
                Employer emp = event.getEmployer();
                inputNaicsTemp(emp.getNaics());
                clickAndWait(BaseButton.SEARCH);
                break;
            default:
                info("Try to insert other name of the search field");
                break;
        }
    }

    /**
     * Checking values in the Search table using Employeer name and Event description.
     *
     * @param event event
     */
    public void validateSearchResult(RapidResponseEvent event) {
        lnkSortArrow.clickAndWait();
        for (int i = 1; i < Constants.RECORDS_ON_PAGE; i++) {
            TableCell tbcEmpl = new TableCell(By.xpath(String.format(xpathExp, i, 3)), "Value of employeer in the search result table");
            TableCell tbcDesc = new TableCell(By.xpath(String.format(xpathExp, i, 6)), "Value of employeer in the search result table");
            info(tbcEmpl.getText());
            info(tbcDesc.getText());
            if (tbcEmpl.getText().contains(event.getEmployer().getCompanyName()) && tbcDesc.getText().contains(event.getDescription())) {
                info("The requested record was found");
                break;
            } else {
                fatal("The requested record wasn't found");
            }
        }
    }

    /**
     * Inputs event number
     *
     * @param eventNumber - event number
     */
    public void inputEventNumber(String eventNumber) {
        tbEventNumber.type(eventNumber);
    }

    /**
     * Check, that changes made on the page are cleared.
     */
    public void checkFieldsCleared() {
        String empty = "";
        CustomAssertion.softAssertEquals(tbNotificationDateFrom.getValue(), empty, "The field 'From' is not empty!");
        CustomAssertion.softAssertEquals(tbNotificationDateTo.getValue(), empty, tbNotificationDateTo.getValue());
        CustomAssertion.softAssertEquals(tbEventNumber.getValue(), empty, "The field 'Event number' is not empty!");
    }

    /**
     * Input date "from" and date "to"
     *
     * @param dateFrom - created rre from
     * @param dateTo   - created rre to.
     */
    public void inputDateFromTo(String dateFrom, String dateTo) {
        tbNotificationDateFrom.type(dateFrom);
        tbNotificationDateTo.type(dateTo);
    }

    /**
     * Selected rapid response event found
     */
    public void selectReturn() {
        rbSelected.click();
        btnReturn.clickAndWait();
    }

    /**
     * Get searched count with regex
     * @return formatted count
     */
    public String getSearchedCountRegex() {
        return CommonFunctions.regexGetMatch(getSearchedCount(), "\\d{1,}(.|,)?\\d{1,}");
    }

}
