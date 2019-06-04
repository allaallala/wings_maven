package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.*;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check Big Rock Reports for different user roles
 * Created by a.vnuchko on 19.10.2016.
 */

@TestCase(id = "WINGS-11152")
public class TC_29_20_BigRock_Reports_Roles extends BaseWingsTest {
    private String reportDate = CommonFunctions.getCurrentDate();
    private static final String PARTICIPANT_HEADER = "Big Rocks - Participant Report";
    private static final String WIA_HEADER = "Big Rocks - WIA Report";
    private static final String EMPLOYER_HEADER = "Big Rocks - Employer Report";
    private static final String JOB_ORDER_HEADER = "Big Rocks - Job Order Report";
    private static final String SERVICES_HEADER = "Big Rocks - Services Report";
    private static final String REFERRAL_HEADER = "Big Rocks - Referral Report";


    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonSteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonSteps(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonSteps(user);
    }

    /**
     * Check reports for different user roles
     * @param user - current user
     */
    private void commonSteps(User user){
        BaseWingsSteps.logInAs(user.getRole());

        logStep("Check, that is possible to create Big Rock Report");
        if(user.getBgReport().getCreate()){
            divideMessage("Create report for "+PARTICIPANT_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_PARTICIPANT_REPORT);
            doTheSameSteps(new BigRocksParticipantReportCreationForm(), PARTICIPANT_HEADER);

            divideMessage("Create report for "+WIA_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_WIA_REPORT);
            doTheSameSteps(new BigRocksWIAReportForm(), WIA_HEADER);

            divideMessage("Create report for "+EMPLOYER_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_EMPLOYER_REPORT);
            doTheSameSteps(new BigRocksEmployerReportCreationForm(), EMPLOYER_HEADER);

            divideMessage("Create report for "+JOB_ORDER_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_JOB_ORDER_REPORT);
            doTheSameSteps(new BigRocksJobOrderReportCreationForm(), JOB_ORDER_HEADER);

            divideMessage("Create report for "+SERVICES_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_SERVICES_REPORT);
            doTheSameSteps(new BigRocksServicesReportCreationForm(), SERVICES_HEADER);

            divideMessage("Create report for "+REFERRAL_HEADER+" type");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_REFERRAL_REPORT);
            doTheSameSteps(new BigRocksReferralsReportCreationForm(), REFERRAL_HEADER);


        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_PARTICIPANT_REPORT, Constants.ZERO);
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_WIA_REPORT, Constants.ZERO);
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_EMPLOYER_REPORT, Constants.ZERO);
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_JOB_ORDER_REPORT, Constants.ZERO);
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_SERVICES_REPORT, Constants.ZERO);
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_BR_REFERRAL_REPORT, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    private void doTheSameSteps(BigRocksReportBaseForm page, String headTitle){
        page.checkHead(headTitle);
        page.inputStartDate(reportDate);
        page.inputEndDate(reportDate);
        page.checkCompleteCreation();
        page.checkHead(headTitle);
    }
}
