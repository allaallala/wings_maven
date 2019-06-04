package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the individual employment plan for external user roles.
 * Created by a.vnuchko on 22.06.2016.
 */

@TestCase(id = "WINGS-11060")
public class TC_24_03_01_IEP_Others_External extends TC_24_01_IEP_Role_Admin_AD {

    public void main() {

        //Role LWDA Staff
        User user = new User(Roles.LWDASTAFF);
        commonSteps(user);

        //Role WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonSteps(user);
    }

}
