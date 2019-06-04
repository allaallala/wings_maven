package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchCreateForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10735")
public class TC_11_05_Job_Search_Interview_Date_Validation extends BaseWingsTest {

    private static final String VALIDATION_INTERVIEW_DATE_MESSAGE = "Interview Date must be no more than 30 days after Application Date.";

    public void main() {


        logStep("Log in as Staff and open Job Search creation page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Create);

        logStep("Enter current date in Application date field");
        JobSearchCreateForm jobSearchCreateForm = new JobSearchCreateForm();
        jobSearchCreateForm.inputApplicationDate(CommonFunctions.getCurrentDate());

        logStep(" In the Interview date field enter date = application date + 31 days");
        jobSearchCreateForm.inputInterviewDate(CommonFunctions.getFutureDate(Constants.DAYS_MONTH+1));

        logStep("Click the Create button");
        jobSearchCreateForm.clickButton(Buttons.Create);

        logStep("Validation Interview Date message is displayed");
        jobSearchCreateForm.validateErrorMessage(VALIDATION_INTERVIEW_DATE_MESSAGE);
    }
}
