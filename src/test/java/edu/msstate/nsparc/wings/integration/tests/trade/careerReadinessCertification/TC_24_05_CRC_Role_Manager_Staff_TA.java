package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the career readiness certification for manager, staff and trade administrator roles.
 * Created by a.vnuchko on 11.02.2016.
 */

@TestCase(id = "WINGS-11064")
public class TC_24_05_CRC_Role_Manager_Staff_TA extends TC_24_04_CRC_Role_Admin_AD_DVOPVet {

    public void main(){

        //Role manager
        User user = new User(Roles.MANAGER);
        commonCrcSteps(user);

        //Role staff
        user.setNewUser(Roles.STAFF);
        commonCrcSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonCrcSteps(user);
    }
}
