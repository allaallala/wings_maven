package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10744")
public class TC_11_13_Job_Search_Verified_Print extends BaseWingsTest {

    private static final String COMPANY_NAME = CommonFunctions.getRandomLiteralCode(10);

    public void main() {

        JobSearch jobSearch = new JobSearch();
        jobSearch.setCompanyName(COMPANY_NAME);
        jobSearch.setApproved(Constants.FALSE);
        TradeEnrollmentSteps.createJobSearchWithStatus(jobSearch);

        logStep("Log in as Admin and open Job Search participantSSDetails page");
        JobOrderSteps.openJobSearchDetailPage(jobSearch, Roles.ADMIN);

        logStep("Expand 'Denials' section");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.expandDenials();

        logStep("Press 'Print Letter' button for the Denial");
        denialSectionForm.print();

        logStep("Check that image 'Verified' is appeared");
        denialSectionForm.checkImageVerified();
    }
}
