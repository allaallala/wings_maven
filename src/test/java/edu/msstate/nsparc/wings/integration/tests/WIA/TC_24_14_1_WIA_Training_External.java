package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check WIA Training functionality for external user roles - LWDA Staff, WIOA Provider
 * Created by a.vnuchko on 29.06.2016.
 */

@TestCase(id = "WINGS-11073")
public class TC_24_14_1_WIA_Training_External extends TC_24_13_WIA_Training_Roles_Admin_AD_Manager {

    public void main() {

        //Role - LWDA Staff
        User user = new User(Roles.LWDASTAFF);
        commonWiaTrainingSteps(user);

        //Role - WIOA Provider
        user = new User(Roles.WIOAPROVIDER);
        commonWiaTrainingSteps(user);
    }
}
