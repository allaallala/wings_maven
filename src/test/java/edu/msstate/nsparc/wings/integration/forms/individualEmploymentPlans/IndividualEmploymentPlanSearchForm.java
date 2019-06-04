package edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import framework.customassertions.CustomAssertion;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Individual Employment Plans -> Search
 */
public class IndividualEmploymentPlanSearchForm extends IndividualEmploymentPlanBaseForm {

    private Link lnkFirstIEPResult = new Link("css=table#results-table > tbody a", "Individual Employment Plan");
    private Link lnkDatePlanCreatedSort = new Link("//th[contains(.,'Date Plan Created')]", "Sorting by date plan created");
    private TextBox tbDateFrom = new TextBox("id=minDateCreation", "Date Plan Created From");
    private TextBox tbDateTo = new TextBox("id=maxDateCreation", "Date Plan Created To");
    private String managerFirstName = "Auto";
    private String managerLastName = "Staff";

    /**
     * Default constructor
     */
    public IndividualEmploymentPlanSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Individual Employment Plan Search')]"), "Individual Employment Plan Search");
    }

    /**
     * Searching for the record
     *
     * @param iep IEP participantSSDetails
     */
    public void performSearch(User user, IndividualEmploymentPlan iep) {
        selectParticipantByUser(user, iep.getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record
     *
     * @param iep IEP participantSSDetails
     */
    public void performSearchAndOpen(User user, IndividualEmploymentPlan iep) {
        performSearch(user, iep);
        lnkFirstIEPResult.clickAndWait();
    }

    public enum IepSearchCriteria { PARTICIPANT, DATEFROM, DATETO, CASEMANAGER }

    /**
     * Fill out Search form by one (and only one) search criteria.
     *
     * @param plan - individual employment plan
     * @param type - search criteria
     */
    public void fillOneCriteria(IndividualEmploymentPlan plan, IepSearchCriteria type) {
        switch (type) {
            case PARTICIPANT:
                selectParticipant(plan.getParticipant().getFirstName(), plan.getParticipant().getLastName());
                break;
            case DATEFROM:
                tbDateFrom.type(plan.getCreationDate());
                break;
            case DATETO:
                tbDateTo.type(plan.getCreationDate());
                break;
            case CASEMANAGER:
                selectSecondaryCaseManager(managerFirstName, managerLastName);
                break;
            default:
                info("Try to enter some correct date");
                break;
        }
    }

    /**
     * Validate all search criteria one by one
     *
     * @param plan - individual employment plan
     */
    public void validateOneCriteria(IndividualEmploymentPlan plan) {
        String participantName, planCreated, planDescription, caseManager;
        int flag = 0;
        lnkDatePlanCreatedSort.clickAndWait();
        if (TablePaginationForm.isPresent()) {
            new TablePaginationForm().openLastPage();
        }

        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 1; i < Constants.RECORDS_ON_PAGE; i++) {
            participantName = resultsForm.getRecordText(i, "2");
            planCreated = resultsForm.getRecordText(i, "3");
            planDescription = resultsForm.getRecordText(i, "4");
            caseManager = resultsForm.getRecordText(i, "5");
            if (participantName.contains(plan.getParticipant().getFirstName())) {
                CustomAssertion.softAssertContains(participantName,
                        plan.getParticipant().getFirstName() + " " + plan.getParticipant().getLastName(),
                        "Incorrect participant first and last names");
                CustomAssertion.softAssertEquals(planCreated, plan.getCreationDate().trim(),
                        "Incorrect date plan created.");
                CustomAssertion.softAssertEquals(planDescription, plan.getPlanDescription().trim(),
                        "Incorrect plan description");
                CustomAssertion.softAssertEquals(caseManager,
                        managerFirstName + " " + managerLastName,
                        "Incorrect manager first and last names");
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            fatal("The requested record was not found");
        }
        clickButton(Buttons.Clear);
    }
}
