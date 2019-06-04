package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the job order for an area director.
 * Created by a.vnuchko on 31.08.2016.
 */

@TestCase(id = "WINGS-11140")
public class TC_29_08_Job_Order_Roles_Area_Director extends TC_29_07_Job_Order_Roles_Admin {

    public void main() {
        String restrictedFname = "area";
        String restrictedLname = "Director";
        User user = new User(Roles.AREADIRECTOR);
        user.getOrder().setJobRestrictedFName(restrictedFname);
        user.getOrder().setJobRestrictedLName(restrictedLname);
        commonStepsJobOrder(user);
    }
}
