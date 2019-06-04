package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check different functionality of the training waiver (Role - staff)
 * Created by a.vnuchko on 26.08.2016.
 */

@TestCase(id = "WINGS-11130")
public class TC_28_10_Training_Waiver_Role_Staff extends TC_28_07_Training_Waiver_Role_Administrator {

    public void main(){
        User user = new User(Roles.STAFF);
        commonTrainingWaiverSteps(user);
    }
}
