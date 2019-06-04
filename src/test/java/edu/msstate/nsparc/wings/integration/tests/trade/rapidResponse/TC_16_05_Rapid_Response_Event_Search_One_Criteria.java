package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 18.06.2015.
 * In this case we check Rapid Response Event Search using only one parameter (criteria).
 */

@TestCase(id = "WINGS-10879")
public class TC_16_05_Rapid_Response_Event_Search_One_Criteria extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some rapid response event");
        RapidResponseEvent event = new RapidResponseEvent();
        EmployerSteps.createRapidResponseEvent(event, Roles.RRADMIN);

        logStep("Log into the system as admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Employers -> Rapid  Response Events");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.EMPLOYEER);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.WORKFORCE);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.NOTIFICATION_FROM);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.NOTIFICATION_TO);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.IMPACT_FROM);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.IMPACT_TO);
        performSearchAndValidate(event, RapidResponseEventSearchForm.RRE_CRITERIA.NAICS);
    }

    /**
     * Method for searching and validating rapid response events.
     * @param event - RREvent
     * @param searchType - search criteria.
     */
    private void performSearchAndValidate(RapidResponseEvent event, RapidResponseEventSearchForm.RRE_CRITERIA searchType){
        logStep("Fill out any search criteria field with the data of existing Rapid  Response Event (all the fields one by one)- "+searchType);
        RapidResponseEventSearchForm eventSearchPage = new RapidResponseEventSearchForm();
        eventSearchPage.performSearchCriteria(event, searchType);

        logResult("The suitable Rapid  Response Events are shown in the Search Results form");
        eventSearchPage.validateSearchResult(event);
    }
}
