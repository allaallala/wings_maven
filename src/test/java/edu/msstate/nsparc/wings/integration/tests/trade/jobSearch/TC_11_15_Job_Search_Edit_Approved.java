package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Create some job searches, edit status to APPROVED and save changes. Check it.
 * Created by a.vnuchko on 28.01.2016.
 */

@TestCase(id = "WINGS-10746")
public class TC_11_15_Job_Search_Edit_Approved extends BaseWingsTest {
    private static final Boolean APPROVED = true;
    private String expectedApproved = "Approved";
    private String expectedDenied = "Denied";

    public void main() {
        editJobSearchSave(APPROVED);
    }

    /**
     * Make some tests steps
     *
     * @param approved - true, if job search APPROVED.
     */
    protected void editJobSearchSave(Boolean approved) {
        String logStatus;
        JobSearch jobSearch = new JobSearch();
        jobSearch.setApproved(approved);
        jobSearch.setStatusDeterminationDate(CommonFunctions.getYesterdayDate());
        if (approved) {
            logStatus = expectedApproved;
        } else {
            logStatus = expectedDenied;
        }

        TradeEnrollmentSteps.createJobSearch(jobSearch, Roles.ADMIN);

        logStep("Log in as Admin and open Job Search participantSSDetails page");
        JobOrderSteps.openJobSearchDetailPage(jobSearch, Roles.ADMIN);

        logStep("Click the [Edit] button");
        JobSearchDetailsForm detailsPage = new JobSearchDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Choose value " + logStatus + " in the field 'Status'");
        JobSearchEditForm editPage = new JobSearchEditForm();

        logStep("Enter data into another required fields");
        editPage.editDetails(jobSearch);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult(String.format("The Job Search Detail Screen is shown. The changes are saved. Status of Job Search is "
                + "changed to '%1$s'", logStatus));
        Assert.assertTrue(detailsPage.getStatusTextPage().contains(logStatus));
    }
}
