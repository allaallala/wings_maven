package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check Relocation functionality using different roles - all, except administrator, area director, manager.
 * Created by a.vnuchko on 04.03.2016.
 */

@TestCase(id = "WINGS-11077")
public class TC_24_17_Relocation_Roles_Others extends TC_24_16_Relocation_Roles_Admin_AD_Manager {

    public void main(){

        //Role - staff
        User user = new User(Roles.STAFF);
        commonRelocationSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonRelocationSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonRelocationSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonRelocationSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonRelocationSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonRelocationSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonRelocationSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonRelocationSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonRelocationSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonRelocationSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonRelocationSteps(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonRelocationSteps(user);

        //Role - WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonRelocationSteps(user);
    }
}
