package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the individual employment plan for intake staff, rapid response administrator, e-verify administrator, wioa administrator, DVOP veteran, LVER, executive.
 * Created by a.vnuchko on 04.02.2016.
 */

@TestCase(id = "WINGS-11061")
public class TC_24_03_IEP_Role_Others extends TC_24_01_IEP_Role_Admin_AD {

    public void main() {

        //Intake Staff
        User user = new User(Roles.INTAKESTAFF);
        commonSteps(user);

        //Role - PC administrator
        user.setNewUser(Roles.PCADMIN);
        commonSteps(user); //

        //Role - E-verify administrator
        user.setNewUser(Roles.EVERIFY);
        commonSteps(user);

        //Role - Rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonSteps(user);

        //DVOP Veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonSteps(user);
    }
}
