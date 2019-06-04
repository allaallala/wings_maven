package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the individual employment plan for MDES manager, MDES staff and Trade administrator
 * Created by a.vnuchko on 03.02.2016.
 */

@TestCase(id = "WINGS-11059")
public class TC_24_02_IEP_Role_Manager_Staff_TA extends TC_24_01_IEP_Role_Admin_AD {

    public void main() {

        //Role Manager
        User user = new User(Roles.MANAGER);
        commonSteps(user);

        //Role Staff
        user.setNewUser(Roles.STAFF);
        commonSteps(user);

        //Role Trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonSteps(user);
    }
}
