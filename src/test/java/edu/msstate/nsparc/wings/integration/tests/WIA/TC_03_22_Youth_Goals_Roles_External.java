package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the youth goals for external user roles.
 * Created by a.vnuchko on 29.06.2016.
 */

@TestCase(id = "WINGS-10577")
public class TC_03_22_Youth_Goals_Roles_External extends TC_03_20_Youth_Goals_Roles_Admin_AD_Manager {

    public void main() {
        //Role - WIOA provider
        User user = new User(Roles.WIOAPROVIDER);
        commonYouthGoalsSteps(user);

        //Role - LWDA Staff
        user = new User(Roles.LWDASTAFF);
        commonYouthGoalsSteps(user);
    }
}
