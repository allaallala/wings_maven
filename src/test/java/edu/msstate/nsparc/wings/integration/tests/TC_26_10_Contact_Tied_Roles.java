package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the Contact module for different user roles. Check only employers that are tied to the Trade or Petition
 * (Area Director, Manager, Staff, Intake Staff, Trade Administrator, Rapid Response Administrator, LVER)
 * Created by a.vnuchko on 13.07.2016.
 */

@TestCase(id = "WINGS-11100")
public class TC_26_10_Contact_Tied_Roles extends TC_26_09_Contact_Not_Tied_Roles {

    public void main() {

        //Role - trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonContactSteps(user, true);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonContactSteps(user, true);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonContactSteps(user, true);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonContactSteps(user, true);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonContactSteps(user, true);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonContactSteps(user, true);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonContactSteps(user, false);
    }
}
