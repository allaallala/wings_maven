package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityParticipantReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Checked data integrity participant report for different user roles
 * Created by a.vnuchko on 20.10.2016.
 */

@TestCase(id = "WINGS-11153")
public class TC_30_01_Data_Integrity_Roles_Participant extends TC_11_42_Data_Integrity_Participant_Service_Enrollments {
    Participant participant;

    public void main() {
        info("We need to create Participant Service Enrollment and Employer Service Enrollment");
        participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, Constants.FALSE, Constants.FALSE);
        AdvancedSqlFunctions.changeServiceEnrollmentDate(participant, dateChange);

        //Role - administrator
        User user = new User(Roles.ADMIN);
        participantReportSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        participantReportSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        participantReportSteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        participantReportSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        participantReportSteps(user);

        //Role - trade director
        user.setNewUser(Roles.TRADEDIRECTOR);
        participantReportSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        participantReportSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        participantReportSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        participantReportSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        participantReportSteps(user);

        //Role - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN);
        participantReportSteps(user);

        //Role - lver
        user.setNewUser(Roles.LVER);
        participantReportSteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        participantReportSteps(user);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        participantReportSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        participantReportSteps(user);
    }

    /**
     * Common steps for checking participant report of the data integrity reports
     * @param user - current user
     */
    private void participantReportSteps(User user) {
        logStep("Check for data integrity participant report, if it is available");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) if user can create data integrity participant report
        if (user.getDataIntegrityReport().getDgParticipantReport()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_PARTICIPANT_REPORT);
            DataIntegrityParticipantReportForm dataIntegrityParticipantReportForm = new DataIntegrityParticipantReportForm();
            dataIntegrityParticipantReportForm.selectServiceEnrollments(serviceEnrollmentDates);

            divideMessage("Search");
            dataIntegrityParticipantReportForm.searchServiceEnrollment();
        } else {
            WingsTopMenu.WingsStaffMenuItem.R_DI_PARTICIPANT_REPORT.getMenuItem().assertIsNotPresent();
        }
        BaseNavigationSteps.logout();
    }

}
