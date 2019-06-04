package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check e-verify functionality for specified user role - all, except administrator, manager, staff.
 * Created by a.vnuchko on 03.05.2016.
 */

@TestCase(id = "WINGS-11086")
public class TC_25_09_Everify_Roles_Others extends TC_25_08_Everify_Roles_Admin_Manager_Staff {

    public void main() {

        //Role - area director
        User user = new User(Roles.AREADIRECTOR);
        commonEverifySteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEverifySteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonEverifySteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonEverifySteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonEverifySteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonEverifySteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonEverifySteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonEverifySteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonEverifySteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEverifySteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonEverifySteps(user);

        user.setNewUser(Roles.LWDASTAFF);
        commonEverifySteps(user);

        user.setNewUser(Roles.WIOAPROVIDER);
        commonEverifySteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonEverifySteps(user);
    }
}
