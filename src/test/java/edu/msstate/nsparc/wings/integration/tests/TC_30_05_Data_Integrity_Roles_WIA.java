package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityWiaReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check data integrity WIA enrollment report for different user roles.
 * Created by a.vnuchko on 27.10.2016.
 */

@TestCase(id = "WINGS-11157")
public class TC_30_05_Data_Integrity_Roles_WIA extends BaseWingsTest {
    Participant participant;

    public void main() {
        participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Constants.FALSE, Constants.FALSE);
        ParticipantSqlFunctions.setInvalidWia(participant);

        //Roles - administrator
        User user = new User(Roles.ADMIN);
        wiaReportSteps(user);

        //Roles - manager
        user.setNewUser(Roles.MANAGER);
        wiaReportSteps(user);

        //Roles - staff
        user.setNewUser(Roles.STAFF);
        wiaReportSteps(user);

        //Roles - area director
        user.setNewUser(Roles.AREADIRECTOR); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - intake staff
        user.setNewUser(Roles.INTAKESTAFF); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - rapid response administrator
        user.setNewUser(Roles.RRADMIN); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - everify administrator
        user.setNewUser(Roles.EVERIFY); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - WIOA administrator
        user.setNewUser(Roles.WIOA); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - project code administrator
        user.setNewUser(Roles.PCADMIN); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - LVER
        user.setNewUser(Roles.LVER); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - executive
        user.setNewUser(Roles.EXECUTIVE); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - LWDA staff
        user.setNewUser(Roles.LWDASTAFF); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);

        //Roles - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER); //issue https://jira.nsparc.msstate.edu/browse/WINGS-9225
        wiaReportSteps(user);
    }

    private void wiaReportSteps(User user) {
        String dataType = "Application Date";
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can make data integrity WIA enrollment report
        if (user.getDataIntegrityReport().getDgWiaReport()) {
            logStep("Check data integrity WIA enrollment report");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_WIA_ENROLLMENT_REPORT);
            DataIntegrityWiaReportForm wiaPage = new DataIntegrityWiaReportForm();
            wiaPage.checkInvalidDates();
            wiaPage.selectInvalidData(dataType);

            divideMessage("Search");
            wiaPage.clickButton(Buttons.Search);
            wiaPage.validateParticipantSearchResults(participant);
        } else {
            new DataIntegrityWiaReportForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_DI_WIA_ENROLLMENT_REPORT, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
