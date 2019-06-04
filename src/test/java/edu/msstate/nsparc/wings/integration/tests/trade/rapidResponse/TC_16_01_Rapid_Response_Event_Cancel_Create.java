package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventCreationForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10875")
public class TC_16_01_Rapid_Response_Event_Cancel_Create extends BaseWingsTest {

    public void main(){


        Employer emp = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(emp, Roles.STAFF);

        logStep("Log in to the system and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.RRADMIN, WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS, Popup.Create);

        logStep("Fill out some fields");
        RapidResponseEventCreationForm rrecf = new RapidResponseEventCreationForm();
        RapidResponseEvent event = new RapidResponseEvent(emp);
        rrecf.fillOutCreationForm(event);

        logStep("Click the [Cancel] button");
        rrecf.clickButton(Buttons.Cancel);
        rrecf.areYouSure(Popup.Yes);

        logStep("The Staff Home screen is shown. ");
        //new StaffHomeForm().assertIsOpen();

        logResult("Check the new event isn't created ");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);
        BaseWingsSteps.popClick(Popup.Search);
        RapidResponseEventSearchForm searchForm = new RapidResponseEventSearchForm();
        searchForm.performSearch(event);
        searchForm.noSearchResults();
    }

}
