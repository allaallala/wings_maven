package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check the functionality of the Assessment module. User roles: all internal roles, except administrator, area director, manager, staff.
 * Created by a.vnuchko on 24.06.2016.
 */

@TestCase(id = "WINGS-10967")
public class TC_19_22_Roles_Others_Internal extends TC_19_21_Roles_Admin_AD_Manager_Staff{

    public void main(){

        //Role Trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonAssmSteps(user);

        //Role WIOA administrator
        //ISSUE WINGS-10134
        user.setNewUser(Roles.WIOA);
        commonAssmSteps(user);

        //Role WIOA administrator PLUS
        //ISSUE WINGS-10134
        user.setNewUser(Roles.WIOAPLUS);
        commonAssmSteps(user);

        //Role Project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonAssmSteps(user);

        //Role DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonAssmSteps(user);

        //Role LVER
        user.setNewUser(Roles.LVER);
        commonAssmSteps(user);

        //Role Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonAssmSteps(user);

        //Role Intake Staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonAssmSteps(user);

        //Role Rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonAssmSteps(user);

        //Role Everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonAssmSteps(user);
    }
}
