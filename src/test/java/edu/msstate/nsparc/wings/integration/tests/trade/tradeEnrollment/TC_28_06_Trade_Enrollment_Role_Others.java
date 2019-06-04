package edu.msstate.nsparc.wings.integration.tests.trade.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this test, we are checking trade enrollment functionality for a trade administrator, rapid response administrator, everify administrator, WIOA administrator,
 * project code administrator, DVOP veteran, EXECUTIVE, LWDA staff, WIOA provider.
 * Created by a.vnuchko on 24.08.2016.
 */

@TestCase(id = "WINGS-11126")
public class TC_28_06_Trade_Enrollment_Role_Others extends TC_28_01_Trade_Enrollment_Role_Administrator {

    public void main() {

        User user = new User(Roles.TRADEDIRECTOR);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.EXECUTIVE);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.LWDASTAFF);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.WIOAPROVIDER);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.RRADMIN);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.EVERIFY);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.WIOA);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.WIOAPLUS);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.PCADMIN);
        commonTradeEnrollmentSteps(user);

        user.setNewUser(Roles.DVOPVETERAN);
        commonTradeEnrollmentSteps(user);
    }
}
