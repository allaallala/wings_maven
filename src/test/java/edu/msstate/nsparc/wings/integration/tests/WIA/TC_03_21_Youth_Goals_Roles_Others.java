package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the youth goals for all roles except administrator, area director and manager.
 * Created by a.vnuchko on 18.02.2016.
 */

@TestCase(id = "WINGS-10576")
public class TC_03_21_Youth_Goals_Roles_Others extends TC_03_20_Youth_Goals_Roles_Admin_AD_Manager {

    public void main(){
        //Role Staff
        User user = new User(Roles.STAFF);
        commonYouthGoalsSteps(user);

        //Role Intake Staff
        user = new User(Roles.INTAKESTAFF);
        commonYouthGoalsSteps(user);

        //Role Trade Administrator
        user = new User(Roles.TRADEDIRECTOR);
        commonYouthGoalsSteps(user);

        //Role Rapid Response Administrator
        user = new User(Roles.RRADMIN);
        commonYouthGoalsSteps(user);

        //Role E-verify Administrator
        user = new User(Roles.EVERIFY);
        commonYouthGoalsSteps(user);

        //Role WIOA Administrator
        user = new User(Roles.WIOA);
        commonYouthGoalsSteps(user);

        //Role Project Code Administrator
        user = new User(Roles.PCADMIN);
        commonYouthGoalsSteps(user);

        //Role LVER
        user = new User(Roles.LVER);
        commonYouthGoalsSteps(user);

        //Role Executive
        user = new User(Roles.EXECUTIVE);
        commonYouthGoalsSteps(user);

        //Role DVOP
        user = new User(Roles.DVOP);
        commonYouthGoalsSteps(user);
    }
}
