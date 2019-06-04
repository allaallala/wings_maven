package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check the functionality of the [Assessment] module for external user roles (LWDA Staff and WIOA Provider)
 * Created by a.vnuchko on 24.06.2016.
 */

@TestCase(id = "WINGS-10968")
public class TC_19_23_Roles_External extends TC_19_21_Roles_Admin_AD_Manager_Staff {

    public void main() {

        //Role Intake Staff
        User user = new User(Roles.WIOAPROVIDER);
        commonAssmSteps(user);

        //Role Intake Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonAssmSteps(user);
    }
}
