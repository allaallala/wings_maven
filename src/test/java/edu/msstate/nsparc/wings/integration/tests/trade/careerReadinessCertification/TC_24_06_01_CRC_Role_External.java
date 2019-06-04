package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the career readiness certification for external user roles.
 * Created by a.vnuchko on 22.06.2016.
 */

@TestCase(id = "WINGS-11065")
public class TC_24_06_01_CRC_Role_External extends TC_24_04_CRC_Role_Admin_AD_DVOPVet {

    public void main() {

        //Role LWDA Staff
        User user = new User(Roles.LWDASTAFF);
        commonCrcSteps(user);

        //Role WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonCrcSteps(user);
    }
}
