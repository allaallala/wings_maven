package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check different functionality of the ATAA/RTAA enrollment, role - Intake staff.
 * Created by a.vnuchko on 29.08.2016.
 */

@TestCase(id = "WINGS-11137")
public class TC_29_05_ATAA_RTAA_Role_Intake_Staff extends TC_29_01_ATAA_RTAA_Role_Admin {

    public void main(){
        User user = new User(Roles.INTAKESTAFF);
        commonAtaaRtaaSteps(user);
    }
}
