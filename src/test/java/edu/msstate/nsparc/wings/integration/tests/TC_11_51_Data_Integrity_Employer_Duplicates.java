package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityEmployerReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


// Author: d.poznyak
@TestCase(id = "WINGS-10758")
public class TC_11_51_Data_Integrity_Employer_Duplicates extends BaseWingsTest {

    public void main() {
        info("We need to create 2 Employers with same MS Account");
        String employerAccount = AccountUtils.getEmployerAccount();
        Employer employer1 = new Employer(employerAccount);
        EmployerSteps.createEmployer(employer1, Roles.STAFF);
        AdvancedSqlFunctions.resetAccount(employerAccount);
        Employer employer2 = new Employer(employerAccount);
        EmployerSteps.createEmployer(employer2, Roles.STAFF);
        EmployerSqlFunctions.setSameEmployerAccount(employer1, employerAccount);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Reports->Data integrity->Employer report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_EMPLOYER_REPORT);

        logStep("Select Duplicates records type");
        DataIntegrityEmployerReportForm dataIntegrityEmployerReportForm = new DataIntegrityEmployerReportForm();
        dataIntegrityEmployerReportForm.selectDuplicatesRecords("Unemployment Services System Usernames");

        logStep("Search");
        dataIntegrityEmployerReportForm.inputCompanyName(employer1.getCompanyName());
        dataIntegrityEmployerReportForm.clickButton(Buttons.Search);

        logStep("Validate search results for first Employer");
        dataIntegrityEmployerReportForm.validateEmployerSearchResultsByColumn(employer1, "1");

        logStep("Validate search results for second Employer");
        dataIntegrityEmployerReportForm.inputCompanyName(employer2.getCompanyName());
        dataIntegrityEmployerReportForm.clickButton(Buttons.Search);
        dataIntegrityEmployerReportForm.validateEmployerSearchResultsByColumn(employer2, "1");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
