package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityEmployerReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Checked data integrity employer report for different user roles
 * Created by a.vnuchko on 20.10.2016.
 */

@TestCase(id = "WINGS-11154")
public class TC_30_02_Data_Integrity_Roles_Employer extends TC_11_50_Data_Integrity_Employer_Service_Enrollments {
    Employer employer;

    public void main(){
        info("We need to create Employer Service Enrollment");
        employer = new Employer(AccountUtils.getEmployerAccount());
        ServiceSteps.createEmployerServiceEnrollment(employer, SERVICE_NAME);
        AdvancedSqlFunctions.changeServiceEnrollmentDate(employer);

        //Role - staff
        User user = new User(Roles.STAFF);
        employerReportSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        employerReportSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        employerReportSteps(user);

        //Role - trade director
        user.setNewUser(Roles.TRADEDIRECTOR);
        employerReportSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        employerReportSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        employerReportSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        employerReportSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9215
        employerReportSteps(user);

        //Role - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN);
        employerReportSteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        employerReportSteps(user);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        employerReportSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        employerReportSteps(user);

        //Role - lver
        user.setNewUser(Roles.LVER);
        employerReportSteps(user);

        //Role - administrator
        user.setNewUser(Roles.ADMIN);
        employerReportSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        employerReportSteps(user);
    }

    /**
     * Common steps for checking employer report of the data integrity reports
     * @param user - current user
     */
    private void employerReportSteps(User user) {
        logStep("Check for data integrity participant report, if it is available");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) if user can create data integrity employer report
        if (user.getDataIntegrityReport().getDgEmployerReport()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_EMPLOYER_REPORT);
            DataIntegrityEmployerReportForm dataIntegrityEmployerReportForm = new DataIntegrityEmployerReportForm();
            dataIntegrityEmployerReportForm.selectServiceEnrollments(serviceEnrollmentType);

            logStep("Search");
            dataIntegrityEmployerReportForm.inputCompanyName(employer.getCompanyName());
            dataIntegrityEmployerReportForm.searchServiceEnrollment();

            logStep("Validate search results");
            dataIntegrityEmployerReportForm.validateEmployerSearchResults(employer);

            divideMessage("Search");
            dataIntegrityEmployerReportForm.searchServiceEnrollment();
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_DI_EMPLOYER_REPORT, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
