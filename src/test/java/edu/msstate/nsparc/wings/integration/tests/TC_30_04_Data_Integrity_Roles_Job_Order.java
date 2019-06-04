package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityJobOrderReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check data integrity job order report for different user roles.
 * Created by a.vnuchko on 27.10.2016.
 */

@TestCase(id = "WINGS-11156")
public class TC_30_04_Data_Integrity_Roles_Job_Order extends BaseWingsTest {
    JobOrder jobOrder;

    public void main() {
        jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.ADMIN);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        EmployerSqlFunctions.setInvalidJobOrderCreationDate(jobOrder);

        //Roles - manager
        User user = new User(Roles.AREADIRECTOR);
        jobOrderReportSteps(user);

        //Roles - staff
        user.setNewUser(Roles.STAFF);
        jobOrderReportSteps(user);

        //Roles - LVER
        user.setNewUser(Roles.LVER);
        jobOrderReportSteps(user);

        //Roles - administrator
        user.setNewUser(Roles.ADMIN);
        jobOrderReportSteps(user);

        //Roles - area director
        user.setNewUser(Roles.MANAGER);
        jobOrderReportSteps(user);

        //Roles - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        jobOrderReportSteps(user);

        //Roles - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        jobOrderReportSteps(user);

        //Roles - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        jobOrderReportSteps(user);

        //Roles - everify administrator
        user.setNewUser(Roles.EVERIFY);
        jobOrderReportSteps(user);

        //Roles - WIOA administrator
        user.setNewUser(Roles.WIOA);
        jobOrderReportSteps(user);

        //Roles - project code administrator
        user.setNewUser(Roles.PCADMIN);
        jobOrderReportSteps(user);

        //Roles - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN);
        jobOrderReportSteps(user);

        //Roles - executive
        user.setNewUser(Roles.EXECUTIVE);
        jobOrderReportSteps(user);

        //Roles - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        jobOrderReportSteps(user);

        //Roles - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        jobOrderReportSteps(user);
    }

    /**
     * Check data integrity job order steps.
     * @param user - current user
     */
    private void jobOrderReportSteps(User user) {
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can make data integrity job order report
        if (user.getDataIntegrityReport().getDgJobOrderReport()) {
            logStep("Check data integrity referral report");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_JOB_ORDER_REPORT);
            DataIntegrityJobOrderReportForm orderPage = new DataIntegrityJobOrderReportForm();
            orderPage.checkInvalidDates();

            logStep("Search");
            orderPage.clickButton(Buttons.Search);

            logStep("Validate search results");
            orderPage.validateEmployerSearchResults(jobOrder.getEmployer());
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_DI_JOB_ORDER_REPORT, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
