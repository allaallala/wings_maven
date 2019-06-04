package edu.msstate.nsparc.wings.integration.tests.trade.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * In this test, we are checking trade enrollment functionality for staff
 * Created by a.vnuchko on 24.08.2016.
 */

@TestCase(id = "WINGS-11124")
public class TC_28_04_Trade_Enrollment_Role_Staff extends TC_28_01_Trade_Enrollment_Role_Administrator{

    public void main(){
        User user = new User(Roles.STAFF);
        commonTradeEnrollmentSteps(user);
    }
}
