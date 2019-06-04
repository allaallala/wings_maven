package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityEmployerReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


// Author: d.poznyak

@TestCase(id = "WINGS-10757")
public class TC_11_50_Data_Integrity_Employer_Service_Enrollments extends BaseWingsTest {

    protected static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    protected String serviceEnrollmentType = "Invalid Service Enrollment Dates";

    public void main() {
        info("We need to create Employer Service Enrollment");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        ServiceSteps.createEmployerServiceEnrollment(employer, SERVICE_NAME);
        AdvancedSqlFunctions.changeServiceEnrollmentDate(employer);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Reports->Data integrity->Employer report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_EMPLOYER_REPORT);

        logStep("Select Service Enrollments records type");
        DataIntegrityEmployerReportForm dataIntegrityEmployerReportForm = new DataIntegrityEmployerReportForm();
        dataIntegrityEmployerReportForm.selectServiceEnrollments(serviceEnrollmentType);

        logStep("Search");
        dataIntegrityEmployerReportForm.inputCompanyName(employer.getCompanyName());
        dataIntegrityEmployerReportForm.searchServiceEnrollment();

        logStep("Validate search results");
        dataIntegrityEmployerReportForm.validateEmployerSearchResultsByColumn(employer, "1");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
