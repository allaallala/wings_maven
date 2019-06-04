package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check data integrity Trade enrollment report for different user roles.
 * Created by a.vnuchko on 28.10.2016.
 */

@TestCase(id = "WINGS-11158")
public class TC_30_06_Data_Integrity_Roles_Trade extends BaseWingsTest {

    public void main(){

        //Roles - administrator
        User user = new User(Roles.ADMIN);
        tradeReportSteps(user);

        //Roles - manager
        user.setNewUser(Roles.MANAGER);
        tradeReportSteps(user);

        //Roles - staff
        user.setNewUser(Roles.STAFF);
        tradeReportSteps(user);

        //Roles - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        tradeReportSteps(user);

        //Roles - area director
        user.setNewUser(Roles.AREADIRECTOR);
        tradeReportSteps(user);

        //Roles - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        tradeReportSteps(user);

        //Roles - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        tradeReportSteps(user);

        //Roles - everify administrator
        user.setNewUser(Roles.EVERIFY);
        tradeReportSteps(user);

        //Roles - WIOA administrator
        user.setNewUser(Roles.WIOA);
        tradeReportSteps(user);

        //Roles - project code administrator
        user.setNewUser(Roles.PCADMIN);
        tradeReportSteps(user);

        //Roles - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN);
        tradeReportSteps(user);

        //Roles - LVER
        user.setNewUser(Roles.LVER);
        tradeReportSteps(user);

        //Roles - executive
        user.setNewUser(Roles.EXECUTIVE);
        tradeReportSteps(user);

        //Roles - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        tradeReportSteps(user);

        //Roles - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        tradeReportSteps(user);
    }
    private void tradeReportSteps(User user){
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can make data integrity Trade enrollment report
        if(user.getDataIntegrityReport().getDgWiaReport()){
            //TODO
        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.R_DI_TRADE_ENROLLMENT_REPORT, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
