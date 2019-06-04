package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the job order for staff.
 * Created by a.vnuchko on 31.08.2016.
 */

@TestCase(id = "WINGS-11142")
public class TC_29_10_Job_Order_Roles_Staff extends TC_29_07_Job_Order_Roles_Admin {

    public void main() {
        User user = new User(Roles.STAFF);
        commonStepsJobOrder(user);
    }
}
