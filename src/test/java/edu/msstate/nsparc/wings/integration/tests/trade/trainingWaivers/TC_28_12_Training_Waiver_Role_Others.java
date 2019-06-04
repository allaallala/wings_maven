package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check different functionality of the training waiver (Roles: trade administrator, rapid response administrator, everify administrator,  WIOA administrator,
 * project code administrator, DVOP veteran, LVER, executive, LWDA staff, WIOA provider)
 * Created by a.vnuchko on 26.08.2016.
 */

@TestCase(id = "WINGS-11132")
public class TC_28_12_Training_Waiver_Role_Others extends TC_28_07_Training_Waiver_Role_Administrator {

    public void main() {

        User user = new User(Roles.TRADEDIRECTOR);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.LWDASTAFF);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.WIOAPROVIDER);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.RRADMIN);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.EVERIFY);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.WIOA);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.WIOAPLUS);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.PCADMIN);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.DVOPVETERAN);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.LVER);
        commonTrainingWaiverSteps(user);

        user.setNewUser(Roles.EXECUTIVE);
        commonTrainingWaiverSteps(user);
    }
}
