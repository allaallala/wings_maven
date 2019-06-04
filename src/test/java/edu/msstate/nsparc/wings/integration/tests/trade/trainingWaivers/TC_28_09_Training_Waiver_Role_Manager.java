package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Check different functionality of the training waiver (Role - manager)
 * Created by a.vnuchko on 26.08.2016.
 */

@TestCase(id = "WINGS-11129")
public class TC_28_09_Training_Waiver_Role_Manager extends TC_28_07_Training_Waiver_Role_Administrator {

    public void main(){
        User user = new User(Roles.MANAGER);
        commonTrainingWaiverSteps(user);
    }
}
