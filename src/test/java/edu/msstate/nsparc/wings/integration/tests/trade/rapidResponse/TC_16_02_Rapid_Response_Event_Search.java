package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10876")
public class TC_16_02_Rapid_Response_Event_Search extends BaseWingsTest {

    public void main(){

        Employer emp = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(emp, Roles.STAFF);

        logStep("Log into the system as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Employers -> Rapid Response Events");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        RapidResponseEventSearchForm responseSearchForm = new RapidResponseEventSearchForm();
        RapidResponseEvent event = new RapidResponseEvent(emp);
        responseSearchForm.inputDateFromTo(CommonFunctions.getCurrentDate(), CommonFunctions.getCurrentDate());
        responseSearchForm.inputEventNumber(event.getDescription());
        responseSearchForm.clickButton(Buttons.Search);

        logStep("Click the [Clear Form] button");
        responseSearchForm.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        responseSearchForm.checkFieldsCleared();
    }
}
