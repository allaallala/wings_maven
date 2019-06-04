package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check different functionality of the training waiver (Roles: trade administrator, rapid response administrator, everify administrator,  WIOA administrator,
 * project code administrator, DVOP veteran, LVER, executive, LWDA staff, WIOA provider)
 * Created by a.vnuchko on 29.08.2016.
 */

@TestCase(id = "WINGS-11138")
public class TC_29_06_ATAA_RTAA_Roles_Others extends TC_29_01_ATAA_RTAA_Role_Admin {

    public void main(){

        User user = new User(Roles.TRADEDIRECTOR);
        commonAtaaRtaaSteps(user);

        //Rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonAtaaRtaaSteps(user);

        //Everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonAtaaRtaaSteps(user);

        //WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonAtaaRtaaSteps(user);

        //WIOA Plus administrator
        user.setNewUser(Roles.WIOAPLUS);
        commonAtaaRtaaSteps(user);

        //Project Code Administrator
        user.setNewUser(Roles.PCADMIN);
        commonAtaaRtaaSteps(user);

        //DVOP Veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonAtaaRtaaSteps(user);

        //LVER
        user.setNewUser(Roles.LVER);
        commonAtaaRtaaSteps(user);

        //Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonAtaaRtaaSteps(user);

        //LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonAtaaRtaaSteps(user);

        //WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonAtaaRtaaSteps(user);
    }
}
