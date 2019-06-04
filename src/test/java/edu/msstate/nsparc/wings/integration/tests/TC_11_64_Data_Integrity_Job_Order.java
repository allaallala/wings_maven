package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityJobOrderReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


// Author: d.poznyak

@TestCase(id = "WINGS-10760")
public class TC_11_64_Data_Integrity_Job_Order extends BaseWingsTest {

    public void main() {
        info("We need to perform Job Order creation and set invalid Creation date");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        EmployerSqlFunctions.setInvalidJobOrderCreationDate(jobOrder);

        logStep("Login to the System");
        LoginForm login = new LoginForm();
        login.loginStaff();
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep("Reports->Data integrity->Job Order report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_JOB_ORDER_REPORT);

        logStep("Select Invalid Dates");
        DataIntegrityJobOrderReportForm dataIntegrityJobOrderReportForm = new DataIntegrityJobOrderReportForm();
        dataIntegrityJobOrderReportForm.checkInvalidDates();

        logStep("Search");
        dataIntegrityJobOrderReportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        dataIntegrityJobOrderReportForm.validateEmployerSearchResults(jobOrder.getEmployer());

        BaseNavigationSteps.logout();
    }
}
