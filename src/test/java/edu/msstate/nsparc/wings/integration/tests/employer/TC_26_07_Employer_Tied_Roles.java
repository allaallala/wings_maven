package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check user permissions for the [Employer] module. For those roles, who care if an employer tied or not to Trade or Petition. Check if it is tied.
 * (Area director, Manager, Staff, Intake staff, Trade administrator, Rapid response administrator, WIOA administrator, LVER]
 * Created by a.vnuchko on 12.07.2016.
 */

@TestCase(id = "WINGS-11097")
public class TC_26_07_Employer_Tied_Roles extends TC_26_06_Employer_Neutral_Roles {

    public void main() {

        //Role - area director
        User user = new User(Roles.AREADIRECTOR);
        commonEmployerSteps(user, true);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonEmployerSteps(user, true);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonEmployerSteps(user, true);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonEmployerSteps(user, true);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonEmployerSteps(user, true);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonEmployerSteps(user, true);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonEmployerSteps(user, true);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonEmployerSteps(user, true);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEmployerSteps(user, true); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9171
    }
}
