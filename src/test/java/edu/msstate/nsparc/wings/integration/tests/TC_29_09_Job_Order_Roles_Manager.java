package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the job order for a manager
 * Created by a.vnuchko on 31.08.2016.
 */

@TestCase(id = "WINGS-11141")
public class TC_29_09_Job_Order_Roles_Manager extends TC_29_07_Job_Order_Roles_Admin {

    public void main() {
        String restrictedFname = "QA";
        String restrictedLname = "Manager";
        User user = new User(Roles.MANAGER);
        user.getOrder().setJobRestrictedFName(restrictedFname);
        user.getOrder().setJobRestrictedLName(restrictedLname);
        commonStepsJobOrder(user);
    }
}
