package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchCreateForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10455")
public class TC_11_01_Job_Search_Full_Workflow extends BaseWingsTest {


    public void main() {
        JobSearch jobSearch = new JobSearch();
        TradeEnrollmentSteps.createTradeEnrollment(jobSearch.getTradeEnrollment(), Roles.ADMIN);

        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Participants - Trade - Job Search - Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Fill out the creation form");
        JobSearchCreateForm creationForm = new JobSearchCreateForm();
        creationForm.fillOutCreationForm(jobSearch);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        JobSearchDetailsForm detailsForm = new JobSearchDetailsForm();
        detailsForm.validateInformation(jobSearch);

        logStep("Navigate to Participants - Trade - Job Search - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Job Search record and open it");
        JobSearchSearchForm searchForm = new JobSearchSearchForm();
        searchForm.performSearchAndOpen(jobSearch);

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        jobSearch.setApproved(true);
        jobSearch.setStatusDeterminationDate(CommonFunctions.getCurrentDate());
        JobSearchEditForm editForm = new JobSearchEditForm();
        editForm.editDetails(jobSearch);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(jobSearch);
    }
}

