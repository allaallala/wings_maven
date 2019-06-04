package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check WIA Enrollment functionality for external user roles - LWDA Staff, WIOA Provider.
 * Created by a.vnuchko on 29.06.2016.
 */

@TestCase(id = "WINGS-11070")
public class TC_24_12_01_WIA_Enrollment_Roles_External extends TC_24_11_WIA_Enrollment_Roles_Admin_AD_Manager {

    public void main() {

        //Role - WIOA Provider
        User user = new User(Roles.WIOAPROVIDER);
        commonWiaEnrollmentSteps(user);

        //Role - LWDA Staff
        user = new User(Roles.LWDASTAFF);
        commonWiaEnrollmentSteps(user);
    }
}
