package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchCreateForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10734")
public class TC_11_04_Job_Search_County_Displaying extends BaseWingsTest {

    private static final String ALABAMA_LOCATION_STATE = "Alabama";
    private static final String MISSISSIPPI_LOCATION_STATE = "Mississippi";

    public void main() {


        logStep("Log in as Staff and open Job Search creation page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Create);

        logStep("Select any state except Mississippi: County drop-down isn't displayed");
        JobSearchCreateForm jobSearchCreateForm = new JobSearchCreateForm();
        jobSearchCreateForm.selectLocationState(ALABAMA_LOCATION_STATE);
        jobSearchCreateForm.checkLocationCountyPresent(Constants.FALSE);

        logStep("Select a state = Mississippi");
        jobSearchCreateForm.selectLocationState(MISSISSIPPI_LOCATION_STATE);
        jobSearchCreateForm.checkLocationCountyPresent(Constants.TRUE);
    }
}
