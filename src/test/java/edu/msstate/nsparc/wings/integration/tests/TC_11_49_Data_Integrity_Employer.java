package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityEmployerReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


// Author: d.poznyak

@TestCase(id = "WINGS-10756")
public class TC_11_49_Data_Integrity_Employer extends BaseWingsTest {

    public void main() {
        info("We need to perform Employer registration");
        String employerAccount = AccountUtils.getEmployerAccount();
        Employer employer = new Employer(employerAccount);
        info("Edit Employer by Staff");
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        employer.setCompanyName(EmployerSteps.editEmployerName(employer));
        info("And reset access username");
        info(employerAccount);
        AdvancedSqlFunctions.resetAccount(employerAccount);
        BaseNavigationSteps.logout();

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Reports->Data integrity->Employer report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_EMPLOYER_REPORT);

        logStep("Select unfinished records type");
        DataIntegrityEmployerReportForm dataIntegrityEmployerReportForm = new DataIntegrityEmployerReportForm();
        dataIntegrityEmployerReportForm.checkUnfinishedRecords();
        dataIntegrityEmployerReportForm.selectUnfinished("No Unemployment Services System Username");

        logStep("Search");
        dataIntegrityEmployerReportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        dataIntegrityEmployerReportForm.validateEmployerSearchResultsByColumn(employer, "1");

        BaseNavigationSteps.logout();
    }
}
