package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create job search, open its participantSSDetails page, edit some parameters but DO NOT SAVE (Cancel edition). Check, that data is not changed.
 * Created by a.vnuchko on 18.11.2015.
 */

@TestCase(id = "WINGS-10745")
public class TC_11_14_Job_Search_Edit_Cancel extends BaseWingsTest {
    private String companyName = "A" + CommonFunctions.getTimestamp();

    public void main() {

        info("Precondition:  create some Job Searches");
        JobSearch jobSearch = new JobSearch();
        TradeEnrollmentSteps.createJobSearch(jobSearch, Roles.ADMIN);

        JobOrderSteps.openJobSearchDetailPage(jobSearch, Roles.ADMIN);

        logStep("Click the [Edit] button");
        JobSearchDetailsForm detailsPage = new JobSearchDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        JobSearchEditForm editPage = new JobSearchEditForm();
        editPage.editCompanyName(companyName);

        logStep("Click the [Cancel] button");
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The Job Search Detail Screen is shown, the changes are not saved");
        detailsPage.validateInformation(jobSearch);
    }
}
