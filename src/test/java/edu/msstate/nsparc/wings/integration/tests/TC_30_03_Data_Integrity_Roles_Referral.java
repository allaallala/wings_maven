package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityReferralReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check data integrity referral report for different user roles
 * Created by a.vnuchko on 20.10.2016.
 */

@TestCase(id = "WINGS-11155")
public class TC_30_03_Data_Integrity_Roles_Referral extends TC_11_58_Data_Integrity_Referral {
    Participant participant;
    JobOrder order;
    private String date = CommonFunctions.getCurrentDate();

    public void main() {
        participant = new Participant(AccountUtils.getParticipantAccount());
        order = new JobOrder(AccountUtils.getEmployerAccount());
        User admin = new User(Roles.ADMIN);
        ReferralSteps.createReferral(participant, order, admin);
        ParticipantSqlFunctions.setInvalidReferralCreationDate(participant);

        //Roles - staff
        User user = new User(Roles.AREADIRECTOR);
        referralReportSteps(user);

        //Roles - administrator
        user.setNewUser(Roles.ADMIN);
        referralReportSteps(user);

        //Roles - manager
        user.setNewUser(Roles.MANAGER);
        referralReportSteps(user);

        //Roles - staff
        user.setNewUser(Roles.STAFF);
        referralReportSteps(user);

        //Roles - intake staff
        user.setNewUser(Roles.INTAKESTAFF); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - rapid response administrator
        user.setNewUser(Roles.RRADMIN); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - everify administrator
        user.setNewUser(Roles.EVERIFY); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - WIOA administrator
        user.setNewUser(Roles.WIOA); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - project code administrator
        user.setNewUser(Roles.PCADMIN); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - LVER
        user.setNewUser(Roles.LVER); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - executive
        user.setNewUser(Roles.EXECUTIVE); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - LWDA staff
        user.setNewUser(Roles.LWDASTAFF); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);

        //Roles - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER); //TODO issue https://jira.nsparc.msstate.edu/browse/WINGS-9222
        referralReportSteps(user);
    }

    /**
     * Common steps for checking employer report of the data integrity reports
     *
     * @param user - current user
     */
    private void referralReportSteps(User user) {
        String dateTypes = "Creation Date";
        BaseWingsSteps.logInAs(user.getRole());

        //(!) if user can create data integrity referral report
        if (user.getDataIntegrityReport().getDgReferralReport()) {
            logStep("Check data integrity referral report");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_REFERRAL_REPORT);
            DataIntegrityReferralReportForm referralReportForm = new DataIntegrityReferralReportForm();
            referralReportForm.inputDateFrom(date);
            referralReportForm.checkInvalidDates();
            referralReportForm.selectDateTypes(dateTypes);

            logStep("Search");
            referralReportForm.clickButton(Buttons.Search);
            referralReportForm.validateParticipantSearchResults(participant);
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_DI_REFERRAL_REPORT, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
